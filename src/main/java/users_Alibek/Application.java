package users_Alibek;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        System.out.println("- Авторизация [1]");
        System.out.println("- Регистрация [2]");
        System.out.print("Выбреите действие: ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        try {
            manager.getTransaction().begin();
            switch (str) {

            }
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
