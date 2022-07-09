package com.portal.journey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.model.Region;

/**
 * Configuration
 * @author santhoshkumardurairaj
 *
 */
@Configuration
public class DynamoDbConfig {
	/**
	 * Endpoint DynamoDB
	 */
	@Value("${aws.dynamodb.endpoint}")
	private String endPoint;
	
	/**
	 * AWS AccessKey
	 */
	@Value("${aws.dynamodb.accessKey}")
	private String awsAccessKey;
	
	/**
	 * AWS SecretKey
	 */
	@Value("${aws.dynamodb.secretKey}")
	private String awsSecretKey;

	/**
	 * Mapper Object DynamoDB
	 * @return
	 */
	@Bean
	public DynamoDBMapper dynamoDbMapper() {
		return new DynamoDBMapper(amazonDynamoDb());
	}

	/**
	 * AWS DynamoDB Connection
	 * @return
	 */
	private AmazonDynamoDB amazonDynamoDb() {
		// TODO Auto-generated method stub
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder
						.EndpointConfiguration(endPoint,Region.AP_Mumbai.getFirstRegionId()))
						.withCredentials(amazonDynamoDbCredentials()).build();
	}

	/**
	 * AWS Connection
	 * @return
	 */
	private AWSCredentialsProvider amazonDynamoDbCredentials() {
		// TODO Auto-generated method stub
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
	}
}
