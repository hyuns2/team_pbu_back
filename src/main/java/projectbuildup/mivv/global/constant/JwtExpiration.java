package projectbuildup.mivv.global.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtExpiration {

    public static Long ACCESS_SEC;
    public static Long REFRESH_SEC;

    @Value("${jwt.expiration.access}")
    public void setAccess(String value){
        ACCESS_SEC = Long.parseLong(value);
    }

    @Value("${jwt.expiration.refresh}")
    public void setRefresh(String value){
        REFRESH_SEC = Long.parseLong(value);
    }
}
