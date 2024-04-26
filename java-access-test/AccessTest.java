import java.net.*;
import java.util.Map;

/**
 * AccessTest
 * Access the bun and deno test webservers.
 * Port 3001 is the bun server.
 * Port 3002 is the deno server.
 * access /test to get the test page.
 */

public class AccessTest {
    public static void main(String[] args) {
        try {
            URL bun = new URL("http://localhost:3001/test");
            URL deno = new URL("http://localhost:3002/test");

            URLConnection bunConn = bun.openConnection();
            URLConnection denoConn = deno.openConnection();

            bunConn.connect();
            denoConn.connect();

            // print header status code and body
            System.out.println("______________________\nBun server response:");
            System.out.println("Status code: " + ((HttpURLConnection) bunConn).getResponseCode());
            System.out.println("Body: " + bunConn.getContent().toString());

            Map<String, java.util.List<String>> bunHeaders = bunConn.getHeaderFields();
            for (Map.Entry<String, java.util.List<String>> entry : bunHeaders.entrySet()) {
                String key = entry.getKey();
                java.util.List<String> values = entry.getValue();
                for (String value : values) {
                    System.out.println(key + ": " + value);
                }
            }

            System.out.println("\nlast modified by method:");
            System.out.println(bunConn.getLastModified());
            System.out.println("\nlast modified by get-key method last-modified:");
            System.out.println(bunConn.getHeaderField("last-modified"));
            System.out.println("\nlast modified by get-key method Last-Modified:");
            System.out.println(bunConn.getHeaderField("Last-Modified"));

            System.out.println("\n\n______________________\nDeno server response:");
            System.out.println("Status code: " + ((HttpURLConnection) denoConn).getResponseCode());
            System.out.println("Body: " + denoConn.getContent().toString());
            
            Map<String, java.util.List<String>> denoHeaders = denoConn.getHeaderFields();
            for (Map.Entry<String, java.util.List<String>> entry : denoHeaders.entrySet()) {
                String key = entry.getKey();
                java.util.List<String> values = entry.getValue();
                for (String value : values) {
                    System.out.println(key + ": " + value);
                }
            }

            System.out.println("\nlast modified by method:");
            System.out.println(denoConn.getLastModified());
            System.out.println("\nlast modified by get-key method last-modified:");
            System.out.println(denoConn.getHeaderField("last-modified"));
            System.out.println("\nlast modified by get-key method Last-Modified:");
            System.out.println(denoConn.getHeaderField("Last-Modified"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
