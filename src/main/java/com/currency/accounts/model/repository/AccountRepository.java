package com.currency.accounts.model.repository;

import com.currency.accounts.model.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByPesel(String pesel);
}
