package common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public int getOption(String squadName) {
        int option = 0;

        do {
            System.out.println("----------------------BIURO TURYSTYCZNE - SKŁAD: " + squadName + "----------------------");
            System.out.println("Wybierz opcje:");
            System.out.println("[1]Dodaj rezerwacje");
            System.out.println("[2]Aktualizuj rezerwacje");
            System.out.println("[3]Usuń rezerwacje");
            System.out.println("[4]Pobierz rezerwacje po id");
            System.out.println("[5]Pobierz liste wszystkich rezerwacji");
            System.out.println("[6]Zaawansowane wyszukiwanie");
            System.out.println("[7]Pobierz statystyki o rezerwacjach");
            System.out.println("[8]Wyjdź");
            option = getInt(option);
        } while (option <= 0);
        return option;
    }

    public Date getDate() throws ParseException {
        String date;
        do {
            System.out.println("Na kiedy chcesz zarezerwować film? Podaj date w formacie YYYY-MM-DD:");
            date = scanner.nextLine();
        } while (date.isEmpty() || !isValid(date));
        return formatter.parse(date);
    }


    public boolean isValid(String dateStr) {
        try {
            formatter.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public int getInt(String message) {
        int numberOfPeople = 0;

        do {
            System.out.println(message);
            numberOfPeople = getInt(numberOfPeople);
        } while (numberOfPeople <= 0);
        return numberOfPeople;
    }

    private int getInt(int numberOfPeople) {
        try {
            numberOfPeople = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
        return numberOfPeople;
    }

    public String readString(String message) {
        String destination;
        do {
            System.out.println(message);
            destination = scanner.nextLine();
        } while (destination.isEmpty());
        return destination;
    }

    public Long getId() {
        System.out.println("Podaj id rezerwacji:");
        while (!scanner.hasNextLong()) {
            System.out.println("Podaj id rezerwacji:");

            scanner.next();
            scanner.nextLine();
        }
        Long numberOfPeople = scanner.nextLong();
        scanner.nextLine();
        return numberOfPeople;
    }


}
