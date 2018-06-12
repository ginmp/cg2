/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.entidades;

import jogo.util.Cor;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class Parede extends Base {

    public Parede(Ponto ponto, Cor cor) {
        super(ponto, cor);
    }

    public Parede(int x, int y, int z, Cor cor) {
        super(x, y, z, cor);
    }

    public Parede(int x, int y, int z, float r, float g, float b) {
        super(x, y, z, r, g, b);
    }

    public Parede(int x, int y, float r, float g, float b) {
        super(x, y, r, g, b);
    }

    public Parede(int x, int y, Cor cor) {
        super(x, y, cor);
    }

//    public Parede(Ponto ponto) {
//        super(ponto);
//    }
//
//    public Parede(int x, int y, int z) {
//        super(x, y, z);
//    }
//
//    public Parede(int x, int y) {
//        super(x, y);
//    }
    public static String getNome(Ponto ponto) {
        return Parede.class.getSimpleName() + " " + ponto.toString();
    }

}
