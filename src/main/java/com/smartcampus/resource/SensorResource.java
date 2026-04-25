package com.smartcampus.resource;
//imports the other files and exceptions
import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.store.DataStore;
//Jax-rs imports the API annotations and responses
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Defines the base URI path for this resource which is sensors
@Path("/sensors")
//API produces JSON responses 
@Produces(MediaType.APPLICATION_JSON)
//API accepts JSON input 
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {
    
    //Handles the HTTP GET requests to retrieve all sensors
    @GET
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensors = new ArrayList<>(DataStore.sensors.values());

        if (type == null || type.isBlank()) {
            return sensors;
        }
        return sensors.stream()
                 .filter(sensor -> sensor.getType() != null
                        && sensor.getType().equalsIgnoreCase(type))
                 .collect(Collectors.toList());
    }
    
    //Handles the HTTP POST requests to create a new sensor
    @POST
    public Response createSensor(Sensor sensor) {
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request body is required")
                    .build();
        }

        if (sensor.getId() == null || sensor.getId().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor ID is required")
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("roomId is required")
                    .build();
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());
        
        //Handles whether there is a sensor or not by checking sensor by its ID
        if (room == null) {
            throw new LinkedResourceNotFoundException(
                    "Room with ID " + sensor.getRoomId() + " does not exist."
            );
        }
        if (room == null) {
            throw new LinkedResourceNotFoundException(
                    "Room with ID " + sensor.getRoomId() + " does not exist."
            );
        }

        if (DataStore.sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Sensor with this ID already exists")
                    .build();
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }

        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }
    //If a request comes to /sensors/{sensorId}/readings it is passed to the SensorReadingResource class to handle
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource() {
        return new SensorReadingResource();
    }
}
