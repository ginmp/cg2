/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.entidades;

import jogo.controle.Controle;
import jogo.util.Cor;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class Player extends Base {

    private Controle controle;

    public Player(Ponto ponto, Cor cor) {
        super(ponto, cor);

    }

    public Player(int x, int y, int z, Cor cor) {
        super(x, y, z, cor);
    }

    public Player(int x, int y, int z, float r, float g, float b) {
        super(x, y, z, r, g, b);
    }

    public Player(int x, int y, float r, float g, float b) {
        super(x, y, r, g, b);
    }

    public Player(int x, int y, Cor cor) {
        super(x, y, cor);
    }

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

}
