package projectbuildup.mivv.integrationtest.setting;

import org.springframework.security.test.context.support.WithSecurityContext;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    String role();
}
