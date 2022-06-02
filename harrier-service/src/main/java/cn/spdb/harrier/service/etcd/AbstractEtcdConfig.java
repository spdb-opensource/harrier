package cn.spdb.harrier.service.etcd;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent.EventType;

public abstract class AbstractEtcdConfig {

	@Autowired
	private EtcdOperator etcdOperator;

	private String etcd_config_prefix;

	protected String getEtcdConfgPrefix() {
		this.etcd_config_prefix = this.getClass().getSuperclass().getName();
		return etcd_config_prefix;
	}

	@PostConstruct
	public void init() {
		etcd_config_prefix = getEtcdConfgPrefix();
		ByteSequence prefix = ByteSequence.from(etcd_config_prefix, StandardCharsets.UTF_8);
		GetOption getOption = GetOption.newBuilder().withRange(prefix).build();
		CompletableFuture<GetResponse> getFuture = etcdOperator.getKv().get(prefix, getOption);
		try {
			getFuture.get().getKvs().forEach(action -> {
				String filedStr = action.getKey().toString(StandardCharsets.UTF_8).replaceFirst(etcd_config_prefix, "");
				String valueStr = action.getValue().toString(StandardCharsets.UTF_8);
				load(filedStr, valueStr);
			});
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		WatchOption watchOption = WatchOption.newBuilder().withRange(prefix).withNoDelete(false).build();
		etcdOperator.getWatch().watch(prefix, watchOption, onNext -> {
			onNext.getEvents().forEach(action -> {
				if (action.getEventType().equals(EventType.PUT)) {
					// update
					KeyValue keyValue = action.getKeyValue();
					String filedStr = keyValue.getKey().toString(StandardCharsets.UTF_8)
							.replaceFirst(etcd_config_prefix, "");
					String valueStr = keyValue.getValue().toString(StandardCharsets.UTF_8);
					load(filedStr, valueStr);
				}
			});
		});
	}

	private void load(String filedStr, String valueStr) {
		try {
			Field field = this.getClass().getField(filedStr);
			if (field == null) {
				return;
			}
			if (!Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
				Object value = JSON.parseObject(valueStr, field.getType());
				try {
					field.set(null, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

}
