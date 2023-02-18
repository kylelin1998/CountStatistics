package web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Boolean requestLog;
    private Integer uriExpireDay;
    private Rate rate;

    @Getter
    @Setter
    public static class Rate {
        private Boolean enabled;
        private Integer uriCount;
        private Integer intervalHour;
    }

}
