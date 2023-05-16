package projectbuildup.mivv.global.utils;

import jakarta.servlet.http.HttpServletRequest;
import projectbuildup.mivv.global.constant.DeviceType;

public class DeviceFinder {
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String MOBILE_DEVICE = "Mobile";

    private static final String IPHONE_AGENT = "iPhone";
    private static final String ANDROID_AGENT = "Android";

    public static DeviceType findUserDevice(HttpServletRequest request){
        String userAgent = request.getHeader(USER_AGENT_HEADER);
        if (userAgent != null && userAgent.contains(IPHONE_AGENT) && userAgent.contains(MOBILE_DEVICE)){
            return DeviceType.IOS;
        }
        if (userAgent != null && userAgent.contains(ANDROID_AGENT) && userAgent.contains(MOBILE_DEVICE)){
            return DeviceType.ANDROID;
        }
        return DeviceType.WEB;
    }
}
