package projectbuildup.mivv.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    SIGNUPED("signuped"),
    REGISTERED("registered"),
    NEW("new");
    final String field;
}
