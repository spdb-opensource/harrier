package cn.com.spdb.uds.background.socket;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.spdb.uds.log.LogEvent;
import cn.com.spdb.uds.log.UdsLogger;

/**
 *Socket终端
 * @author T-luzl
 *
 */
public class Terminal implements Runnable {

	public static String CharsetWriter="utf-8";
	public static String CharsetReader="utf-8";
	
	private Socket conn;
	private PrintWriter writer;
	private List<String> beforeCommandList=new ArrayList<String>();
	private int berforeCommandIndex=0;
	private boolean isArrowInput=false;
	private InterfaseTerminalHanlder hanlder;
	
	
	
	public Terminal(Socket socket,InterfaseTerminalHanlder hander){
		try {
			this.conn=socket;
			this.hanlder=hander;
			
			this.writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),CharsetWriter));
			this.writer.println("*******************************");
			this.writer.println("welcome to console");
			this.writer.println("*******************************");
			this.writer.println("plesae input command");
			this.writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public void run() {
		String msgString=null;
		try {
			byte[] arr=new byte [1024];
			int len=0;
			int c=0;
			int arrowInputStart=-1;
			
			while ((c=this.conn.getInputStream().read())!=-1) {
				if(c==27){
					arrowInputStart=len;
				}
				if(arrowInputStart>-1&&len==arrowInputStart+2){
					if(arr[arrowInputStart]==27&&arr[arrowInputStart+1]==91){
						switch (c) {
						case 65:{//up
							len=arrowInputStart;
							arrowInputStart=-1;
							berforeCommandIndex--;
							if(berforeCommandIndex<0){
								berforeCommandIndex=0;
							}
							if(berforeCommandIndex<beforeCommandList.size()){
								isArrowInput=true;
								String str=beforeCommandList.get(berforeCommandIndex);
								writer.println("want input command:"+str);
								writer.flush();
								byte[] tmp =str.getBytes(CharsetWriter);
								for(int i=0;i<tmp.length;i++){
									arr[i]=tmp[i];
								}
								len=tmp.length;
							}
							continue;	
						}
						case 66: {
							len = arrowInputStart;
							arrowInputStart = -1;
							berforeCommandIndex++;
							if (berforeCommandIndex < 0) {
								berforeCommandIndex = 0;
							}
							if (berforeCommandIndex < beforeCommandList.size()) {
								isArrowInput = true;
								String str = beforeCommandList
										.get(berforeCommandIndex);
								writer.println("want input command:" + str);
								writer.flush();
								byte[] tmp = str.getBytes(CharsetWriter);
								for (int i = 0; i < tmp.length; i++) {
									arr[i] = tmp[i];
								}
								len = tmp.length;
							}
							continue;
						}	
						case 67:
						case 68:{
							len=arrowInputStart;
							arrowInputStart=-1;
							break;
						}
						default:
							break;
						}
					}
				}
				if(c=='\n'||c=='\r'){
					if(len==0){
						continue;
					}
					msgString=new String(Arrays.copyOf(arr, len),CharsetWriter);
					if(!isArrowInput){
						beforeCommandList.add(msgString);
						berforeCommandIndex=beforeCommandList.size();
					}
					len=0;
				}
				if(msgString!=null){
					if(msgString.equals("quit")||msgString.equals("exit")){
						conn.close();
					}else{						
						this.writer.println("result:");
						this.hanlder.handler(msgString, writer);
						isArrowInput=false;
						msgString=null;
						this.writer.println("plesae input command");
						this.writer.flush();
						continue;
					}
				}
				arr[len++]=(byte)c;
				//自动扩容
				if(len>arr.length){
					arr=Arrays.copyOf(arr, arr.length*2);
				}
			}
		} catch (Exception e) {
			UdsLogger.logEvent(LogEvent.BACK_ERROR,e.getMessage());
			e.printStackTrace();
		}finally{
			if(this.writer!=null){				
				this.writer.close();
			}
		}
	}
}

