package org.example.dao;

import org.example.utils.PropertyUtil;
import org.example.utils.SparkToMongoUtil;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;
public class SparkMongoDao {
    private PropertyUtil propertyUtil;
    private JavaSparkContext jsc;
    public SparkMongoDao(){
        String host = propertyUtil.getProperty("mongodb","host");
        String port = propertyUtil.getProperty("mongodb","port");
        String username = propertyUtil.getProperty("mongodb","username");
        String password = propertyUtil.getProperty("mongodb","password");
        String source = propertyUtil.getProperty("mongodb","source");
        jsc = SparkToMongoUtil
                .getSparkConn(username,password,host,port,source);
    }
    public JavaMongoRDD<Document> readFromMongoDB(
            String database,
            String collection){
        Map<String, String> readOverrides = new HashMap<String, String>();
        readOverrides.put("database", database);
        readOverrides.put("collection", collection);
        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        JavaMongoRDD<Document> customRdd = MongoSpark.load(jsc, readConfig);

        return customRdd;
    }
    public void writeToMongoDB(
            String database,
            String collection,
            JavaRDD<Document> data){
        Map<String, String> writeOverrides = new HashMap<String, String>();
        writeOverrides.put("database", database);
        writeOverrides.put("collection", collection);
        WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);
        MongoSpark.save(data,writeConfig);
    }
    public void close(){
        jsc.close();
    }
}
