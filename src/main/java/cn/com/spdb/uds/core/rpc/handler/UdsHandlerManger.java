package cn.com.spdb.uds.core.rpc.handler;

import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.spdb.uds.UdsConstant;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClient;
import cn.com.spdb.uds.core.rpc.client.UdsRpcClientManager;
import cn.com.spdb.uds.core.rpc.event.RpcAttrKey;
import cn.com.spdb.uds.core.rpc.event.RpcCommand;
import cn.com.spdb.uds.core.rpc.event.RpcResultCode;
import cn.com.spdb.uds.core.rpc.event.UdsRpcEvent;
import cn.com.spdb.uds.utils.ClassUtils;
import cn.com.spdb.uds.utils.NameThreadFactory;
import cn.com.spdb.uds.utils.ClassUtils.ClassFilter;

public class UdsHandlerManger {

	public static UdsHandlerManger instance;

	private static Logger LOGGER = LoggerFactory.getLogger(UdsHandlerManger.class.getSimpleName());

	private static final Object KEY = new Object();

	public static UdsHandlerManger getInstance() {
		synchronized (KEY) {
			if (instance == null) {
				instance = new UdsHandlerManger();
				instance.init();
			}
		}
		return instance;
	}

	/** 服务处理 */
	private ConcurrentHashMap<Integer, UdsRpcHandler> SERVER_HANDLERS = new ConcurrentHashMap<Integer, UdsRpcHandler>();

	/** 固定线程池 */
	private ThreadPoolExecutor executorReceive = new ThreadPoolExecutor(30, 30, 10000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(), new NameThreadFactory(UdsHandlerManger.class.getSimpleName()));
	private ThreadPoolExecutor executorSend = new ThreadPoolExecutor(20, 20, 10000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(), new NameThreadFactory(UdsHandlerManger.class.getSimpleName()));

	/**
	 * Task 发送消息封装
	 */
	private TaskPoolFactory taskPoolFactory;
	/**
	 * 发送消息生产
	 */
	private SendProducer sendProducer;

	/**
	 * 接受消息生产消费
	 */
	private ReceiveProducer receiveProducer;

	public UdsRpcHandler getServerRpcEventHandler(int command) {
		return SERVER_HANDLERS.get(command);
	}

	private void init() {
		/** 发生消息封装池 */
		taskPoolFactory = new TaskPoolFactory(20, 60);
		/** 发送消息生成者 */
		sendProducer = new SendProducer();
		new NameThreadFactory(UdsHandlerManger.class.getSimpleName() + "-sendProducer").newDeamonThread(sendProducer)
				.start();
		/** 接受消息生产者 */
		receiveProducer = new ReceiveProducer();
		new NameThreadFactory(UdsHandlerManger.class.getSimpleName() + "-receiveProducer")
				.newDeamonThread(receiveProducer).start();
		/**
		 * 扫包加载 SERVER_HANDLERS
		 */
		String packPath = this.getClass().getPackage().getName();
		LOGGER.info("scanPackage : " + packPath);
		ClassUtils.scanPackage(packPath, new ClassFilter() {
			@Override
			public boolean actionFilter(Class<?> clazz) {
				int modifers = clazz.getModifiers();
				if (Modifier.isAbstract(modifers) || Modifier.isInterface(modifers)) {
					return false;
				}
				if (ServerRpcEventHandler.class.isAssignableFrom(clazz)) {
					RpcEventProtocol rpcEventProtocol = clazz.getAnnotation(RpcEventProtocol.class);
					if (rpcEventProtocol == null) {
						return false;
					}
					int command = rpcEventProtocol.value();
					UdsRpcHandler handler = SERVER_HANDLERS.get(command);

					if (handler != null) {
						LOGGER.error("handler is exist please check command: " + command + " class: " + clazz);
						return false;
					}
					try {
						handler = (UdsRpcHandler) clazz.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					LOGGER.info("handler  load command: " + command + " class: " + clazz);
					SERVER_HANDLERS.put(command, handler);
				}
				return true;
			}
		});
	}

	/**
	 * 发送消息事件添加生产，等待处理
	 * 
	 * @param event
	 */
	public void addSendProducerTask(UdsRpcClient rpcClient, UdsRpcEvent udsRpcEvent, Object object) {
		sendProducer.addTask(rpcClient, udsRpcEvent, object);
	}

	/**
	 * 接受消息事件添加生产，等待处理
	 * 
	 * @param event
	 */
	public void addReceiveProducerRpcEvent(UdsRpcEvent event) {
		receiveProducer.addEvent(event);
	}

	/**
	 * 停止接受消息处理
	 */
	public void stopReceiveProducerRpcEvent() {
		receiveProducer.stopRuning();

	}

	/**
	 * 发送消息封装
	 * 
	 * @author T-luzl
	 *
	 */
	private class Task {
		private UdsRpcClient rpcClient;
		private UdsRpcEvent udsRpcEvent;
		private Object object;

		public UdsRpcClient getRpcClient() {
			return rpcClient;
		}

		public void setRpcClient(UdsRpcClient rpcClient) {
			this.rpcClient = rpcClient;
		}

		public UdsRpcEvent getRpcEvent() {
			return udsRpcEvent;
		}

		public void setRpcEvent(UdsRpcEvent udsRpcEvent) {
			this.udsRpcEvent = udsRpcEvent;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		/**
		 * 释放资源
		 */
		public void releaseTask() {
			this.object = null;
			this.rpcClient = null;
			this.udsRpcEvent = null;
		}
	}

	/**
	 * 消息封装池
	 * 
	 * @author T-luzl
	 *
	 */
	private class TaskPoolFactory {
		/** 容量 */
		private int MAX_TOTAL = 50;

		private LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<UdsHandlerManger.Task>();

		public TaskPoolFactory(int minToatl, int maxTotal) {
			this.MAX_TOTAL = maxTotal;

			for (int i = 0; i < minToatl; i++) {
				queue.add(new Task());
			}
		}

		/**
		 * 归还Task
		 */
		public void returnTask(Task task) {
			if (this.queue.size() < MAX_TOTAL) {
				task.releaseTask();
				queue.offer(task);
			}
		}

		/**
		 * 借用Task
		 */
		public Task borrowTask() {
			Task task = queue.poll();
			if (task == null) {
				task = new Task();
			}
			return task;
		}
	}

	/**
	 * 发送消息，生成者
	 * 
	 * @author T-luzl
	 *
	 */
	private class SendProducer implements Runnable {

		private LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<UdsHandlerManger.Task>();

		public void addTask(UdsRpcClient rpcClient, UdsRpcEvent udsRpcEvent, Object object) {
			Task task = taskPoolFactory.borrowTask();
			if (task == null) {
				task = new Task();
			}
			task.setRpcEvent(udsRpcEvent);
			task.setRpcClient(rpcClient);
			task.setObject(object);
			queue.add(task);
		}

		@Override
		public void run() {
			while (true) {
				try {
					Task task = null;
					task = queue.take();
					if (task != null) {
						executorSend.submit(new SendConsumer(task));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送消息消费者
	 * 
	 * @author T-luzl
	 *
	 */
	private class SendConsumer implements Runnable {

		private Task task;

		public SendConsumer(Task task) {
			this.task = task;
		}

		@Override
		public void run() {
			try {
				if (task == null) {
					return;
				}
				UdsRpcEvent udsRpcEvent = task.getRpcEvent();
				UdsRpcClient rpcClient = task.getRpcClient();

				if (udsRpcEvent == null || rpcClient == null) {
					LOGGER.error("udsRpcEvent or rpcClient is null");
					return;
				}

				UdsRpcHandler handler = getServerRpcEventHandler(udsRpcEvent.getCommand());
				if (handler == null) {
					LOGGER.error("handler is null check command" + udsRpcEvent.getCommand());
					return;
				}
				// 发送节点是否存活
				if (!rpcClient.isActive()) {
					// 不是心跳协议和注册协议直接返回错误
					if (udsRpcEvent.getCommand() == RpcCommand.SERVER_HEARTBEAT
							|| udsRpcEvent.getCommand() == RpcCommand.SERVER_REGISTER) {
						LOGGER.error("udsRpcClient is not  active ,RpcCommand.SERVER_HEARTBEAT recover active :"
								+ rpcClient.toString());
					} else {
						LOGGER.error("udsRpcClient is not  active please cheak udsRpcClient: :" + rpcClient.toString());
						// 其他协议直接返回错误
						if (handler instanceof ServerRpcEventCallBack) {
							ServerRpcEventCallBack callBack = (ServerRpcEventCallBack) handler;
							UdsRpcEvent callBackEvent = udsRpcEvent.callBackEvent();
							callBackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
							callBack.callback(callBackEvent);
						}
						return;
					}
				}
				// 是否是
				if (handler instanceof ServerRpcEventHandler) {
					// 发送前信息处理
					ServerRpcEventHandler serverRpcEventHandler = (ServerRpcEventHandler) handler;
					serverRpcEventHandler.sendHandle(udsRpcEvent, task.getObject());
				} else {
					LOGGER.error("not handler message deal with");
					return;
				}
				// 序列化
				udsRpcEvent.outputSerialize();
				if (udsRpcEvent.getCommand() != RpcCommand.SERVER_HEARTBEAT) {
					LOGGER.info("rpc SendConsumer send event :" + udsRpcEvent.toString());
				}
				rpcClient.write(udsRpcEvent);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				taskPoolFactory.returnTask(task);
			}
		}
	}

	/**
	 * 接受消息生成者
	 * 
	 * @author T-luzl
	 *
	 */
	private class ReceiveProducer implements Runnable {

		/** 消息队列 */
		private LinkedBlockingQueue<UdsRpcEvent> events = new LinkedBlockingQueue<UdsRpcEvent>();
		/** 运行状态 */
		private AtomicBoolean runStatus = new AtomicBoolean(true);

		/** 添加事件 */
		public void addEvent(UdsRpcEvent event) {
			events.add(event);
		}

		public void stopRuning() {
			runStatus.getAndSet(false);
			events.clear();
		}

		@Override
		public void run() {
			while (runStatus.get()) {
				try {
					UdsRpcEvent event = events.take();
					if (event != null) {
						// 提交执行
						executorReceive.submit(new ReceiveConsumer(event));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 接受消息消费者
	 * 
	 * @author T-luzl
	 *
	 */
	private class ReceiveConsumer implements Runnable {

		private UdsRpcEvent event;

		public ReceiveConsumer(UdsRpcEvent event) {
			this.event = event;
		}

		@Override
		public void run() {
			if (event == null) {
				return;
			}
			final UdsRpcHandler handler = getServerRpcEventHandler(event.getCommand());
			if (handler == null) {
				LOGGER.error("handler is null check command" + event.getCommand());
				return;
			}
			// 接受目标不是我
			if (!event.getTargetId().equals(UdsConstant.SERVER_NAME)) {
				LOGGER.error("not send me  event：" + event.toString());
				return;
			}
			UdsRpcClient rpcClient = UdsRpcClientManager.getInstance().getUdsRpcClient(event.getSourceId());
			// 注册协议通过，其他协议没有客服端则拦截
			if (rpcClient == null && event.getCommand() != RpcCommand.SERVER_REGISTER) {
				LOGGER.error("rpcClient  is null ,not register local server ,event：" + event.toString());
				return;
			}
			if (rpcClient != null) {
				rpcClient.updateMillisTime();
			}
			if (!event.isCallBack() && handler instanceof ServerRpcEventHandler) {
				receiveServerEvent((ServerRpcEventHandler) handler, event, rpcClient);
			} else if (event.isCallBack() && handler instanceof ServerRpcEventCallBack) {
				callbackServerEvent((ServerRpcEventCallBack) handler, event);
			} else {
				LOGGER.error("rpc receive  Event is unknow " + event.toString());
			}
		}

		/**
		 * 接到事件处理
		 * 
		 * @param handler
		 * @param event
		 */
		private void receiveServerEvent(ServerRpcEventHandler handler, UdsRpcEvent event, UdsRpcClient rpcClient) {

			if (event.getCommand() != RpcCommand.SERVER_HEARTBEAT) {
				LOGGER.info("rpc receive event： " + event.toString());
			}
			/** 反序列 */
			event.inputSerialize();
			/** 接受处理 */
			UdsRpcEvent callBackEvent = handler.receiveHandle(event);

			/** 返回信息 */
			if (callBackEvent != null && handler instanceof ServerRpcEventCallBack) {
				/** 序列化 */
				callBackEvent.outputSerialize();
				if (callBackEvent.getCommand() != RpcCommand.SERVER_HEARTBEAT) {
					LOGGER.info("rpc send callBackEvent " + callBackEvent.toString());
				}

				/** 发送回调事件 */
				rpcClient.write(callBackEvent);
			}
		}

		/**
		 * 回调处理
		 * 
		 * @param handler
		 * @param event
		 */
		private void callbackServerEvent(ServerRpcEventCallBack handler, UdsRpcEvent event) {
			// 回调接收的信息
			if (event.getCommand() != RpcCommand.SERVER_HEARTBEAT) {
				LOGGER.info("rpc receive CallBackEvent " + event.toString());
			}
			/** 反序列 */
			event.inputSerialize();
			/** 回调处理 */
			ServerRpcEventCallBack callBack = (ServerRpcEventCallBack) handler;
			callBack.callback(event);
		}
	}

	/**
	 * 同步处理发送模块
	 * 
	 * @param event
	 * @return
	 */
	public UdsRpcEvent sendServerConcurrentEvent(UdsRpcEvent udsRpcEvent, UdsRpcClient rpcClient, Object object) {

		UdsRpcHandler handler = getServerRpcEventHandler(udsRpcEvent.getCommand());
		if (handler == null) {
			LOGGER.error("handler is null check command" + udsRpcEvent.getCommand());
			return null;
		}
		RpcEventProtocol rpcEventProtocol = handler.getClass().getAnnotation(RpcEventProtocol.class);
		int concurrent = rpcEventProtocol.concurrent();
		if (concurrent != 1) {
			LOGGER.error("handler is not Concurrent check command" + udsRpcEvent.getCommand());
			return null;
		}
		// 发送节点是否存活
		if (!rpcClient.isActive()) {
			LOGGER.error("udsRpcClient is not  active please cheak udsRpcClient: :" + rpcClient.toString());
			// 其他协议直接返回错误
			if (handler instanceof ServerRpcEventCallBack) {
				ServerRpcEventCallBack callBack = (ServerRpcEventCallBack) handler;
				UdsRpcEvent callBackEvent = udsRpcEvent.callBackEvent();
				callBackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
				callBack.callback(callBackEvent);
				return callBackEvent;
			}
		}
		// 是否
		if (handler instanceof ServerRpcEventHandler) {
			// 发送前信息处理
			ServerRpcEventHandler serverRpcEventHandler = (ServerRpcEventHandler) handler;
			serverRpcEventHandler.sendHandle(udsRpcEvent, object);
		} else {
			LOGGER.error("not handler message deal with");
			return null;
		}
		// 序列化
		udsRpcEvent.outputSerialize();
		if (udsRpcEvent.getCommand() != RpcCommand.SERVER_HEARTBEAT) {
			LOGGER.info("rpc send Server Concurrent event :" + udsRpcEvent.toString());
		}
		// 发送等待响应
		UdsRpcEvent callBackEvent = rpcClient.writeConcurrent(udsRpcEvent);
		// 处理响应
		if (callBackEvent != null && handler instanceof ServerRpcEventCallBack) {
			ServerRpcEventCallBack callBack = (ServerRpcEventCallBack) handler;
			// 反序列化
			callBackEvent.inputSerialize();
			callBack.callback(callBackEvent);
		}
		LOGGER.info("rpc call Back Concurrent event :" + udsRpcEvent.toString());
		return callBackEvent;
	}

	/**
	 * 同步处理发送模块
	 * 
	 * @param event
	 * @return
	 */
	public UdsRpcEvent receiveServerConcurrentEvent(UdsRpcEvent udsRpcEvent) {
		final UdsRpcHandler handler = getServerRpcEventHandler(udsRpcEvent.getCommand());
		UdsRpcEvent callBackEvent = udsRpcEvent.callBackEvent();
		callBackEvent.addAttribute(RpcAttrKey.CODE, RpcResultCode.ERROR);
		if (handler == null) {
			LOGGER.error("handler is null check command" + udsRpcEvent.getCommand());
			return callBackEvent;
		}
		RpcEventProtocol rpcEventProtocol = handler.getClass().getAnnotation(RpcEventProtocol.class);
		int concurrent = rpcEventProtocol.concurrent();
		if (concurrent != 1) {
			LOGGER.error("handler is not Concurrent check command" + udsRpcEvent.getCommand());
			return callBackEvent;
		}

		// 接受目标不是我
		if (!udsRpcEvent.getTargetId().equals(UdsConstant.SERVER_NAME)) {
			LOGGER.error("not send me  event：" + udsRpcEvent.toString());
			return callBackEvent;
		}
		/** 反序列 */
		udsRpcEvent.inputSerialize();
		// 是否
		if (handler instanceof ServerRpcEventHandler) {
			// 接受信息处理
			ServerRpcEventHandler serverRpcEventHandler = (ServerRpcEventHandler) handler;
			callBackEvent = serverRpcEventHandler.receiveHandle(udsRpcEvent);
		}
		// 序列化
		callBackEvent.outputSerialize();
		return callBackEvent;
	}

	public ThreadPoolExecutor getExecutorReceive() {
		return executorReceive;
	}

	public void setExecutorReceive(ThreadPoolExecutor executorReceive) {
		this.executorReceive = executorReceive;
	}

	public ThreadPoolExecutor getExecutorSend() {
		return executorSend;
	}

	public void setExecutorSend(ThreadPoolExecutor executorSend) {
		this.executorSend = executorSend;
	}

}
