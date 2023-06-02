package projectbuildup.mivv.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.notification.dto.NotificationDto;
import projectbuildup.mivv.domain.notification.entity.NotificationEntity;
import projectbuildup.mivv.domain.notification.entity.NotificationType;
import projectbuildup.mivv.domain.notification.repository.NotificationRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CNotificationNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository repo;
    private final ImageUploader imageUploader;

    /**
     * 관리자가 이벤트 알림 생성
     *
     * @param dto 알림 제목, 내용, 로고
     * @throws IOException
     */
    public void createEventNotification(final NotificationDto.NotificationRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), ImageType.NOTIFICATION);

        NotificationEntity entity = NotificationDto.NotificationRequestDto.toEntity(dto, image.getImagePath(), NotificationType.EVENT);

        repo.save(entity);

    }

    /**
     * 관리자가 공지 알림 생성
     *
     * @param dto 알림 제목, 내용, 로고
     * @throws IOException
     */
    public void createNoticeNotification(final NotificationDto.NotificationRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), ImageType.NOTIFICATION);

        NotificationEntity entity = NotificationDto.NotificationRequestDto.toEntity(dto, image.getImagePath(), NotificationType.NOTICE);

        repo.save(entity);

    }

    /**
     * 관리자가 알림 삭제
     *
     * @param id 알림 Id
     * @throws CNotificationNotFoundException 알림 찾기 실패시
     */
    public void deleteNotification(final Long id) {

        Optional<NotificationEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CNotificationNotFoundException();
        }

        NotificationEntity result = target.get();
        repo.delete(result);

    }

    /**
     * 알림 전체 조회
     *
     * @return List<NotificationDto.NotificationResponseDto> 전체 알림 정보
     */
    public List<NotificationDto.NotificationResponseDto> retrieveNotifications() {
        List<NotificationEntity> result = repo.findAll();

        return result.stream().map(NotificationDto.NotificationResponseDto::new).collect(Collectors.toList());
    }

}
