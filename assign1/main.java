import java.util.ArrayList;
import java.util.Scanner;


import java.util.Random;

class Assign1 {
    static boolean leadElected = false;
    static int leadid = 0;
    public static Node[] getNeighbours(Node node, Node[] ring, int size) {
        Node[] neighbours = new Node[size-1];
        neighbours = new Node[size-1];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (ring[i] != node) {
                neighbours[count] = ring[i];
                count++;
            }
        }
        return neighbours;
    }
    public static int[] getRandomIDs(int size) {
        int chosen[] = new int[size];
        ArrayList<Integer> chosenList = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            Random rnd = new Random();
            int id = rnd.nextInt(size*20);  
            while (chosenList.contains(id)) {
                id = rnd.nextInt(size);
            }
            chosenList.add(id);
        }
        chosen = chosenList.stream().mapToInt(i -> i).toArray();
        return chosen;
    }
        
    
    public static Node[] initRing(int size) {
        // create ring and assign id to every node in ring
        Node[] ring = new Node[size];
        int[] randIDs = getRandomIDs(size);
        for (int i = 0; i < size;i++) {
            Node currNode = new Node();
            ring[i] = currNode;
            currNode.position = i;
            currNode.state="asleep";
            currNode.id = randIDs[i]; // change this to be random
            currNode.start = i;
            currNode.inid = -1;
            currNode.sendid = currNode.id;
            currNode.status = "unknown";
        } 
        // link nodes to prev and next
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                ring[i].prev = ring[size-1];
                ring[i].next = ring[i+1];
            } else if (i == size-1) {
                ring[i].next = ring[0];
                ring[i].prev = ring[i-1];
            } else {
                ring[i].prev = ring[i-1];
                ring[i].next = ring[i+1];
            }
            ring[i].neighbours = getNeighbours(ring[i], ring, size);
        }
        return ring;
    }
    public static Node[] mainLCR(Node[] ring) {
        int n = ring.length;
        int round = 0;
        while (!leadElected) {
            for (int i=0; i<n; i++) {
                if (ring[i].position+1==n) {
                        round++;
                    }
                    if (round==ring[i].start) {
                        ring[i].state = "awake";
                        if (ring[i].next.state == "awake") {
                            ring[i].next.inid = ring[i].sendid;
                        } else { continue; }
                    } else {
                        if (ring[i].inid > ring[i].id) {
                            ring[i].sendid = ring[i].inid;
                            if (ring[i].next.state=="awake") {
                                ring[i].next.inid = ring[i].sendid;
                            }
                        } else if (ring[i].inid == ring[i].id) {
                            ring[i].status = "leader";
                            leadid = ring[i].id;
                            leadElected = true;
                        } else {
                            ring[i].sendid = ring[i].id;
                            if (ring[i].next.state == "awake") {
                                ring[i].next.inid = ring[i].sendid;
                            }                            
                        }
                    }
            }
        }
        for (int i=0; i<n; i++) {
            ring[i].leadid = leadid;
            ring[i].state = "terminated";
        }
        return ring;
    }

    public static NodeList createSubringList(int[] interfacenodes, String[] interfacenodesString, int[] subringsizes, String[] subringsizeinput, NodeList allnode) {
        for (int i = 0; i < interfacenodes.length; i++) {
            interfacenodes[i] = Integer.parseInt(interfacenodesString[i]);
            subringsizes[i] = Integer.parseInt(subringsizeinput[i]);
            allnode.interfaceNodes.put(interfacenodes[i], new Node[subringsizes[i]]);           
        } 
        return allnode;
    }
    
    public static void main(String[] args) {
        Scanner userinput = new Scanner(System.in);

        System.out.println("How many nodes are in the main ring? ");
        int mainnum_nodes = userinput.nextInt();
        NodeList allnodes = new NodeList();
        allnodes.normalNodes = initRing(mainnum_nodes);
      //  mainring = mainLCR(mainring);

        // Get interface node positions
        System.out.println("Which nodes are interface nodes? (~ if none, or # between each number) ");
        String interfaceinput = userinput.next();
        int[] interfacenodes;
        String[] interfacenodesString;
        if (!interfaceinput.equals("~")) {
            interfacenodesString = interfaceinput.split("#");
            interfacenodes = new int[interfacenodesString.length];   
            System.out.println("How many nodes are in each interface node's subring? (Leave blank if none and # between each number) ");
            String[] subringsizeinput = userinput.next().split("#");
            userinput.close();
            int[] subringsizes = new int[subringsizeinput.length];
            if (interfacenodes.length != 0) {
                allnodes = createSubringList(interfacenodes, interfacenodesString, subringsizes, subringsizeinput, allnodes);
            } 
            for (int i = 0; i < allnodes.interfaceNodes.size(); i++) {
                Node[] thisSubring = initRing(subringsizes[i]);
                allnodes.interfaceNodes.put(interfacenodes[i], thisSubring);
                thisSubring= mainLCR(thisSubring);
                allnodes.normalNodes[interfacenodes[i]].id = thisSubring[0].leadid;
                allnodes.normalNodes = mainLCR(allnodes.normalNodes);
            }
        } else {
            interfacenodes = new int[0];
            interfacenodesString = new String[0];
        }
        
        allnodes.normalNodes = mainLCR(allnodes.normalNodes);
        // System.out.println(allnodes.normalNodes[0].leadid);
        

        
        /* IGNORE THESE
        // Used for testing interface nodes map
        /* System.out.println("Interface nodes: " + Arrays.toString(allnodes.interfacenodes));
        for (int i = 0; i < allnode.interfaceNodes.size(); i++) {
            System.out.print("Size of each subring: ");
            System.out.println(allnode.interfaceNodes.get(interfacenodes[i]).length);
        } */

        



        
        /* int[] mainnids = new int[mainring.length-1];
        // tests nodes are linked correctly, and finding neighbours works
        for (int i = 0; i < mainring.length; i++) {
            for (int n = 0; n < mainnids.length; n++) {
                mainnids[n] = mainring[i].neighbours[n].id;
            }
            //System.out.println(String.format("Node: %s\nPrevious node: %s\nNext node: %s\nNeighbour IDs: %s\nElected leader: %s\nStatus: %s\nState: %s\n",
                //                            mainring[i].id, mainring[i].prev.id, mainring[i].next.id, Arrays.toString(mainnids), mainring[i].leadid, mainring[i].status, mainring[i].state));
        } */
        /* Node[] onering = initRing(7);
        onering = mainLCR(onering);
        int[] onenids = new int[onering.length-1];
        // tests nodes are linked correctly, and finding neighbours works
        for (int i = 0; i < onering.length; i++) {
            for (int n = 0; n < onenids.length; n++) {
                onenids[n] = onering[i].neighbours[n].id;
            }
         //   System.out.println(String.format("Node: %s\nPrevious node: %s\nNext node: %s\nNeighbour IDs: %s\nElected leader: %s\nStatus: %s\nState: %s\n",
           //                                 onering[i].id, onering[i].prev.id, onering[i].next.id, Arrays.toString(onenids), onering[i].leadid, onering[i].status, onering[i].state));
        }
        Node[] threering = initRing(5);
        threering = mainLCR(threering);
        int[] threenids = new int[threering.length-1];
        // tests nodes are linked correctly, and finding neighbours works
        for (int i = 0; i < threering.length; i++) {
            for (int n = 0; n < threenids.length; n++) {
                threenids[n] = threering[i].neighbours[n].id;
            }
          //  System.out.println(String.format("Node: %s\nPrevious node: %s\nNext node: %s\nNeighbour IDs: %s\nElected leader: %s\nStatus: %s\nState: %s\n",
                //                            threering[i].id, threering[i].prev.id, threering[i].next.id, Arrays.toString(threenids), threering[i].leadid, threering[i].status, threering[i].state));
        }*/
        
    }
} 
