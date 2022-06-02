package cn.spdb.harrier.service.aws;

import org.apache.commons.lang3.ObjectUtils;

import cn.spdb.harrier.common.uitls.URI;
import cn.spdb.harrier.dao.utils.BeanContext;

public class AwsUtils {

	public static Boolean load(URI uri, String fileName) {
		AwsOperator awsOperator = BeanContext.getBean(AwsOperator.class);
		if (ObjectUtils.isNotEmpty(awsOperator) && ObjectUtils.isEmpty(uri.getHost())) {
			String s3ObjectKey = uri.getPath();
			return awsOperator.download(s3ObjectKey, fileName);
		} 
		return false;
	}
}
