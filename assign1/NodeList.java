import java.util.*;

class NodeList {
    Node[] normalNodes;
    // Key: the position of the interface node in the main ring. Value: the nodes in each subring
    Map<Integer, Node[]> interfaceNodes = new LinkedHashMap<Integer, Node[]>();
}