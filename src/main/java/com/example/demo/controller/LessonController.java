package com.example.demo.controller;

import com.example.demo.LessonRepository;
import com.example.demo.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private LessonRepository repository;

    public LessonController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Lesson select(@PathVariable Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.repository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Lesson update(@PathVariable Long id, @RequestBody Lesson lessonRequest) {
        return this.repository.save(new Lesson(id, lessonRequest.getTitle(), lessonRequest.getDeliveredOn()));
    }
}
