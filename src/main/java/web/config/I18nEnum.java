package web.config;

import lombok.Getter;

@Getter
public enum I18nEnum {

    Title("title"),
    Keywords("keywords"),
    Description("description"),
    IntroductionMore("introduction_more"),
    IntroductionName("introduction_name"),
    Introduction("introduction"),
    PvText("pv_text"),
    OnlineText("online_text"),

    ;

    private String key;

    I18nEnum(String key) {
        this.key = key;
    }

}
