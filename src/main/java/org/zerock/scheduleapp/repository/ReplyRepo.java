package org.zerock.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.scheduleapp.entity.Reply;

public interface ReplyRepo extends JpaRepository<Reply,Long> {
}
