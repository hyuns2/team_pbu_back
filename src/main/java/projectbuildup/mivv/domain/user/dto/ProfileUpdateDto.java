package projectbuildup.mivv.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ProfileUpdateDto {
    @Schema(description = "큰제목")
    String nickname;

    @Schema(description = "이미지")
    MultipartFile imageFile;
}
