package tcp;

import java.io.*;
import java.net.Socket;

/**
 * Date: 01.05.13
 * Time: 10:33
 *
 * @author Artem Prigoda
 */
public class TCPPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9099);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        }


        out.close();
        in.close();
        socket.close();
        stdIn.close();
    }
}
