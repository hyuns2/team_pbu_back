package projectbuildup.mivv.domain.user.entity;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("password_change_link")
public class PasswordChangeLink {
    public static final Long EXPIRATION_MINUTES = 30L;

    @Id
    private Long id;
    @Indexed
    String serialNumber;
    Long userId;
    @TimeToLive
    private Long expiration;

    public PasswordChangeLink(User user){
        this.serialNumber = RandomStringUtils.randomAlphabetic(8);
        this.userId = user.getId();
        this.expiration = TimeUnit.SECONDS.convert(EXPIRATION_MINUTES, TimeUnit.MINUTES);
    }

    public String makeQueryUri(){
        final String USER_ID_PARAM = "userId=";
        final String SN_PARAM = "sn=";
        final String START_PARAM = "?";
        final String AND_PARAM = "&";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START_PARAM);
        stringBuilder.append(USER_ID_PARAM);
        stringBuilder.append(userId);
        stringBuilder.append(AND_PARAM);
        stringBuilder.append(SN_PARAM);
        stringBuilder.append(serialNumber);
        return stringBuilder.toString();
    }
}
