package org.zerock.scheduleapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping("/schedules")
    public ScheduleResponseDTO addSchedule(@RequestBody ScheduleRequestDTO schedule) {
        return service.addSchedule(schedule);
    }

}
