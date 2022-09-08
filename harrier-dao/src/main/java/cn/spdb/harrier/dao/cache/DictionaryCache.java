package cn.spdb.harrier.dao.cache;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.spdb.harrier.dao.entity.MDictionary;
import cn.spdb.harrier.dao.mapper.MDictionaryMapper;
import cn.spdb.harrier.dao.utils.BeanContext;

@Component
public class DictionaryCache {

	private ConcurrentHashMap<String, ConcurrentHashMap<String, MDictionary>> hashMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, MDictionary>>();

	@Autowired
	private MDictionaryMapper dictionaryMapper;

	public static DictionaryCache getInstance() {
		return BeanContext.getBean(DictionaryCache.class);
	}

	@PostConstruct
	public void load() {
		List<MDictionary> dictionaries = dictionaryMapper.select(c -> c);
		for (MDictionary dictionary : dictionaries) {
			addDictionary(dictionary);
		}
	}

	public void addDictionary(MDictionary dictionary) {
		ConcurrentHashMap<String, MDictionary> concurrentHashMap = hashMap.get(dictionary.getDicCode());
		if (concurrentHashMap == null) {
			concurrentHashMap = new ConcurrentHashMap<String, MDictionary>();
			hashMap.put(dictionary.getDicCode(), concurrentHashMap);
		}
		concurrentHashMap.put(dictionary.getDicKey(), dictionary);
	}

	public ConcurrentHashMap<String, MDictionary> getCodeMap(String code) {
		return hashMap.get(code);
	}

	public Collection<MDictionary> getMDictionaryColl(String code) {
		return getCodeMap(code).values();
	}

	public MDictionary getMDictionary(String code, String key) {
		ConcurrentHashMap<String, MDictionary> concurrentHashMap = hashMap.get(code);
		if (concurrentHashMap != null) {
			return concurrentHashMap.get(key);
		} else {
			MDictionary dictionary = dictionaryMapper.selectMDictionary(code, key);
			if (dictionary != null) {
				addDictionary(dictionary);
			}
		}
		return null;
	}

	public String getMDictionaryValue(String code, String key) {
		MDictionary dictionary = getMDictionary(code, key);
		if (dictionary != null) {
			return dictionary.getDicValue();
		}
		return "";
	}
}
