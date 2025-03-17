package projectbuildup.gasomann.integrationTest.setting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithAuthUser(role = "ADMIN", id = "1")
public @interface WithAuthAdmin {
}
