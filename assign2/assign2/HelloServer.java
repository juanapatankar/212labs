package assign2;

import java.net.*;
import java.util.Arrays;
import java.io.*;
import java.lang.Math;
import assign2.listOfNumbers;

public class HelloServer {
	

	public static int[] basetwox(int[] orig) {
		int[] newNumbers = orig;
		for (int i = 0; i < newNumbers.length; i++) {
			newNumbers[i] = 2^newNumbers[i];
		}
		return newNumbers;
	}

	public static int[] xsquared(int[] orig) {
		int[] newNumbers = orig;
		for (int i = 0; i < newNumbers.length; i++) {
			newNumbers[i] = newNumbers[i]^2;
		}
		return newNumbers;
	}

	public static double[] rootx(int[] orig) {
		double[] newNumbers = new double[orig.length];
		for (int i = 0; i < newNumbers.length; i++) {
			newNumbers[i] = Math.sqrt(orig[i]);
		}
		return newNumbers;
	}
	public static double[] logx(int[] orig) {
		double[] newNumbers = new double[orig.length];
		for (int i = 0; i < newNumbers.length; i++) {
			newNumbers[i] = Math.log(orig[i]);
		}
		return newNumbers;
	}

	public static int[] getIntArray(String input) {
		String[] numberInp = input.split(" ");
		int[] list = new int[numberInp.length];
		for (int i = 0; i < list.length; i++) {
			list[i] = Integer.parseInt(numberInp[i]);
		}
		return list;
	}

	public static listOfNumbers calcNewNumbers(String input, int[] numbers) {
		int choice = Integer.parseInt(input);
		listOfNumbers finishedList = new listOfNumbers();
		switch(choice) {
			case 1:
				finishedList.whichIndex = 0;
				outputIsInts(choice, numbers);
				break;
			case 2:
				finishedList.whichIndex = 0;
				outputIsInts(choice, numbers);
				break;
			case 3:
				finishedList.whichIndex = 1;
				outputIsDoubles(choice, numbers);
				break;
			case 4:
				finishedList.whichIndex = 1;
				outputIsDoubles(choice, numbers);
				break;
			default:
				System.out.println("Invalid input");
		}
		
		return finishedList;
	}

	public static int[] outputIsInts(int choice, int[] numbers) {
		int[] processed = new int[numbers.length];
		switch(choice) {
			case 1:
				processed = basetwox(numbers);
				break;
			case 2:
				processed = xsquared(numbers);
				break;
			default:
				System.out.println("Invalid input");
		}
		return processed;
	}

	public static double[] outputIsDoubles(int choice, int[] numbers) {
		double[] processed = new double[numbers.length];
		switch(choice) {
			case 3:
				processed = rootx(numbers);
				break;
			case 4:
				processed = logx(numbers);
				break;
			default:
				System.out.println("Invalid input");
		}
		return processed;
	}

	public static void main(String[] args) throws IOException {
		int portNumber;
		
        if (args.length < 1) {
			System.out.println("Warning: You have provided no arguments\nTrying to connect to the default port 8000...");
			portNumber = 8000;
        } else if (args.length == 1) {
			portNumber = Integer.parseInt(args[0]);
		} else {
			System.out.println("Warning: You have provided > 1 arguments\nTrying with the first argument to connect to a port...");
			portNumber = Integer.parseInt(args[0]);
		}
		
		while(true){ //in order to serve multiple clients but sequentially, one after the other
			try (
				ServerSocket myServerSocket = new ServerSocket(portNumber);
				Socket aClientSocket = myServerSocket.accept();
				PrintWriter output = new PrintWriter(aClientSocket.getOutputStream(),true);
				BufferedReader input = new BufferedReader(new InputStreamReader(aClientSocket.getInputStream()));
			) {
				System.out.println("Connection established with a new client with IP address: " + aClientSocket.getInetAddress() + "\n");
				output.println("Server says: Hello Client " + aClientSocket.getInetAddress() + ". This is server " + myServerSocket.getInetAddress() + 
				" speaking. Our connection has been successfully established!");
				String inputLine = input.readLine();				
				System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
				System.out.println("Client says: " + inputLine);
				int[] origNumbers = getIntArray(inputLine);
				output.println("Server says: Your list has been successfully received!");
				inputLine = input.readLine();
				listOfNumbers listToOutput = calcNewNumbers(inputLine, origNumbers);
				String toBeOutput = "";
				if (listToOutput.whichIndex == 0) {
					toBeOutput = Arrays.toString(listToOutput.integerList);
				} else {
					toBeOutput = Arrays.toString(listToOutput.doubleList);
				}
				output.println(toBeOutput);
				System.out.println("Connection with client " + aClientSocket.getInetAddress() + " is now closing...\n");
			
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port "
				+ portNumber + " or listening for a connection");
				System.out.println(e.getMessage());			
			}
		}
	}
}