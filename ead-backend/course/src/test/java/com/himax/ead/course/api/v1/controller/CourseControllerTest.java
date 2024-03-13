package com.himax.ead.course.api.v1.controller;

import com.himax.ead.course.domain.model.Course;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;
class CourseControllerTest {

    @Test
    void getOne(){
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Course-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UUID uuid = UUID.fromString("f60a230e-7833-41d0-85e9-b4aa78a6b741");
        Course course = entityManager.find(Course.class, uuid);



        entityManager.close();
        entityManagerFactory.close();
    }



}