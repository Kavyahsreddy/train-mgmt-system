package com.bnpp.wm.actimize.model;

import java.util.Date;

public class Customer {
    public int id;
    public String name;
    public Date dob;

    public Customer(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }
}
