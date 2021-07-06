package com.example.demo;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataProcessing {

    public static Dataset<Row> cleanData(Dataset<Row> csvData, SparkSession sparkSession) {
        csvData = csvData.dropDuplicates();
        csvData.createOrReplaceTempView("Jobs");
        csvData = sparkSession.sql("SELECT * FROM Jobs WHERE YearsExp NOT LIKE '%null%'");
        csvData.createOrReplaceTempView("Jobs");
        return csvData;
    }
}
