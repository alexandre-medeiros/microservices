package com.himax.ead.course.domain.service;

import com.himax.ead.course.domain.model.Lesson;
import com.himax.ead.course.domain.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;


    @Transactional
    public Lesson save(Lesson lessonModel) {
        return lessonRepository.save(lessonModel);
    }

    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Transactional
    public void delete(Lesson lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    public List<Lesson> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }
}
