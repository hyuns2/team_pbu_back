package projectbuildup.mivv.domain.auth.service.component;

import projectbuildup.mivv.infra.kisa.KISA_SEED_CBC;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class CbcDecoder {

    static String charset = "utf-8";

    public static byte[] Key = new String("wP3O41AL7+jU/CMZUSUo5Q==").getBytes();
    public static byte[] bszIV = new String("SASKGINICIS00000").getBytes();

    static Decoder decoder = Base64.getDecoder();
    static byte[] pbUserKey = decoder.decode(Key);

    public static void main(String[] args) {

        byte[] encryptData = encrypt("홍길동");
        decrypt(encryptData);

    }

    public static byte[] encrypt(String str) {
        byte[] enc = null;

        try {
            //암호화 함수 호출
            enc = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, bszIV, str.getBytes(charset), 0,
                    str.getBytes(charset).length);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Encoder encoder = Base64.getEncoder();
        byte[] encArray = encoder.encode(enc);
        try {
            System.out.println(new String(encArray, "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encArray;
    }

    public static String decrypt(byte[] str) {

        Decoder decoder = Base64.getDecoder();
        byte[] enc = decoder.decode(str);

        String result = "";
        byte[] dec = null;

        try {
            //복호화 함수 호출
            dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, bszIV, enc, 0, enc.length);
            result = new String(dec, charset);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("복호화 결과 = " + result);

        return result;
    }
}