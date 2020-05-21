package menu;


import cinema.CinemaReservation;
import cinema.ICinemaReservation;
import cinema.impl.GoogleFirestoreCloudCinemaReservationImpl;
import cinema.impl.MongoDBCinemaReservationImpl;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import common.Constants;
import common.InputService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;

public class Menu {

    private ICinemaReservation iCinemaReservation;
    private String squadName = null;
    private final InputService inputService = new InputService();


    public int start() throws ParseException {
        int databaseChoice = inputService.getInt("Wybierz skład dokumentów którego chcesz użyć:\n[1]MongoDB\n[2]Google cloud firestore");
        if (databaseChoice == 1) {
            setupMongo();
        } else if (databaseChoice == 2) {
            setupGoogleCloudFirestore();
        }

        while (true) {
            switch (inputService.getOption(squadName)) {

                case 1:
                    String name = inputService.readString("Podaj swoje imie:");
                    String lastName = inputService.readString("Podaj nazwisko:");
                    String phoneNumber = inputService.readString("Podaj numer telefonu");
                    String movie = inputService.readString("Jakiego filmu dotyczy rezerwacja:");
                    int numberOfPeople = inputService.getInt("Podaj liczbe osób:");
                    Date date = inputService.getDate();


                    Long reservationNumber = iCinemaReservation.add(new CinemaReservation(name, lastName, phoneNumber, movie, numberOfPeople, date));
                    System.out.println("Dodano nową rezerwacje, numer Twojej rezerwacji to:" + reservationNumber);
                    break;
                case 2:
                    reservationNumber = inputService.getId();
                    if (iCinemaReservation.isReservationExists(reservationNumber)) {
                        Date newDate = inputService.getDate();
                        iCinemaReservation.update(reservationNumber, newDate);
                    } else {
                        System.out.println("Rezerwacja filmu o id " + reservationNumber + " nie istnieje!");
                    }
                    break;
                case 3:
                    reservationNumber = inputService.getId();
                    if (iCinemaReservation.isReservationExists(reservationNumber)) {
                        iCinemaReservation.remove(reservationNumber);
                    } else {
                        System.out.println("Rezerwacja filmu o id " + reservationNumber + " nie istnieje!");
                    }
                    break;
                case 4:
                    reservationNumber = inputService.getId();
                    iCinemaReservation.getById(reservationNumber).ifPresent(System.out::println);
                    break;
                case 5:
                    iCinemaReservation.getAll().forEach(System.out::println);
                    break;
                case 6:
                    name = inputService.readString("Podaj imię, po którym chcesz wyszukać:");
                    lastName = inputService.readString("Podaj nazwisko, po którym chcesz wyszukać:");
                    String[] strings = {name, lastName};
                    iCinemaReservation.findByQuery(strings).ifPresent(System.out::println);
                    break;
                case 7:
                    System.out.println(iCinemaReservation.reservationStatistic().toString());
                    break;
                case 8:
                    System.exit(200);
                    break;
                default:
                    System.out.println("Nie ma takiej opcji");


            }


        }

    }

    private void setupGoogleCloudFirestore() {
        squadName = Constants.GOOGLE;
        iCinemaReservation = new GoogleFirestoreCloudCinemaReservationImpl();
        InputStream serviceAccount;
        GoogleCredentials credentials = null;
        try {
            serviceAccount = new FileInputStream("systemy-rozproszone-081ca1bb343e.json");
            credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setupMongo() {
        iCinemaReservation = new MongoDBCinemaReservationImpl();
        squadName = Constants.MONGO;
    }


}
