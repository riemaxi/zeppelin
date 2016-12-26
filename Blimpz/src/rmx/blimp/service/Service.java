/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author Samuel
 */
public class Service {
    protected final int port;
    protected EventLoopGroup boss;
    Channel channel;
    protected EventLoopGroup worker;
    
    
    public Service(int port){
        this.port = port;
    }
    
    protected void close(){
        channel.close();
        worker.shutdownGracefully();
        boss.shutdownGracefully();
   }
    
    protected ChannelInitializer getInitializer(){
        throw new UnsupportedOperationException();
    }
    
    public void start(){
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
             .channel(NioServerSocketChannel.class)
             .childHandler(getInitializer());
            
            channel = b.bind(port).sync().channel();
        }catch(Exception e){
            close();
        }
        
    }

    public void stop(){
        close();
    }
    
}
