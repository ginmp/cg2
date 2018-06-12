/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias.ai;

import java.util.LinkedList;
import jogo.Jogo;
import jogo.controle.BuildWall;
import jogo.controle.Controle;
import jogo.entidades.Player;
import jogo.estrategias.Path;
import jogo.estruturas.Node;
import jogo.mapas.Mapa;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class SeguirAI implements Controle, AI, BuildWall {

    private Mapa mapa;
    private Path path;

    private Player playerOponente, playerControlado;

    private LinkedList<Ponto> ultimasPosicoes = new LinkedList<Ponto>();
    private int nPosicoes = 20;
    private int paredes;

    private boolean jogar;
    private boolean build;

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
        ultimasPosicoes.clear();
        paredes = 0;
    }

    private void add(Ponto ponto) {
        if (ultimasPosicoes.size() >= nPosicoes * Jogo.MULT) {
            ultimasPosicoes.removeFirst();
        }
        ultimasPosicoes.addLast(ponto);
    }

    @Override
    public boolean mover() {

        path.algoritmoCaminho(playerOponente.getPonto().toString(), playerControlado.getPonto().toString());

        Node n = mapa.getGraph().find(playerControlado.getPonto().toString());

        if (n.getParent() != null) {

//            if (ultimasPosicoes.contains(playerControlado.getPonto()) && build) {
//                if (mapa.construirParede(playerControlado.getPonto(), playerControlado.getCor().getCorInvertida())) {
//                    ultimasPosicoes.clear();
//                    paredes++;
//                }
//            }
//
//            add(playerControlado.getPonto());
            playerControlado.setPonto(n.getParent().getPonto());

            return true;
        }

        return false;
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

    @Override
    public int getParedes() {
        return paredes;
    }

    @Override
    public boolean isBuild() {
        return build;
    }

    @Override
    public void setBuild(boolean build) {
        this.build = build;
    }

}
