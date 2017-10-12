package com.howtodoinjava.restful;

import com.howtodoinjava.model.Group;
import com.howtodoinjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
public class UsersResourceBean implements UsersResource {

	@Autowired
	private UserService userService;

	@Override
	public Response greeting(@PathParam("name") String name) {
		String result = userService.greeting(name);
		return Response.status(200).entity(result).build();
	}

	@Override
	public Response hi() {
		// return Response.status(200).entity("Hi.......................").build();
		throw new IllegalStateException("wrong state ....");
	}

	@Override
	public Group getGroup(@Context ServletContext servletContext) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Group group = new Group();
		group.setId(1);
		group.setName("Group A, " + principal.toString());
		return group;
	}

	@Override
	public String addUser(@PathParam("userName") String userName) {
		System.out.println("add " + userName);
		return "add " + userName;
	}

	@Override
	public Response registerUser(Long userId, Long deviceId) {
		System.out.println("user: " + userId + ", device: " + deviceId);
		return Response.ok().build();
	}

	@Override
	public String updateUser(@PathParam("userName") String userName) {
		System.out.println("update " + userName);
		return "update " + userName;
	}
}