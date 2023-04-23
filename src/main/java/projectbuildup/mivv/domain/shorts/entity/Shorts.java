package projectbuildup.mivv.domain.shorts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Shorts {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ShortsType type;
    private String title;
    private String content;
    private String videoUrl;
}
