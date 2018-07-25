package com.example.demo;

import com.example.demo.model.AreaRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MathServiceTest {
    private MathService mathService;

    @Before
    public void setUp() throws Exception {
        mathService = new MathService();
    }

    @Test
    public void calculate_addsCorrectlyAndReturnsString() throws Exception {
        String actual = mathService.calculate("add", "4", "7");
        assertEquals(actual, "4 + 7 = 11");
    }

    @Test
    public void calculate_subtractsCorrectlyAndReturnsString() throws Exception {
        String actual = mathService.calculate("subtract", "4", "7");
        assertEquals(actual, "4 - 7 = -3");
    }

    @Test
    public void calculate_multipliesCorrectlyAndReturnsString() throws Exception {
        String actual = mathService.calculate("multiply", "4", "7");
        assertEquals(actual, "4 * 7 = 28");
    }

    @Test
    public void calculate_dividesCorrectlyAndReturnsString() throws Exception {
        String actual = mathService.calculate("divide", "10", "2");
        assertEquals(actual, "10 / 2 = 5");
    }

    @Test
    public void sum_addsAllValuesCorrectly_andReturnsString() throws Exception {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("n1", "4");
        valueMap.put("n2", "5");
        valueMap.put("n3", "6");
        valueMap.put("n4", "90");
        String actual = mathService.sum(valueMap);
        assertEquals(actual, "4 + 5 + 6 + 90 = 105");
        valueMap.put("n5", "-110");
        String actual2 = mathService.sum(valueMap);
        assertEquals(actual2, "4 + 5 + 6 + 90 + -110 = -5");
    }

    @Test
    public void area_findsAreaOfCircle_andReturnsFormattedString() throws Exception {
        AreaRequest request = AreaRequest.builder().type("circle").radius("5").build();
        String actual = mathService.area(request);
        assertEquals(actual, "Area of a circle with a radius of 5 is 78.53981633974483");
    }

    @Test
    public void area_findsAreaOfRectangle_andReturnsFormattedString() throws Exception {
        AreaRequest request = AreaRequest.builder().type("rectangle")
                .width("5")
                .height("8")
                .build();

        String actual = mathService.area(request);
        assertEquals(actual, "Area of a 5x8 rectangle is 40");
    }

    @Test
    public void area_returnsInvalid_whenWrongCombinationPassed() throws Exception {
        AreaRequest badRequest1 = AreaRequest.builder()
                .type("circle")
                .width("5")
                .height("8")
                .build();

        AreaRequest badRequest2 = AreaRequest.builder()
                .type("rectangle")
                .radius("5")
                .build();

        AreaRequest badRequest3 = AreaRequest.builder()
                .type("oval")
                .radius("5")
                .build();

        assertEquals(mathService.area(badRequest1), "Invalid");
        assertEquals(mathService.area(badRequest2), "Invalid");
        assertEquals(mathService.area(badRequest3), "Invalid");
    }
}