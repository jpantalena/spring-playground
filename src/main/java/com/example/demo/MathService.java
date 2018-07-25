package com.example.demo;

import com.example.demo.model.AreaRequest;
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

    public String area(AreaRequest request) {
        if (!validation(request)) {
            return "Invalid";
        }

        return request.getType().equals("circle")
                ? "Area of a circle with a radius of " + request.getRadius() + " is "
                + (Math.PI * Integer.valueOf(request.getRadius()) * Integer.valueOf(request.getRadius()))
                : "Area of a " + request.getWidth() + "x" + request.getHeight() + " rectangle is "
                + (Integer.valueOf(request.getWidth()) * Integer.valueOf(request.getHeight()));
    }

    private static boolean validation(AreaRequest request) {
        if (request.getType().equals("circle")) {
            return isThere(request.getRadius()) && !isThere(request.getWidth()) && !isThere(request.getHeight());
        } else
            return request.getType().equals("rectangle") && !isThere(request.getRadius())
                    && isThere(request.getWidth()) && isThere(request.getHeight());
    }

    private static boolean isThere(String s) {
        return s != null && !s.isEmpty();
    }

}