package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    private MathService mathService;

    public HelloController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello from Spring!";
    }

    @GetMapping("/math/pi")
    public String getPi() {
        return "3.141592653589793";
    }

    @RequestMapping(value = "/math/calculate", method = RequestMethod.GET)
    public String math(@RequestParam(defaultValue = "add") String operation,
                       @RequestParam String x,
                       @RequestParam String y) {
        return mathService.calculate(operation, x, y);
    }

    @PostMapping("/math/sum")
    public String sum(@RequestParam Map<String, String> values) {
        return mathService.sum(values);
    }
}
