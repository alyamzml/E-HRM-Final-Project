package com.emp;

public class Name {

    private String fname;
    private String lname;
    private String fullname;

    // constructor
    public Name(String f, String l){
        this.fname = f;
        this.lname = l;
        setFullname();
    }

    // to concatenate fname and lname into fullname
    public void setFullname(){
        this.fullname = fname + " " + lname;
    }

    // return fullname
    public String getFullname(){
        return fullname;
    }

}
