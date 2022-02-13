import java.util.Arrays;

class Lab1 {
    static int n = 10;
    static Node[] ring = new Node[n];
    static int[] nids = new int[n-1];
    static int round = 1;
    static boolean leadElected = false;
    static int leadid = 0;
    public static Node[] getNeighbours(Node node, Node[] ring) {
        Node[] neighbours = new Node[n-1];
        neighbours = new Node[n-1];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (ring[i] != node) {
                neighbours[count] = ring[i];
                count++;
            }
        }
        return neighbours;
    }

    public static Node[] initRing() {
        // create ring and assign id to every node in ring
        for (int id = 1; id <= n;id++) {
            Node currNode = new Node();
            ring[id-1] = currNode;
            currNode.state="active";
            currNode.id =id;
            currNode.start = id;
            currNode.inid = 0;
            currNode.sendid = currNode.id;
            currNode.status = "unknown";
        } 
        // link nodes to prev and next
        for (int id = 1; id <= n; id++) {
            if (id == 1) {
                ring[id-1].prev = ring[n-1];
                ring[id-1].next = ring[id];
            } else if (id == n) {
                ring[id-1].next = ring[0];
                ring[id-1].prev = ring[id-2];
            } else {
                ring[id-1].prev = ring[id-2];
                ring[id-1].next = ring[id];
            }
            ring[id-1].neighbours = getNeighbours(ring[id-1], ring);
        }
        for (int i = 0; i < n; i++) {
           // ring[i].neighbours = getNeighbours(ring[i], ring);
        }
        return ring;
    }
    public static Node[] LCR(Node[] ring) {
        while (!leadElected) {
            for (int i=0; i<n; i++) {
                if (ring[i].id==n) {
                        round++;
                    }
                    if (round==ring[i].start) {
                        ring[i].next.inid = ring[i].sendid;
                        
                    } else {
                        if (ring[i].inid > ring[i].id) {
                            ring[i].sendid = ring[i].inid;
                            ring[i].next.inid = ring[i].sendid;
                        } else if (ring[i].inid == ring[i].id) {
                            ring[i].status = "leader";
                            leadid = ring[i].id;
                            leadElected = true;
                        } else {
                            ring[i].sendid = ring[i].id;
                            ring[i].next.inid = ring[i].sendid;
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
    public static void main(String[] args) {
        ring = initRing();
        ring = LCR(ring);
        // tests nodes are linked correctly, and finding neighbours works
        for (int i = 0; i < n; i++) {
            for (int n = 0; n < nids.length; n++) {
                nids[n] = ring[i].neighbours[n].id;
            }
            System.out.println(String.format("Node: %s\nPrevious node: %s\nNext node: %s\nNeighbour IDs: %s\nElected leader: %s\nStatus: %s\nState: %s\n",
                                            ring[i].id, ring[i].prev.id,ring[i].next.id, Arrays.toString(nids), ring[i].leadid, ring[i].status,ring[i].state));
        }
    }
} 
