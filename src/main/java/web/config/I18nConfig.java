package web.config;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class I18nConfig {

    private Map<String, Map<String, Map<String, String>>> cacheMap = new HashMap<>();

    public Map<String, Map<String, String>> cache(I18nLocaleEnum i18nLocaleEnum) {
        String alias = i18nLocaleEnum.getAlias();
        if (!cacheMap.containsKey(alias)) {
            HashMap<String, Map<String, String>> map = new HashMap<>();
            HashMap<String, String> hashMap = new HashMap<>();
            for (I18nEnum value : I18nEnum.values()) {
                ResourceBundle bundle = ResourceBundle.getBundle("i18n/i18n", i18nLocaleEnum.getLocale());
                hashMap.put(value.getKey(), bundle.getString(value.getKey()));
            }
            map.put("i18n", hashMap);
            cacheMap.put(alias, map);
        }

        return cacheMap.get(alias);
    }

}
