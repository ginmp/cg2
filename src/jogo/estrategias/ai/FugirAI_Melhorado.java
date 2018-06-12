/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
public class FugirAI_Melhorado implements Controle, AI {

    private Mapa mapa;
    private Path path;

    private Player playerOponente, playerControlado;

    private boolean jogar;

    private int profundidade = 10;

    @Override
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
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

        int distancia = 0;
        Node nc = mapa.getGraph().find(playerControlado.getPonto().toString());
        Node melhorNode;

        // a partir do player controlado, fazer passeio no grafo
        // pegar os nodes ate um certo nivel/profundidade
        // fazer passeio desses nodes ate o outro player
        // encontrar ql tiver maior distancia
        // com o node mais distante, fazer o passeio deste node ate o player controlado
        // deste passeio, pegar o node cujo pai é o player controlado
        List<Node> nodesProfundidadeLimitada = new ArrayList<Node>();

        melhorNode = nc;

        path.ativarProfundidadeLimitada(profundidade);
        path.algoritmoCaminho(playerControlado.getPonto().toString()); // a partir do player controlado, fazer passeio no grafo

        nodesProfundidadeLimitada.addAll(path.getNodes().values()); // pegar os nodes ate um certo nivel/profundidade  

        path.desativarProfundidadeLimitada();

        TreeMap<Integer, List<Node>> nodesMelhorDistancia = new TreeMap<Integer, List<Node>>();

        for (Node n : nodesProfundidadeLimitada) {
            path.algoritmoCaminho(playerOponente.getPonto().toString(), n.getPonto().toString()); // fazer passeio desses nodes ate o outro player
            if (path.getDistancia() >= distancia && n.getEdges().size() > 1) {   // encontrar ql tiver maior distancia              
                distancia = path.getDistancia();
                melhorNode = n;

                // alteracao : pegando todos com msm distancia
                {
                    List<Node> melhores = nodesMelhorDistancia.get(distancia);

                    if (melhores == null) {
                        melhores = new ArrayList<Node>();
                        nodesMelhorDistancia.put(distancia, melhores);
                    }
                    melhores.add(n);
//                    Collections.shuffle(melhores);
//                    System.out.println("path dist = " + distancia);
//                    System.out.println("lastkey " + nodesMelhorDistancia.lastKey());
//                    System.out.println("size lastentry " + nodesMelhorDistancia.lastEntry().getValue().size());

                } // alteracao

            }
        }

        // alteracao
        {
            Map<Node, Map<String, Node>> nodesCaminhoAteMelhorDistancia = new HashMap<Node, Map<String, Node>>();

//            System.out.println("distancia = " + nodesMelhorDistancia.lastKey());
            if (nodesMelhorDistancia.size() != 0) {
                for (Node n : nodesMelhorDistancia.lastEntry().getValue()) { // nodes mais distantes
                    path.algoritmoCaminho(playerControlado.getPonto().toString(), n.getPonto().toString()); // caminho do player sendo controlado ate o ponto mais distance do outro player
//                System.out.println(path.getDistancia());
                    nodesCaminhoAteMelhorDistancia.put(n, path.getNodes());

                }

                List<Node> nodesPathPlayers = new ArrayList<Node>();

                path.algoritmoCaminho(playerOponente.getPonto().toString(), playerControlado.getPonto().toString()); // caminho entre os players

                nodesPathPlayers.addAll(path.getNodes().values());

                Map<Node, Map<String, Node>> nodesMap = new HashMap<Node, Map<String, Node>>();
                List<Node> nodesList = new ArrayList<Node>();

                //testando
                TreeMap<Integer, List<Node>> menosDistantesDentroCaminho = new TreeMap<Integer, List<Node>>(); // nodes menos distantes entre o outro player e os 
                //nodes dentro do caminho entre player controlado ate o ponto mais distante

                for (Map.Entry<Node, Map<String, Node>> m : nodesCaminhoAteMelhorDistancia.entrySet()) {

                    List<Node> list = new ArrayList<Node>();
                    list.addAll(m.getValue().values()); // nodes do caminho ate a melhor distancia
                    list.retainAll(nodesPathPlayers); // mantem nodes q possuem no caminho entre os players

                    //testando
                    {
                        distancia = 0;
                        for (Node n : list) {
                            path.algoritmoCaminho(playerOponente.getPonto().toString(), n.getNome());

                            distancia += path.getDistancia();
                        }

                        List<Node> lm = menosDistantesDentroCaminho.get(distancia);
                        if (lm == null) {
                            lm = new ArrayList<Node>();
                            menosDistantesDentroCaminho.put(distancia, lm);
                        }
                        lm.add(m.getKey());

                    } // testando
                    if (m.getKey().getEdges().size() > 1) {
                        if (list.size() == nodesList.size()) {

                            if (new Random().nextInt() % 2 == 0) {
                                nodesList = list;
                                melhorNode = m.getKey();
//                        System.out.println("list ou nodesList");
                            }
                        } else if (nodesList.size() < list.size()) {
                            nodesList = list;
                            melhorNode = m.getKey();
                        }
                    }

                }

                //testando
                if (menosDistantesDentroCaminho.lastEntry().getValue().size() >= 2) {
//                System.out.println(nodesCaminhoAteMelhorDistancia.size() + " PIOR");
//                System.out.println(menosDistantesDentroCaminho.lastEntry().getValue().size() + " = PIOR");
                    List<Node> nodes = menosDistantesDentroCaminho.lastEntry().getValue();
                    Collections.shuffle(nodes);

                    melhorNode = nodes.get(0);

                }
            }
        }// alteracao

        path.algoritmoCaminho(playerControlado.getPonto().toString(), melhorNode.getPonto().toString());  // com o node mais distante, fazer o passeio deste node ate o player controlado
        Node node = melhorNode;
        while (node != null && node.getParent() != nc) {
            node = node.getParent();        // deste passeio, pegar o node cujo pai é o player controlado
        }

        // alteracao 
        {
            if (node != null) {
                melhorNode = node;
                path.algoritmoCaminho(playerOponente.getPonto().toString(), melhorNode.getPonto().toString());
            }

        } // alteracao

//        if(node == null){
        if (path.getDistancia() < 2 || node == null) { // alteracao
            int d = path.getDistancia();
            path.algoritmoCaminho(playerOponente.getPonto().toString(), playerControlado.getPonto().toString());
            melhorNode = nc;

//            System.out.println(d + " FugirAI " + node);
            // alteracao
            {
                FugirAI fai = new FugirAI();
                fai.setPlayerOponente(this.playerOponente);
                fai.setPlayerControlado(this.playerControlado);
                fai.setPath(this.path);
                fai.setMapa(this.mapa);
                fai.mover();
            } // alteracao
        } else {
            melhorNode = node;

            // alteracao
            {
                playerControlado.setPonto(melhorNode.getPonto());
            } // alteracao
        }

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
    public void setPath(Path path) {
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
