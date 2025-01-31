import com.apartmentrental.controllers.UserController;
import com.apartmentrental.controllers.ApartmentController;
import com.apartmentrental.controllers.RentalController;
import com.apartmentrental.models.User;

import java.util.Scanner;

public class Mainn {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();
        ApartmentController apartmentController = new ApartmentController();
        RentalController rentalController = new RentalController();

        userController.start();

        User loggedInUser = userController.getLoggedInUser();
        if (loggedInUser == null) {
            System.out.println("Программа завершена.");
            return;
        }

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Посмотреть доступные квартиры");
            System.out.println("2. Найти квартиру по названию");
            System.out.println("3. Арендовать квартиру");
            System.out.println("4. Просмотр аренды");
            System.out.println("5. Выход");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> apartmentController.listApartments();
                case 2 -> apartmentController.searchApartmentByName();
                case 3 -> rentalController.rentApartment(loggedInUser.getId());
                case 4 -> rentalController.viewUserRentals(loggedInUser.getId());
                case 5 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}
