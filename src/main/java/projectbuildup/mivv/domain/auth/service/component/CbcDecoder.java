package projectbuildup.mivv.domain.auth.service.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import projectbuildup.mivv.infra.kisa.KISA_SEED_CBC;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@Slf4j
public class CbcDecoder {

    static String charset = "utf-8";
    public static byte[] bszIV = "SASKGINICIS00000".getBytes();

    public static String decrypt(byte[] str, String token) {
        Decoder decoder = Base64.getDecoder();
        byte[] pbUserKey = decoder.decode(token.getBytes());
        byte[] enc = decoder.decode(str);
        String result = "";
        byte[] dec;

        try {
            dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, bszIV, enc, 0, enc.length);
            result = new String(dec, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}