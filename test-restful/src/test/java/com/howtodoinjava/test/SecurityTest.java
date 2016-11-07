package com.howtodoinjava.test;

import com.howtodoinjava.model.Group;
import com.howtodoinjava.model.Order;
import com.howtodoinjava.restful.UsersResource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class SecurityTest {

	@Test
	public void registerTest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/uwifi/newuser");
		Invocation.Builder request = target.request();
		Response response = request.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		response.getCookies().forEach((k, v) -> System.out.println(k + ": " + v + ", " + v.getValue()));
	}

	@Test
	public void registerTest2() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/uwifi/user/Kyle/orders");
		Invocation.Builder request = target.request();

		Order order = new Order();
		order.setOrderCode("AAAAADDDDDDDDFFFF");
		Response response = request.post(Entity.entity(order, MediaType.APPLICATION_JSON_TYPE));
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		response.getCookies().forEach((k, v) -> System.out.println(k + ": " + v + ", " + v.getValue()));
	}

	@Test
	public void loginTest() throws Exception {
		BasicAuthentication auth = new BasicAuthentication("user", "password");
		Client client = ClientBuilder.newClient().register(auth);

		WebTarget target = client.target("http://localhost:8080/restful/login");
		Invocation.Builder request = target.request();

		Form form = new Form();
		form.param("username", "user");
		form.param("password", "password");
		Response response = request.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

//		Response response = request.get();

		System.out.println(response.getStatus());
		response.getCookies().forEach((k, v) -> System.out.println(k + ": " + v + ", " + v.getValue()));
	}

	@Test
	public void basicAuthTest() throws Exception {
		BasicAuthentication auth = new BasicAuthentication("user", "password");
//		Client client = ClientBuilder.newClient().register(auth);
//
//		WebTarget target = client.target("http://localhost:8080/restful/rest/users/group");
//		Invocation.Builder request = target.request();
//
//		Response response = request.get();
//		MultivaluedMap<String, Object> headers = response.getHeaders();
//		headers.forEach((k, v) -> {
//			StringBuilder sb = new StringBuilder();
//			v.forEach(item -> sb.append(item.toString()).append(" | "));
//			System.out.println(k + ": " + sb.toString());
//		});
//
//		System.out.println(response.getStatus());
//		Map<String, NewCookie> cookies = response.getCookies();
//		cookies.forEach((k, v) -> System.out.println(k + ": " + v + ", " + v.getValue()));
//
//		String sessionId = cookies.get("JSESSIONID").getValue();
//		System.out.println("sessionId: " + sessionId);
//
//		// second
//		Client client2 = ClientBuilder.newClient();
//
//		WebTarget target2 = client2.target("http://localhost:8080/restful/rest/users/group");
//		Invocation.Builder request2 = target2.request();
//		request2.header("Cookie", "JSESSIONID=" + sessionId);
//
//		Response response2 = request2.get();
//
//		System.out.println("Response2: " + response2.getStatus());
//		System.out.println(response2.readEntity(Group.class).toString());
//		response2.getCookies().forEach((k, v) -> System.out.println(k + ": " + v));

		// third
		ResteasyClient client3 = new ResteasyClientBuilder().build().register(auth);
		ResteasyWebTarget target3 = client3.target("http://localhost:8080/restful/rest/");

		UsersResource usersResource = target3.proxy(UsersResource.class);
//		Response response3 = usersResource.greeting("ABC");
//		System.out.println("Response3: " + response3.getStatus());
//		System.out.println(response3.readEntity(String.class).toString());

		String abc = usersResource.addUser("abc");
		System.out.println(abc);

		String xyz = usersResource.updateUser("xyz");
		System.out.println(xyz);
//		System.out.println(response3.readEntity(String.class).toString());

//		BasicAuthentication
	}

	@Test
	public void apacheLoginTest() {
		String BASE_URL = "http://localhost:8080/basicauth/foo/admin";

		DefaultHttpClient client = new DefaultHttpClient();

		client.getCredentialsProvider().setCredentials(
				new AuthScope("localhost", 8080),
				new UsernamePasswordCredentials("user1", "user1Pass"));

		HttpGet httppost = new HttpGet(BASE_URL);

		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response = null;
		try {
			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				System.out.println("status: " + response.getStatusLine().getStatusCode());
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader((entity.getContent())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
			}
			client.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}