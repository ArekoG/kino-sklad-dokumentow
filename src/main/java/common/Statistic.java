package common;

public class Statistic {

    private String topMovie;
    private int averageNumberOfPeople;

    public Statistic() {
    }

    public Statistic(String topMovie, int averageNumberOfPeople) {
        this.topMovie = topMovie;
        this.averageNumberOfPeople = averageNumberOfPeople;
    }

    public String getTopMovie() {
        return topMovie;
    }

    public void setTopMovie(String topMovie) {
        this.topMovie = topMovie;
    }

    public int isAverageNumberOfPeople() {
        return averageNumberOfPeople;
    }

    public void setAverageNumberOfPeople(int averageNumberOfPeople) {
        this.averageNumberOfPeople = averageNumberOfPeople;
    }

    @Override
    public String toString() {
        return "Statystyki{" +
                "Najchętniej wybierany film='" + topMovie + '\'' +
                ", średnia liczba osób na jedną rezerwacje=" + averageNumberOfPeople +
                '}';
    }
}
