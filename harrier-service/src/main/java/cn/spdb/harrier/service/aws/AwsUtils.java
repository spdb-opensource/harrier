package cn.spdb.harrier.service.aws;

import org.apache.commons.lang3.ObjectUtils;

import com.amazonaws.AmazonClientException;

import cn.spdb.harrier.common.utils.URI;
import cn.spdb.harrier.dao.utils.BeanContext;

public class AwsUtils {

	public static Boolean load(URI uri, String fileName) {
		AwsOperator awsOperator = BeanContext.getBean(AwsOperator.class);
		if (ObjectUtils.isNotEmpty(awsOperator) && ObjectUtils.isEmpty(uri.getHost())) {
			String s3ObjectKey = uri.getPath();
			try {
				return awsOperator.download(s3ObjectKey, fileName);
			} catch (AmazonClientException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static byte[] load(URI uri, Long pos, Long size) {
		AwsOperator awsOperator = BeanContext.getBean(AwsOperator.class);
		if (ObjectUtils.isNotEmpty(awsOperator) && ObjectUtils.isEmpty(uri.getHost())) {
			String s3ObjectKey = uri.getPath();
			return awsOperator.downloadObject(null, s3ObjectKey, pos, size);
		}
		return null;
	}

}
