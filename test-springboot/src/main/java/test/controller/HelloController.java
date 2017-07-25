package test.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("/hello/{name}")
	public String sayHello(@PathVariable("name") String name) {
		return "Hello " + name;
	}

	@RequestMapping(value = "/put", method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE)
	public void put(@RequestBody Employee employee) {
		System.out.println(employee.toString());
	}
}
