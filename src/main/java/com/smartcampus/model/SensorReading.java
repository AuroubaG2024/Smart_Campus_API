package com.smartcampus.model;

public class SensorReading {
    
    //Defining variables
    private String id;
    private long timestamp;
    private double value;
  
    //Empty constructor for the JSON body for the postman tests 
    public SensorReading() {}
  
    //Constructor for all the values
    public SensorReading(String id, long timestamp, double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }
    
    
    //Getters and Setters
    public String getId() { 
        return id; 
    }
    
    public void setId(String id) {
        this.id = id; 
    }

    public long getTimestamp() { 
        return timestamp; 
    }
    
    public void setTimestamp(long timestamp) { 
        this.timestamp = timestamp; 
    }

    public double getValue() { 
        return value; 
    }
    
    public void setValue(double value) { 
        this.value = value; 
    }
    
}