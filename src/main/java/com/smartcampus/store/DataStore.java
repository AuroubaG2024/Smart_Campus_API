package com.smartcampus.store;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

public class DataStore {
     // store rooms
    public static Map<String, Room> rooms = new HashMap<>();

    // store sensors
    public static Map<String, Sensor> sensors = new HashMap<>();

    // store readings for each sensor
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    
}
