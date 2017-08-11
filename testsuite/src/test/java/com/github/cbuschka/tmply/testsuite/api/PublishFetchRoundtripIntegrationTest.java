package com.github.cbuschka.tmply.testsuite.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PublishFetchRoundtripIntegrationTest
{
	private TmplyClient tmplyClient = new TmplyClient();

	@Test
	public void happyPath()
	{
		final String bucketName = "slot" + System.currentTimeMillis();
		final String dummyData = "testtest" + bucketName;

		verifySlotIsFree(bucketName);

		publish(bucketName, dummyData);
		verifySlotHasData(bucketName, dummyData);

		delete(bucketName);
		verifySlotIsFree(bucketName);
	}

	private void delete(String bucketName)
	{
		this.tmplyClient.delete(bucketName);
	}

	private void verifySlotHasData(String bucketName, String dummyData)
	{
		String data = tmplyClient.fetch(bucketName);
		assertThat(data, is(dummyData));
	}

	private void publish(String bucketName, String data)
	{
		this.tmplyClient.publish(bucketName, data);
	}

	private void verifySlotIsFree(String bucketName)
	{
		String data = tmplyClient.fetch(bucketName);
		assertThat(data, is(nullValue()));
	}
}
