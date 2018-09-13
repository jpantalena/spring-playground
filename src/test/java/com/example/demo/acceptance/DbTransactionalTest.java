package com.example.demo.acceptance;

import com.example.demo.LessonRepository;
import com.example.demo.model.Lesson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure=false)
@Ignore
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
                .content(lessonRequest(null, title, null));

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
        String id = createNewLesson(1L, "lessonTitle", new Date(1536316200000L));

        MockHttpServletRequestBuilder request = get("/lessons/"+id).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.title", equalTo("lessonTitle")))
                .andExpect(jsonPath("$.id", equalTo(Integer.valueOf(id))));
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchById() throws Exception {
        String id = createNewLesson(1L, "lessonTitle", new Date(1536316200000L));

        MockHttpServletRequestBuilder request = patch("/lessons/"+id)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(lessonRequest(null, "my updated lesson", new Date()));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.title", equalTo("my updated lesson")))
                .andExpect(jsonPath("$.id", equalTo(Integer.valueOf(id))));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLessonsByTitle() throws Exception {
        String id = createNewLesson(1L, "lessonTitle", new Date(1536316200000L));
        MockHttpServletRequestBuilder request = get("/lessons/find/lessonTitle")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.title", equalTo("lessonTitle")))
                .andExpect(jsonPath("$.id", equalTo(Integer.valueOf(id))));

    }

    @Test
    @Transactional
    @Rollback
    public void testGetBetweenDates() throws Exception {
        String id_1 = createNewLesson(1L, "lessonTitle1", new Date(1536258600000L));// 9/6/18 12:30 PM
        String id_2 = createNewLesson(2L, "lessonTitle2", new Date(1536345000000L)); // 9/7/18 12:30 PM
        String id_3 = createNewLesson(3L, "lessonTitle3", new Date(1536431400000L)); // 9/8/18 12:30 PM

        Date date1 = new Date(1536258600000L); // 9/6/18 12:30 PM
        Date date2 = new Date(1536431400000L); // 9/8/18 12:30 PM

        MockHttpServletRequestBuilder request = get("/lessons/between")
                .contentType(MediaType.APPLICATION_JSON)
                .param("date1", date1.toString())
                .param("date2", date2.toString());

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(Integer.valueOf(id_2))))
                .andExpect(jsonPath("$[1].id", equalTo(Integer.valueOf(id_3))));
    }

    private String lessonRequest(Long id, String title, Date deliveredOn) throws JsonProcessingException {
        Lesson lesson = new Lesson(id, title, deliveredOn);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(lesson);
    }

    private String createNewLesson(Long id, String title, Date date) {
        Lesson lesson = new Lesson(id, title, date);
        Lesson save = repository.save(lesson);
        return save.getId().toString();
    }

}
