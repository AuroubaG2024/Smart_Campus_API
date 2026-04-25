package com.smartcampus.model;

import java.util.List;

public class Room {
    //Defining variables 
    private String id;
    private String name;
    private int capacity;
    private List<String> sensorIds;

    //empty constructor which is needed for JSON postman tests
    public Room() {
    }

    //Constructor for all values
    public Room(String id, String name, int capacity, List<String> sensorIds) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIds = sensorIds;
    }

    //Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}