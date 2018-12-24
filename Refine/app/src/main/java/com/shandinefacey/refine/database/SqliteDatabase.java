package com.shandinefacey.refine.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shandinefacey.refine.Bill;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "bill";
    private	static final String TABLE_BILLS = "bills";

    private static final String COLUMN_ID = "_id";
    private static final	String COLUMN_BILLNAME = "billname";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_DATE = "date";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_BILLS_TABLE = "CREATE	TABLE " + TABLE_BILLS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_BILLNAME + " TEXT," + COLUMN_COST + " FLOAT," + COLUMN_DATE + " TEXT" +")";
        db.execSQL(CREATE_BILLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        onCreate(db);
    }

    public List<Bill> listBills() {
        String sql = "select * from " + TABLE_BILLS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Bill> storeBills = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String billname = cursor.getString(1);
                Float cost = Float.parseFloat(cursor.getString(2));
                String date = cursor.getString(3);

                storeBills.add(new Bill(id, billname, cost,date));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeBills;
    }

    public void addBill(Bill bill){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILLNAME, bill.getName());
        values.put(COLUMN_COST, bill.getCost());
        values.put(COLUMN_DATE,bill.getDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BILLS, null, values);
    }

    public void updateBill(Bill bill){
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILLNAME, bill.getName());
        values.put(COLUMN_COST, bill.getCost());
        values.put(COLUMN_DATE,bill.getDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_BILLS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(bill.getId())});
    }

    public Bill findBill(String name){
        String query = "Select * FROM "	+ TABLE_BILLS + " WHERE " + COLUMN_BILLNAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Bill mBill = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String BillName = cursor.getString(1);
            Float BillCost = Float.parseFloat(cursor.getString(2));
            String date = cursor.getString(3);

            mBill = new Bill(id, BillName, BillCost,date);
        }
        cursor.close();
        return mBill;
    }

    public void deleteBill(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BILLS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
