package projectbuildup.mivv.domain.account.entity;

import java.util.List;

public class OwnAccounts {
    private final List<String> accounts;

    public OwnAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public boolean contains(String accountNumber){
        return accounts.contains(accountNumber);
    }
}
