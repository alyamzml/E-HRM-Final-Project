package com.emp;

public class Payroll2 {
    private double OT, rateOT = 20;
    private double incometax;
    private double epf;
    private double netSalary = 3500;
    private double income, deduct, finalPayroll;

    OverTime duration = new OverTime();

    // to calculate income based on overtime
    public double calcIncome(){
        OT = duration.getTotalTime() * rateOT;
        income = netSalary + OT;
        return income;
    }

    // to calculate deduct based on incocme tax and epf
    public double calcDeduct(){
        incometax = netSalary * 0.03;
        epf = netSalary * 0.11;
        deduct = incometax + epf;
        return deduct;
    }

    // to calculate final payroll
    public void calcPayroll(double income, double deduct){
        finalPayroll = income - deduct;
    }

    // to display income, deduct and final payroll
    public String displayIncome(){
        return ("RM" + income);
    }

    public String displayDeduct(){
        return ("RM" + deduct);
    }

    public String displayTotal(){
        return ("RM" + finalPayroll);
    }
}
