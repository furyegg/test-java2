package test.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import test.controller.Employee;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientApp {
	public static void main(String[] args) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut("http://localhost:8080/put");
		// put.setHeader("Content-type", "application/json");

		Employee employee = new Employee("Kyle", 34);

		ObjectMapper mapper = new ObjectMapper();
		String value = mapper.writeValueAsString(employee);
		System.out.println(value);

		Map<String, String> map = new HashMap<>();
		map.put("employee", value);

		put.setEntity(buildHttpEntity(map));

		HttpResponse response = httpClient.execute(put);
		System.out.println(response.getStatusLine().getStatusCode());
	}

	public static HttpEntity buildHttpEntity(Map<String, String> params) throws UnsupportedEncodingException {
		final List<BasicNameValuePair> nameValuePairs = new ArrayList<>();
		params.forEach((kev, value) -> nameValuePairs.add(new BasicNameValuePair(kev, value)));
		return new UrlEncodedFormEntity(nameValuePairs);
	}
}
