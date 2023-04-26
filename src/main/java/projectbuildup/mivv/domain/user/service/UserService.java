package projectbuildup.mivv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.image.Image;
import projectbuildup.mivv.domain.image.ImageUploader;
import projectbuildup.mivv.domain.user.dto.ProfileUpdateDto;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ImageUploader imageUploader;
    private final UserRepository userRepository;

    public void func(Long userId, ProfileUpdateDto requestDto, MultipartFile profileImage) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        deleteBeforeUpdate(user);
        Image image = imageUploader.upload(profileImage);
        user.updateProfile(requestDto.getNickname(), image);
        userRepository.save(user);
    }

    private void deleteBeforeUpdate(User user) throws IOException {
        if (user.getProfileImage() != null){
            imageUploader.delete(user.getProfileImage());
        }
    }
}
