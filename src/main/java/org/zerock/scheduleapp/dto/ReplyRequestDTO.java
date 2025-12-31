package org.zerock.scheduleapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ReplyRequestDTO {
    @Length(max = 100)
    private String content;
    @NotBlank
    private String author;
    @NotBlank
    private String password;
}
