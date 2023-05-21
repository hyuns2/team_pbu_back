package projectbuildup.mivv.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="NOTIFICATION")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String kind;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, length = 1000)
    private String imagePath;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime timeStamp;

}
