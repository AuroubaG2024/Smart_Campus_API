package com.smartcampus.resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo() {
        //Creates a map to store information about the API
        Map<String, Object> info = new HashMap<>();
       
        
        info.put("name", "Smart Campus API");
        info.put("version", "v1");
        //Provides the administrator contact email for the API
        info.put("adminContact", "admin@westminster.ac.uk");
        //Creates another map to store the available API resource endpoints
        Map<String, String> resources = new HashMap<>();
        //Defines the endpoints for rooms
        resources.put("rooms", "/api/v1/rooms");
        //Defines the endpoints for the sensors
        resources.put("sensors", "/api/v1/sensors");
        
        //Add the resouces map into the API response
        info.put("resources", resources);
        //Returns the API information as a JSON object 
        return info;
    }
}
