package cn.spdb.harrier.rpc.code;

import java.io.IOException;
import java.util.List;

import cn.spdb.harrier.rpc.compress.Compress;
import cn.spdb.harrier.rpc.compress.CompressEnum;
import cn.spdb.harrier.rpc.protocol.ProtocolEventType;
import cn.spdb.harrier.rpc.protocol.ProtocolHeader;
import cn.spdb.harrier.rpc.protocol.RpcProtocol;
import cn.spdb.harrier.rpc.protocol.RpcProtocolConstants;
import cn.spdb.harrier.rpc.serializer.SerializeEnum;
import cn.spdb.harrier.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;

	public NettyDecoder(Class<?> genericClass) {
		super();
		this.genericClass = genericClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < RpcProtocolConstants.HEADER_LENGTH) {
			return;
		}
		in.markReaderIndex();
		int magic = in.readInt();
		if (RpcProtocolConstants.MAGIC != magic) {
			throw new IllegalArgumentException("magic number is illegal,magic:" + magic);
		}
		byte version = in.readByte();
		byte eventType = in.readByte();
		byte compressType = in.readByte();
		byte serialization = in.readByte();
		long requestId = in.readLong();
		int dataLength = in.readInt();
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);

		try {
			Compress compress = CompressEnum.getCompressByType(compressType);
			if (compress != null) {
				data = compress.unCompress(data);
				dataLength = data.length;
			}
		} catch (IOException e) {
			eventType = ProtocolEventType.COMPERESS_ERROR.getType();
			e.printStackTrace();
		}
		RpcProtocol<Object> protocol = new RpcProtocol<Object>();
		ProtocolHeader protocolHeader = new ProtocolHeader();
		protocolHeader.setEventType(eventType);
		protocolHeader.setVersion(version);
		protocolHeader.setMagic(magic);
		protocolHeader.setSerialization(serialization);
		protocolHeader.setRequestId(requestId);
		protocolHeader.setDataLength(dataLength);
		protocol.setProtocolHeader(protocolHeader);
		if (eventType != ProtocolEventType.HEARTBEAT.getType()) {
			Serializer serializable = SerializeEnum.getSerializerByType(serialization);
			Object body = serializable.deserialize(data, genericClass);
			protocol.setBody(body);
		}
		out.add(protocol);
	}

}
