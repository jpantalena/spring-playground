package com.example.demo.controller;

import com.example.demo.BadRequestException;
import com.example.demo.MathService;
import com.example.demo.model.AreaRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MathController.class)
public class MathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathService mathService;

    @Test
    public void getPi_returns200_andPiValue() throws Exception {
        this.mockMvc.perform(get("/math/pi").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("3.141592653589793"))
                .andExpect(status().isOk());
    }

    @Test
    public void math_returns200_andCallMathService() throws Exception {
        when(mathService.calculate(anyString(), anyString(), anyString()))
                .thenReturn("3 * 10 = 30");

        this.mockMvc.perform(get("/math/calculate?operation=multiply&x=3&y=10"))
                .andExpect(content().string("3 * 10 = 30"))
                .andExpect(status().isOk());

        verify(mathService, times(1))
                .calculate("multiply", "3", "10");
    }

    @Test
    public void math_throws400BadRequestException_whenOperationIsInvalid() throws Exception {
        when(mathService.calculate(anyString(), anyString(), anyString()))
                .thenThrow(new BadRequestException("Invalid operation query parameter"));

        this.mockMvc.perform(get("/math/calculate?operation=badValue&x=3&y=10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void math_usesDefaultValueAdd_whenOperationIsNotPassed() throws Exception {
        this.mockMvc.perform(get("/math/calculate?&x=4&y=7")
                .accept(MediaType.APPLICATION_JSON));
        verify(mathService, times(1)).calculate("add", "4", "7");
    }

    @Test
    public void sum_returns200_andAddsValues() throws Exception {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("n1", "4");
        valueMap.put("n2", "5");
        valueMap.put("n3", "6");
        valueMap.put("n4", "90");
        when(mathService.sum(any())).thenReturn("4 + 5 + 6 + 90 = 105");

        this.mockMvc.perform(post("/math/sum?n1=4&n2=5&n3=6&n4=90"))
                .andExpect(content().string("4 + 5 + 6 + 90 = 105"))
                .andExpect(status().isOk());

        verify(mathService, times(1)).sum(valueMap);

    }

    @Test
    public void volume_returns200_andCorrectVolumeResponse_forGET_PATCH_POST_PUT() throws Exception {
        this.mockMvc.perform(get("/math/volume/10/20/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 10x20x10 rectangle is 2000"));

        this.mockMvc.perform(patch("/math/volume/10/20/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 10x20x10 rectangle is 2000"));

        this.mockMvc.perform(post("/math/volume/10/20/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 10x20x10 rectangle is 2000"));

        this.mockMvc.perform(put("/math/volume/10/20/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("The volume of a 10x20x10 rectangle is 2000"));
    }

    @Test
    public void area_returns200_andCorrectAreaForACircle() throws Exception {
        when(mathService.area(any())).thenReturn("Area of a circle with a radius of 5 is 78.54");

        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("radius", "5");

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("Area of a circle with a radius of 5 is 78.54"));

        verify(mathService, times(1)).area(AreaRequest.builder()
                .type("circle")
                .radius("5")
                .build());
    }

    @Test
    public void area_returns200_andCorrectAreaForARectangle() throws Exception {
        when(mathService.area(any())).thenReturn("Area of a 5x6 rectangle is 30");

        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "rectangle")
                .param("width", "5")
                .param("height", "6");

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("Area of a 5x6 rectangle is 30"));

        verify(mathService, times(1)).area(AreaRequest.builder()
                .type("rectangle")
                .width("5")
                .height("6")
                .build());
    }

    @Test
    public void area_returns200_andInvalidResponse_whenIncorrectFormDataPassed() throws Exception {
        when(mathService.area(any())).thenReturn("Invalid");

        MockHttpServletRequestBuilder request = post("/math/area")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("type", "circle")
                .param("width", "5")
                .param("height", "6");

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("Invalid"));

        verify(mathService, times(1)).area(AreaRequest.builder()
                .type("circle")
                .width("5")
                .height("6")
                .build());
    }

}