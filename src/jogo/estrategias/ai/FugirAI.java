/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import jogo.controle.Controle;
import jogo.entidades.Parede;
import jogo.entidades.Player;
import jogo.estrategias.Path;
import jogo.estruturas.Node;
import jogo.mapas.Mapa;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class FugirAI implements Controle, AI {

    private Mapa mapa;
    private Path path;

    private Player playerOponente, playerControlado;

    private boolean jogar;

    private List<Node> getSucessores(Node node) {
        List<Node> sucessores = new ArrayList<Node>();

        for (Node n : mapa.getGraph().getNeighbours(node.getNome())) {
            sucessores.add(n);
        }

        Collections.shuffle(sucessores);

        return sucessores;
    }

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

        path.algoritmoCaminho(playerOponente.getPonto().toString(), playerControlado.getPonto().toString());
        distancia = path.getDistancia();

        melhorNode = nc;

        List<Node> sucessores = getSucessores(nc);

        for (Node node : sucessores) {
            path.algoritmoCaminho(playerOponente.getPonto().toString(), node.getPonto().toString());
            if (path.getDistancia() >= distancia) {
                melhorNode = node;
                distancia = path.getDistancia();
            }
        }

        path.algoritmoCaminho(playerOponente.getPonto().toString(), playerControlado.getPonto().toString());
        if (melhorNode == nc) {
            if (path.getDistancia() > 2) {
                melhorNode = sucessores.get(new Random().nextInt(sucessores.size()));
            }
        }

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
