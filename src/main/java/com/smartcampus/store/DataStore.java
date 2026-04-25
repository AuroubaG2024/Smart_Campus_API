package com.smartcampus.store;
//Import Java collections used to store data in memory
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Import model classes representing entities in the system
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
//It stores all rooms, sensors, and sensor readings using HashMaps
public class DataStore {
     //stores the rooms
    public static Map<String, Room> rooms = new HashMap<>();

    //stores the sensors
    public static Map<String, Sensor> sensors = new HashMap<>();

    //stores the readings for each sensor
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    
}
