class Node {
    String state = "asleep";
    int id;
    int position;
    Node next;
    Node prev;
    Node[] neighbours = new Node[9];
    int start;
    int sendid;
    int inid;
    String status;
    int leadid;
}