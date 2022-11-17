package com.currency.accounts.model.entity;

import com.currency.accounts.model.dto.NewAccountDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true, length = 11)
    private String pesel;

    @Column
    private Double plnBalance;

    @Column
    private Double usdBalance;

    public Account() {

    }

    public Account(NewAccountDto newAccountDto) {
        this.setFirstName(newAccountDto.getFirstName());
        this.setLastName(newAccountDto.getLastName());
        this.setPesel(newAccountDto.getPesel());
        this.setPlnBalance(newAccountDto.getPlnBalance());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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

    public Double getPlnBalance() {
        return plnBalance;
    }

    public void setPlnBalance(Double plnBalance) {
        this.plnBalance = plnBalance;
    }

    public Double getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(Double usdBalance) {
        this.usdBalance = usdBalance;
    }
}
