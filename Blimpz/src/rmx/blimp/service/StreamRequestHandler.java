/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import java.util.HashMap;
import rmx.StreamSource;

/**
 *
 * @author Samuel
 */
public class StreamRequestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    private StreamSource source;
    private HashMap<String, StreamSource> sources;
    
    public StreamRequestHandler(HashMap<String,StreamSource> sources){
        this.sources = sources;
    }
    
    private void send(ChannelHandlerContext ctx, String msg){
        ctx.channel().writeAndFlush(new TextWebSocketFrame(msg));
    }
    
    private void stream(ChannelHandlerContext ctx, String id, String data){
        source = sources.get(id);
        if (source == null){
            send(ctx, id + " not found");
            return;
        }
        
        source.init(data);
        
        source.stream(f -> ctx.channel().writeAndFlush(new TextWebSocketFrame(f.end,0,f.data)));
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String request = frame.text();
        String[] command = request.split(" ",2);
        stream(ctx, command[0], command[1]);
    }
}
