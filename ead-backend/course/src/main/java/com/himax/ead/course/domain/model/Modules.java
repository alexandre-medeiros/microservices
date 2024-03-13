package com.himax.ead.course.domain.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_MODULES")
public class Modules {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false, length = 250)
    private String description;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Lesson> lessons;
}
