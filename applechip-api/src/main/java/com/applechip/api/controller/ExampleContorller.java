package com.applechip.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Yawn Date: 2014. 6. 14. Time: 오후 11:15
 */
@Controller
public class ExampleContorller {

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

	@RequestMapping(value = "/hello/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> helloApi(@PathVariable String userName) {
		Map<String, String> helloMap = new HashMap<>();

		helloMap.put("ProjectName", "AppleChip");
		helloMap.put("User", userName);

		return helloMap;
	}
}
