package com.smartcampus.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider //This annotation registers the mapper with JAX-RS
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    
    //Deplays the response status
    @Override
    public Response toResponse(SensorUnavailableException ex) {

        return Response.status(Response.Status.FORBIDDEN)
                .entity(ex.getMessage())
                .build();
    }
}