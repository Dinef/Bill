package com.shandinefacey.refine;

import java.util.Date;

public class Bill {
    private	int	id;
    private	String billname;
    private	float cost;
    private String date;

    public Bill(String billname, float cost,String date) {
        this.billname= billname;
        this.cost = cost;
        this.date = date;
    }

    public Bill(int id,String billname, float cost,String date){
        this.id = id;
        this.billname = billname;
        this.cost = cost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return billname;
    }

    public void setName(String name) {
        this.billname = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDate()
    {
        return date;
    }


    public void setDate(String date)
    {
        this.date = date;
    }
}