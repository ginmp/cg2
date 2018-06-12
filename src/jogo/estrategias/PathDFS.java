/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias;

import jogo.estruturas.Graph;
import jogo.estruturas.Node;
import jogo.estruturas.Stack;

/**
 *
 * @author inmp
 */
public class PathDFS extends Path {

    public PathDFS(Graph graph) {
        super(graph);
        estrutura = new Stack<Node>();
    }

    @Override
    public void algoritmoCaminho(String source, String target) {
        clearVisited();
        Node ns = graph.find(source);
        Node nt = graph.find(target);

        if (ns == null || nt == null) {
            return;
        }

        estrutura.add(ns);

        while (!estrutura.isEmpty()) {
            Node nq = estrutura.get();
            if (!nq.isVisited()) {
                nq.setVisited(true);
                nodes.put(nq.getNome(), nq);
            }

            for (Node n : graph.getNeighbours(nq.getNome())) {
                if (!n.isVisited() && nq.getLevel() <= profundidade) {

                    if (profundidadeLimitada) {
                        n.setLevel(nq.getLevel() + 1);
                    }

                    estrutura.add(n);
                    n.setParent(nq);
                }
            }
            if (nodes.containsKey(target)) {
                break;
            }

        } // while

        somaDistancia(source, target);
        System.out.println("DFS " + distancia);

    }

    @Override
    public void algoritmoCaminho(String source) {

        clearVisited();
        Node ns = graph.find(source);

        if (ns == null) {
            return;
        }

        estrutura.add(ns);

        while (!estrutura.isEmpty()) {
            Node nq = estrutura.get();
            if (!nq.isVisited()) {
                nq.setVisited(true);
                nodes.put(nq.getNome(), nq);
            }

            for (Node n : graph.getNeighbours(nq.getNome())) {
                if (!n.isVisited() && nq.getLevel() <= profundidade) {

                    if (profundidadeLimitada) {
                        n.setLevel(nq.getLevel() + 1);
                    }
                    
                    estrutura.add(n);
                    n.setParent(nq);
                }
            }

        } // while

    }

}
