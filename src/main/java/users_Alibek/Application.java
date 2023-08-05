package users_Alibek;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import users_Alibek.entity.Users;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        System.out.println("- Авторизация [1]");
        System.out.println("- Регистрация [2]");
        System.out.print("Выберите действие: ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        try {
            manager.getTransaction().begin();
            switch (str) {
                case "1" -> {
                    System.out.print("Введите логин: ");
                    String login = scanner.nextLine();
                    TypedQuery<Users> usersTypedQuery = manager.createQuery("select u from Users u where u.login = ?1",
                            Users.class);
                    usersTypedQuery.setParameter(1, login);
                    Users user = usersTypedQuery.getSingleResult();
                    if (user != null) {
                        System.out.print("Введите пароль: ");
                        String password = scanner.nextLine();
                        boolean correctPassword = false;
                        while (!correctPassword) {
                            if (password.equals(user.getPassword())) {
                                correctPassword = true;
                                String userInfo = "Город: %s\n, Имя: %s\n, Фамилия: %s";
                                System.out.printf((userInfo) + "%n", user.getCity().getName(),
                                        user.getName(), user.getSurname());
                            } else {
                                System.out.print("Неверный пароль, попробуйте еще раз: ");
                                password = scanner.nextLine();
                            }
                        }
                    } else {
                        System.out.println("Неверный логин");
                    }
                }
            }
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
