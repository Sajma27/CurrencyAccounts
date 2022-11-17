package com.currency.accounts.service;

import com.currency.accounts.model.dto.NewAccountDto;
import com.currency.accounts.model.entity.Account;
import com.currency.accounts.model.repository.AccountRepository;
import com.currency.accounts.service.validator.AccountValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountValidator accountValidator;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          AccountValidator accountValidator) {
        this.accountRepository = accountRepository;
        this.accountValidator = accountValidator;
    }

    public void addNewAccount(NewAccountDto newAccountDto) throws Exception {
        Account account = new Account(newAccountDto);
        this.accountValidator.validateNewAccount(account);
        if (account.getPlnBalance() == null) {
            account.setPlnBalance(0D);
        }
        if (account.getUsdBalance() == null) {
            account.setUsdBalance(0D);
        }
        this.accountRepository.save(account);
    }

    public Account getAccount(String pesel) {
        return this.accountRepository.findByPesel(pesel);
    }

    public void exchangePlnToUsd(String pesel, Double amount) throws Exception {
        Account account = getAccount(pesel);
        this.accountValidator.validateAccount(account);
        this.accountValidator.validateAccountMinPlnBalanceAmount(account, amount);
        double usdSellRate = getSellRateForUsd();
        double plnBalance = account.getPlnBalance();
        account.setPlnBalance(plnBalance - amount);
        double newUsdBalance = account.getUsdBalance() + (amount / usdSellRate);
        account.setUsdBalance(newUsdBalance);
        this.accountRepository.save(account);
    }

    public void exchangeUsdToPln(String pesel, Double amount) throws Exception {
        Account account = getAccount(pesel);
        this.accountValidator.validateAccount(account);
        this.accountValidator.validateAccountMinUsdBalanceAmount(account, amount);
        double usdBuyRate = getBuyRateForUsd();
        double usdBalance = account.getUsdBalance();
        account.setUsdBalance(usdBalance - amount);
        double newPlnBalance = account.getPlnBalance() + (amount * usdBuyRate);
        account.setPlnBalance(newPlnBalance);
        this.accountRepository.save(account);
    }

    private double getSellRateForUsd() throws IOException {
        JsonNode rates = getPlnUsdExchangeRates();
        return rates.get("ask").doubleValue();
    }

    private double getBuyRateForUsd() throws IOException {
        JsonNode rates = getPlnUsdExchangeRates();
        return rates.get("bid").doubleValue();
    }

    private JsonNode getPlnUsdExchangeRates() throws IOException {
        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/c/usd/");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(url).get("rates").get(0);
        } catch (IOException e) {
            throw new IOException("Cannot download exchange rates");
        }
    }
}
