/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.zepp.bio;

import java.util.function.Consumer;
import java.util.stream.IntStream;
import rmx.StreamSource;

/**
 *
 * @author Samuel
 */
public class Popgen implements StreamSource {
    private int size;
    
    @Override
    public void stream(Consumer<Fragment> consumer) {
        Fragment f = new Fragment();
        for(int i=0; i<size-1; i++){
            f.end = false;
            f.data = "data " + i;
            consumer.accept(f);
        }
        
        f.end = true;
        f.data = "data " + (size-1);
        consumer.accept(f);
    }

    @Override
    public void init(String data) {
        size = Integer.valueOf(data);
    }
}
