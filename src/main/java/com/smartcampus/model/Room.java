package com.smartcampus.model;


import java.util.List;

public class Room {

    private String id;
    private String name;
    private int capacity;
    private List<String> sensorIds;

    // empty constructor (needed for JSON)
    public Room() {
    }

    // full constructor
    public Room(String id, String name, int capacity, List<String> sensorIds) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIds = sensorIds;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}