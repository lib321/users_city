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
                    user.setLogin(login);
                    System.out.print("Введите пароль: ");
                    password = scanner.nextLine();
                    user.setPassword(password);
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    user.setName(name);
                    System.out.print("Введите фамилию: ");
                    String surname = scanner.nextLine();
                    user.setSurname(surname);
                    TypedQuery<City> cityTypedQuery = manager.createQuery("select c from City c", City.class);
                    System.out.println("Выберите город: ");
                    List<City> cities = cityTypedQuery.getResultList();
                    System.out.println("[0] - ввести свой город");
                    for (City city : cities) {
                        System.out.println("[" + city.getId() + "] " + city.getName());
                    }
                    System.out.print("Введите номер: ");
                    String cityId = scanner.nextLine();
                    long id = Integer.parseInt(cityId);
                    if (id != 0) {
                        City city = manager.find(City.class, id);
                        user.setCity(city);
                        manager.persist(user);
                    } else {

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
