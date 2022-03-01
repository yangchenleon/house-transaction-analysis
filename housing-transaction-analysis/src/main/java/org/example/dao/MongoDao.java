package org.example.dao;

import org.example.utils.DBUtils;
import org.example.utils.PropertyUtil;
import com.mongodb.client.*;
import org.bson.Document;
import java.io.Serializable;
import static com.mongodb.client.model.Filters.*;
public class MongoDao implements Serializable {
    private static final long serialVersionUID = 1L;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;
    private PropertyUtil propertyUtil;
    public MongoDao(String dbName,String collectionName){
        String host = propertyUtil.getProperty("mongodb","host");
        String port = propertyUtil.getProperty("mongodb","port");
        String username = propertyUtil.getProperty("mongodb","username");
        String password = propertyUtil.getProperty("mongodb","password");
        String source = propertyUtil.getProperty("mongodb","source");
        mongoClient =
                DBUtils.getMongoClient(username,source,password,host,port);
        mongoDatabase=mongoClient.getDatabase(dbName);
        collection=mongoDatabase.getCollection(collectionName);
    }
    public void insert(String data){
        Document document = Document.parse(data);
        collection.insertOne(document);
    }
    public FindIterable<Document> findAll(){
        FindIterable<Document> resultAll = collection
                .find(ne("district",""));
        return resultAll;
    }
    public void close(){
        mongoClient.close();
    }
}