package com.github.cbuschka.tmply.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatusController
{
	public static class StatusResponse
	{
		public String status = "OK";
	}

	@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody StatusResponse
	getStatus()
	{
		return new StatusResponse();
	}
}
