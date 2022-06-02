package cn.spdb.harrier.api.utils;

public enum JobDeployPath {
	// 路径相关
	UPLOAD_SCRIPT_PATH("脚本上传路径", "./job/project/dev"),
	COMPRESS_TEMP_PATH("压缩临时路径", "./job/project/compress"),
	UPLOAD_DEPLOY_TEMP_PATH("导入工程临时路径", "./job/project/deploy");
	
	// 路径名称
	private String key;
	// 路径值
	private String value;
	
	// 构造
	private JobDeployPath(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}
