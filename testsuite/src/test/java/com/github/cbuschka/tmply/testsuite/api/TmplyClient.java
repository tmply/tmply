package com.github.cbuschka.tmply.testsuite.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TmplyClient
{
	private RestTemplate restTemplate = new RestTemplate();

	private String baseUrl = "http://localhost:8080";

	public void delete(String bucketName)
	{
		this.restTemplate.delete(baseUrl + "/api/buckets/{0}", bucketName);
	}

	public void publish(String bucketName, String data)
	{
		ResponseEntity<PublishResponse> responseEntity = this.restTemplate.postForEntity(baseUrl + "/api/buckets",
				new PublishRequest(bucketName, data),
				PublishResponse.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful())
		{
			throw new RuntimeException("Publish failed: " + responseEntity.getStatusCode());
		}
	}

	public String fetch(String slotName)
	{
		try
		{
			ResponseEntity<FetchResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/api/buckets/{0}", FetchResponse.class, slotName);
			if (!responseEntity.getStatusCode().is2xxSuccessful())
			{
				throw new RuntimeException("Fetch failed: " + responseEntity.getStatusCode());
			}

			return responseEntity.getBody().data;
		}
		catch (HttpClientErrorException ex)
		{
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
			{
				return null;
			}
			else
			{
				throw new RuntimeException("Fetch failed: " + ex.getStatusCode());
			}
		}
	}

	public static class PublishRequest
	{
		public String bucketName;

		public String data;

		public PublishRequest(String bucketName, String data)
		{
			this.bucketName = bucketName;
			this.data = data;
		}
	}

	public static class FetchResponse
	{
		public String bucketName;

		public String data;
	}


	public static class PublishResponse
	{
		public String bucketName;

		public String data;
	}
}
