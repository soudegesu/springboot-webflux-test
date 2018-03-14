package com.soudegesu.example.mvc.controller;

import com.soudegesu.example.mvc.response.User;
import com.soudegesu.example.mvc.service.HelloService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Timed
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> hello(@RequestParam(name = "time", defaultValue = "0.0") String time) {

        User user = helloService.getUser(Double.valueOf(time));

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
