/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import jogo.controle.Controle;
import jogo.mapas.Mapa;

/**
 *
 * @author inmp
 */
public class Jogando implements IJogo {

    private Mapa mapa;
    private boolean p1Turn = true;
    private boolean p2Turn = false;

    public Jogando(Mapa mapa) {
        this.mapa = mapa;
    }

    private void player1Jogar() {

        if (!p2Turn) {
            Controle controle = mapa.getPlayer1().getControle();
            controle.setJogar(true);
            controle.jogar();
            p1Turn = controle.isJogar();
        }
    }

    private void player2Jogar() {

        if (!p1Turn) {
            Controle controle = mapa.getPlayer2().getControle();
            controle.setJogar(true);
            controle.jogar();
            p2Turn = controle.isJogar();
        }
    }

    @Override
    public void jogar() {
        player1Jogar();
        player2Jogar();
    }

}
