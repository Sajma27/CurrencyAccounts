package com.currency.accounts.service.pesel;

import java.time.LocalDate;

public class PeselUtils {

    public static LocalDate getDateOfBirth(String pesel) throws Exception {
        if (pesel == null || pesel.length() != 11) {
            return null;
        }
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month >= 1 && month <= 12) {
            year += 1900;
        } else if (month > 20 && month < 80) {
            year += 2000;
            month -= 20;
        } else if (month > 80) {
            year += 1800;
            month -= 80;
        } else {
            throw new Exception("Cannot parse date of birth");
        }

        return LocalDate.of(year, month, day);
    }
}
