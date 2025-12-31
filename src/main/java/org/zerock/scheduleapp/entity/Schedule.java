package org.zerock.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "schedules")
@Getter
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String author;

    private String password;

}
