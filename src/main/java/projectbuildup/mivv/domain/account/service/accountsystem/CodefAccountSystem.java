package projectbuildup.mivv.domain.account.service.accountsystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.domain.account.dto.AccountCertifyTransferDto;
import projectbuildup.mivv.domain.account.dto.AccountRegisterDto;
import projectbuildup.mivv.domain.account.entity.Account;
import projectbuildup.mivv.domain.account.entity.BankType;
import projectbuildup.mivv.domain.account.entity.OpenBanking;
import projectbuildup.mivv.domain.account.entity.OwnAccounts;
import projectbuildup.mivv.domain.account.service.accountsystem.codefclient.CodefClient;
import projectbuildup.mivv.domain.auth.repository.IdentityVerificationRepository;
import projectbuildup.mivv.domain.user.entity.IdentityVerification;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CAccountNotFoundException;
import projectbuildup.mivv.global.error.exception.CBadRequestException;
import projectbuildup.mivv.global.error.exception.CNotOwnAccountException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Component
@Slf4j
@RequiredArgsConstructor
public class CodefAccountSystem implements AccountSystem {

    private final CodefClient codefClient;
    private final IdentityVerificationRepository identityVerificationRepository;

    @Override
    public Account createAccount(AccountRegisterDto accountDto, User user) {
        String connectedId = issueConnectedId(accountDto);
        OwnAccounts ownAccounts = findAllAccountNumbers(connectedId, accountDto.getOrganizationCode());
        if (!ownAccounts.contains(accountDto.getAccountNumbers())) {
            throw new CAccountNotFoundException();
        }
        return new Account(accountDto.getAccountNumbers(), BankType.findByCode(accountDto.getOrganizationCode()), OpenBanking.CODEF, connectedId);
    }

    private String issueConnectedId(AccountRegisterDto accountDto) {
        Map<String, Object> dataMap = codefClient.createConnectedId(accountDto);
        return (String) dataMap.get("connectedId");
    }

    private OwnAccounts findAllAccountNumbers(String connectedId, String bankType) {
        Map<String, Object> dataMap = codefClient.getOwnAccounts(bankType, connectedId);
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) dataMap.get("resDepositTrust");
        List<String> accounts = list.stream()
                .map(elem -> (String) elem.get("resAccount"))
                .toList();
        return new OwnAccounts(accounts);
    }
    @Override
    public String certifyTransfer(AccountCertifyTransferDto requestDto) {
        String name =  (String) codefClient.holderAuthentication(requestDto.getOrganizationCode(), requestDto.getAccountNumbers(), requestDto.getBirthDate()).get("name");
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(requestDto.getVerificationCode()).orElseThrow(CResourceNotFoundException::new);
        if (identityVerification.getName().equals(name)){
            return (String) codefClient.certifyTransfer(requestDto.getOrganizationCode(), requestDto.getAccountNumbers()).get("authCode");
        }
        throw new CNotOwnAccountException();
    }
}
