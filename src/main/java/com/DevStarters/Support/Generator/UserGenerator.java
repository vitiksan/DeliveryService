package com.DevStarters.Support.Generator;

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
                login += new Character((char) (random.nextInt(57) + 65)).toString();
            }
            String pass = "";
            for (int i = 0; i < 8; i++) {
                pass += new Character((char) (random.nextInt(126))).toString();
            }
            int year = random.nextInt(117) + 1900;
            int month = random.nextInt(11);
            int day = random.nextInt(31);
            User user = new User(readFile("name"), readFile("surname"), login, pass,
                    "вул. " + readFile("surname"), year, month, day);
            users.add((User) dao.create(user));
        }

    }

    public static String readFile(String str) throws FileNotFoundException {
        ArrayList<String> words = new ArrayList<>();

        String inputData = FileReader.loadBook("./src/main/java/com/DevStarters/Support/Generator/" + str + ".txt");
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
