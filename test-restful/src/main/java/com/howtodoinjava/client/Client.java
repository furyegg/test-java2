package com.howtodoinjava.client;

import com.howtodoinjava.model.User;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class Client {
	public static void main(String[] args) throws Exception {
		// sampleResteasyClientGETRequest();
		sampleResteasyClientPostRequest();
	}

	public static void sampleResteasyClientGETRequest() throws Exception {
		//Define the API URI where API will be accessed
		ClientRequest request = new ClientRequest("http://localhost:8080/restful/user-management/users2/10");

		//Set the accept header to tell the accepted response format
		request.accept("application/xml");

		//RESTEasy client automatically converts the response to desired objects.
		//This is how it is done.
		//Populate the response in user object
		ClientResponse<User> response = request.get(User.class);

		//First validate the api status code
		int statusCode = response.getResponseStatus().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("Failed with HTTP error code : " + statusCode);
		}

		//Get the user object from entity
		User user = response.getEntity();

		//verify the user object
		System.out.println(user.getId());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
	}

	public static void sampleResteasyClientPostRequest() throws Exception {
		User user = new User();
		user.setId(100);
		user.setFirstName("Lokesh");
		user.setLastName("Gupta");

		StringWriter writer = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.marshal(user, writer);

		//Define the API URI where API will be accessed
		ClientRequest request = new ClientRequest("http://localhost:8080/restful/user-management/users");

		//Set the accept header to tell the accepted response format
		request.body("application/xml", writer.getBuffer().toString());

		//Send the request
		ClientResponse response = request.post();

		//First validate the api status code
		int apiResponseCode = response.getResponseStatus().getStatusCode();
		if (response.getResponseStatus().getStatusCode() != 201) {
			throw new RuntimeException("Failed with HTTP error code : " + apiResponseCode);
		}
	}
}