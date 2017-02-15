package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.controllers.WebSocketMessage;

public class StatsNotification extends WebSocketMessage
{
	private int max;

	private int free;

	public StatsNotification()
	{
	}

	public StatsNotification(int max, int free)
	{
		this.max = max;
		this.free = free;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
	}

	public int getFree()
	{
		return free;
	}

	public void setFree(int free)
	{
		this.free = free;
	}
}
