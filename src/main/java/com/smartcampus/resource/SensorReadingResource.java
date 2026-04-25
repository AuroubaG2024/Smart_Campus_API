package com.smartcampus.resource;
import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.exception.SensorUnavailableException;
//Imports the model.classes representing sensors and their readings
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.store.DataStore;
//JAX-RS imports for REST API annotations and response handling
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Defines the base URI for this resource.
//This represents a nested endpoint for sensor readings
@Path("/sensors/{sensorId}/readings")
//API produces JSON responses 
@Produces(MediaType.APPLICATION_JSON)
//API accepts JSON input
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    
    //Handles HTTP GET requests to retrieve all readings for a specific sensor
    @GET
    public List<SensorReading> getReadings(@PathParam("sensorId") String sensorId) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new LinkedResourceNotFoundException(
                "Sensor with ID " + sensorId + " does not exist."
            );
        }

        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    //Handles HTTP POST requests to add a new reading for a sensor
    @POST
    public Response addReading(@PathParam("sensorId") String sensorId, SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new LinkedResourceNotFoundException(
                "Sensor with ID " + sensorId + " does not exist."
            );
        }

        if ("MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException(
                "Sensor is under maintenance and cannot accept readings."
            );
        }

        //create reading metadata
        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

        //add reading to datastore
        //computeIfAbsent ensures a list exists for the sensor ID
        DataStore.readings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        //update the sensor's current value 
        sensor.setCurrentValue(reading.getValue());
        
        //Returns the HTTP status 201 (Created) with the newly created reading
        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}