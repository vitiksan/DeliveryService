package com.DevStarters.Menu;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.Domain.ChainStore;
import com.DevStarters.Domain.Product;
import com.DevStarters.MySql.MySqlDaoFactory;
import com.DevStarters.Util.Session;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Logger log = Logger.getLogger(Menu.class);
    private static int choose = -1;

    public static void main(String[] args) {
        System.out.println("Welcome to the shop");
        do {
            information();
            choose = chooses();
            done(choose);
        } while (choose != 0);
    }

    private static void information() {
        System.out.println("You can: ");
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
        if (Session.getCurrentUser().getLogin().contentEquals("admin")
                || Session.getCurrentUser().getLogin().contentEquals("Admin")) {
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
                System.out.print("Enter your choice, please: ");
                temp = Integer.parseInt(in.next());
                if ((Session.getCurrentUser().getLogin().contentEquals("admin") && temp > 13)
                        || temp > 9) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Your choice  undefined");
                log.error("Your choice undefined");
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
                EditProfile.edit();
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

    private static void addChainStore() {
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            ChainStore shop = (ChainStore) dao.createWithField(0);
            if (shop == null) throw new DaoException();
        } catch (DaoException e) {
            System.out.println("Error with create chain store: " + e.getMessage());
        }
    }

    private static void addProduct() {
        Scanner in = new Scanner(System.in);
        boolean temp = false;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            ArrayList<ChainStore> shops = dao.readAll();
            dao = factory.getDao(factory.getConnection(), Product.class);
            for (ChainStore item : shops) System.out.println(item.toString());
            System.out.println("Enter chain store`s id you want to add product to: ");
            int id = in.nextInt();
            for (ChainStore item : shops) {
                if (item.getId() == id) {
                    Product product = (Product) dao.createWithField(id);
                    if (product == null) throw new DaoException("Error with create product");
                    temp = true;
                }
            }
            if (!temp) throw new DaoException("Not found chain store");
        } catch (DaoException e) {
            System.out.println("Error with add product: " + e.getMessage());
        }
    }

    private static void deleteChainStore() {
        Scanner in = new Scanner(System.in);
        boolean temp = false;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            ArrayList<ChainStore> shops = dao.readAll();
            for (ChainStore item : shops) System.out.println(item.toString());
            System.out.println("Enter chain store`s id which you want to delete: ");
            int id = in.nextInt();
            for (ChainStore item : shops) {
                if (item.getId() == id) {
                    dao.delete(item);
                    temp = true;
                }
            }
            if (!temp) throw new DaoException("Not found chain store");
        } catch (DaoException e) {
            System.out.println("Error with delete chain store: " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        Scanner in = new Scanner(System.in);
        boolean temp = false;
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), ChainStore.class);
            ArrayList<ChainStore> shops = dao.readAll();
            ArrayList<Product> products = null;
            dao = factory.getDao(factory.getConnection(), Product.class);
            for (ChainStore item : shops) System.out.println(item.toString());
            System.out.println("Enter chain store`s id you want to delete product from: ");
            int id = in.nextInt();
            for (Product item : products) {
                if (item.getVendorId() == id) System.out.println(item.toString());
            }
            System.out.println("Enter product`s id which you want to delete: ");
            int prId = in.nextInt();
            for (Product item : products) {
                if (item.getId() == prId) {
                    dao.delete(item);
                    temp = true;
                }
            }
            if (!temp) throw new DaoException("Not found product");
        } catch (DaoException e) {
            System.out.println("Error with delete product: " + e.getMessage());
        }
    }
}
