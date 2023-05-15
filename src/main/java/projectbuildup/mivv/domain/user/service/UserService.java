package projectbuildup.mivv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.global.common.imageStore.Image;
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
    public ProfileDto.Response getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return new ProfileDto.Response(user);
    }

    public void updateProfile(Long userId, ProfileDto.UpdateRequest requestDto) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        deleteBeforeUpdate(user);
        Image image = imageUploader.upload(requestDto.getImageFile(), "profiles");
        imageUploader.delete(user.getProfileImage());
        user.updateProfile(requestDto.getNickname(), image);
        userRepository.save(user);
    }

    private void deleteBeforeUpdate(User user) throws IOException {
        if (user.getProfileImage() != null) {
            imageUploader.delete(user.getProfileImage());
        }
    }
}
