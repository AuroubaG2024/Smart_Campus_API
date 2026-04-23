package com.smartcampus.model;
package com.smartcampus.resource;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.exception.SensorUnavailableException;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.store.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/sensors/{sensorId}/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SensorReading {
    @GET
    public List<SensorReading> getReadings(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new LinkedResourceNotFoundException("Sensor with ID " + sensorId + " does not exist.");
        }

        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    public 
}
