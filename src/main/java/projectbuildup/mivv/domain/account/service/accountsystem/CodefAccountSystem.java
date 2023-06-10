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
        return new Account(accountDto.getAccountNumbers(), BankType.findByCode(accountDto.getOrganizationCode()), OpenBanking.CODEF, connectedId, user);
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

    /**
     * 입력한 계좌의 예금주를 조회합니다.
     *
     * @param organizationCode 기관코드
     * @param accountNumbers   계좌번호
     * @param verificationCode 본인인증 코드
     * @return 계좌 예금주명
     */
    @Override
    public String getAccountOwner(String organizationCode, String accountNumbers, String verificationCode) {
        IdentityVerification identityVerification = identityVerificationRepository.findByCode(verificationCode).orElseThrow(CResourceNotFoundException::new);
        String birthDate = identityVerification.getBirthDate().substring(2);
        return (String) codefClient.holderAuthentication(organizationCode, accountNumbers, birthDate).get("name");
    }

    /**
     * 입력한 계좌로 1원 인증 코드를 전송합니다.
     *
     * @param organizationCode 기관코드
     * @param accountNumbers   계좌번호
     * @return 1원 인증 코드 (4자리 숫자)
     */
    @Override
    public String certifyTransfer(String organizationCode, String accountNumbers) {
        return (String) codefClient.certifyTransfer(organizationCode, accountNumbers).get("authCode");
    }
}
