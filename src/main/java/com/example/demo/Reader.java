package com.example.demo;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import javax.xml.crypto.Data;

public class Reader {

    private Dataset<Row> csvData;

    public Dataset<Row> readData(String path, SparkSession sparkSession) {
        // Get DataFrameReader using SparkSession
        final DataFrameReader dataFrameReader = sparkSession.read ();
        // Set header option to true to specify that first row in file contains
        // name of columns
        dataFrameReader.option ("header", "true");
        final Dataset<Row> csvDataFrame = dataFrameReader.csv (path);
        this.csvData = csvDataFrame;
        return csvDataFrame;
    }

    public void printSchema() {
        csvData.printSchema();
    }

    public void printSummary() {
        System.out.println(csvData.describe());
    }

    public void printHead() {
        csvData.show(10);
    }
}

