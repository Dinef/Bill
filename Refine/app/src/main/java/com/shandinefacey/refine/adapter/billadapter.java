package com.shandinefacey.refine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


        import com.shandinefacey.refine.Bill;
        import com.shandinefacey.refine.R;
        import com.shandinefacey.refine.database.SqliteDatabase;

        import java.util.List;

public class billadapter extends RecyclerView.Adapter<BillViewHolder>{

    private Context context;
    private List<Bill> listBills;

    private SqliteDatabase mDatabase;

    public billadapter(Context context, List<Bill> listBills) {
        this.context = context;
        this.listBills = listBills;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_layout, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        final Bill singleBill = listBills.get(position);

        holder.name.setText(singleBill.getName());

        holder.editBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleBill);
            }
        });

        holder.deleteBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteBill(singleBill.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBills.size();
    }


    private void editTaskDialog(final Bill bill){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_bill_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText costField = (EditText)subView.findViewById(R.id.enter_cost);
        final EditText dateField= (EditText)subView.findViewById(R.id.enter_date);

        if(bill != null){
            nameField.setText(bill.getName());
            costField.setText(String.valueOf(bill.getCost()));
            dateField.setText(bill.getDate());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit bill");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT BILL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int cost = Integer.parseInt(costField.getText().toString());
                final String date = dateField.getText().toString();

                if(TextUtils.isEmpty(name) || cost <= 0 || TextUtils.isEmpty(date)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateBill(new Bill(bill.getId(), name, cost,date));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
