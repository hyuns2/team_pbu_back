package projectbuildup.mivv.domain.saving_count.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.saving_count.entity.SavingCount;
import projectbuildup.mivv.domain.saving_count.repository.SavingCountRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingCountService {
    private final SavingCountRepository savingCountRepository;

    /**
     * 해당 사용자의 금일 참여 횟수를 1 증가시킵니다.
     *
     * @param participation 참여 정보
     */
    public void addCount(Participation participation) {
        SavingCount savingCount = savingCountRepository.findByParticipation(participation).orElseThrow(CUserNotFoundException::new);
        savingCount.addCount();
        savingCountRepository.save(savingCount);
    }

    /**
     * 모든 사용자의 참여 횟수를 0으로 초기화합니다.
     */
    public void initializeToZero() {
        List<SavingCount> all = savingCountRepository.findAll();
        for (SavingCount savingCount : all) {
            savingCount.initialize();
            savingCountRepository.save(savingCount);
        }
    }
}
