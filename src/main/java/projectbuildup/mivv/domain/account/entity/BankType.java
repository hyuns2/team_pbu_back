package projectbuildup.mivv.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.mivv.global.error.exception.CIllegalArgumentException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BankType {
    KDB("산업", "0002"),
    IBK("기업", "0003"),
    KB("국민", "0004"),
    SUHYUP("수협", "0007"),
    NONGHYUP("농협", "0011"),
    WOORI("우리", "0020"),
    SC("SC", "0023"),
    CITY("씨티", "0027"),
    DAEGU("대구", "0031"),
    BUSAN("부산", "0032"),
    GWANGJU("광주", "0034"),
    JEJU("제주", "0035"),
    JEONBUK("전북", "0037"),
    GYEONGNAM("경남", "0039"),
    SAEMAEUL("새마을금고", "0045"),
    SHINHYUP("신협", "0048"),
    EPOST("우체국", "0071"),
    HANA("KEB하나", "0081"),
    SHINHAN("신한", "0088");

    final String description;
    final String code;

    public static BankType findByCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode().equals(code))
                .findFirst().orElseThrow(() -> new CIllegalArgumentException("잘못된 기관 코드입니다."));
    }
}
