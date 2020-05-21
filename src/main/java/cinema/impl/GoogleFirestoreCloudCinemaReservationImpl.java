package cinema.impl;

import cinema.CinemaReservation;
import cinema.ICinemaReservation;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import common.Constants;
import common.Statistic;
import common.StatisticService;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GoogleFirestoreCloudCinemaReservationImpl implements ICinemaReservation {

    private final StatisticService statisticService = new StatisticService();

    @Override
    public Long add(CinemaReservation cinemaReservation) {
        Firestore db = FirestoreClient.getFirestore();
        long reservationNumber = ThreadLocalRandom.current().nextLong(1, 10000000);
        DocumentReference reservation = db.collection(Constants.COLLECTION_NAME).document(String.valueOf(reservationNumber));
        cinemaReservation.setReservationNumber(reservationNumber);
        reservation.set(cinemaReservation);
        return reservationNumber;
    }

    @Override
    public void update(Long reservationNumber, Date neDate) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(Constants.COLLECTION_NAME).document(String.valueOf(reservationNumber));
        ApiFuture<DocumentSnapshot> reservation = documentReference.get();
        CinemaReservation cinemaReservation = null;
        try {
            cinemaReservation = reservation.get().toObject(CinemaReservation.class);
            cinemaReservation.setDate(neDate);
            documentReference.set(cinemaReservation);
        } catch (Exception e) {
        }

    }

    @Override
    public void remove(Long reservationNumber) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(Constants.COLLECTION_NAME).document(String.valueOf(reservationNumber)).delete();
    }

    @Override
    public Optional<Collection<CinemaReservation>> findByQuery(String[] query) {
        Firestore db = FirestoreClient.getFirestore();
        try {
            List<CinemaReservation> cinemaReservations = db.collection(Constants.COLLECTION_NAME).whereEqualTo("name", query[0]).whereEqualTo("lastName", query[1]).get().get().toObjects(CinemaReservation.class);
            return Optional.of(cinemaReservations);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CinemaReservation> getById(Long reservationNumber) {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> reservation = db.collection(Constants.COLLECTION_NAME).document(String.valueOf(reservationNumber)).get();
        CinemaReservation cinemaReservation = null;
        try {
            cinemaReservation = reservation.get().toObject(CinemaReservation.class);
            return Optional.ofNullable(cinemaReservation);
        } catch (Exception e) {
            return Optional.ofNullable(cinemaReservation);
        }

    }

    @Override
    public Collection<CinemaReservation> getAll() {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = db.collection(Constants.COLLECTION_NAME).get();
        try {
            return getAll(querySnapshotApiFuture);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<CinemaReservation> getAll(ApiFuture<QuerySnapshot> querySnapshotApiFuture) throws InterruptedException, java.util.concurrent.ExecutionException {
        return querySnapshotApiFuture.get().getDocuments().stream()
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(CinemaReservation.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isReservationExists(Long reservationNumber) {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> reservation = db.collection(Constants.COLLECTION_NAME).document(String.valueOf(reservationNumber)).get();
        try {
            CinemaReservation cinemaReservation = reservation.get().toObject(CinemaReservation.class);
            return Objects.nonNull(cinemaReservation);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Statistic reservationStatistic() {
        Collection<CinemaReservation> cinemaReservations = getAll();
        return statisticService.getStatistic(cinemaReservations);
    }


}
