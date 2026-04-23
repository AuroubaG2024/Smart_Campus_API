/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.smartcampusapi;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class SmartCampusAPI {

    public static final String BASE_URI = "http://localhost:8081/";

    public static HttpServer startServer() {

        final ResourceConfig config = new ResourceConfig()
                .packages("com.smartcampus.resource");

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI), config);
    }

    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();

        System.out.println("Smart Campus API running at:");
        System.out.println(BASE_URI + "api/v1");

        System.out.println("Press ENTER to stop the server");
        System.in.read();

        server.shutdownNow();
    }
}

