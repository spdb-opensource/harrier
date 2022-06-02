package cn.spdb.harrier.server.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import cn.spdb.harrier.common.uitls.Symbol;
import cn.spdb.harrier.common.uitls.URI;
import io.netty.util.CharsetUtil;

public class JobStepBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long taskInstanceId;
	
	private String stepType;

	private byte stepNum;

	private String workdir;

	private URI stepUri;

	private LocalDateTime updateTime;
	
	private String cmd;

	private String pararmeter;

	private String envs;

	private String logPath;

	private int processId;

	private int exitId;

	private int timeOut;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public Byte getStepNum() {
		return stepNum;
	}

	public void setStepNum(Byte stepNum) {
		this.stepNum = stepNum;
	}

	public String getWorkdir() {
		return workdir;
	}

	public void setWorkdir(String workdir) {
		this.workdir = workdir;
	}

	public String getStepPath() {
		if (ObjectUtils.isEmpty(stepUri)) {
			return "";
		}
		return stepUri.getPath();
	}

	public URI getStepUri() {
		return stepUri;
	}

	public void setStepUri(URI stepUri) {
		this.stepUri = stepUri;
	}

	public void setStepNum(byte stepNum) {
		this.stepNum = stepNum;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmds) {
		this.cmd = cmds;
	}

	public String getPararmeter() {
		return pararmeter;
	}

	public void setPararmeter(String pararmeter) {
		this.pararmeter = pararmeter;
	}

	public String getEnvs() {
		return envs;
	}

	public void setEnvs(String envs) {
		this.envs = envs;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getExitId() {
		return exitId;
	}

	public void setExitId(int exitId) {
		this.exitId = exitId;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public HttpEntity getHttpEntityOfEnv() {
		try {
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			if (StringUtils.isNotEmpty(envs)) {
				for (String str : envs.split(Symbol.FEN_HAO)) {
					String[] strIndex = str.split(Symbol.DENG_YU_HAO, 2);
					if (strIndex.length != 2) {
						continue;
					}
					paramsList.add(new BasicNameValuePair(strIndex[0], strIndex[1]));
				}
				if (CollectionUtils.isEmpty(paramsList)) {
					return new StringEntity(envs);
				} else {
					envs = JSON.toJSONString(paramsList);
					return new UrlEncodedFormEntity(paramsList, CharsetUtil.UTF_8);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public NameValuePair[] getNameValuePairOfParameter() {
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (StringUtils.isNotEmpty(pararmeter)) {
			for (String str : pararmeter.split(Symbol.FEN_HAO)) {
				String[] strIndex = str.split(Symbol.DENG_YU_HAO, 2);
				if (strIndex.length != 2) {
					continue;
				}
				paramsList.add(new BasicNameValuePair(strIndex[0], strIndex[1]));
			}
		}
		return paramsList.toArray(new NameValuePair[paramsList.size()]);
	}

	public Map<String, String> getEnvMap() {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(envs)) {
			for (String str : envs.split(Symbol.FEN_HAO)) {
				String[] strIndex = str.split(Symbol.DENG_YU_HAO, 2);
				if (strIndex.length != 2) {
					continue;
				}
				map.put(strIndex[0], strIndex[1]);
			}
		}
		return map;
	}

	public List<String> getParameterList() {
		List<String> parameterList = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(pararmeter)) {
			StringTokenizer st = new StringTokenizer(pararmeter);
			while (st.hasMoreElements())
				parameterList.add(st.nextToken());
		}
		return parameterList;
	}

	public List<String> getCmdList() {
		List<String> cmdList = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(cmd)) {
			StringTokenizer st = new StringTokenizer(cmd);
			while (st.hasMoreElements())
				cmdList.add(st.nextToken());
		}
		return cmdList;
	}

	public Object[] getParameterObject() {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(pararmeter)) {
			for (String str : pararmeter.split(Symbol.FEN_HAO)) {
				String[] strIndex = str.split(Symbol.DENG_YU_HAO, 2);
				if (strIndex.length != 2) {
					continue;
				}
				Object object = null;
				switch (strIndex[0].toLowerCase()) {
				case "int":
					object = Integer.valueOf(strIndex[1]);
					break;
				case "byte":
					object = Byte.valueOf(strIndex[1]);
					break;
				case "short":
					object = Short.valueOf(strIndex[1]);
					break;
				case "long":
					object = Long.valueOf(strIndex[1]);
					break;
				case "double":
					object = Double.valueOf(strIndex[1]);
					break;
				case "float":
					object = Float.valueOf(strIndex[1]);
					break;
				case "string":
					object = String.valueOf(strIndex[1]);
					break;
				case "boolen":
					object = Boolean.valueOf(strIndex[1]);
				case "null":
					object = null;
					break;
				default:
					try {
						Class<?> clazz = Class.forName(strIndex[0]);
						if (ObjectUtils.isNotEmpty(clazz)) {
							object = JSON.parseObject(strIndex[1], clazz);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}
				list.add(object);
			}
		}
		return list.toArray(new Object[list.size()]);
	}

	public Class<?>[] getParameterClass() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		if (StringUtils.isNotEmpty(pararmeter)) {
			for (String str : pararmeter.split(Symbol.FEN_HAO)) {
				String[] strIndex = str.split(Symbol.DENG_YU_HAO, 2);
				if (strIndex.length != 2) {
					continue;
				}
				Class<?> clazz = null;
				switch (strIndex[0].toLowerCase()) {
				case "int":
					clazz = Integer.class;
					break;
				case "byte":
					clazz = Byte.class;
					break;
				case "short":
					clazz = Short.class;
					break;
				case "long":
					clazz = Long.class;
					break;
				case "double":
					clazz = Double.class;
					break;
				case "float":
					clazz = Float.class;
					break;
				case "string":
					clazz = String.class;
					break;
				case "boolen":
					clazz = Boolean.class;
				default:
					try {
						clazz = Class.forName(strIndex[0]);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}
				list.add(clazz);
			}
		}
		return list.toArray(new Class<?>[list.size()]);
	}

	public String getRunCmd() {
		StringJoiner joiner = new StringJoiner(Symbol.KONG_GE);
		if (ObjectUtils.isNotEmpty(cmd)) {
			StringTokenizer st = new StringTokenizer(cmd);
			while (st.hasMoreElements())
				joiner.add(st.nextToken());
		}
		if (StringUtils.isNotEmpty(getStepPath())) {
			joiner.add(getStepPath());
		}
		if (ObjectUtils.isNotEmpty(pararmeter)) {
			for (String para : pararmeter.split(Symbol.FEN_HAO))
				joiner.add(para);
		}
		return joiner.toString();
	}

//	public List<String> getRunCmdList() {
//		List<String> list = new ArrayList<String>();
//		if (ObjectUtils.isNotEmpty(cmd)) {
//			StringTokenizer st = new StringTokenizer(cmd);
//			while (st.hasMoreElements())
//				list.add(st.nextToken());
//		}
//		if (StringUtils.isNotEmpty(stepPath)) {
//			list.add(stepPath);
//		}
//		if (ObjectUtils.isNotEmpty(pararmeter)) {
//			StringTokenizer st = new StringTokenizer(pararmeter);
//			while (st.hasMoreElements())
//				list.add(st.nextToken());
//		}
//		return list;
//	}

	

	public void resetPararmeter(HashMap<String, String> gloParams) {
		StringJoiner joiner = new StringJoiner(Symbol.FEN_HAO);
		if (!CollectionUtils.isEmpty(gloParams)) {
			if (ObjectUtils.isNotEmpty(pararmeter)) {
				StringTokenizer st = new StringTokenizer(pararmeter, " \t\n\r\f;；");
				while (st.hasMoreElements()) {
					String value = st.nextToken();
					if (StringUtils.isNotBlank(value)) {
						String[] strIndex = value.split(Symbol.DENG_YU_HAO, 2);
						if (strIndex.length == 2) {
							if (StringUtils.isEmpty(strIndex[0])) {
								continue;
							}
							if (strIndex[0].startsWith("${") && strIndex[0].endsWith("}")) {
								String tmpVlaue = gloParams.get(strIndex[0]);
								if (StringUtils.isNotBlank(tmpVlaue)) {
									strIndex[0] = tmpVlaue;
								}
							}
							if (strIndex[1].startsWith("${") && strIndex[1].endsWith("}")) {
								String tmpVlaue = gloParams.get(strIndex[1]);
								if (StringUtils.isNotBlank(tmpVlaue)) {
									strIndex[1] = tmpVlaue;
								}
							}
							value = strIndex[0] + Symbol.DENG_YU_HAO + strIndex[1];
						} else {
							if (value.startsWith("${") && value.endsWith("}")) {
								String tmpVlaue = gloParams.get(value);
								if (StringUtils.isNotBlank(tmpVlaue)) {
									value = tmpVlaue;
								}
							}
						}
						joiner.add(value);
					}
				}
				pararmeter = joiner.toString();
			}
		}

	}

	public void resetEnvs(HashMap<String, String> gloEnvs) {
		StringJoiner joiner = new StringJoiner(Symbol.FEN_HAO);
		if (!CollectionUtils.isEmpty(gloEnvs)) {
			if (ObjectUtils.isNotEmpty(envs)) {
				StringTokenizer st = new StringTokenizer(envs, " \t\n\r\f;；");
				while (st.hasMoreElements()) {
					String value = st.nextToken();
					if (StringUtils.isNotBlank(value)) {
						String[] strIndex = value.split(Symbol.DENG_YU_HAO, 2);
						if (strIndex.length == 2) {
							if (StringUtils.isEmpty(strIndex[0])) {
								continue;
							}
							if (strIndex[0].startsWith("${") && strIndex[0].endsWith("}")) {
								String tmpVlaue = gloEnvs.get(strIndex[0]);
								if (StringUtils.isNotBlank(tmpVlaue)) {
									strIndex[0] = tmpVlaue;
								}
							}
							if (strIndex[1].startsWith("${") && strIndex[1].endsWith("}")) {
								String tmpVlaue = gloEnvs.get(strIndex[1]);
								if (StringUtils.isNotBlank(tmpVlaue)) {
									strIndex[1] = tmpVlaue;
								}
							}
							value = strIndex[0] + Symbol.DENG_YU_HAO + strIndex[1];
						} else {
							if (value.startsWith("${") && value.endsWith("}")) {
								String tmpVlaue = gloEnvs.get(value);
								if (StringUtils.isNotBlank(tmpVlaue)) {
									value = tmpVlaue;
								}
							}
						}
						joiner.add(value);
					}
				}
				envs = joiner.toString();
			}
		}
	}

	public void setStepPath(String path) {
		int i = path.indexOf("://");
		if (i >= 0) {
			stepUri = URI.valueOf(path);
		} else {
			stepUri = URI.valueOf("local:///" + path);
		}
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public long getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}
	
}
