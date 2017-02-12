package com.github.cbuschka.tmply.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "bucket")
public class BucketEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String bucketName;

	@Column(name = "data")
	private String data;

	@Column(name = "expiry_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryTime;

	public BucketEntity() {}

	public BucketEntity(String bucketName, String data, Date expiryTime)
	{
		this.bucketName = bucketName;
		this.data = data;
		this.expiryTime = expiryTime;
	}

	public String getBucketName()
	{
		return bucketName;
	}

	public void setBucketName(String bucketName)
	{
		this.bucketName = bucketName;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getData()
	{
		return data;
	}

	public void setExpiryTime(Date expiryTime)
	{
		this.expiryTime = expiryTime;
	}

	public Date getExpiryTime()
	{
		return expiryTime;
	}
}
