package projectbuildup.mivv.domain.account.service.accountdetails;

import projectbuildup.mivv.domain.user.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AccountDetailsSystem {
    List<Map<String, String>> getHistory(User user);
}
