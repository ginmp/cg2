/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estruturas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author inmp
 */
public class Graph {

    private Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<String, Node>();
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.put(node.getNome(), node);
    }

    public void removeNode(String nome) {
        Node node = nodes.get(nome);
        if (node != null) {
            for (Edge e : node.getEdges().values()) {
                Node to = e.getTo();

                to.removeEdge(to.findEdge(nome));
            }
            nodes.remove(nome);
        }
    }

    public void addEdge(String from, String to) {
        Node nodeTo = find(to);
        Node nodeFrom = find(from);

        if (nodeFrom != null && nodeTo != null) {
            nodeFrom.addEdge(nodeTo);
            nodeTo.addEdge(nodeFrom);
        }
    }

    public Node find(String nome) {
        return nodes.get(nome);
    }

    public Collection<Node> getNeighbours(String nome) {
        List<Node> vizinhos = new ArrayList<Node>();
        Node n = find(nome);

        if (n == null) {
            return vizinhos;
        }

        for (Edge e : n.getEdges().values()) {
            vizinhos.add(e.getTo());
        }

        return vizinhos;
    }

    public void clear() {
        nodes.clear();
    }

}
