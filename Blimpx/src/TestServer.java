
import java.net.URL;
import java.util.Scanner;
import rmx.P;
import rmx.client.blimp.RootInterpreter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public class TestServer {
    public static void main(String ... args) throws Exception{
        RootInterpreter ri = new RootInterpreter();
        ri.load(P.s("home"));
        System.out.println(ri.getUI());
    }
}
