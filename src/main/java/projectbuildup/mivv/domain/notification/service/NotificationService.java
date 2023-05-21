package projectbuildup.mivv.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.notification.dto.NotificationDto;
import projectbuildup.mivv.domain.notification.entity.NotificationEntity;
import projectbuildup.mivv.domain.notification.repository.NotificationRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
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

    public void createEventNotification(final NotificationDto.NotificationRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), "notifications");

        NotificationEntity entity = NotificationDto.NotificationRequestDto.toEntity(dto, image.getImagePath(), "이벤트");

        repo.save(entity);

    }

    public void createNoticeNotification(final NotificationDto.NotificationRequestDto dto) throws IOException {

        Image image = imageUploader.upload(dto.getImage(), "notifications");

        NotificationEntity entity = NotificationDto.NotificationRequestDto.toEntity(dto, image.getImagePath(), "공지사항");

        repo.save(entity);

    }

    public void deleteNotification(final Long id) {

        Optional<NotificationEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CNotificationNotFoundException();
        }

        NotificationEntity result = target.get();
        repo.delete(result);

    }

    public List<NotificationDto.NotificationResponseDto> retrieveNotifications() {
        List<NotificationEntity> result = repo.findAll();

        return result.stream().map(NotificationDto.NotificationResponseDto::new).collect(Collectors.toList());
    }

}
