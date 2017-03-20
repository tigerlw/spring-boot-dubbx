package com.ucloudlink.endport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ucloudlink.dubbox.service.UserService;

@RestController
public class ConsumerController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String queryUser()
	{
		String result = userService.queryUser("liuwei");
		
		return result;
	}

}
