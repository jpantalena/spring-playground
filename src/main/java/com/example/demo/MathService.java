package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class MathService {
    public String calculate(String operation, String x, String y) {
        switch (operation) {
            case "add":
                return x + " + " + y + " = " + (Integer.valueOf(x) + Integer.valueOf(y));
            case "subtract":
                return x + " - " + y + " = " + (Integer.valueOf(x) - Integer.valueOf(y));
            case "multiply":
                return x + " * " + y + " = " + (Integer.valueOf(x) * Integer.valueOf(y));
            case "divide":
                return x + " / " + y + " = " + (Integer.valueOf(x) / Integer.valueOf(y));
        }
        throw new BadRequestException("Invalid operation query parameter");
    }

    public String sum(Map<String, String> valueMap) {
        List<Integer> numbersToSum = valueMap.entrySet().stream()
                .map(e -> Integer.valueOf(e.getValue()))
                .collect(toList());

        String sum = numbersToSum.stream()
                .reduce(0, (v1, v2) -> v1 + v2).toString();

        String result = numbersToSum.stream()
                .map(Object::toString)
                .reduce((n1, n2) -> n1 + " + " + n2)
                .orElseThrow(RuntimeException::new);

        return result + " = " + sum;
    }
}