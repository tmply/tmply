package com.github.cbuschka.tmply.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PropertySource(value="classpath:build.properties",ignoreResourceNotFound = true)
public class StatusController
{
	@Value("${revision:unknown}")
	private String revision;

	@Value("${version:unknown}")
	private String version;

	@Value("${timestamp:unknown}")
	private String buildTime;

	public static class StatusResponse
	{
		public String status;
		public String commitish;
		public String version;
		public String buildTime;

		public StatusResponse(String status, String commitish, String version, String buildTime)
		{
			this.status = status;
			this.commitish = commitish;
			this.version = version;
			this.buildTime = buildTime;
		}
	}

	@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody StatusResponse
	getStatus()
	{
		return new StatusResponse("OK", this.revision, this.version, this.buildTime);
	}
}
