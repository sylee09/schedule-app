package org.zerock.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "replies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String author;
    private String password;

    @ManyToOne
    @JoinColumn
    private Schedule schedule;

    public Reply(String content, String author, String password) {
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void setSchedule(Schedule schedule) {
        if (this.schedule != null) {
            this.schedule.getReplies().remove(this);
        }
        this.schedule = schedule;
        schedule.getReplies().add(this);
    }
}
