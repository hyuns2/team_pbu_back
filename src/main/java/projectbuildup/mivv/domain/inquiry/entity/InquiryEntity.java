package projectbuildup.mivv.domain.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="inquiry")
public class InquiryEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "answer", length = 1000)
    private String answer;

    @Column(name = "time_stamp", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime timeStamp;

}
