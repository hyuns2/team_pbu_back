package projectbuildup.mivv.domain.value.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.value.repository.ValueRepository;
import projectbuildup.mivv.global.error.ErrorCode;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import static org.apache.commons.lang3.ObjectUtils.compare;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueValidationService {
    /**
     * pathVariable로 받은 id값과 RequestBody로 받은 DTO안의 id값을 비교해 같으면 OK
     *
     *
     */
    private final ValueRepository valueRepository;
    public void isSameValueId(Long valueId, Long valueIdIdDto){
        if(!ObjectUtils.equals(valueId, valueIdIdDto))
            throw new CUserNotFoundException();
    }
    public void isRealValueId(Long valueId){
        if(valueRepository.findById(valueId)==null)
            throw new CUserNotFoundException();
    }

}
