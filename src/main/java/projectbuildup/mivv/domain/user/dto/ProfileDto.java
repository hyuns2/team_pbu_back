package projectbuildup.mivv.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.common.imageStore.Image;

public class ProfileDto {
    @Getter
    @Setter //ModelAttribute는 프로퍼티 접근법인 setter를 이용해 값 주입
    public static class UpdateRequest {
        @Schema(description = "닉네임")
        @NotBlank
        String nickname;

        @Schema(description = "이미지")
        MultipartFile imageFile;
    }
    @Getter
    public static class Response {
        @Schema(description = "닉네임")
        String nickname;
        @Schema(description = "이미지")
        Image profileImage;

        public Response(User user){
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
        }
    }
}