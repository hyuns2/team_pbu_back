package projectbuildup.mivv.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;


public class ProfileDto {
    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        @Schema(description = "닉네임")
        @NotBlank
        String nickname;

        @Schema(description = "이미지")
        @NotNull
        MultipartFile imageFile;
    }
    @Getter
    @NoArgsConstructor
    public static class Response {
        @Schema(description = "닉네임")
        @NotBlank
        String nickname;

        @Schema(description = "이미지")
        @NotNull
        Image profileImage;

        public Response(User user){
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
        }
    }
}
