package com.company.rns;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/rns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NumberResource {
    @Inject
    NumberRepository numberRepository;

    @GET
    public List<NumberEntity> getAllNumbers() {
        return numberRepository.listAllNumbers();
    }

    @POST
    public NumberEntity createNumber(@QueryParam("value") int value) {
        return numberRepository.addNumber(value);
    }

    @GET
    @Path("/{id}")
    public NumberEntity getNumber(@PathParam("id") Long id) {
        return numberRepository.getNumber(id);
    }

    @PUT
    @Path("/{id}")
    public NumberEntity updateNumber(@PathParam("id") Long id, @QueryParam("value") int newValue) {
        return numberRepository.updateNumber(id, newValue);
    }

    // @DELETE
    // @Path("/{id}")
    // public void deleteNumber(@PathParam("id") Long id) {
    //     numberRepository.deleteNumber(id);
    // }
}