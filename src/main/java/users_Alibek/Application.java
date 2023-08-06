package users_Alibek;

import jakarta.persistence.*;
import users_Alibek.entity.City;
import users_Alibek.entity.Users;

import java.util.List;
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
            String login;
            String password;
            switch (str) {
                case "1" -> {
                    boolean correctLogin = false;
                    while (!correctLogin){
                        try {
                            System.out.print("Введите логин: ");
                            login = scanner.nextLine();
                            TypedQuery<Users> usersTypedQuery = manager.createQuery("select u from Users u where u.login = ?1",
                                    Users.class);
                            usersTypedQuery.setParameter(1, login);
                            Users user = usersTypedQuery.getSingleResult();
                            System.out.print("Введите пароль: ");
                            password = scanner.nextLine();
                            boolean correctPassword = false;
                            while (!correctPassword) {
                                if (password.equals(user.getPassword())) {
                                    correctPassword = true;
                                    String userInfo = "Город: %s\nИмя: %s\nФамилия: %s";
                                    System.out.printf((userInfo) + "%n", user.getCity().getName(),
                                            user.getName(), user.getSurname());
                                    correctLogin = true;
                                } else {
                                    System.out.print("Неверный пароль, попробуйте еще раз: ");
                                    password = scanner.nextLine();
                                }
                            }
                        } catch (NoResultException e) {
                            System.out.println("Неправильный логин, попробуйте еще раз");
                        }
                    }
                }
                case "2" -> {
                    System.out.print("Введите логин: ");
                    Users user = new Users();
                    login = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    password = scanner.nextLine();
                    TypedQuery<City> cityTypedQuery = manager.createQuery("select c from City c", City.class);
                    System.out.print("Введите город: ");
                    List<City> cities = cityTypedQuery.getResultList();
                    for (City city : cities) {
                        System.out.println(city.getName());
                    }
                }
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
