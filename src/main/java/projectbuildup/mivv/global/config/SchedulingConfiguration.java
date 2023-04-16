package projectbuildup.mivv.global.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(  //.yml의 값으로 빈의 생성이 결정된다.
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
@EnableScheduling // 꼭 메인 Application에 쓰지 않고 configuration으로 분리 가능
public class SchedulingConfiguration {
}
