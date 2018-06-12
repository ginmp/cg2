/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.controle;

import jogo.mapas.Mapa;

/**
 *
 * @author inmp
 */
public interface Controle {

    void setMapa(Mapa mapa);

    Mapa getMapa();

    boolean isJogar();

    void setJogar(boolean jogar);

    void reset();

    boolean mover();

    void colisao();

    void jogar();

}
