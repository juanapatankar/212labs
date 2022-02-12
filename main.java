import java.util.Arrays;

class Lab1 {
    static int n = 10;
    public static Node[] getNeighbours(Node node, Node[] ring) {
        Node[] neighbours = new Node[n-1];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (ring[i] != node) {
                neighbours[count] = ring[i];
                count++;
            }
        }
        return neighbours;
    }
    public static void main(String[] args) {
        System.out.println("hi");
        Node[] ring = new Node[10];
        
        // assign id to every node in ring
        for (int id = 1; id <= n;id++) {
            Node currNode = new Node();
            currNode.id =id;
            ring[id-1] = currNode;
        } 
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
        }
        //tests nodes are linked correctly, and finding neighbours works
        for (int i = 0; i < 10; i++) {
            Node[] neighbours = getNeighbours(ring[i], ring);
            int[] nids = new int[neighbours.length];
            for (int n = 0; n < nids.length; n++) {
                nids[n] = neighbours[n].id;
            }
            System.out.println(String.format("Node: %s\nPrevious node: %s\nNext node: %s\nNeighbour IDs: %s\n",ring[i].id, ring[i].prev.id,ring[i].next.id, Arrays.toString(nids)));
        }
    }
} 
