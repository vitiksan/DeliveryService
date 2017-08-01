package com.DevStarters.Support;// Created by on 18.07.2017.

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.Domain.ChainStore;
import com.DevStarters.Domain.Product;
import com.DevStarters.MySql.MySqlDaoFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Logger log = Logger.getLogger(Menu.class);
    private static int choose = -1;

    public static void main(String[] args) {
        System.out.println("Welcome to shop");
        do {
            information();
            choose = chooses();
            done(choose);
        } while (choose != 0);
    }

    private static void information() {
        System.out.println("You can:");
        System.out.println("1-Login");
        System.out.println("2-Register");
        if (!Session.getCurrentUser().equals(null)) {
            System.out.println("3-Show all chain stores");
            System.out.println("4-Show all products");
            System.out.println("5-Show all products in concrete chain store");
            System.out.println("6-Create new order");
            System.out.println("7-Add products to order");
            System.out.println("8-Edit profile");
            System.out.println("9-Logout");
        }
        if (Session.getCurrentUser().getLogin().contentEquals("admin")) {
            System.out.println("10-Add new chain store");
            System.out.println("11-Add product to chain store");
            System.out.println("12-Delete product from chain store");
            System.out.println("13-Delete chain store");
        }
        System.out.println("0-Exit");
    }

    private static int chooses() {
        Scanner in = new Scanner(System.in);
        int temp = -1;
        do {
            try {
                System.out.print("Please enter your choices: ");
                temp = Integer.parseInt(in.next());
                if ((Session.getCurrentUser().getLogin().contentEquals("admin") && temp > 13)
                        || temp > 9) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Your choices not defined");
                log.error("Your choices not defined");
                log.error(e.getMessage());
                temp = -1;
            }
        } while (temp < 0);
        return temp;
    }

    private static void done(int temp) {
        switch (temp) {
            case 1:
                Session.login();
                break;
            case 2:
                Session.register();
                break;
            case 3:
                showChainStores();
                break;
            case 4:
                showProducts();
                break;
            case 5:
                showProductsFromChainStore();
                break;
            case 6:
                Session.getCurrentUser().addNewOrder();
                break;
            case 7:
                Session.getCurrentUser().addProductToOrder();
                break;
            case 8:
                editProfile();
                break;
            case 9:
                Session.logOut();
                break;
            case 10:
                addChainStore();
                break;
            case 11:
                addProduct();
                break;
            case 12:
                deleteProduct();
                break;
            case 13:
                deleteChainStore();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private static void showChainStores() {
        ArrayList<ChainStore> shops = null;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            shops = dao.readAll();
            for (ChainStore item : shops) {
                System.out.println(item.toString());
            }
        } catch (DaoException e) {
            System.out.println("Error with read DB");
        }
    }

    private static void showProducts() {
        ArrayList<Product> products = null;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
            products = dao.readAll();
            for (Product item : products) {
                System.out.println(item.toString());
            }
        } catch (DaoException e) {
            System.out.println("Error with read DB");
        }
    }

    private static void showProductsFromChainStore() {
        ArrayList<Product> products = null;
        Scanner in = new Scanner(System.in);
        showChainStores();
        System.out.println("Enter chain store`s id: ");
        int id = in.nextInt();
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), Product.class);
            products = dao.readAll();
            for (Product item : products) {
                if (item.getVendorId() == id)
                    System.out.println(item.toString());
            }
        } catch (DaoException e) {
            System.out.println("Error with read DB");
        }
    }

    private static void editProfile() {

    }

    private static void addChainStore() {

    }

    private static void addProduct() {

    }

    private static void deleteChainStore() {

    }

    private static void deleteProduct() {

    }
}
