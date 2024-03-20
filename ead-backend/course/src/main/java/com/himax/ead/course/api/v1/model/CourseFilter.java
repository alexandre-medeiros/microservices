package com.himax.ead.course.api.v1.model;

import com.himax.ead.course.domain.enums.CourseLevel;
import com.himax.ead.course.domain.enums.CourseStatus;
import lombok.Getter;
import java.beans.ConstructorProperties;

@Getter
public class CourseFilter {
    private final CourseLevel courseLevel;
    private final CourseStatus courseStatus;
    private final String name;

    @ConstructorProperties({"courseLevel","courseStatus", "name"})
    public CourseFilter(CourseLevel courseLevel, CourseStatus courseStatus, String name) {
        this.courseLevel = courseLevel;
        this.courseStatus = courseStatus;
        this.name = name;
    }
}
