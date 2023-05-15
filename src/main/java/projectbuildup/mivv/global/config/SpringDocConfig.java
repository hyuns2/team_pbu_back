package projectbuildup.mivv.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "https://server.gasomann.com/", description = "Dev Server URL"),
                @Server(url = "http://localhost:8080", description = "Local Server URL")
        },
        info = @Info(
                title = "MIVV API 명세서",
                description = "MIVV의 API 명세서입니다.",
                version = "0.0.1",
                contact = @Contact(
                        name = "HANSOL",
                        email = "hansol8701@naver.com"
                )
        )
)
@Configuration
public class SpringDocConfig {
}
