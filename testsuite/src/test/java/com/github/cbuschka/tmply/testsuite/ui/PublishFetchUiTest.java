package com.github.cbuschka.tmply.testsuite.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PublishFetchUiTest
{
	private long testNumber = System.currentTimeMillis();

	private WebDriver webDriver;
	private TmplyPage tmplyPage;

	@Before
	public void setUp()
	{
		webDriver = new ChromeDriver();
		tmplyPage = new TmplyPage(webDriver);
	}

	@Test
	public void unpublishedBucketIsEmpty()
	{
		String bucketName = "bucket#" + testNumber;

		tmplyPage.navigateTo();

		assertThat(tmplyPage.isBucketNameInputPresent(), is(true));

		tmplyPage.setBucketName(bucketName);

		tmplyPage.clickFetchButtonAndWaitForBucketEmptyMessage();

		assertThat(tmplyPage.getBucketData(), is(""));
	}

	@After
	public void shutdown()
	{
		webDriver.close();
	}
}
