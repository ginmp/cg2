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
public class Teleport extends Base {

    private Teleport teleport;

    public Teleport(Ponto ponto, Cor cor) {
        super(ponto, cor);
    }

    public Teleport(int x, int y, int z, Cor cor) {
        super(x, y, z, cor);
    }

    public Teleport(int x, int y, int z, float r, float g, float b) {
        super(x, y, z, r, g, b);
    }

    public Teleport(int x, int y, float r, float g, float b) {
        super(x, y, r, g, b);
    }

    public Teleport(int x, int y, Cor cor) {
        super(x, y, cor);
    }

    public Teleport getTeleport() {
        return teleport;
    }

    public void setTeleport(Teleport teleport) {
        this.teleport = teleport;
    }

    public static String getNome(Ponto ponto) {
        return Teleport.class.getSimpleName() + " " + ponto.toString();
    }

}
