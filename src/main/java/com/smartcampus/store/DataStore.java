package com.smartcampus.store;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

public class DataStore {
     //stores the rooms
    public static Map<String, Room> rooms = new HashMap<>();

    //stores the sensors
    public static Map<String, Sensor> sensors = new HashMap<>();

    //stores the readings for each sensor
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    
}
