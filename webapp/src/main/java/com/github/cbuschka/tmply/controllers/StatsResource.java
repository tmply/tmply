package com.github.cbuschka.tmply.controllers;

import com.github.cbuschka.tmply.business.GetStatsBusinessService;
import com.github.cbuschka.tmply.business.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsResource
{
	@Autowired
	private GetStatsBusinessService getStatsBusinessService;

	@RequestMapping(value = "/api/stats", method = RequestMethod.GET)
	public
	@ResponseBody
	ResponseEntity<StatsDto> getBucket()
	{
		StatsDto stats = this.getStatsBusinessService.getStats();
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
}
