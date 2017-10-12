package com.howtodoinjava.restful;

import com.howtodoinjava.model.Group;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/user")
public interface UsersResource {
	@GET
	@Path("/greeting/{name}")
	Response greeting(@PathParam("name") String name);

	@GET
	@Path("/hi")
	Response hi();

	@GET
	@Path("/group")
	@Produces("application/json")
	Group getGroup(@Context ServletContext servletContext);

	@POST
	@Path("/{userName}")
	String addUser(@PathParam("userName") String userName);

	@POST
	@Path("{userId}/device")
	@Consumes("application/json")
	@Produces("application/json")
	Response registerUser(@PathParam("userId") Long userId, Long deviceId);

	@POST
	@Path("/{userName}")
	String updateUser(@PathParam("userName") String userName);
}