package projectbuildup.mivv.domain.account.dto;

import lombok.Getter;

@Getter
public class IdPasswordBasedRegisterDto extends AccountRegisterDto{
    String bankId;
    String bankPassword;
}
