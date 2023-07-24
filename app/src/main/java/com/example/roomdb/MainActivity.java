package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText tv_typehere;
    Button bt_add,bt_reset;
    RecyclerView rv_lists;

    List<MyData> myData=new ArrayList();
    LinearLayoutManager linearLayoutManager;
    RoomDbClass roomDB;
    MainAdapter mainAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_typehere=findViewById(R.id.typehere);
        bt_add=findViewById(R.id.addbtn);
        bt_reset=findViewById(R.id.resetbtn);
        rv_lists=findViewById(R.id.lists);

        //initialize the database
        roomDB= RoomDbClass.getInstance(this);

        myData=roomDB.mainDao().getAll();

        linearLayoutManager=new LinearLayoutManager(this);
        rv_lists.setLayoutManager(linearLayoutManager);
        mainAdapter=new MainAdapter(this,myData);

        rv_lists.setAdapter(mainAdapter);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s=tv_typehere.getText().toString().trim();

                if (!s.equals(""))
                {
                    MyData myData1=new MyData();
                    myData1.setText(s);

                    roomDB.mainDao().insert(myData1);
                    tv_typehere.setText("");
                    myData.clear();
                    myData.addAll(roomDB.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please fill the field...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roomDB.mainDao().reset(myData);
                myData.clear();
                myData.addAll(roomDB.mainDao().getAll());
                mainAdapter.notifyDataSetChanged();            }
        });

    }
}