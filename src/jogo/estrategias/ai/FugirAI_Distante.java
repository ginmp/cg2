/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import jogo.controle.Controle;
import jogo.entidades.Player;
import jogo.estrategias.Path;
import jogo.estruturas.Node;
import jogo.mapas.Mapa;

/**
 *
 * @author inmp
 */
public class FugirAI_Distante implements Controle, AI {

    private Mapa mapa;
    private Path path;

    private Player playerOponente, playerControlado;

    private boolean jogar;

    private int profundidade;

    @Override
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
        profundidade = 120;
    }

    @Override
    public Mapa getMapa() {
        return mapa;
    }

    @Override
    public boolean isJogar() {
        return jogar;
    }

    @Override
    public void setJogar(boolean jogar) {
        this.jogar = jogar;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean mover() {

        Node no = mapa.getGraph().find(playerOponente.getPonto().toString());
        Node nc = mapa.getGraph().find(playerControlado.getPonto().toString());
        Node melhorNode = nc;

        path.ativarProfundidadeLimitada(profundidade);
        path.algoritmoCaminho(nc.getNome(), no.getNome());
        path.desativarProfundidadeLimitada();

        TreeMap<Integer, Map<String, Node>> nodesP0 = path.getOrdenadoProfundidade();

        List<Node> nodesP1 = new ArrayList<Node>();

        path.setProfundidadeLimitada(true);
        for (Map.Entry<Integer, Map<String, Node>> tm : nodesP0.entrySet()) {
            int prof = tm.getKey();
            path.setProfundidade(prof);

            for (Node np0 : tm.getValue().values()) {
                path.algoritmoCaminho(no.getNome(), np0.getNome());
                if (np0.getParent() == null) {
                    nodesP1.add(np0);
                }
            }
        }

        path.desativarProfundidadeLimitada();

        int distancia = 0;

        TreeMap<Integer, List<Node>> nodesP2 = new TreeMap<Integer, List<Node>>();
        List<Node> alNodesP2;

        for (Node np1 : nodesP1) {

            path.algoritmoCaminho(no.getNome(), np1.getNome());

            if (path.getDistancia() > 1) { // alteracao...
                alNodesP2 = nodesP2.get(path.getDistancia());
                if (alNodesP2 == null) {
                    alNodesP2 = new ArrayList<Node>();
                    nodesP2.put(path.getDistancia(), alNodesP2);
                }

                if (np1.getEdges().size() > 1) {
                    alNodesP2.add(np1);// alteracao...

                }
            }

        }
        melhorNode = nc;
        int edges = 1;
        distancia = 2;
        boolean continuar = true;

        while (continuar && !nodesP2.isEmpty()) {
            alNodesP2 = nodesP2.lastEntry().getValue();

            nodesP2.remove(nodesP2.lastKey(), nodesP2.lastEntry().getValue());

            for (Node np2 : alNodesP2) {
                path.algoritmoCaminho(nc.getNome(), np2.getNome());
                Node node = np2;
                while (node != null && node.getParent() != nc) {
                    node = node.getParent();
                }

                if (node != null) {
                    path.algoritmoCaminho(no.getNome(), node.getNome());

                    if (path.getDistancia() >= distancia) {

                        if (node.getEdges().size() > edges) {
                            edges = node.getEdges().size();
                            melhorNode = node;
                            distancia = path.getDistancia();
                            continuar = melhorNode == nc;
                        } else if (node.getEdges().size() == edges) {
                            if (new Random().nextInt() % 2 == 0) {
                                melhorNode = node;
                                distancia = path.getDistancia();
                                continuar = melhorNode == nc;
                            }
                        } else if (path.getDistancia() > distancia && node.getEdges().size() > 1) {
                            melhorNode = node;
                            continuar = melhorNode == nc;

                        }
                    }
                } else if (melhorNode == nc && path.getDistancia() > 1) {
                    melhorNode = node;
                    System.out.println(melhorNode);

                }
            }

        }

//        path.algoritmoCaminho(n2.getNome(), melhorNode.getNome());
//        Node node = melhorNode;
//        while (node != null && node.getParent() != n2) {
//            node = node.getParent();
//        }
//
//        if (node != null) {
//            melhorNode = node;
//        } else {
//            melhorNode = n2;
//        }
        path.algoritmoCaminho(playerOponente.getPonto().toString(), melhorNode.getPonto().toString());
        playerControlado.setPonto(melhorNode.getPonto());

        return true;
    }

    @Override
    public void colisao() {
        if (playerControlado.colisao(playerOponente)) {
            mapa.setNovoMapa(true);
        }
    }

    @Override
    public void jogar() {
        if (mover()) {
            colisao();
            jogar = false;
        }

        if (path.getDistancia() == 0) {
            mapa.setNovoMapa(true);
        }

    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path
    ) {
        this.path = path;
    }

    @Override
    public Player getPlayerOponente() {
        return playerOponente;
    }

    @Override
    public void setPlayerOponente(Player oponente) {
        this.playerOponente = oponente;
    }

    @Override
    public Player getPlayerControlado() {
        return playerControlado;
    }

    @Override
    public void setPlayerControlado(Player controlado) {
        this.playerControlado = controlado;
    }

}
