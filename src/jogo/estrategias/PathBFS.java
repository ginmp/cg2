/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias;

import jogo.estruturas.Graph;
import jogo.estruturas.Node;
import jogo.estruturas.Queue;

/**
 *
 * @author inmp
 */
public class PathBFS extends Path {

    public PathBFS(Graph graph) {
        super(graph);
        estrutura = new Queue<Node>();

    }

    @Override
    public void algoritmoCaminho(String source, String target) {
        clearVisited();

        Node ns = graph.find(source);
        Node nt = graph.find(target);
        if (ns == null || nt == null) {
            return;
        }

        ns.setVisited(true);
        estrutura.add(ns);
        nodes.put(ns.getNome(), ns);

        while (!estrutura.isEmpty()) {
            Node nq = estrutura.get();

            for (Node n : graph.getNeighbours(nq.getNome())) {
                if (!n.isVisited() && nq.getLevel() <= profundidade) {

                    if (profundidadeLimitada) {
                        n.setLevel(nq.getLevel() + 1);
                    }
                    n.setParent(nq);
                    n.setVisited(true);

                    estrutura.add(n);
                    nodes.put(n.getNome(), n);
                }
            }
            if (nodes.containsKey(target)) {
                break;
            }
        } // while

        somaDistancia(source, target);

    }

    @Override
    public void algoritmoCaminho(String source) {
        clearVisited();

        Node ns = graph.find(source);

        if (ns == null) {
            return;
        }

        ns.setVisited(true);
        estrutura.add(ns);
        nodes.put(ns.getNome(), ns);

        while (!estrutura.isEmpty()) {
            Node nq = estrutura.get();

            for (Node n : graph.getNeighbours(nq.getNome())) {
                if (!n.isVisited() && nq.getLevel() <= profundidade) {

                    if (profundidadeLimitada) {
                        n.setLevel(nq.getLevel() + 1);
                    }
                    n.setParent(nq);
                    n.setVisited(true);

                    estrutura.add(n);
                    nodes.put(n.getNome(), n);
                }
            }
        } // while

    }

}

/*
 public List<Node> ShortestPath(string nome, string sai)
        {
            Graph solucao = new Graph();
            solucao.AddNode(nome);

            while (solucao.Find(sai) == null)
				// enqto solucao nao possui node final
            {
                Node no_min = null;
                Node no_pai = null;
                double min_dist = -1;

                foreach (Node n in solucao.nodes)
				// para cada node no grafo solucao
                {
                    Node no = this.Find(n.Name);
					// node original referente ao node do grafo solucao
                    foreach (Edge e in no.Edges)
					// para cada arco do original
                    {
                        if (solucao.Find(e.To.Name) == null)
							// se grafo solucao nao possui node vizinho ao original
                        {
                            double dist = Convert.ToInt32(n.Info) + e.Cost;
							// calcular distancia do vizinho ate o inicio
							
                            if (min_dist == -1 || dist < min_dist)
                            {
                                no_pai = n;
                                min_dist = dist;
                                no_min = e.To;

                            }

                        }

                    }

                }
                solucao.AddNode(no_min.Name);
                solucao.AddEdge(no_min.Name, no_pai.Name, 0);
                solucao.Find(no_min.Name).Info = min_dist;

            }

            List<Node> ret = solucao.BreadthFirstSearch(sai);
            ret.Reverse();


            return ret;
        }
 */
