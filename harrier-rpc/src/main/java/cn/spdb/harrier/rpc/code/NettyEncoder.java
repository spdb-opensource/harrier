package cn.spdb.harrier.rpc.code;

import cn.spdb.harrier.rpc.compress.Compress;
import cn.spdb.harrier.rpc.compress.CompressEnum;
import cn.spdb.harrier.rpc.protocol.ProtocolHeader;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import cn.spdb.harrier.rpc.serializer.SerializeEnum;
import cn.spdb.harrier.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
		ProtocolHeader protocolHeader = msg.getProtocolHeader();
		out.writeInt(protocolHeader.getMagic());
		out.writeByte(protocolHeader.getVersion());
		out.writeByte(protocolHeader.getEventType());
		byte compressType = protocolHeader.getCompressType();
		out.writeByte(compressType);
		out.writeByte(protocolHeader.getSerialization());
		out.writeLong(protocolHeader.getRequestId());
		byte[] data = new byte[0];
		int dataLength = 0;
		Serializer serializable = SerializeEnum.getSerializerByType(protocolHeader.getSerialization());
		if (serializable != null) {
			data = serializable.serialize(msg.getBody());
			dataLength = data.length;
		}

		Compress compress = CompressEnum.getCompressByType(compressType);
		if (compress != null) {
			data = compress.compress(data);
			dataLength = data.length;
		}
		out.writeInt(dataLength);
		out.writeBytes(data);

	}

}
