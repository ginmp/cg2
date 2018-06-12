/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estruturas;

import java.util.HashMap;
import java.util.Map;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class Node {

    private Map<String, Edge> edges;

    private String nome;

    private Ponto ponto;

    private int level;

    private boolean visited;

    private Node parent;

    public Node() {
        edges = new HashMap<String, Edge>();
    }

    public Node(String nome, Ponto ponto) {
        this();
        this.nome = nome;
        this.ponto = ponto;
    }

    public Map<String, Edge> getEdges() {
        return edges;
    }

    public void setEdges(Map<String, Edge> edges) {
        this.edges = edges;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addEdge(Node to) {
        Edge edge = new Edge(this, to);
        edges.put(to.nome, edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge.getTo().nome);
    }

    public Edge findEdge(String to) {
        return edges.get(to);
    }



    @Override
    public String toString() {
        return "Node(" + nome + ")";
    }

}
