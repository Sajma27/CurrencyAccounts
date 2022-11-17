package com.currency.accounts;

import com.currency.accounts.model.dto.NewAccountDto;
import com.currency.accounts.model.entity.Account;
import com.currency.accounts.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CurrencyAccountsApplicationTests {

    private String userPesel = "99010101111";
    private String userFirstName = "Test";
    private String userLastName = "User";

    @Autowired
    private AccountService accountService;

    @Test
    void shouldAddUser() throws Exception {
        NewAccountDto dto = new NewAccountDto();
        dto.setFirstName(userFirstName);
        dto.setLastName(userLastName);
        dto.setPesel(userPesel);
        dto.setPlnBalance(1000);
        this.accountService.addNewAccount(dto);
        Account account = this.accountService.getAccount(userPesel);
        assertNotNull(account);
        assertEquals(account.getPesel(), userPesel);
        assertEquals(account.getFirstName(), userFirstName);
        assertEquals(account.getLastName(), userLastName);
        assertEquals(account.getPlnBalance(), 1000);
    }

    @Test
    void shouldNotAddUserWhenToYoung() {
        NewAccountDto dto = new NewAccountDto();
        dto.setFirstName(userFirstName);
        dto.setLastName(userLastName);
        dto.setPesel("20280122222");
        dto.setPlnBalance(1000);
        assertThrows(Exception.class, () -> this.accountService.addNewAccount(dto));
    }

    @Test
    void shouldNotAllowToSameUsers() {
        NewAccountDto dto = new NewAccountDto();
        dto.setFirstName(userFirstName);
        dto.setLastName(userLastName);
        dto.setPesel(userPesel);
        dto.setPlnBalance(1000);
        assertDoesNotThrow(() -> this.accountService.addNewAccount(dto));
        assertThrows(Exception.class, () -> this.accountService.addNewAccount(dto));
    }

//    TODO more tests for: adding bad users, exchanging

}
