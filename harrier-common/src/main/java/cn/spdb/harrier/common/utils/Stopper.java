
package cn.spdb.harrier.common.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  if the process closes, a signal is placed as true, and all threads get this flag to stop working
 */
public class Stopper {

	private static AtomicBoolean signal = new AtomicBoolean(false);
	
	public static final boolean isStopped(){
		return signal.get();
	}
	
	public static final boolean isRunning(){
		return !signal.get();
	}
	
	public static final void stop(){
		signal.set(true);
	}
}
