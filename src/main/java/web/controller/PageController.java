package web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import web.config.I18nConfig;
import web.config.I18nLocaleEnum;

import java.util.HashMap;

@Controller
public class PageController {

    @Autowired
    private I18nConfig i18nConfig;

    public ModelAndView build(String viewName, String localAlias) {
        I18nLocaleEnum i18nLocaleEnum = I18nLocaleEnum.ZH_CN;
        if (StringUtils.isNotBlank(localAlias)) {
            I18nLocaleEnum localeEnum = I18nLocaleEnum.getI18nLocaleEnumByAlias(localAlias.toLowerCase());
            if (null != localeEnum) {
                i18nLocaleEnum = localeEnum;
            }
        }

        I18nLocaleEnum switchLang = null;
        HashMap<String, String> switchLangMap = new HashMap<>();
        switch (i18nLocaleEnum) {
            case ZH_CN:
                switchLang = I18nLocaleEnum.EN;
                break;
            case EN:
                switchLang = I18nLocaleEnum.ZH_CN;
                break;
        }
        switchLangMap.put("href", "/" + switchLang.getAlias());
        switchLangMap.put("displayText", switchLang.getDisplayText());

        ModelAndView view = new ModelAndView(viewName);
        view.addObject("lang", i18nLocaleEnum.getAlias());
        view.addObject("switchLang", switchLangMap);
        view.addAllObjects(i18nConfig.cache(i18nLocaleEnum));
        return view;
    }

    @GetMapping(value = { "/" })
    public ModelAndView main() {
        ModelAndView view = build("index", null);
        return view;
    }

    @GetMapping(value = { "/{local}", "index/{local}", "home/{local}", "main/{local}"})
    public ModelAndView index(@PathVariable("local") String local) {
        ModelAndView view = build("index", local);
        return view;
    }

}
