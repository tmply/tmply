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

	@Before
	public void setUp()
	{
		webDriver = new ChromeDriver();
	}

	@Test
	public void unpublishedBucketIsEmpty()
	{
		webDriver.get("http://localhost:8081/");

		WebElement bucketNameInput = getBucketNameInput();
		assertThat(bucketNameInput.isDisplayed(), is(true));

		String bucketName = "bucket#" + testNumber;
		bucketNameInput.sendKeys(bucketName);

		WebElement fetchButton = getFetchButton();
		fetchButton.click();

		waitForBucketEmptyMessage();
	}

	private void waitForBucketEmptyMessage()
	{
		WebElement messagePanel = getMessagePanel();

		WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 3);
		webDriverWait.until(ExpectedConditions.textToBePresentInElement(messagePanel, "No such bucket."));
	}

	private WebElement getMessagePanel()
	{
		return webDriver.findElement(By.id("messagePanel"));
	}

	private WebElement getFetchButton()
	{
		return webDriver.findElement(By.id("fetchButton"));
	}

	private WebElement getBucketNameInput()
	{
		return webDriver.findElement(By.id("bucketNameInput"));
	}

	private WebElement getBucketDataInput()
	{
		return webDriver.findElement(By.id("bucketDataInput"));
	}

	@After
	public void shutdown()
	{
		webDriver.close();
	}
}
