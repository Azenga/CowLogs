package com.shadow.cowlogs.models;

import java.io.Serializable;

public class LogEntry implements Serializable {

    private String condition;
    private String dateTime;
    private String breed;
    private int id;
    private double weight;
    private int age;

    public LogEntry() {
    }

    public LogEntry(String condition, String dateTime, String breed, int id, double weight, int age) {
        this.condition = condition;
        this.dateTime = dateTime;
        this.breed = breed;
        this.id = id;
        this.weight = weight;
        this.age = age;
    }

    public String getCondition() {
        return condition;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBreed() {
        return breed;
    }
}
