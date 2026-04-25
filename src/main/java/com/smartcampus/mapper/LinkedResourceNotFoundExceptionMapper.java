package com.smartcampus.mapper;

import com.smartcampus.exception.LinkedResourceNotFoundException;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

//Instead of returning a server error, the API returns a structured response of HTTP 422

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    // mapper handles cases where a referenced resource does not exist
    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {

        Map<String, Object> error = new HashMap<>();
        //the API returns a structured HTTP 422 Unprocessable Entity response
        error.put("message", exception.getMessage());
        error.put("status", 422);
        error.put("error", "Linked resource not found");

        return Response.status(422)
                .entity(error)
                .build();
    }
}