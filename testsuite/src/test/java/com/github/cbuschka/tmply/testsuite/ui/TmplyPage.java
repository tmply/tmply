package com.github.cbuschka.tmply.testsuite.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TmplyPage
{
	private WebDriver webDriver;

	public TmplyPage(WebDriver webDriver)
	{
		this.webDriver = webDriver;
	}

	public void navigateTo()
	{
		webDriver.get("http://localhost:8081/");
	}

	public boolean isBucketNameInputPresent()
	{
		WebElement bucketNameInput = getBucketNameInput();
		return bucketNameInput.isDisplayed();
	}

	public void setBucketName(String bucketName)
	{
		WebElement bucketNameInput = getBucketNameInput();
		bucketNameInput.sendKeys(bucketName);
	}

	public String getBucketData()
	{
		WebElement bucketDataInput = getBucketDataInput();
		return bucketDataInput.getText();
	}

	public void clickFetchButtonAndWaitForBucketEmptyMessage()
	{
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
}
