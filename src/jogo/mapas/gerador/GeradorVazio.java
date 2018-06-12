/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.mapas.gerador;

import jogo.mapas.Mapa;
import jogo.util.Cor;

/**
 *
 * @author inmp
 */
public class GeradorVazio implements GeradorMapa {

    private Cor cor;
    private float r = 12, g = 58, b = 128;

    public GeradorVazio() {
        r /= 256;
        g /= 256;
        b /= 256;
        cor = new Cor(r, g, b);
    }

    @Override
    public void gerarMapa(Mapa mapa) {

        mapa.limparCollection();

        for (int i = 0; i <= mapa.getMapa_dim(); i++) {
            mapa.construirParede(0, i, cor);
            mapa.construirParede(mapa.getMapa_dim(), i, cor);
            mapa.construirParede(i, 0, cor);
            mapa.construirParede(i, mapa.getMapa_dim(), cor);
        }

        mapa.removerParede(mapa.getPlayer1());
        mapa.removerParede(mapa.getPlayer2());

        mapa.validarMapa();

    }

}
