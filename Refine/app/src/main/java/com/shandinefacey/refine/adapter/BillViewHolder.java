package com.shandinefacey.refine.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.shandinefacey.refine.R;

public class BillViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView deleteBill;
    public  ImageView editBill;

    public BillViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.bill_name);
        deleteBill = (ImageView)itemView.findViewById(R.id.delete_bill);
        editBill = (ImageView)itemView.findViewById(R.id.edit_bill);
    }

}
