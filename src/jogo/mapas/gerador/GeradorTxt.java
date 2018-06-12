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
public class GeradorTxt implements GeradorMapa {

    private String txt;

    private Cor cor;
    private float r = 12, g = 58, b = 128;

    public GeradorTxt() {
        r /= 256;
        g /= 256;
        b /= 256;
        cor = new Cor(r, g, b);
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public void gerarMapa(Mapa mapa) {
        char[] txtChar = txt.toCharArray();
        mapa.limparCollection();

        for (int i = 0; i <= mapa.getMapa_dim(); i++) {
            for (int j = 0; j <= mapa.getMapa_dim(); j++) {
                if (txtChar[i * (mapa.getMapa_dim() + 1) + j] == '*') {
                    mapa.construirParede(j, i, cor);
                }
            }
        }
        mapa.removerParede(mapa.getPlayer1());
        mapa.removerParede(mapa.getPlayer2());
        mapa.validarMapa();

    }

}
