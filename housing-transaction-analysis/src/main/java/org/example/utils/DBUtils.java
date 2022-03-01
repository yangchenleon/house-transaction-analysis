package org.example.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.Arrays;

public class DBUtils {
    public static MongoClient getMongoClient(String username, String source
            , String password, String host, String port) {
        MongoCredential credential = MongoCredential
                .createCredential(username, source,
                        password.toCharArray());
        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(
                                        Arrays.asList(
                                                new ServerAddress(
                                                        host,
                                                        Integer.parseInt(port))
                                        )))
                        .credential(credential)
                        .build());
        return mongoClient;
    }
}
