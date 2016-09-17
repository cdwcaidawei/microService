package cn.wpeace.netty;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
/**
 * 客户端建立
 * 1.设置线程池
 * 2.设置处理器
 * 3.连接端口
 * @author peace
 *
 */
public class NettyClient {
     public static void main(String[] args) {
		String host="127.0.0.1";//服务端IP
		int port=8081;//端口
		EventLoopGroup group=new NioEventLoopGroup();//线程池
		try {
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<NioSocketChannel>() {//设置处理器

				@Override
				protected void initChannel(NioSocketChannel channel) throws Exception {
					channel.pipeline().addLast("split",new DelimiterBasedFrameDecoder(1000, Delimiters.lineDelimiter()));
					channel.pipeline().addLast("decoder",new StringDecoder());
					channel.pipeline().addLast("encoder",new StringEncoder());
					channel.pipeline().addLast("hander",new FirstClientHandler());//自定义客户端处理器
				}
			});
			Channel channel = bootstrap.connect(host,port).sync().channel();
			Scanner scanner=new Scanner(System.in);//读取键盘输入
			while(true){
				String line = scanner.nextLine();
				if(line==null||"".equals(line)){
					continue;
				}
				if("exit".equals(line)){//退出字符串
					channel.close();
					break;
				}
				channel.writeAndFlush(line+ "\r\n");//\n为了分隔识别，\r为了格式化输出
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
		
	}
}
class FirstClientHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		  System.out.println("Server :"+msg);//读取消息
		  
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client active");//通道激活调用
		super.channelActive(ctx);
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client close");//通道退出调用
		super.channelInactive(ctx);
	}
}
