package com.DevStarters.Support;

import java.util.Date;
import java.util.Random;

public class CardNumberGenerator {

    /**
     * @return - генерація 16-значного номера рахунку
     */
    public static String generateVCNumber() {
        String temp = "4";
        Date currentDate = new Date();
        Random random = new Random(currentDate.getTime());
        for (int i = 0; i < 15; i++) {
            temp += String.valueOf(random.nextInt(10));
        }
        return temp;
    }
}
