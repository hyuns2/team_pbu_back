package projectbuildup.mivv.domain.account.service.accountdetails;

import projectbuildup.mivv.domain.user.entity.User;

import java.util.HashMap;
import java.util.List;

public interface AccountDetailsSystem {
    List<HashMap<String, String>> getHistory(User user);
}
