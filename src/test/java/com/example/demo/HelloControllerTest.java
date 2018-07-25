package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {
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

}