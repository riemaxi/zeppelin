/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx;

import java.util.function.Consumer;

/**
 *
 * @author Samuel
 */
public interface StreamSource {
    public class Fragment{
        public boolean end;
        public String data;
    }
    void stream(Consumer<Fragment> consumer);
    void init(String data);
}
