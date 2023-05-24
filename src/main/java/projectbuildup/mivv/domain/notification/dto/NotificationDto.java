package projectbuildup.mivv.domain.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.notification.entity.NotificationEntity;
import projectbuildup.mivv.domain.notification.entity.NotificationType;

import java.io.IOException;
import java.time.LocalDateTime;

public class NotificationDto {

    @AllArgsConstructor
    @Data
    public static class NotificationRequestDto {

        @NotBlank
        @Length(min = 2, max = 30)
        @Schema(description = "알림 제목")
        private String title;

        @NotBlank
        @Length(min = 2, max = 1000)
        @Schema(description = "알림 내용")
        private String content;

        @NotNull
        @Schema(description = "로고 이미지 파일")
        private MultipartFile image;

        public static NotificationEntity toEntity(final NotificationRequestDto dto, String imagePath, NotificationType type) throws IOException {
            return NotificationEntity.builder()
                    .type(type)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .image_path(imagePath)
                    .time_stamp(LocalDateTime.now())
                    .build();
        }

    }

    @AllArgsConstructor
    @Data
    public static class NotificationResponseDto {
        @Schema(description = "알림 고유번호")
        private Long id;

        @Schema(description = "알림 종류")
        private String type;

        @Schema(description = "알림 제목")
        private String title;

        @Schema(description = "알림 내용")
        private String content;

        @Schema(description = "로고 이미지 파일경로")
        private String imagePath;

        @Schema(description = "알림 생성 시간")
        private LocalDateTime timeStamp;

        public NotificationResponseDto(final NotificationEntity entity) {
            this.id = entity.getId();
            this.type = entity.getType().name();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.imagePath = entity.getImage_path();
            this.timeStamp = entity.getTime_stamp();
        }
    }

}
