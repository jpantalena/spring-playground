package com.example.demo.acceptance;

import com.example.demo.LessonRepository;
import com.example.demo.model.Lesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DbTransactionalTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Test
    @Transactional
    @Rollback
    public void testCreate() throws Exception {
        double random = Math.random();
        String title = String.valueOf(random);
        MockHttpServletRequestBuilder request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \""+ title +"\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)));

        Iterable<Lesson> all = repository.findAll();
        List<Lesson> lessons = new ArrayList<>();
        all.forEach(lessons::add);
        boolean found = lessons.stream().anyMatch(lesson -> title.equalsIgnoreCase(lesson.getTitle()));
        assertTrue(found);
    }

    @Test
    @Transactional
    @Rollback
    public void testGetById() throws Exception {
        Lesson lesson = new Lesson("my new lesson");
        Lesson save = repository.save(lesson);
        String id = save.getId().toString();

        MockHttpServletRequestBuilder request = get("/lessons/"+id).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.title", equalTo("my new lesson")))
                .andExpect(jsonPath("$.id", equalTo(save.getId().intValue())));
    }

}
