package cinema;

import java.util.Date;

public class CinemaReservation {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String movie;
    private Date date;
    private int numberOfPeople;
    private long reservationNumber;


    public CinemaReservation() {
    }

    public CinemaReservation(String name, String lastName, String phoneNumber, String movie, int numberOfPeople, Date date) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.movie = movie;
        this.date = date;
        this.numberOfPeople = numberOfPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }


    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rezerwacja:{" +
                "Imie='" + name + '\'' +
                ", nazwisko='" + lastName + '\'' +
                ", number telefonu='" + phoneNumber + '\'' +
                ", film='" + movie + '\'' +
                ", data=" + date +
                ", liczba osób=" + numberOfPeople +
                ", numer rezerwacji=" + reservationNumber +
                '}';
    }
}
