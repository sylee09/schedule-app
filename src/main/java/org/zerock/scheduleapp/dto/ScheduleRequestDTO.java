package org.zerock.scheduleapp.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ScheduleRequestDTO {

    private String title;

    private String content;

    private String author;

    private String password;

}
