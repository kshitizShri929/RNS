// package com.company.rns;

// import jakarta.inject.Inject;
// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import java.util.List;

// @Path("/rns")
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
// public class NumberResource {
//     @Inject
//     NumberRepository numberRepository;

//     @GET
//     public List<NumberEntity> getAllNumbers() {
//         return numberRepository.listAllNumbers();
//     }

//     @POST
//     public NumberEntity createNumber(@QueryParam("value") int value) {
//         return numberRepository.addNumber(value);
//     }

//     @GET
//     @Path("/{id}")
//     public NumberEntity getNumber(@PathParam("id") Long id) {
//         return numberRepository.getNumber(id);
//     }

//     @PUT
//     @Path("/{id}")
//     public NumberEntity updateNumber(@PathParam("id") Long id, @QueryParam("value") int newValue) {
//         return numberRepository.updateNumber(id, newValue);
//     }

//     // @DELETE
//     // @Path("/{id}")
//     // public void deleteNumber(@PathParam("id") Long id) {
//     //     numberRepository.deleteNumber(id);
//     // }
// }


// package com.company.rns;

// import java.util.List;

// import jakarta.inject.Inject;
// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;

// @Path("/rns")
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
// public class NumberResource {
//     @Inject
//     NumberRepository numberRepository;

//     @GET
//     public List<NumberEntity> getAllNumbers() {
//         return numberRepository.listAllNumbers();
//     }

//     @POST
//     public NumberEntity createNumber(@QueryParam("value") int value) {
//         return numberRepository.addNumber(value);
//     }

//     @GET
//     @Path("/{id}")
//     public NumberEntity getNumber(@PathParam("id") Long id) {
//         return numberRepository.getNumber(id);
//     }

//     @PUT
//     @Path("/{id}")
//     public NumberEntity updateNumber(@PathParam("id") Long id, @QueryParam("value") int newValue) {
//         return numberRepository.updateNumber(id, newValue);
//     }

//     // @DELETE
//     // @Path("/{id}")
//     // public void deleteNumber(@PathParam("id") Long id) {
//     //     numberRepository.deleteNumber(id);
//     // }

//     @POST
//     @Path("/generate-random/{count}")
//     public void generateRandomNumbers(@PathParam("count") int count) {
//         numberRepository.insertRandomNumbers(count);
//     }

//     // @POST
//     // @Path("/sync-db-to-redis")
//     // public void syncDbToRedis() {
//     //     numberRepository.syncDbToRedis();
//     // }

//     @POST
//     @Path("/insert-valkey/{key}/{value}")
//     public void insertIntoValkey(@PathParam("key") String key, @PathParam("value") String value) {
//         numberRepository.insertIntoValkeyDirectly(key, value);
//     }
// }



// package com.company.rns;

// import java.util.List;

// import jakarta.inject.Inject;
// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;

// @Path("/rns")
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
// public class NumberResource {
//     @Inject
//     NumberRepository numberRepository;

//     // Get all numbers
//     @GET
//     public Response getAllNumbers() {
//         List<NumberEntity> numbers = numberRepository.listAllNumbers();
//         if (numbers.isEmpty()) {
//             return Response.status(Response.Status.NO_CONTENT).build();  // 204 No Content if no numbers
//         }
//         return Response.ok(numbers).build();  // 200 OK with list of numbers
//     }

//     // Create a number
//     @POST
//     public Response createNumber(@QueryParam("value") int value) {
//         NumberEntity createdNumber = numberRepository.addNumber(value);
//         if (createdNumber == null) {
//             return Response.status(Response.Status.BAD_REQUEST).build();  // 400 Bad Request in case of failure
//         }
//         return Response.status(Response.Status.CREATED).entity(createdNumber).build();  // 201 Created
//     }

//     // Get number by ID
//     @GET
//     @Path("/{id}")
//     public Response getNumber(@PathParam("id") Long id) {
//         NumberEntity number = numberRepository.getNumber(id);
//         if (number == null) {
//             return Response.status(Response.Status.NOT_FOUND).build();  // 404 Not Found if number doesn't exist
//         }
//         return Response.ok(number).build();  // 200 OK with the number
//     }

//     // Update number by ID
//     @PUT
//     @Path("/{id}")
//     public Response updateNumber(@PathParam("id") Long id, @QueryParam("value") int newValue) {
//         NumberEntity updatedNumber = numberRepository.updateNumber(id, newValue);
//         if (updatedNumber == null) {
//             return Response.status(Response.Status.NOT_FOUND).build();  // 404 Not Found if number doesn't exist
//         }
//         return Response.ok(updatedNumber).build();  // 200 OK with updated number
//     }

//     // Generate random numbers
//     @POST
//     @Path("/generate-random/{count}")
//     public Response generateRandomNumbers(@PathParam("count") int count) {
//         numberRepository.insertRandomNumbers(count);
//         return Response.status(Response.Status.NO_CONTENT).build();  // 204 No Content after generating random numbers
//     }

//     // Insert into valkey
//     @POST
//     @Path("/insert-valkey/{key}/{value}")
//     public Response insertIntoValkey(@PathParam("key") String key, @PathParam("value") String value) {
//         numberRepository.insertIntoValkeyDirectly(key, value);
//         return Response.status(Response.Status.CREATED).build();  // 201 Created after insertion
//     }
// }


package com.company.rns;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/rns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NumberResource {
    @Inject
    NumberRepository numberRepository;

    @GET
    public Response getAllNumbers() {
        List<NumberEntity> numbers = numberRepository.listAllNumbers();
        return Response.ok(numbers).build();
         //numbers.isEmpty()
            //? Response.status(Response.Status.NO_CONTENT).build()
            
    }

    @POST
    public Response createNumber(@QueryParam("value") long value) {
        NumberEntity createdNumber = numberRepository.addNumber(value);
        return Response.status(Response.Status.CREATED).entity(createdNumber).build();
    }

    @POST
    @Path("/generate-random/{count}")
    public Response generateRandomNumbers(@PathParam("count") int count) {
        numberRepository.insertRandomNumbers(count);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/insert-valkey/{key}")
    public Response insertIntoValkey(@PathParam("key") String key, @PathParam("value") String value) {
        String result = numberRepository.insertIntoValkeyDirectly(key, value);
        return Response.ok(result).build();
    }
}
