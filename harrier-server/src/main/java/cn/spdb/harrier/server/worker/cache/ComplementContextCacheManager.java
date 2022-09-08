package cn.spdb.harrier.server.worker.cache;

import cn.spdb.harrier.server.worker.exec.ComplementRunable;

public interface ComplementContextCacheManager {


	ComplementRunable getByComplementId(Long complementId);

	void cacheComplementContext(ComplementRunable comRunable);

	void removeByComplementId(Long complementId);

	boolean updateComplementContext(ComplementRunable comRunable);

}