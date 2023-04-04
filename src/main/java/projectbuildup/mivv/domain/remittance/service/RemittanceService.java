package projectbuildup.mivv.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CInternalServerException;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import static java.lang.Thread.sleep;


@Service
@Slf4j
@RequiredArgsConstructor
public class RemittanceService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final RemittanceRepository remittanceRepository;


    /**
     * 절약 정보를 등록합니다.
     *
     * @param requestDto 회원 아이디넘버, 챌린지 아이디넘버, 절약금액
     */
    public void remit(RemittanceDto.RemitRequest requestDto) {
        Remittance remittance = createRemittance(requestDto);
        try{
            check(remittance);
        }catch (InterruptedException e){
            throw new CInternalServerException();
        }
    }

    private Remittance createRemittance(RemittanceDto.RemitRequest requestDto){
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(CUserNotFoundException::new);
        return new Remittance(challenge, user, requestDto.getAmount());
    }

    //Async
    private void check(Remittance remittance) throws InterruptedException {
        log.info("송금액 확인 시작");
        for (int i=0; i<5; i++){
            // 실제로 송금했는지 확인
            if (hasRecord()){
                remittanceRepository.save(remittance);
                break;
            }
            sleep(1000);
        }
        log.info("송금액 확인 종료");
    }
    private boolean hasRecord(){
        return true;
    }
}
