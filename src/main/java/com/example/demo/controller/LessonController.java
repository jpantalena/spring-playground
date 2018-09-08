package com.example.demo.controller;

import com.example.demo.LessonRepository;
import com.example.demo.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @GetMapping("/find/{title}")
    public Lesson findByTitle(@PathVariable String title) {
        return this.repository.findByTitle(title);
    }

    @GetMapping("/between")
    public List<Lesson> findBetween(@RequestParam Date date1, @RequestParam Date date2) {
        return this.repository.findLessonsBetweenDates(date1, date2);
    }


}
