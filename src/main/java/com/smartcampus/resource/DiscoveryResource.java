/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


@Path("/")
public class DiscoveryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo() {
        Map<String, Object> info = new HashMap<>();

        info.put("name", "Smart Campus API");
        info.put("version", "v1");
        info.put("adminContact", "admin@westminster.ac.uk");

        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");

        info.put("resources", resources);

        return info;
    }
}
