package com.github.cbuschka.tmply.business;

public class StatsDto
{
	private int maxBucketsCount;

	private int usedBucketsCount;

	public StatsDto(int maxBucketsCount, int usedBucketsCount)
	{
		this.maxBucketsCount = maxBucketsCount;
		this.usedBucketsCount = usedBucketsCount;
	}

	public int getMaxBucketsCount()
	{
		return maxBucketsCount;
	}

	public int getUsedBucketsCount()
	{
		return usedBucketsCount;
	}
}
