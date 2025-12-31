package org.zerock.scheduleapp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class ScheduleRequestDTO {

    @Length(max = 30)
    @NotBlank
    private String title;

    @Length(max=200)
    @NotBlank
    private String content;

    @NotBlank
    private String author;

    @NotBlank
    private String password;

}
