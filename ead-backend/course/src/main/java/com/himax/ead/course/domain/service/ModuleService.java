package com.himax.ead.course.domain.service;

import com.himax.ead.course.domain.model.Modules;
import com.himax.ead.course.domain.repository.LessonRepository;
import com.himax.ead.course.domain.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    public void delete(Modules moduleModel) {
        moduleRepository.delete(moduleModel);
    }

    @Transactional
    public Modules save(Modules moduleModel) {
        return moduleRepository.save(moduleModel);
    }

    public Optional<Modules> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    public List<Modules> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllLModulesIntoCourse(courseId);
    }

    public Optional<Modules> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    public Page<Modules> findAllByCourse(Pageable pageable) {
        return moduleRepository.findAll(pageable);
    }
}
