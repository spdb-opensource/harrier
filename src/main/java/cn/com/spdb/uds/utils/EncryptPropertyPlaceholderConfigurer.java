package cn.com.spdb.uds.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (propertyName.contains("password")&&propertyValue.length()==16&&propertyValue.matches("[0-9a-z]+")) {
			IceKey ikey = new IceKey(0);
			/**解密*/
			propertyValue = ikey.decode(propertyValue, propertyName);
		}
		return propertyValue;
	}

}
