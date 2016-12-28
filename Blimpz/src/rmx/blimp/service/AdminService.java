/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 *
 * @author Samuel
 */
public class AdminService extends UXService{
    public AdminService(int port, int maxContentSize, String www){
        super(port,maxContentSize, www);
        this.name = "Admin";
    }
}
