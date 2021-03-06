//package com.tc.test;
// 
//import org.apache.http.impl.bootstrap.ServerBootstrap;
//
//import io.netty.channel.ChannelHandlerContext;
//
///**
// * 2 * Netty 服务端代码 3 * 4 * @author lihzh 5 * @alia OneCoder 6 * @blog
// * http://www.coderli.com
// */
//public class HelloServer {
//
//	public static void main(String args[]) {
//		// Server服务启动器
//		ServerBootstrap bootstrap = new ServerBootstrap(
//				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
//		// 设置一个处理客户端消息和各种消息事件的类(Handler)
//		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
//			@Override
//			public ChannelPipeline getPipeline() throws Exception {
//				return Channels.pipeline(new HelloServerHandler());
//			}
//		});
//		// 开放8000端口供客户端访问。
//		bootstrap.bind(new InetSocketAddress(8000));
//	}
//
//	private static class HelloServerHandler extends SimpleChannelHandler {
//
//		/**
//		 * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
//		 * 
//		 * @alia OneCoder
//		 * @author lihzh
//		 */
//		@Override
//		public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
//			System.out.println("Hello world, I'm server.");
//		}
//	}
//}
