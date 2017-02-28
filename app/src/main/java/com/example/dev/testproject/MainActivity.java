package com.example.dev.testproject;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btn_fire;
    DatabaseReference mDatabase;
    String count="";
    EditText et_name, et_age;
    TextView tv_num;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle aToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        btn_fire = (Button) findViewById(R.id.btn_firebase);
        et_name = (EditText)findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        tv_num = (TextView) findViewById(R.id.tv_num);




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
        aToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.rvusers);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = String.valueOf(dataSnapshot.child("users").getChildrenCount());
                tv_num.setText(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        btn_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User  u = new User("Jack",33);
                String userName = et_name.getText().toString();
                int age = Integer.parseInt(et_age.getText().toString());
                User u = new User(userName,age);
                int nextNum = Integer.parseInt(count) + 1;
                String next = String.valueOf(nextNum);

                mDatabase.child("users").child(next).setValue(u);
//                mDatabase.child("users").child("3").setValue(u);




            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(aToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
