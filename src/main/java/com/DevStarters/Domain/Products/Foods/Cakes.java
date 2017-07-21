package com.DevStarters.Domain.Products.Foods;

import java.time.LocalDate;
import java.time.Period;

public class Cakes extends Food{
    public Cakes() {
        super();
        setKitchen("Italian");
    }

    public Cakes(String name, double price, String vendor, Period periodOfValidity) {
        super(name, price, vendor, periodOfValidity, "Italian");
    }

    public Cakes(String name, double price, String vendor, LocalDate productionDate, LocalDate expirationDate) {
        super(name, price, vendor, productionDate, expirationDate, "Italian");
    }
}
