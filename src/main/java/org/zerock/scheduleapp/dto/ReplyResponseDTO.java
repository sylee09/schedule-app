package org.zerock.scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ReplyResponseDTO {
    private Long id;

    private String content;

    private String author;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

}
