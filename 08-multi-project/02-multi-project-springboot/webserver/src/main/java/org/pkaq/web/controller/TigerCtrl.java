package org.pkaq.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * Author: S.PKAQ
 * Datetime: 2016-12-01 16:33
 */
@Controller
@Slf4j
public class TigerCtrl {
    @RequestMapping("/")
    public ModelAndView tiger(){
        log.info("Soft kitty,warm kitty Little ball of fur .");
        return new ModelAndView("tiger","words","Roar ~ Roar ~ ");
    }
}
