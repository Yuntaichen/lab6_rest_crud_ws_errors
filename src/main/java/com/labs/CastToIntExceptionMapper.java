package com.labs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CastToIntExceptionMapper implements ExceptionMapper<CastToIntException> {
    @Override
    public Response toResponse(CastToIntException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}