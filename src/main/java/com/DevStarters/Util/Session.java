package com.DevStarters.Util;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.User;
import com.DevStarters.MySql.MySqlDaoFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Session {
    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void login() {
        Scanner in = new Scanner(System.in);
        String login;
        String password;
        ArrayList<User> users = null;
        try {
            MySqlDaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
            users = dao.readAll();
        } catch (DaoException e) {
            System.out.println("Error with connect to DB" + e.getMessage());
        }
        System.out.print("Enter your login: ");
        login = in.nextLine();
        System.out.print("Enter your password: ");
        password = in.nextLine();
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    return;
                } else System.out.println("Incorrect password");
            }
        }
        System.out.println("Account not found. Do you want try again? 1-Login, 2-Register, 0-Exit");
        int choice = Integer.parseInt(in.next());
        if (choice == 1) login();
        if (choice == 2) register();
        if (choice == 0) logOut();
    }

    public static void register() {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try {
            AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
            Scanner in = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = in.nextLine();
            System.out.print("Enter your surname: ");
            String surname = in.nextLine();
            System.out.print("Enter your login: ");
            String login = in.nextLine();
            System.out.print("Enter your password: ");
            String password = in.nextLine();
            System.out.print("Enter your address: ");
            String address = in.nextLine();
            System.out.print("Enter your year of born: ");
            int year = Integer.parseInt(in.next());
            System.out.print("Enter your month of born: ");
            int month = Integer.parseInt(in.next());
            System.out.print("Enter your day of born: ");
            int day = Integer.parseInt(in.next());

            User user = new User(name, surname, login, password, address, year, month, day);
            currentUser = (User) dao.create(user);
        } catch (DaoException e) {
            System.out.println("Error with register" + e.getMessage());
        }
    }

    public static void logOut() {
        currentUser = null;
    }
}
