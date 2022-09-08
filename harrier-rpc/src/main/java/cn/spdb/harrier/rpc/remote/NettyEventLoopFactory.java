package cn.spdb.harrier.rpc.remote;

import java.util.concurrent.ThreadFactory;

import cn.spdb.harrier.common.utils.NameThreadFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyEventLoopFactory {
	public static EventLoopGroup eventLoopGroup(int threads, String threadFactoryName) {
		ThreadFactory threadFactory = new NameThreadFactory(threadFactoryName);
		return shouldEpoll() ? new EpollEventLoopGroup(threads, threadFactory)
				: new NioEventLoopGroup(threads, threadFactory);
	}

	public static Class<? extends SocketChannel> socketChannelClass() {
		return shouldEpoll() ? EpollSocketChannel.class : NioSocketChannel.class;
	}

	public static Class<? extends ServerSocketChannel> serverSocketChannelClass() {
		return shouldEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
	}

	private static boolean shouldEpoll() {
		if (!System.getProperty("os.name").toLowerCase().contains("linux")) {
			return false;
		}
		if (!Epoll.isAvailable()) {
			return false;
		}
		return true;
	}
}
