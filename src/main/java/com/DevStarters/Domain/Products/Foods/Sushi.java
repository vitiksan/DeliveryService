package com.DevStarters.Domain.Products.Foods;

import java.time.LocalDate;
import java.time.Period;

public class Sushi extends Food {

    public Sushi() {
        super();
        setKitchen("Japanese");
    }

    public Sushi(String name, double price, String vendor, Period periodOfValidity) {
        super(name, price, vendor, periodOfValidity, "Japanese");
    }

    public Sushi(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate) {
        super(name, price, vendor, productionDate, expirationDate, "Japanese");
    }
}
