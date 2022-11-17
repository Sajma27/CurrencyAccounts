package com.currency.accounts.model.dto;

public class NewAccountDto {
    private String firstName;
    private String lastName;
    private String pesel;
    private double plnBalance;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public double getPlnBalance() {
        return plnBalance;
    }

    public void setPlnBalance(double plnBalance) {
        this.plnBalance = plnBalance;
    }
}
