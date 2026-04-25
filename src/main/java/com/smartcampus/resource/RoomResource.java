package com.smartcampus.resource;
//imports the other files and exceptions
import com.smartcampus.exception.RoomNotEmptyException;
import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Room;
import com.smartcampus.store.DataStore;
//Jax-rs imports the API annotations and responses 
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//Defines the base URI path for rooms
@Path("/rooms")
//API produces JSON responses 
@Produces(MediaType.APPLICATION_JSON)
//API accepts JSON input 
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {
    
    //Handles the HTTP GET requests to retrieve all rooms
    @GET
    public List<Room> getAllRooms() {
        return new ArrayList<>(DataStore.rooms.values());
    }
    
    //Handles the HTTP POST requests to create a new room
    @POST
    public Response createRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);
        //Returns the HTTP status 201 (Created) along with the created room object
        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }
    
    //Handles the HTTP GET requests to retrieve a specific room by ID
    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }
        //If the room exists, return HTTP 200 with the room data
        return Response.ok(room).build();
    }
    
    //Handles the HTTP DELETE requests to remove a room by ID
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        
        //If the room does not exist, throw a custom exception
        //It would return an HTTP 404 response
        if (room == null) {
            throw new LinkedResourceNotFoundException("Room with ID " + roomId + " does not exist.");
        }
        
        //Check if the room still has sensors assigned to it
        //Room with sensors should not be deleted
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room " + roomId + " cannot be deleted because it still has assigned sensors.");
        }

        DataStore.rooms.remove(roomId);
        
        //Returns a success response confirming the room was deleted
        return Response.ok("Room deleted successfully").build();
    }
}