package org.zerock.scheduleapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDTO {

    private String title;

    private String content;

    private String author;

    private String password;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
