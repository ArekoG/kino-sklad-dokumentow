package cinema;

import common.Statistic;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface ICinemaReservation {
    Long add(CinemaReservation cinemaReservation);

    void update(Long id, Date neDate);

    void remove(Long id);

    Optional<Collection<CinemaReservation>> findByQuery(String[] query);

    Optional<CinemaReservation> getById(Long id);

    Collection<CinemaReservation> getAll();

    boolean isReservationExists(Long id);

    Statistic reservationStatistic();
}
