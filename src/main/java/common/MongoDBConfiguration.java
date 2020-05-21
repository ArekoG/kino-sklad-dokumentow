package common;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Objects;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBConfiguration {
    private static MongoClient mongoClient = null;

    public static MongoDatabase getConnection() {
        String user = Constants.USER;
        String password = Constants.PASSWORD;
        String host = Constants.HOST;
        int port = Constants.PORT;
        String database = Constants.DATABASE;

        if (Objects.nonNull(mongoClient)) {
            return mongoClient.getDatabase(database);
        }
        String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
        ConnectionString connectionString = new ConnectionString(clientURI);
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = getMongoClientSettings(connectionString, codecRegistry);
        MongoClient mongoClient = MongoClients.create(clientSettings);
        return mongoClient.getDatabase(database);
    }

    private static MongoClientSettings getMongoClientSettings(ConnectionString connectionString, CodecRegistry codecRegistry) {
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
    }

    public static void closeConnection() {
        if (Objects.nonNull(mongoClient)) {
            mongoClient.close();
        }
    }

}
