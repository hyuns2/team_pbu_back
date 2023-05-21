package projectbuildup.mivv.global.common.imageStore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageType {
    CHALLENGE("challenges"),
    CARD("cards"),
    COUPON("coupons"),
    NOTIFICATION("notifications"),
    SHORTS("shorts"),
    USER_PROFILE("profiles"),
    VALUE("values");

    private final String directoryName;
}
