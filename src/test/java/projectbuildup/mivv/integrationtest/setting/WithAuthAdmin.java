package projectbuildup.mivv.integrationtest.setting;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithAuthUser(role = "ADMIN")
public @interface WithAuthAdmin {
}
