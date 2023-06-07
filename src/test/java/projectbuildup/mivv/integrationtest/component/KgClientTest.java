package projectbuildup.mivv.integrationtest.component;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import projectbuildup.mivv.domain.auth.service.component.KgClient;
import projectbuildup.mivv.integrationtest.setting.IntegrationTest;

public class KgClientTest extends IntegrationTest {
    @Autowired
    KgClient kgClient;

    private final static String TX_ID = "INISA_INIiasTest202306070013040592419956";
    private final static String AUTH_REQUEST_URL = "https://kssa.inicis.com/getResultData";

    @Test
    @DisplayName("")
    void test() {
        // given

        // when

        // then
    }
}
