/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.util;

/**
 *
 * @author inmp
 */
public class Cor {

    private float r, g, b;

    public Cor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Cor() {
        this(1, 1, 1);
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public int[] getIntRGB() {
        int[] rgb = new int[3];

        rgb[0] = (int) r * 256;
        rgb[1] = (int) g * 256;
        rgb[2] = (int) b * 256;

        rgb[0] ^= 256;
        rgb[1] ^= 256;
        rgb[2] ^= 256;
        return rgb;
    }

    public Cor getCorInvertida() {
        Cor cor;
        int[] rgb = getIntRGB();

        rgb[0] /= 256;
        rgb[1] /= 256;
        rgb[2] /= 256;

        cor = new Cor(rgb[0], rgb[1], rgb[2]);
        return cor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Float.floatToIntBits(this.r);
        hash = 11 * hash + Float.floatToIntBits(this.g);
        hash = 11 * hash + Float.floatToIntBits(this.b);
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
        final Cor other = (Cor) obj;
        if (Float.floatToIntBits(this.r) != Float.floatToIntBits(other.r)) {
            return false;
        }
        if (Float.floatToIntBits(this.g) != Float.floatToIntBits(other.g)) {
            return false;
        }
        if (Float.floatToIntBits(this.b) != Float.floatToIntBits(other.b)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cor{" + "r=" + r + ", g=" + g + ", b=" + b + '}';
    }

}
