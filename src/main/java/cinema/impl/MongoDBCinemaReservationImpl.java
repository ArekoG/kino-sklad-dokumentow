package cinema.impl;

import cinema.CinemaReservation;
import cinema.ICinemaReservation;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import common.MongoDBConfiguration;
import common.Statistic;
import common.StatisticService;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBCinemaReservationImpl implements ICinemaReservation {

    private final StatisticService statisticService = new StatisticService();

    @Override
    public Long add(CinemaReservation cinemaReservation) {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);
        long reservationNumber = ThreadLocalRandom.current().nextLong(1, 10000000);
        cinemaReservation.setReservationNumber(reservationNumber);
        collection.insertOne(cinemaReservation);
        MongoDBConfiguration.closeConnection();
        return reservationNumber;
    }

    @Override
    public void update(Long id, Date newDate) {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);
        CinemaReservation cinemaReservation = getById(id).get();
        cinemaReservation.setDate(newDate);
        Document document = new Document("reservationNumber", id);
        collection.findOneAndReplace(document, cinemaReservation);
        MongoDBConfiguration.closeConnection();
    }

    @Override
    public void remove(Long reservationNumber) {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);
        Document document = new Document("reservationNumber", reservationNumber);
        collection.deleteOne(document);
        MongoDBConfiguration.closeConnection();

    }

    @Override
    public Optional<Collection<CinemaReservation>> findByQuery(String[] query) {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", query[0]);
        whereQuery.put("lastName", query[1]);
        MongoCursor<CinemaReservation> cinemaReservations = collection.find(whereQuery).iterator();
        Collection<CinemaReservation> result = new ArrayList<>();
        while (cinemaReservations.hasNext()) {
            result.add(cinemaReservations.next());
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<CinemaReservation> getById(Long reservationNumber) {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);

        CinemaReservation cinemaReservation = collection.find(eq("reservationNumber", reservationNumber)).first();
        MongoDBConfiguration.closeConnection();

        return Optional.ofNullable(cinemaReservation);
    }

    @Override
    public Collection<CinemaReservation> getAll() {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);

        MongoCursor<CinemaReservation> iterator = collection.find().iterator();
        Collection<CinemaReservation> cinemaReservations = new ArrayList<>();
        while (iterator.hasNext()) {
            cinemaReservations.add(iterator.next());
        }

        MongoDBConfiguration.closeConnection();
        return cinemaReservations;
    }

    @Override
    public boolean isReservationExists(Long reservationNumber) {
        MongoDatabase connection = MongoDBConfiguration.getConnection();
        MongoCollection<CinemaReservation> collection = connection.getCollection(Constants.COLLECTION_NAME, CinemaReservation.class);

        CinemaReservation cinemaReservation = collection.find(eq("reservationNumber", reservationNumber)).first();

        MongoDBConfiguration.closeConnection();

        return Objects.nonNull(cinemaReservation);
    }

    @Override
    public Statistic reservationStatistic() {
        Collection<CinemaReservation> cinemaReservations = getAll();
        return statisticService.getStatistic(cinemaReservations);
    }
}
