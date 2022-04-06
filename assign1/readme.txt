README for Assignment 1 code

Make sure you are running main.java from the same folder as Node.java and NodeList.java.
Run main.java with a debug console, which will prompt you for the total number of nodes in the main ring. E.g. 430.
The program will then prompt you to enter which nodes are interface ones.
	Rules: Starts from 0, i.e. the 'first' node in the ring will require you to enter 0.	
		 Separate the ID of each node with '#'
		 Each interface node nummber entered MUST be less than the total number of nodes. E.g. if there are 12 nodes in the main ring, the largest number you can enter as interface is 11
The program will then prompt you to enter the number of nodes in each interface node's subring. 
	Rule: Must be greater than 1.

To test the code, uncommenting line 154 (a print to console) will allow you to check the ID is the largest in the main ring, accounting for subring IDs. 
Can also use a breakpoint here.