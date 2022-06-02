package cn.spdb.harrier.service.aws;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import cn.spdb.harrier.service.aws.config.AwsConfiguration;

@Component
public class AwsOperator {

	@Autowired
	private AwsConfiguration awsConfiguration;

	private AmazonS3 amazonS3;

	private TransferManager transferManager;

	@PostConstruct
	public void init() {
		ClientConfiguration s3config = new ClientConfiguration();
		if (StringUtils.isEmpty(awsConfiguration.getServiceEndpoint())) {
			return;
		}
		// 创建S3客户端
		AmazonS3ClientBuilder builder = AmazonS3Client.builder();
		builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
				awsConfiguration.getServiceEndpoint(), Regions.US_EAST_1.getName()));
		builder.setCredentials(new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(awsConfiguration.getAccessKey(), awsConfiguration.getSecretKey())));
		builder.setPathStyleAccessEnabled(false);
		builder.setClientConfiguration(s3config);
		amazonS3 = builder.build();
		transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
	}

	public Upload upload(String bucket, String s3ObjectKey, File file) {
		Upload upload = transferManager.upload(bucket, s3ObjectKey, file);
		return upload;
	}

	public Download download(String bucket, String s3ObjectKey, File file) {
		GetObjectRequest readObject = new GetObjectRequest(bucket, s3ObjectKey);
		Download download = transferManager.download(readObject, file);
		return download;
	}

	public ObjectListing selectList(String bucket, String s3ObjectKeyPrefix) {
		ObjectListing list = amazonS3.listObjects(bucket, s3ObjectKeyPrefix);
		return list;
	}

	public List<Bucket> selectBucket() {
		return amazonS3.listBuckets();
	}

	public void delect(String bucket, String s3ObjectKey) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, s3ObjectKey);
		amazonS3.deleteObject(deleteObjectRequest);
	}

	public Boolean upload(String s3ObjectKey, String fileName) {
		File file = new File(fileName);
		if (!file.exists() || file.isDirectory()) {
			return false;
		}
		return upload(awsConfiguration.getBucket(), s3ObjectKey, file).isDone();
	}

	public Boolean download(String s3ObjectKey, String fileName) {
		return download(awsConfiguration.getBucket(), s3ObjectKey, new File(fileName)).isDone();
	}

	public List<String> selectList(String s3ObjectKeyPrefix) {
		ObjectListing objectListing = selectList(awsConfiguration.getBucket(), s3ObjectKeyPrefix);
		return objectListing.getObjectSummaries().stream().map(map -> map.getBucketName()).collect(Collectors.toList());

	}

	public AwsConfiguration getAwsConfiguration() {
		return awsConfiguration;
	}

	public void setAwsConfiguration(AwsConfiguration awsConfiguration) {
		this.awsConfiguration = awsConfiguration;
	}

	public AmazonS3 getAmazonS3() {
		return amazonS3;
	}

	public void setAmazonS3(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public TransferManager getTransferManager() {
		return transferManager;
	}

	public void setTransferManager(TransferManager transferManager) {
		this.transferManager = transferManager;
	}

	@PreDestroy
	public void shoutDown() {
		if (ObjectUtils.isEmpty(transferManager)) {
			transferManager.shutdownNow();
		}
		if (ObjectUtils.isEmpty(amazonS3)) {
			amazonS3.shutdown();
		}
	}
}
