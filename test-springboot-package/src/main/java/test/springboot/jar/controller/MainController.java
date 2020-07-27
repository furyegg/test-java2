package test.springboot.jar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.springboot.jar.metric.ActuatorConfig;

@RestController
@RequestMapping("metric")
public class MainController {
//    @Autowired
//    private ActuatorConfig actuatorConfig;
    
    @GetMapping("/start")
    public void start() {
    
    }
}