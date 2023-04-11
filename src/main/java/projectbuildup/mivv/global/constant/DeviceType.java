package projectbuildup.mivv.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeviceType {
    ANDROID("http://www.naver.com"),
    IOS("http://www.google.com"),
    WEB("https://www.daum.net");

    private final String schemaRoot;

}
