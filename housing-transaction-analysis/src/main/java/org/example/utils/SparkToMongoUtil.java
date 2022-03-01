package org.example.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.awt.*;

public class SparkToMongoUtil {
    public static JavaSparkContext getSparkConn(
            String username,
            String password,
            String host,
            String port,
            String source){
        SparkConf conf = new SparkConf().setJars(new String[]{"D:\\Work\\IdeaProjects\\MongoProject\\out\\artifacts\\MongoProject_jar\\MongoProject.jar"});
        SparkSession spark = SparkSession.builder()
                .master("spark://primary:7077".toString())
                .appName("MongoSparkConnector")
                .config("spark.serializer","org.apache.spark.serializer.KryoSerializer")
                .config("spark.mongodb.input.uri",
                        "mongodb://"+username+":"
                                +password+"@"
                                +host+":"
                                +port+"/mongoproject.house?authSource="+source)
                .config("spark.mongodb.output.uri",
                        "mongodb://"+username+":"
                                +password+"@"
                                +host+":"
                                +port+"/mongoproject.avgprice?authSource="+source)
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        return jsc;
    }
}
