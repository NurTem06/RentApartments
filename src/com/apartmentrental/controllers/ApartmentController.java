import com.apartmentrental.models.Apartment;
import com.apartmentrental.repositories.ApartmentRepository;

import java.util.List;
import java.util.Scanner;

public class ApartmentController {
    private final ApartmentRepository apartmentRepository = new ApartmentRepository();

    public void listApartments() {
        List<Apartment> apartments = apartmentRepository.getAllApartments();
        if (apartments.isEmpty()) {
            System.out.println("Нет доступных квартир.");
        } else {
            for (Apartment apartment : apartments) {
                System.out.println(apartment.getId() + ". " + apartment.getName() + " - " +
                        apartment.getCity() + ", " + apartment.getDistrict() + ", " + apartment.getStreet() +
                        " | Этаж: " + apartment.getFloor() + " | Комнат: " + apartment.getRooms() +
                        " | Цена за день: " + apartment.getPricePerDay() + " | Цена за месяц: " +
                        apartment.getPricePerMonth() + " | Цена за год: " + apartment.getPricePerYear() +
                        " | Доступность: " + apartment.getStatus());
            }
        }
    }

    public void searchApartmentByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название квартиры: ");
        String name = scanner.nextLine();

        List<Apartment> apartments = apartmentRepository.findApartmentByName(name);
        if (apartments.isEmpty()) {
            System.out.println("Квартира не найдена.");
        } else {
            for (Apartment apartment : apartments) {
                System.out.println(apartment.getId() + ". " + apartment.getName() + " - " +
                        apartment.getCity() + ", " + apartment.getDistrict() + ", " + apartment.getStreet());
            }
        }
    }
}