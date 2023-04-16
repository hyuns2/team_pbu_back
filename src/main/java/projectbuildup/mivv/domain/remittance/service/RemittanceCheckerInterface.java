package projectbuildup.mivv.domain.remittance.service;

import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.domain.user.entity.User;

import java.time.LocalDateTime;

public interface RemittanceCheckerInterface {
    boolean check(Remittance remittance, User user, LocalDateTime startTime) throws InterruptedException;
}
