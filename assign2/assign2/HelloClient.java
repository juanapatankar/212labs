package assign2;
import java.net.*;
import java.io.*;

public class HelloClient {
	public static void main(String[] args) throws IOException {
		
        if (args.length != 2) {
            System.err.println(
                "Usage: java HelloClient <host name> <port number>");
            System.exit(1);
        }

		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		
		try (
			Socket myClientSocket = new Socket(hostName, portNumber);
			PrintWriter output = new PrintWriter(myClientSocket.getOutputStream(),true);
			BufferedReader input = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput;
			System.out.println(input.readLine()); // reads the first message from the server and prints it
			System.out.println("Enter a list of numbers (separated with a space): ");
			userInput = stdIn.readLine(); // reads user's input
			output.println(userInput); // user's input transmitted to server
			System.out.println(input.readLine()); // reads server's ack and prints it
			System.out.println("Which operation would you like to do?\nChoose 1, 2, 3 or 4\n1) 2^x\n2) x^2\n3) x^0.5 (sqrt(x))\n4) log(2)x");
			userInput = stdIn.readLine();
			output.println(userInput);
			System.out.println("Now getting the new numbers... ");
			System.out.println(input.readLine());
			System.out.println(input.readLine());
			System.out.println("-----------End of communication-----------");
			System.out.println("\nCommunication with server " + hostName + " was successful! Now closing...");
			
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
			e.printStackTrace();
            System.exit(1);
        }
	
	}
}