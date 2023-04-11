package projectbuildup.mivv.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.remittance.dto.RemittanceDto;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.remittance.repository.RemittanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemittanceDetailsService {
    private final RemittanceRepository remittanceRepository;
    private final UserRepository userRepository;

    /**
     * 해당 연월에 기록된 절약 내역을 모두 조회합니다.
     *
     * @param userId       사용자 아이디넘버
     * @param yearMonthStr yyyyMM
     * @return 절약 내역
     */
    public List<RemittanceDto.DetailsResponse> getRemittanceDetails(Long userId, String yearMonthStr) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        LocalDate localDate = LocalDate.parse(yearMonthStr + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        YearMonth yearMonth = YearMonth.from(localDate);
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        List<Remittance> remittances = remittanceRepository.findAllByUserAndCreatedTimeBetween(user, startTime, endTime);
        return remittances.stream()
                .map(RemittanceDto.DetailsResponse::new)
                .toList();
    }
}
