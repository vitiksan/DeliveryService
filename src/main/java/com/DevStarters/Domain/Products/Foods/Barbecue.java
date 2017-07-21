package com.DevStarters.Domain.Products.Foods;

import java.time.LocalDate;
import java.time.Period;

public class Barbecue extends Food {
    public Barbecue() {
        super();
        setKitchen("American");
    }

    public Barbecue(String name, double price, String vendor, Period periodOfValidity) {
        super(name, price, vendor, periodOfValidity, "American");
    }

    public Barbecue(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate) {
        super(name, price, vendor, productionDate, expirationDate, "American");
    }
}
