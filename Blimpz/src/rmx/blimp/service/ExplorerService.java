/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

/**
 *
 * @author Samuel
 */
public class ExplorerService extends Service{
    public ExplorerService(int port){
        super(port);
        this.name = "Explorer";
    }
}
