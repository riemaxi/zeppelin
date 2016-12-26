/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *
 * @author Samuel
 */
public class UXServer extends Server{
    class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{
        private final String www;
        
        public DefaultChannelInitializer(String www){
            this.www = www;
        }
        
        protected ChannelHandler getRequestHandler(){
            return new UXRequestHandler(www);
        }
        
        @Override
        protected void initChannel(SocketChannel c) throws Exception {
            ChannelPipeline p = c.pipeline();
            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(maxContentSize));
            p.addLast(getRequestHandler());
        }
    }
    
    protected final int maxContentSize;
    protected final String www;
    public UXServer(int port, int maxContentSize, String www){
        super(port);
        this.maxContentSize = maxContentSize;
        this.www = www;
    }
    
    @Override
    protected ChannelInitializer getInitializer(){
        return new DefaultChannelInitializer(www);
    }
}
