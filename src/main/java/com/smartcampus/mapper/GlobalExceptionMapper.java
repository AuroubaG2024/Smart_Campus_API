package com.smartcampus.mapper;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

//This mapper catches all unhandled exceptions in the SmartCampus API.
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        Map<String, Object> error = new HashMap<>();
        //returns a structured JSON response instead
        error.put("message", "An unexpected server error occurred.");
        error.put("status", 500);
        error.put("error", exception.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}