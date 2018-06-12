/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.mapas;

import java.util.ArrayList;
import java.util.Collection;
import jogo.util.Cor;
import jogo.entidades.Player;
import jogo.entidades.Parede;
import jogo.Jogo;
import jogo.util.Ponto;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jogo.controle.BuildWall;
import jogo.controle.Controle;
import jogo.controle.ControlePlayer;
import jogo.entidades.Base;
import jogo.entidades.Teleport;
import jogo.estrategias.Path;
import jogo.estrategias.PathBFS;
import jogo.estrategias.ai.AI;
import jogo.estrategias.ai.SeguirAI;
import jogo.estruturas.Graph;
import jogo.estruturas.Node;
import jogo.mapas.gerador.GeradorMapa;

/**
 *
 * @author inmp
 */
public class Mapa {

    private final Map<String, Ponto> itens;

    public int mapa_dim = 22 * Jogo.MULT;

    private final Map<String, Parede> paredes;

    private final Player player1;

    private final Player player2;

    private final Graph graph;

    private GeradorMapa gerador;

    private int pontoPlayer1 = 1, pontoPlayer2 = mapa_dim / 2;
    private boolean novoMapa;

    private Map<String, Teleport> teleports;

    public Mapa(GeradorMapa gerador, Controle controlePlayer1, Controle controlePlayer2) {
        this.gerador = gerador;
        graph = new Graph();
        itens = new HashMap<String, Ponto>();
        paredes = new HashMap<String, Parede>();
        teleports = new HashMap<String, Teleport>();

        player1 = new Player(new Ponto(pontoPlayer1, pontoPlayer1), new Cor(0, 1, 0));
        player1.setControle(controlePlayer1);
        controlePlayer1.setMapa(this);

        player2 = new Player(new Ponto(pontoPlayer2, pontoPlayer2), new Cor(1, 0, 0));
        player2.setControle(controlePlayer2);
        controlePlayer2.setMapa(this);

        if (controlePlayer1 instanceof AI) {
            AI cai = (AI) controlePlayer1;
            cai.setPath(new PathBFS(graph));
            cai.setPlayerOponente(player2);
            cai.setPlayerControlado(player1);
        } else if (controlePlayer1 instanceof ControlePlayer) {
            ControlePlayer cp = (ControlePlayer) controlePlayer1;
            cp.setPlayer(player1);
        }

        if (controlePlayer2 instanceof AI) {
            AI cai = (AI) controlePlayer2;
            cai.setPath(new PathBFS(graph));
            cai.setPlayerOponente(player1);
            cai.setPlayerControlado(player2);
        } else if (controlePlayer2 instanceof ControlePlayer) {
            ControlePlayer cp = (ControlePlayer) controlePlayer2;
            cp.setPlayer(player2);
        }

        if (controlePlayer1 instanceof BuildWall || controlePlayer2 instanceof BuildWall) {
            if (controlePlayer1 instanceof SeguirAI) {
                ((SeguirAI) controlePlayer1).setBuild(true);
            } else if (controlePlayer2 instanceof SeguirAI) {
                ((SeguirAI) controlePlayer2).setBuild(true);
            } else if (controlePlayer1 instanceof ControlePlayer) {
                ((ControlePlayer) controlePlayer1).setBuild(true);
            }
        }
    }

    public int getMapa_dim() {
        return mapa_dim;
    }

    public Map<String, Parede> getParedes() {
        return paredes;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isNovoMapa() {
        boolean mn = novoMapa;
//        novoMapa = false;
        return mn;
    }

    public void setNovoMapa(boolean mapaNovo) {
        this.novoMapa = mapaNovo;
    }

    public void setGerador(GeradorMapa gerador) {
        this.gerador = gerador;
    }

    public Map<String, Teleport> getTeleports() {
        return teleports;
    }

    public void removerParede(Base base) {
        paredes.remove(Parede.getNome(base.getPonto()));
    }

    public boolean construirParede(Ponto ponto, Cor cor) {
        if (!teleports.containsKey(Teleport.getNome(ponto))) {
            Parede p = new Parede(ponto.getX(), ponto.getY(), ponto.getZ(), cor);
            paredes.put(p.toString(), p);
            graph.removeNode(ponto.toString());
            return true;
        }
        return false;
    }

    public boolean construirParede(int x, int y, int z, Cor cor) {
        return this.construirParede(new Ponto(x, y, z), cor);
    }

    public boolean construirParede(int x, int y, Cor cor) {
        return this.construirParede(x, y, 0, cor);
    }

    public void destruirParede(Ponto ponto) {

        if (ponto.getX() != 0 && ponto.getY() != 0 && ponto.getX() != getMapa_dim() && ponto.getY() != getMapa_dim()) {
            if (paredes.remove(Parede.getNome(ponto)) != null) {
                graph.addNode(new Node(ponto.toString(), ponto));

                Ponto cima, direita, baixo, esquerda;

                cima = new Ponto(ponto.getX(), ponto.getY() - 1);
                baixo = new Ponto(ponto.getX(), ponto.getY() + 1);
                direita = new Ponto(ponto.getX() + 1, ponto.getY());
                esquerda = new Ponto(ponto.getX() - 1, ponto.getY());

                Node nCima, nDir, nBaixo, nEsq;
                nCima = graph.find(cima.toString());
                nDir = graph.find(direita.toString());
                nBaixo = graph.find(baixo.toString());
                nEsq = graph.find(esquerda.toString());

                if (nCima != null) {
                    graph.addEdge(ponto.toString(), nCima.getNome());
                }
                if (nDir != null) {
                    graph.addEdge(ponto.toString(), nDir.getNome());
                }
                if (nBaixo != null) {
                    graph.addEdge(ponto.toString(), nBaixo.getNome());
                }
                if (nEsq != null) {
                    graph.addEdge(ponto.toString(), nEsq.getNome());
                }

            }
        }
    }

    public void gerarMapa() {
        novoMapa = false;
        graph.clear();

        Controle cp1 = player1.getControle();
        Controle cp2 = player2.getControle();

        cp1.reset();
        cp2.reset();

        player1.setPonto(new Ponto(pontoPlayer1, pontoPlayer1));
        player2.setPonto(new Ponto(pontoPlayer2, pontoPlayer2));

        gerador.gerarMapa(this);
        gerarItens();
    }

    private void addNode(int x, int y) {
        Ponto ponto;
        Node nodePontoAt;
        Node node;
        Ponto pontoAtual = new Ponto(x, y);
        // baixo
        {
            ponto = new Ponto(x, y + 1);

            if (!paredes.containsKey(Parede.getNome(pontoAtual)) && !paredes.containsKey(Parede.getNome(ponto))) {

                nodePontoAt = graph.find(pontoAtual.toString());
                node = graph.find(ponto.toString());

                nodePontoAt = nodePontoAt == null ? new Node(pontoAtual.toString(), pontoAtual) : nodePontoAt;
                node = node == null ? new Node(ponto.toString(), ponto) : node;

                graph.addNode(nodePontoAt);
                graph.addNode(node);

                graph.addEdge(nodePontoAt.getNome(), node.getNome());
            }
        } // baixo

        // direita
        {
            ponto = new Ponto(x + 1, y);

            if (!paredes.containsKey(Parede.getNome(pontoAtual)) && !paredes.containsKey(Parede.getNome(ponto))) {

                nodePontoAt = graph.find(pontoAtual.toString());
                node = graph.find(ponto.toString());

                nodePontoAt = nodePontoAt == null ? new Node(pontoAtual.toString(), pontoAtual) : nodePontoAt;
                node = node == null ? new Node(ponto.toString(), ponto) : node;

                graph.addNode(nodePontoAt);
                graph.addNode(node);

                graph.addEdge(nodePontoAt.getNome(), node.getNome());
            }
        } // direita

    }

    private Ponto getPonto() {
        return getPonto(0);
    }

    private Ponto getPonto(int i) {
        Ponto ponto;
        int x, y;

        do {
            do {
                x = new Random(i % 5 == 0 ? System.currentTimeMillis() : System.nanoTime()).nextInt(mapa_dim);

                i++;
            } while (x == 0 || x == player1.getX() || x == player2.getX());

            do {
                y = new Random(i % 5 != 0 ? System.nanoTime() : System.currentTimeMillis()).nextInt(mapa_dim);

                i++;
            } while (y == 0 || y == player1.getY() || y == player2.getY());

            ponto = new Ponto(x, y);
        } while (teleports.containsKey(Teleport.getNome(ponto)));

        return ponto;

    }

    private void gerarGrafo() {
        for (int x = 1; x < mapa_dim; x++) {
            for (int y = 1; y < mapa_dim; y++) {

                addNode(x, y);

            } // for y
        } // for x

        { // teleport test
//            Ponto p0 = new Ponto(2, 2);
//            Ponto p1 = new Ponto(mapa_dim - 3, mapa_dim - 3);
            int nTeleports = new Random(System.currentTimeMillis()).nextInt(Jogo.MULT + new Random(System.nanoTime()).nextInt(3));
            for (int i = 0; i <= nTeleports; i++) {
                Ponto p0 = getPonto();
                Ponto p1 = getPonto();

                while (p0 == p1) {
                    p1 = getPonto();
                }

                Path path = new PathBFS(graph);

                path.algoritmoCaminho(p0.toString(), p1.toString());

                int j = mapa_dim;
                while (path.getDistancia() < j) {
                    if (j % 5 == 0) {
                        p0 = getPonto(j);
                    } else {
                        p1 = getPonto(j);
                    }
                    path.algoritmoCaminho(p0.toString(), p1.toString());
                    j--;
                }

                destruirParede(p0);
                destruirParede(p1);
//            addNode(2, 2);
//            addNode(mapa_dim - 3, mapa_dim - 3);
                addNode(p0.getX(), p0.getY());
                addNode(p1.getX(), p1.getY());

                Node n0 = graph.find(p0.toString());
                Node n1 = graph.find(p1.toString());

                graph.addEdge(n0.getNome(), n1.getNome());

                Cor cor = new Cor(new Random(System.currentTimeMillis()).nextFloat(),
                        new Random(System.nanoTime()).nextFloat(),
                        new Random().nextFloat());
//            cor = new Cor(1, .8, .2)
                Teleport t0 = new Teleport(p0, cor);
                Teleport t1 = new Teleport(p1, cor);
                t0.setTeleport(t1);
                t1.setTeleport(t0);
                teleports.put(t0.toString(), t0);
                teleports.put(t1.toString(), t1);
            }
        } // teleport test

        System.out.println("graph nodes " + graph.getNodes().size());
        System.out.println("paredes " + paredes.size());

        int cnt = 0;

        for (Node n : graph.getNodes().values()) {
            cnt += n.getEdges().size();
        }
        System.out.println("edges " + cnt);
    }

    public boolean validarMapa() {
        gerarGrafo();
        Path path = new PathBFS(graph);

        path.algoritmoCaminho(player1.getPonto().toString(), player2.getPonto().toString());

        System.out.println("Distancia = " + path.getDistancia());

        if (path.getDistancia() == 0) {
            graph.clear();
        }

        Ponto p00 = new Ponto();
        Cor c00 = new Cor(.8f, .6f, .1f);

        Parede p = paredes.get(Parede.getNome(p00));
        if (p != null) {
            p.setCor(c00);
        }

        return path.getDistancia() > 0;

    }

    public void limparCollection() {
        paredes.clear();
        teleports.clear();
        itens.clear();
    }

    private void gerarItens() {

        int n = 20;

        while (n > 0) {
            if (gerarItem()) {
                n--;
            }
        }

    }

    private boolean gerarItem() {

        int x, y;
        boolean item = false;

        Ponto ponto;
        x = new Random().nextInt(mapa_dim);
        y = new Random().nextInt(mapa_dim);

        ponto = new Ponto(x, y);

        if (x != pontoPlayer1 
                && y != pontoPlayer1
                && x != pontoPlayer2
                && y != pontoPlayer2
                && !teleports.containsKey(Teleport.getNome(ponto))
                && !paredes.containsKey(Parede.getNome(ponto))
                && !itens.containsKey(ponto.toString())) {

            itens.put(ponto.toString(), ponto);
            return true;
        }

        return false;
    }

    public Map<String, Ponto> getItens() {
        return itens;
    }

}
