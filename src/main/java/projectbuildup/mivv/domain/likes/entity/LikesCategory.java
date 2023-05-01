package projectbuildup.mivv.domain.likes.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum LikesCategory {
    WORTHY_CONSUMPTION("가치소비 찜"),
    SHORTS_SAVING("절약 쇼츠 찜"),
    SHORTS_EDU("소비교육 쇼츠 찜");

    private final String title;
}
