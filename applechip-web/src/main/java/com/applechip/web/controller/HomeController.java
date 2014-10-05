package com.applechip.web.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.CookieGenerator;

@Controller
@Slf4j
public class HomeController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String displayHome(HttpServletResponse response, Model model) {
    CookieGenerator cookieGer = new CookieGenerator();
    cookieGer.setCookieName("cookie_value_test");
    cookieGer.addCookie(response, "applechip");
    return "/index";
  }

  @RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET)
  public String filename(HttpServletResponse response, Model model) {
    CookieGenerator cookieGer = new CookieGenerator();
    cookieGer.setCookieName("cookie_value_test");
    cookieGer.addCookie(response, "applechip");
    return "/index";
  }

  @RequestMapping(value = "/ssss", method = RequestMethod.GET)
  public String home(Locale locale, Model model) {
    log.info(String.format("Welcome home! The client locale is {%s}.", locale));


    Date date = new Date();
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

    String formattedDate = dateFormat.format(date);

    model.addAttribute("serverTime", formattedDate);

    return "views/home";
  }

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Locale locale, Model model) {
    log.info(String.format("Welcome home! The client locale is {%s}.", locale));


    Date date = new Date();
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

    String formattedDate = dateFormat.format(date);

    model.addAttribute("serverTime", formattedDate);

    return "index";
  }

}
