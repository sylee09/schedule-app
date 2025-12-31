package org.zerock.scheduleapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ScheduleResponseDTO {

    private Long id;

    private String title;

    private String content;

    private String author;

    private List<ReplyResponseDTO> replies;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

}
