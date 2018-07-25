package com.example.demo.controller;

import com.example.demo.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/math")
public class MathController {

    @Autowired
    private MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("/pi")
    public String getPi() {
        return "3.141592653589793";
    }


    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public String math(@RequestParam(defaultValue = "add") String operation, @RequestParam String x, @RequestParam String y) {
        return mathService.calculate(operation, x, y);
    }


    @PostMapping("/sum")
    public String sum(@RequestParam Map<String, String> values) {
        return mathService.sum(values);
    }


    @RequestMapping("/volume/{length}/{width}/{height}")
    public String volume(@PathVariable String length, @PathVariable String width, @PathVariable String height) {
        int volume = (Integer.valueOf(length) * Integer.valueOf(width) * Integer.valueOf(height));
        return "The volume of a " + length + "x" + width + "x" + height + " rectangle is " + volume;
    }

}
