package com.DevStarters.Support;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.DAO.DaoFactory;
import com.DevStarters.Domain.User;
import com.DevStarters.MySql.MySqlDaoFactory;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.Scanner;

public class EditProfile {
    private static final Logger log = Logger.getLogger(Menu.class);
    private static int choose = -1;
    private static Scanner in = new Scanner(System.in);

    public static void edit() {
        do {
            information();
            choose = chooses();
            done(choose);
        } while (choose != 0);
    }

    public static void information() {
        System.out.println("You can:");
        System.out.println("1-Change name");
        System.out.println("2-Change surname");
        System.out.println("3-Change password");
        System.out.println("4-Change address");
        System.out.println("5-Change born date");
        System.out.println("6-Add card");
        System.out.println("7-Delete card");
        System.out.println("0-Back to main menu");
    }

    public static int chooses() {
        int temp = -1;
        do {
            try {
                System.out.print("Enter your choice, please: ");
                temp = Integer.parseInt(in.next());
                if (temp > 7) {
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

    public static void done(int temp) {
        switch (temp) {
            case 1:
                System.out.println("Enter new name: ");
                String name = in.next();
                Session.getCurrentUser().setName(name);
                updateUser();
                break;
            case 2:
                System.out.println("Enter new surname: ");
                String surname = in.next();
                Session.getCurrentUser().setName(surname);
                updateUser();
                break;
            case 3:
                System.out.println("Enter current password: ");
                String pass = in.nextLine();
                if (Session.getCurrentUser().getPassword().equals(pass)) {
                    System.out.println("Enter new password: ");
                    String newPass = in.nextLine();
                    System.out.println("Repeat new password: ");
                    String repeat = in.nextLine();
                    if (newPass.equals(repeat)) {
                        Session.getCurrentUser().setPassword(newPass);
                        updateUser();
                    } else System.out.println("New password isn`t equals with repeat new password");
                } else System.out.println("False current password");
                break;
            case 4:
                System.out.println("Enter new address: ");
                String address = in.nextLine();
                Session.getCurrentUser().setAddress(address);
                updateUser();
                break;
            case 5:
                int year = 0;
                int month = 0;
                int day = 0;
                do {
                    System.out.println("Enter year of born: ");
                    year = in.nextInt();
                } while (year < 1920 || year > LocalDate.now().getYear());
                do {
                    System.out.println("Enter month of born: ");
                    month = in.nextInt();
                } while (month < 1 || month > 12);
                do {
                    System.out.println("Enter day of born: ");
                    day = in.nextInt();
                } while (day < 1 || day > 31);
                Session.getCurrentUser().setBornDate(LocalDate.of(year, month, day));
                updateUser();
                break;
            case 6:
                //TODO:
                break;
            case 7:
                Session.getCurrentUser().deleteAccount();
                break;
            case 0:
                break;
            default:
                break;
        }
    }

    public static void updateUser() {
        try {
            DaoFactory factory = new MySqlDaoFactory();
            AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
            dao.update(Session.getCurrentUser());
        } catch (DaoException e) {
            System.out.println("Error with update" + e.getMessage());
        }
    }
}
