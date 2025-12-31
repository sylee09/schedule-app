package org.zerock.scheduleapp.dto;

import lombok.Data;

@Data
public class ReplyRequestDTO {
    private String content;
    private String author;
    private String password;
}
