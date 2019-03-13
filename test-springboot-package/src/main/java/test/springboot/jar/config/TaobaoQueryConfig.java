package test.springboot.jar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("taobao.query")
@Data
public class TaobaoQueryConfig {
    private String sellerItemFields;
    private String fxProductFields;
}