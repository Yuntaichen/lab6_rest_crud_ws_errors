package com.labs;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Provider
public class EmptyFieldExceptionMapper implements ExceptionMapper<EmptyFieldException> {
    @Override
    public Response toResponse(EmptyFieldException ex) {
        return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}