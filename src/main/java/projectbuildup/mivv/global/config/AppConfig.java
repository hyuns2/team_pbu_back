package projectbuildup.mivv.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectbuildup.mivv.domain.account.service.accountdetails.AccountDetailsSystem;
import projectbuildup.mivv.domain.account.service.accountdetails.CodefAccountDetailsSystem;
import projectbuildup.mivv.domain.account.service.accountsystem.codefclient.CodefClient;
import projectbuildup.mivv.domain.account.service.accountsystem.codefclient.CodefSandBoxClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(10);
    }
}
