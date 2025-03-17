package projectbuildup.gasomann.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projectbuildup.gasomann.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.gasomann.domain.account.service.accountdetails.CodefAccountDetailsSystem;
import projectbuildup.gasomann.domain.account.service.accountsystem.codefclient.CodefClient;
import projectbuildup.gasomann.domain.account.service.accountsystem.codefclient.CodefSandBoxClient;

@Configuration
public class AccountConfig {
    @Bean
    public CodefClient codefTestClient(){
        return new CodefSandBoxClient();
    }
    @Bean
    public AccountDetailsSystem testAccountDetailsSystem(){
        return new CodefAccountDetailsSystem(codefTestClient());
    }

}
