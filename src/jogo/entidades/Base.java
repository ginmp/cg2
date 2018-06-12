/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.entidades;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import jogo.util.Cor;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public abstract class Base {

    protected Ponto ponto;
    protected Cor cor;

    private enum Move {
        UP, DOWN, RIGHT, LEFT
    }
    private Move lastMove;

    private LinkedList<Move> direcoes;

    public Base(Ponto ponto, Cor cor) {
        direcoes = new LinkedList<Move>();
        direcoes.addLast(Move.UP);
        direcoes.addLast(Move.RIGHT);
        direcoes.addLast(Move.DOWN);
        direcoes.addLast(Move.LEFT);

        this.ponto = ponto;
        this.cor = cor;
    }

    public Base(int x, int y, int z, Cor cor) {
        this(new Ponto(x, y, z), cor);
    }

    public Base(int x, int y, int z, float r, float g, float b) {
        this(new Ponto(x, y, z), new Cor(r, g, b));
    }

    public Base(int x, int y, float r, float g, float b) {
        this(new Ponto(x, y, 0), new Cor(r, g, b));
    }

    public Base(int x, int y, Cor cor) {
        this(new Ponto(x, y, 0), cor);
    }

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }

    public int getX() {
        return ponto.getX();
    }

    public void setX(int x) {
        ponto.setX(x);
    }

    public int getY() {
        return ponto.getY();
    }

    public void setY(int y) {
        ponto.setY(y);
    }

    public int getZ() {
        return ponto.getZ();
    }

    public void setZ(int z) {
        ponto.setZ(z);
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public float getR() {
        return cor.getR();
    }

    public void setR(float r) {
        cor.setR(r);
    }

    public float getG() {
        return cor.getG();
    }

    public void setG(float g) {
        cor.setG(g);
    }

    public float getB() {
        return cor.getB();
    }

    public void setB(float b) {
        cor.setB(b);
    }

    public void moveUP() {
//        this.setY(this.getY() - 1);
//        move = Move.UP;

        Move dir = direcoes.getFirst();
        move(dir, Move.UP);
    }

    public void moveDOWN() {
//        this.setY(this.getY() + 1);
//        move = Move.DOWN;

        Move dir = direcoes.get(2);
        move(dir, Move.DOWN);
    }

    public void moveRIGHT() {
//        this.setX(this.getX() + 1);
//        move = Move.RIGHT;

        Move dir = direcoes.get(1);
        move(dir, Move.RIGHT);
    }

    public void moveLEFT() {
//        this.setX(this.getX() - 1);
//        move = Move.LEFT;

        Move dir = direcoes.getLast();
        move(dir, Move.LEFT);
    }

    private void move(Move direcao, Move direcaoVisual) {
        if (direcao == Move.UP) {
            this.setY(this.getY() - 1);
            lastMove = direcaoVisual;
        } else if (direcao == Move.RIGHT) {
            this.setX(this.getX() + 1);
            lastMove = direcaoVisual;
        } else if (direcao == Move.DOWN) {
            this.setY(this.getY() + 1);
            lastMove = direcaoVisual;
        } else {
            this.setX(this.getX() - 1);
            lastMove = direcaoVisual;
        }
    }

    public void cancelMove() {

        if (lastMove != null) {
            if (lastMove == Move.UP) {
                moveDOWN();
            } else if (lastMove == Move.DOWN) {
                moveUP();
            } else if (lastMove == Move.RIGHT) {
                moveLEFT();
            } else {
                moveRIGHT();
            }
        }
    }

    public boolean colisao(Base base) {
        return this.ponto.equals(base.ponto);
    }

    public void girar() {
        Collections.rotate(direcoes, -1);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.ponto);
        hash = 37 * hash + Objects.hashCode(this.cor);
        hash = 37 * hash + Objects.hashCode(this.lastMove);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Base other = (Base) obj;
        if (!Objects.equals(this.ponto, other.ponto)) {
            return false;
        }
        if (this.lastMove != other.lastMove) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + ponto.toString();

    }

}
