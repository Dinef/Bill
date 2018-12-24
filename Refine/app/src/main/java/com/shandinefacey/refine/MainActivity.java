package com.shandinefacey.refine;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.shandinefacey.refine.adapter.billadapter;
import com.shandinefacey.refine.database.SqliteDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView BillView = (RecyclerView)findViewById(R.id.bill_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        BillView.setLayoutManager(linearLayoutManager);
        BillView.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(this);
        List<Bill> allBills = mDatabase.listBills();


        if(allBills.size() > 0){
            BillView.setVisibility(View.VISIBLE);
            billadapter mAdapter = new billadapter(this, allBills);
            BillView.setAdapter(mAdapter);

        }else {
            BillView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no bill in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_bill_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText costField = (EditText)subView.findViewById(R.id.enter_cost);
        final EditText dateField = (EditText)subView.findViewById(R.id.enter_date);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new bill");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD BILL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int cost = Integer.parseInt(costField.getText().toString());
                final String date = dateField.getText().toString();


                if(TextUtils.isEmpty(name) || cost <= 0 || TextUtils.isEmpty(date) ){
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Bill newBill = new Bill(name, cost,date);
                    mDatabase.addBill(newBill);

                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }
}



