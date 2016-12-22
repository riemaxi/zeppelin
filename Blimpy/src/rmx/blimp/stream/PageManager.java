/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.stream;

/**
 *
 * @author Samuel
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Generates the demo HTML page which is served at http://localhost:8080/
 */
public final class PageManager {

    public static String INDEX_PAGE = "index.html";
    public static ByteBuf getContent(String root, String page, String webSocketLocation) {
        if ("/".equals(page))
            page = INDEX_PAGE;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(root + page))){
            String html = reader.lines().reduce("", (sum,s) -> sum.isEmpty() ? s : String.format("%s%n%s", sum, s));
            html = html.replace("_WEBSOCKET_LOCATION_", webSocketLocation);
            return Unpooled.copiedBuffer(html, CharsetUtil.US_ASCII);
        }catch(Exception e){
            return null;
        }
    }

    private PageManager() {
        // Unused
    }
}
