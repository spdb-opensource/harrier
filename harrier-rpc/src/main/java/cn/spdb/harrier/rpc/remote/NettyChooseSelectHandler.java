package cn.spdb.harrier.rpc.remote;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.Environment;

import cn.spdb.harrier.common.utils.PropertyUtils;
import cn.spdb.harrier.rpc.code.NettyDecoder;
import cn.spdb.harrier.rpc.code.NettyEncoder;
import cn.spdb.harrier.rpc.common.RpcRequest;
import cn.spdb.harrier.rpc.protocol.RpcProtocolConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.LineSeparator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class NettyChooseSelectHandler extends ByteToMessageDecoder {

	private static boolean enable = PropertyUtils.getBoolean("harrier.http.server.enable", false);
	private static String telnet = PropertyUtils.getString("harrier.netty.server.password4", "spdbstart");



	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		if (isPass(in, telnet)) {
			ctx.pipeline().addLast("fram-decoder", new LineBasedFrameDecoder(80))
					.addLast("string-decodec", new StringDecoder(CharsetUtil.UTF_8))
					.addLast("string-encodec", new LineEncoder(LineSeparator.UNIX, CharsetUtil.UTF_8));
		} else {
			in.resetReaderIndex();
			if (in.readableBytes() < RpcProtocolConstants.HEADER_LENGTH) {
				return;
			}
			in.markReaderIndex();
			int magic = in.readInt();
			if (RpcProtocolConstants.MAGIC == magic) {
				ctx.pipeline().addLast("rpc-decoder", new NettyDecoder(RpcRequest.class))
						.addLast("rpc-encoder", new NettyEncoder())
						.addLast("server-idle-handler", new IdleStateHandler(0, 0, 1000 * 180, TimeUnit.MILLISECONDS));

			} else {
				if (enable) {
					ctx.pipeline().addLast("http-codec", new HttpServerCodec())
							.addLast(new HttpObjectAggregator(1024 * 50))
							.addLast("http-chunked", new ChunkedWriteHandler())
							.addLast("compressor",new HttpContentCompressor())
							.addLast("ws-aggregator", new WebSocketFrameAggregator(1024 * 50))
							.addLast("ws-protocol-handler", new WebSocketServerProtocolHandler("/ws"));
				} else {
					ctx.close();
					return;
				}
			}
			in.resetReaderIndex();
		}
		ctx.pipeline().addLast("server-handler", new NettyServerHandler()).remove(this);

	}

	private boolean isPass(ByteBuf in, String mod) {
		int length = in.readableBytes();
		int readLength = mod.getBytes().length;
		if (length > readLength && readLength > 0) {
			length = readLength;
		} else {
			return false;
		}
		byte[] content = new byte[length];
		in.readBytes(content);
		return mod.equals(new String(content));
	}
}
