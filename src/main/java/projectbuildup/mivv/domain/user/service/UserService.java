package projectbuildup.mivv.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.domain.user.dto.ProfileDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageUploader imageUploader;
    private final UserRepository userRepository;

    /**
     * 프로필을 조회합니다.
     *
     * @param userId 사용자 아이디넘버
     * @return 프로필 정보
     */
    public ProfileDto.Response getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return new ProfileDto.Response(user);
    }

    /**
     * 프로필을 수정합니다.
     *
     * @param userId     사용자 아이디넘버
     * @param requestDto 수정할 닉네임, 프로필 이미지
     * @throws IOException
     */
    @Transactional
    public void updateProfile(Long userId, ProfileDto.UpdateRequest requestDto) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        deleteExistingImage(user);
        Image image = imageUploader.upload(requestDto.getImageFile(), ImageType.USER_PROFILE);
        user.updateProfile(requestDto.getNickname(), image);
        userRepository.save(user);
    }
    private void deleteExistingImage(User user) throws IOException {
        if (user.getProfileImage() != null) {
            imageUploader.delete(user.getProfileImage());
        }
    }
}
