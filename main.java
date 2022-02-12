import java.util.Arrays;

class Lab1 {
    public static void main(String[] args) {
        System.out.println("hi");
        Node[] ring = new Node[10];
        int n = 10;
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
        // FIX THIS
        /*int count = 0;
        for (int id = 1; id <= n; id++) {
            for (int nid = 1; nid <= n; nid++) {
                if (nid != ring[id-1].id) {
                
                    ring[id-1].neighbours[count] = ring[nid-1];
                }
            }
            
        } */
        //tests nodes are linked correctly
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("Node: %s\nPrevious node: %s\nNext node: %s\nNeighbour IDs: %s\n",ring[i].id, ring[i].prev.id,ring[i].next.id, Arrays.toString(ring[i].neighbours)));
        }
    }
} 
