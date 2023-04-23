package projectbuildup.mivv.domain.shorts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShortsType {
    SAVING("절약 쇼츠"),
    EDUCATION("금융 교육 쇼츠");

    private final String title;
}
