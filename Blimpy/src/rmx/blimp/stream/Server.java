/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.stream;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import java.util.Locale;


/**
 *
 * @author Samuel
 */
public class Server {
    public static class FrameHandler extends SimpleChannelInboundHandler<WebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

          if (frame instanceof TextWebSocketFrame) {
              // Send the uppercase string back.
              String request = ((TextWebSocketFrame) frame).text();
              String message = request.toUpperCase(Locale.US);
              for(int i = 0; i<200000; i++){
                ctx.channel().writeAndFlush(new TextWebSocketFrame(message + i));
                try{
                    Thread.sleep(1);
                }catch(Exception e){
                    
                }
              }
          } else {
              String message = "unsupported frame type: " + frame.getClass().getName();
              throw new UnsupportedOperationException(message);
          }
        }
        
    }
    
    public static class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{
        private final String path;
        private final int contentSize = 65536;
        
        public DefaultChannelInitializer(String path){
            this.path = path;
        }
        
        protected ChannelHandler getIndexPageHandler(String path){
            return new IndexPageHandler(path);
        }
        
        protected ChannelHandler getFrameHandler(){
            return new FrameHandler();
        }
        
        @Override
        protected void initChannel(SocketChannel c) throws Exception {
            ChannelPipeline p = c.pipeline();
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(contentSize));
            p.addLast(new WebSocketServerCompressionHandler());
            p.addLast(new WebSocketServerProtocolHandler(path, null, true));
            p.addLast(getIndexPageHandler(path));
            p.addLast(getFrameHandler());
            
        }
    }
    
    private final int port;
    
    public Server(){
        this(80, "/websocket");
    }
    
    public Server(int port){
        this(port, "/websocket");
    }
    
    public Server(int port, String path){
        this.port = port;
        
        start(path);
    }
    
    protected ChannelInitializer getInitializer(String path){
        return new DefaultChannelInitializer(path);
    }
    
    public void start(){
        start("/websocket");
    }
    
    public void start(String path){
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(worker, worker)
             .channel(NioServerSocketChannel.class)
             .childHandler(getInitializer(path));
            
            Channel channel = b.bind(port).sync().channel();
            
            System.out.println("Listening on channel: " + channel.id() + ". Go to http://localhost:" + port);
        }catch(Exception e){
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
