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
@Table(name="INQUIRY")
public class InquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(length = 1000)
    private String answer;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime timeStamp;

}
