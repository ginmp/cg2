/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.mapas.gerador;

import java.util.Random;
import jogo.mapas.Mapa;
import jogo.util.Cor;

/**
 *
 * @author inmp
 */
public class GeradorSimples implements GeradorMapa {

    private Cor cor;
    private float r = 12, g = 58, b = 128;

    public GeradorSimples() {
        r /= 256;
        g /= 256;
        b /= 256;
        cor = new Cor(r, g, b);
    }

    @Override
    public void gerarMapa(Mapa mapa) {

        Random rand = new Random();
        int r = 0;
        do {
            mapa.limparCollection();

            for (int i = 0; i <= mapa.getMapa_dim(); i++) {

                for (int j = 0; j <= mapa.getMapa_dim(); j++) {
                    r = rand.nextInt(0x100) & 0xC;
                    if (i == 0 || j == 0 || i == mapa.getMapa_dim() || j == mapa.getMapa_dim() || r == 0x8) {
                        mapa.construirParede(i, j, cor);
                    }

                } // for j

            }//for i

            mapa.removerParede(mapa.getPlayer1());
            mapa.removerParede(mapa.getPlayer2());

        } while (!mapa.validarMapa());

    }

}
