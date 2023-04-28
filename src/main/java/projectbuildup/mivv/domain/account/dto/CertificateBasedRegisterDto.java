package projectbuildup.mivv.domain.account.dto;

import lombok.Getter;

@Getter
public class CertificateBasedRegisterDto extends AccountRegisterDto{
    String defFile;
    String keyFile;
}
