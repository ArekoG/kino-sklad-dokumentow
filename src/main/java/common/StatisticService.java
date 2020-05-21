package common;

import cinema.CinemaReservation;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class StatisticService {


    public Statistic getStatistic(Collection<CinemaReservation> cinemaReservations) {
        if (Objects.isNull(cinemaReservations))
            return new Statistic();
        String topMovie = getTopMovie(cinemaReservations);
        double average = getAverage(cinemaReservations);
        Statistic statistic = new Statistic(topMovie, (int) average);
        return statistic;
    }

    private double getAverage(Collection<CinemaReservation> cinemaReservations) {
        OptionalDouble average = cinemaReservations.stream()
                .map(CinemaReservation::getNumberOfPeople)
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(Integer::byteValue)
                .average();

        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

    private String getTopMovie(Collection<CinemaReservation> cinemaReservations) {
        return cinemaReservations.stream()
                .collect(Collectors.groupingBy(CinemaReservation::getMovie, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }

}
