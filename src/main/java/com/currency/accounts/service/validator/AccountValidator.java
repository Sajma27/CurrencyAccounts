package com.currency.accounts.service.validator;

import com.currency.accounts.model.entity.Account;
import com.currency.accounts.model.repository.AccountRepository;
import com.currency.accounts.service.pesel.PeselUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class AccountValidator {

    private AccountRepository accountRepository;

    @Autowired
    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validateNewAccount(Account account) throws Exception {
        this.validateAccount(account);
        LocalDate ownerDateOfBirth = PeselUtils.getDateOfBirth(account.getPesel());
        if (ChronoUnit.YEARS.between(ownerDateOfBirth, LocalDate.now()) < 18) {
            throw new Exception("The account holder must be at least 18 years old");
        }
        Account accountFromDb = this.accountRepository.findByPesel(account.getPesel());
        if (accountFromDb != null) {
            throw new Exception("Account with provided pesel number already present");
        }
    }

    public void validateAccount(Account account) throws Exception {
        if (account == null) {
            throw new Exception("Invalid account");
        }
        if (account.getFirstName() == null || account.getLastName() == null) {
            throw new Exception("Invalid first name or last name");
        }
        if (account.getPesel() == null || account.getPesel().length() != 11) {
            throw new Exception("Invalid pesel");
        }
        if (account.getPlnBalance() != null && account.getPlnBalance() < 0) {
            throw new Exception("Invalid PLN balance");
        }
        if (account.getUsdBalance() != null && account.getUsdBalance() < 0) {
            throw new Exception("Invalid USD balance");
        }
    }

    public void validateAccountMinUsdBalanceAmount(Account account, Double amount) throws Exception {
        if (account.getUsdBalance() < amount) {
            throw new Exception("Account USD balance is too low");
        }
    }

    public void validateAccountMinPlnBalanceAmount(Account account, Double amount) throws Exception {
        if (account.getPlnBalance() < amount) {
            throw new Exception("Account PLN balance is too low");
        }
    }
}
