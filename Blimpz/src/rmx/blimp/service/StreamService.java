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
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import java.util.HashMap;
import rmx.StreamSource;

/**
 *
 * @author Samuel
 */
public class StreamService extends Service{
    private class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{
        private final String websocketEnd;
        private HashMap<String, StreamSource> sources;
        public DefaultChannelInitializer(HashMap<String, StreamSource> sources, String websocketEnd){
            this.sources = sources;
            this.websocketEnd = websocketEnd;
        }
        
        protected ChannelHandler getRequestHandler(){
            return new StreamRequestHandler(sources);
        }
        
        @Override
        protected void initChannel(SocketChannel c) throws Exception {
            ChannelPipeline p = c.pipeline();
            p.addLast(new WebSocketServerCompressionHandler());
            p.addLast(new WebSocketServerProtocolHandler(websocketEnd, null, true));
            p.addLast(getRequestHandler());
        }
    }

    private HashMap<String, StreamSource> sources;
    protected final String websocketEnd;
    public StreamService(int port, HashMap<String, StreamSource> sources,  String websocketEnd){
        super(port);
        this.websocketEnd = websocketEnd;
        this.sources = sources;
        this.name = "Stream";
    }
    
    @Override
    protected ChannelInitializer getInitializer(){
        return new DefaultChannelInitializer(sources, websocketEnd);
    }
}
