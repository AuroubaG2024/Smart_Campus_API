package com.smartcampus.mapper;

import com.smartcampus.exception.RoomNotEmptyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {

        Map<String, Object> error = new HashMap<>();

        error.put("message", exception.getMessage());
        error.put("status", 409);
        error.put("error", "Room still contains sensors");

        return Response.status(Response.Status.CONFLICT)
                .entity(error)
                .build();
    }
}