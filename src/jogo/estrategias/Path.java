/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import jogo.estruturas.SQBase;
import jogo.estruturas.Graph;
import jogo.estruturas.Node;

/**
 *
 * @author inmp
 */
public abstract class Path {

    protected SQBase<Node> estrutura;

    protected int profundidade;
    protected boolean profundidadeLimitada;

    protected int distancia;

    protected Map<String, Node> nodes;

    protected Graph graph;

    public Path(Graph graph) {
        this.graph = graph;
        nodes = new HashMap<String, Node>();
    }

    public abstract void algoritmoCaminho(String source, String target);

    public abstract void algoritmoCaminho(String source);

    public void clearVisited() {
        for (Node n : graph.getNodes().values()) {
            n.setVisited(false);
            n.setParent(null);
            n.setLevel(0);
        }

        estrutura.clear();
        nodes.clear();
        distancia = 0;

    }

    public int getDistancia() {
        return distancia;
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public boolean isProfundidadeLimitada() {
        return profundidadeLimitada;
    }

    public void setProfundidadeLimitada(boolean profundidadeLimitada) {
        this.profundidadeLimitada = profundidadeLimitada;
    }

    public void ativarProfundidadeLimitada(int profundidade) {
        this.profundidade = profundidade;
        this.profundidadeLimitada = true;
    }

    public void desativarProfundidadeLimitada() {
        this.profundidade = 0;
        this.profundidadeLimitada = false;
    }

    public void somaDistancia(String source, String target) {
        Node nt = nodes.get(target);
        Node ns = nodes.get(source);

        while (nt != ns && nt != null) {
            nt = nt.getParent();
            distancia++;
        }
    }

    public TreeMap<Integer, Map<String, Node>> getOrdenadoProfundidade() {
        TreeMap<Integer, Map<String, Node>> tm = new TreeMap<Integer, Map<String, Node>>();
        Map<String, Node> m;
        for (Node n : nodes.values()) {
            m = tm.get(n.getLevel());

            if (m == null) {
                m = new HashMap<String, Node>();
                tm.put(n.getLevel(), m);
            }
            m.put(n.getNome(), n);
        }

        return tm;
    }

}
