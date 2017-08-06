package com.DevStarters.Util.DBGenerator;

import com.DevStarters.DAO.AbstractDao;
import com.DevStarters.DAO.DaoException;
import com.DevStarters.Domain.User;
import com.DevStarters.MySql.MySqlDaoFactory;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class UserGenerator {
    private static GregorianCalendar calendar = new GregorianCalendar();
    private static Random random = new Random(calendar.getTimeInMillis());

    public static void main(String[] args) throws FileNotFoundException, DaoException {
        HashSet<User> users = new HashSet<>();
        MySqlDaoFactory factory = new MySqlDaoFactory();
        AbstractDao dao = factory.getDao(factory.getConnection(), User.class);
        Scanner in = new Scanner(System.in);
        System.out.print("Enter count of users: ");
        int count = Integer.parseInt(in.next());

        while (count != users.size()) {

            String login = "";
            for (int i = 0; i < random.nextInt(7) + 5; i++) {
                int x = 47;
                do {
                    x = (random.nextInt(124));
                } while (!((x > 64 && x < 91) || (x > 96 && x < 123) || (x > 47 && x < 58)));
                login += new Character((char) x).toString();
            }
            String pass = "";
            for (int i = 0; i < 8; i++) {
                int x = 47;
                do {
                    x = (random.nextInt(124));
                } while (!((x > 64 && x < 91) || (x > 96 && x < 123) || (x > 47 && x < 58)));
                pass += new Character((char) x).toString();
            }
            int year = random.nextInt(117) + 1900;
            int month = 0;
            do{
                month=random.nextInt(12) % 12;
            }while (month<1 || month>12);
            int day=0;
            do{
                day = random.nextInt(31) % 31;
            }while (day<1 || day>31);

            User user = new User(readFile("name"), readFile("surname"), login, pass,
                    "вул. " + readFile("surname"), year, month, day);
            users.add((User) dao.create(user));
        }

    }

    public static String readFile(String str) throws FileNotFoundException {
        ArrayList<String> words = new ArrayList<>();

        String inputData = FileReader.loadBook("./src/main/java/com/DevStarters/Util/DBGenerator/" + str + ".txt");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(byteArrayInputStream);

        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNext()) {
                String word = in.next();
                words.add(word);
            }
        }
        int index;

        do {
            index = random.nextInt();
        } while (index > words.size() - 1 || index < 0);
        return words.get(index);
    }
}
