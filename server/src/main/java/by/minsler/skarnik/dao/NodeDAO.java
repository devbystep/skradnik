package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Node;

import java.util.ArrayList;

public interface NodeDAO {

    ArrayList<Node> getNodes();

    Node getNode(int id);

    boolean addNode(Node Node);

    int deleteNode(int id);

    int deleteAllNode();
}
