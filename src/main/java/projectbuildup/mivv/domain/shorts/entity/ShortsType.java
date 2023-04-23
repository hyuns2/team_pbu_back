package projectbuildup.mivv.domain.shorts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShortsType {
    SAVING("절약 쇼츠입니다."),
    EDUCATION("금융 교육 쇼츠입니다.");

    private final String title;
}
