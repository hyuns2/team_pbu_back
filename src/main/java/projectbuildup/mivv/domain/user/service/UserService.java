package projectbuildup.mivv.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.account.entity.UserState;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.domain.user.dto.ProfileDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageUploader imageUploader;
    private final UserRepository userRepository;
    private final IdentityVerificationRepository identityVerificationRepository;

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
        Image image = getUploadedImage(requestDto.getImageFile());
        user.updateProfile(requestDto.getNickname(), image);
        userRepository.save(user);
    }

    private void deleteExistingImage(User user) throws IOException {
        if (!user.getProfileImage().equals(Image.newDefaultProfileImage())) {
            imageUploader.deleteIfExists(user.getProfileImage());
        }
    }

    private Image getUploadedImage(MultipartFile multipartFileImage) {
        if (multipartFileImage == null) {
            return Image.newDefaultProfileImage();
        }
        return imageUploader.upload(multipartFileImage, ImageType.USER_PROFILE);
    }

    /**
     * 회원의 상태를 조회합니다.
     * 회원가입까지만 한 유저 => SIGNUPED
     * 계좌연동까지 한 유저 => REGISTERD
     * 새 유저(혹은 본인인증은 했는데 회원가입을 완료하지 않은 유저) => NEW
     *
     * @param verificationCode 인증 코드
     * @return 회원 상태
     */
    public UserState getUserState(String verificationCode) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(verificationCode).orElseThrow(CResourceNotFoundException::new);
        Optional<User> userOptional = userRepository.findByIdentityVerification(identityVerification);
        if (userOptional.isEmpty()) {
            return UserState.NEW;
        }
        if (userOptional.get().getAccount() == null) {
            return UserState.SIGNUPED;
        }
        return UserState.REGISTERED;
    }


}
