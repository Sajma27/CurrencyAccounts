package com.currency.accounts.api;

import com.currency.accounts.model.dto.NewAccountDto;
import com.currency.accounts.model.entity.Account;
import com.currency.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/{pesel}")
    public @ResponseBody Account get(@PathVariable String pesel) {
        return this.accountService.getAccount(pesel);
    }

    @RequestMapping(value = "/addNew", method = RequestMethod.POST)
    public void addNewAccount(@RequestBody NewAccountDto newAccountDto) {
        try {
            this.accountService.addNewAccount(newAccountDto);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @RequestMapping("/exchange/pln/usd/{pesel}/{amount}")
    public void exchangePlnToUsd(@PathVariable String pesel, @PathVariable double amount) {
        try {
            this.accountService.exchangePlnToUsd(pesel, amount);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @RequestMapping("/exchange/usd/pln/{pesel}/{amount}")
    public void exchangeUsdToPln(@PathVariable String pesel, @PathVariable double amount) {
        try {
            this.accountService.exchangeUsdToPln(pesel, amount);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
