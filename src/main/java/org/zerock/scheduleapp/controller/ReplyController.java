package org.zerock.scheduleapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.scheduleapp.dto.ReplyRequestDTO;
import org.zerock.scheduleapp.dto.ReplyResponseDTO;
import org.zerock.scheduleapp.exception.RepliesLimitException;
import org.zerock.scheduleapp.repository.ReplyRepo;
import org.zerock.scheduleapp.service.ReplyService;

@RestController
@AllArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/replies/{id}")
    public ReplyResponseDTO addReply(@PathVariable Long id, @RequestBody ReplyRequestDTO replyRequestDTO) {
        if (replyService.isPossibleToAddReply(id)) {
            return replyService.addReply(replyRequestDTO, id);
        } else {
            throw new RepliesLimitException("댓글은 10개까지만 작성할 수 있습니다.");
        }
    }
}
