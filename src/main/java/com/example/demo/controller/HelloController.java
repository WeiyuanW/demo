package com.example.demo.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 *
 * @RestController = @Controller + @ResponseBody
 *
 * @RequestMapping("v2/root")
 */
//@Controller
@RestController
@RequestMapping("v1/root")
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<>("Hello, Spring Boot!",
                HttpStatusCode.valueOf(204));
    }

    /**
     *
     * © org.springframework.http.ResponseEntity<T>
     * public ResponseEntity(
     * @Nullable T body,
     * @NotNulla org.springframework.http.HttpStatusCode status
     *
     *
     */

    @GetMapping("/employee/{id}")
    public String getEmployeeById(@PathVariable long employeeId){
        return "Hello, Spring Boot!";
    }

/*
    orderId = 101 (204)
                  (404)

    read   -> GET (X RequestBody)
    insert -> POST(RequestBody)
                -> check existence first
                  -> insert
                  -> pop exception, return same status code
                -> idempotent??? (idempotency)
    update -> UPDATE/PATCH
    delete -> DELETE
    idempotency -> multiple times -> DB records

    */



    /*@RequestHeader
      @RequestParam vs @PathVariable
      @RequestBody

      @RequestParam vs @PathVariable
     */
    @PostMapping("/greet")
    public String greet(@RequestParam String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/greet/{name}")
    public String greet1(@PathVariable String name) {
        return "PathVariable Hello, " + name + "!";
    }

}




