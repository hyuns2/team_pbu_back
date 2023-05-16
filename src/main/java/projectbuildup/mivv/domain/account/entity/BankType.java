package projectbuildup.mivv.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.mivv.global.error.exception.CIllegalArgumentException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BankType {
    KDB("산업은행", "0002"),
    IBK("기업은행", "0003"),
    KB("국민은행", "0004"),
    SUHYUP("수협은행", "0007"),
    NONGHYUP("농협은행", "0011"),
    WOORI("우리은행", "0020"),
    SC("SC은행", "0023"),
    CITY("씨티은행", "0027"),
    DAEGU("대구은행", "0031"),
    BUSAN("부산은행", "0032"),
    GWANGJU("광주은행", "0034"),
    JEJU("제주은행", "0035"),
    JEONBUK("전북은행", "0037"),
    GYEONGNAM("경남은행", "0039"),
    SAEMAEUL("새마을금고", "0045"),
    SHINHYUP("신협은행", "0048"),
    EPOST("우체국", "0071"),
    HANA("KEB하나은행", "0081"),
    SHINHAN("신한은행", "0088"),
    KBANK("K뱅크", "0089");

    final String description;
    final String code;

    public static BankType findByCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode().equals(code))
                .findFirst().orElseThrow(() -> new CIllegalArgumentException("잘못된 기관 코드입니다."));
    }
}
