package com.example.demo.controller;

import com.example.demo.Reader;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import scala.collection.Seq;

import javax.xml.crypto.Data;
import java.util.*;

@RestController
public class DemoController {
    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private JavaSparkContext javaSparkContext;

    @Autowired
    private Dataset<Row> csvData;


    @GetMapping("/popular-jobcompanies")
    public Map<String, Integer> popular_jobcompanies() {
        Dataset<Row> data = sparkSession.sql("SELECT company, COUNT(*) as cnt FROM Jobs GROUP BY company ORDER BY cnt DESC");
        Map<String, Integer> rowsMap = new LinkedHashMap<>();
        for(Row r: data.collectAsList()) {
            String first = r.get(0).toString();
            int second = Integer.parseInt(r.get(1).toString());
            rowsMap.put(first, second);
        }
        return rowsMap;
    }

    @GetMapping("/popular-jobtitles")
    public Map<String, Integer> popular_jobtitles() {
        Dataset<Row> data = sparkSession.sql("SELECT title, COUNT(*) as cnt FROM Jobs GROUP BY title ORDER BY cnt DESC");
        Map<String, Integer> rowsMap = new LinkedHashMap<>();
        for(Row r: data.collectAsList()) {
            String first = r.get(0).toString();
            int second = Integer.parseInt(r.get(1).toString());
            rowsMap.put(first, second);
        }
        return rowsMap;
    }

    @GetMapping("/popular-areas")
    public Map<String, Integer> popular_areas() {
        Dataset<Row> data = sparkSession.sql("SELECT location, COUNT(*) as cnt FROM Jobs GROUP BY location ORDER BY cnt DESC");
        Map<String, Integer> rowsMap = new LinkedHashMap<>();
        for(Row r: data.collectAsList()) {
            String first = r.get(0).toString();
            int second = Integer.parseInt(r.get(1).toString());
            rowsMap.put(first, second);
        }
        return rowsMap;
    }

    @GetMapping("/popular-skills")
    public Map<String, Long> popular_skills() {
        List<String> data = csvData.select("skills").as(Encoders.STRING()).collectAsList();
        List<String> skills = new ArrayList<>();
        for(String s: data) {
            String[] r = s.split(",");
            for(int i =0; i < r.length; i++) {
                skills.add(r[i].toLowerCase().trim());
            }
        }

        JavaRDD java_rdd = javaSparkContext.parallelize(skills);
        Map<String, Long> unordered = java_rdd.countByValue();
        Map<String, Long> ordered = new LinkedHashMap<>();
        unordered.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> ordered.put(x.getKey(), x.getValue()));

        return ordered;
    }

    @GetMapping("/show-data")
    public List<List<String>> show_data() {
        String[] columns = csvData.columns();
        List<String> columns_data = new ArrayList<>(Arrays.asList(columns));
        List<Row> rows = csvData.limit(10).collectAsList();
        List<List<String>> string_data = new ArrayList<>();
        string_data.add(columns_data);
        for(Row row : rows) {
            List<String> record = new ArrayList<>();
            for(int i = 0; i < columns.length; i++) {
                record.add(row.get(i).toString());
            }
            string_data.add(record);
        }
        return string_data;
    }

    @GetMapping("/print-schema")
    public Map<String, String> print_schema() {
        StructType schema = csvData.schema();
        StructField[] data = schema.fields();
        Map<String, String> string_data = new LinkedHashMap<>();
        for(StructField f : data) {
            string_data.put(f.name(), f.dataType().typeName().toString());
        }
        return string_data;
    }

    @GetMapping("/print-summary")
    public Map<String, Integer> print_summary() {
        StructType schema = csvData.schema();
        StructField[] data = schema.fields();
        Map<String, Integer> string_data = new LinkedHashMap<>();
        /*for(StructField f : data) {
            string_data.put(f.name(), csvData.select(f.name()).distinct().count());
        }*/
        for(StructField f : data) {
            List<String> col = csvData.select(f.name()).as(Encoders.STRING()).collectAsList();
            JavaRDD java_rdd = javaSparkContext.parallelize(col);
            Map<String, Long> unordered = java_rdd.countByValue();
            string_data.put(f.name(), unordered.size());
        }
        return string_data;
    }

}