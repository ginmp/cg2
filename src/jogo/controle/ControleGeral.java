/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.controle;

import jogo.Jogo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class ControleGeral implements KeyListener, MouseListener, MouseMotionListener {

    private Ponto mousePoint = new Ponto(0, 0);
    private double dim = ((double) Jogo.RES / (double) (23 * Jogo.MULT));
    private boolean pause;
    private boolean sempreReiniciar = true;
    private boolean novoMapa;
    private boolean mudarCamera;
    private boolean girarCamera;
    private boolean aumentar;
    private boolean diminuir;
    private boolean follow;
    private boolean telaConfig;
    private boolean sair;
    private boolean mouseClick;
    private boolean delete;
    private boolean shift;
    private boolean click2;
    private boolean salvar;
    private boolean fpsAdd;
    private boolean fpsSub;
    private boolean moverP1;
    private boolean moverP2;

    public boolean isPause() {
        return pause;
    }

    public boolean isSempreReiniciar() {
        return sempreReiniciar;
    }

    public void setSempreReiniciar(boolean sempreReiniciar) {
        this.sempreReiniciar = sempreReiniciar;
    }

    public boolean isNovoMapa() {
        boolean nm = novoMapa;
        novoMapa = false;
        return nm;
    }

    public boolean isMudarCamera() {
        return mudarCamera;
    }

    public boolean isGirarCamera() {
        boolean gc = girarCamera;
        girarCamera = false;
        return gc;
    }

    public boolean isAumentar() {
        return aumentar;
    }

    public boolean isDiminuir() {
        return diminuir;
    }

    public boolean isFollow() {
        return follow;
    }

    public boolean isTelaConfig() {
        return telaConfig;
    }

    public boolean isSair() {
        return sair;
    }

    public Ponto getMousePoint() {
        mouseClick = false;
        return new Ponto(mousePoint.getX(), mousePoint.getY());
    }

    public boolean isMouseClick() {
        return mouseClick;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isShift() {
        return shift && click2;
    }

    public boolean isSalvar() {
        boolean s = salvar;
        salvar = false;
        return s;
    }

    public boolean isFpsAdd() {
        return fpsAdd;
    }

    public boolean isFpsSub() {
        return fpsSub;
    }

    public boolean isMoverP1() {
        return moverP1;
    }

    public boolean isMoverP2() {
        return moverP2;
    }

    public void reset() {
        mousePoint = new Ponto(0, 0);
        pause = novoMapa = girarCamera = mouseClick = delete = shift = click2 = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            sair = pause;
        }
        if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            aumentar = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            diminuir = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ADD  || e.getKeyCode() == KeyEvent.VK_EQUALS  ) {
            fpsAdd = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SUBTRACT  || e.getKeyCode() == KeyEvent.VK_MINUS  ) {
            fpsSub = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pause = !pause;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            sempreReiniciar = !sempreReiniciar;
        }

        if (e.getKeyCode() == KeyEvent.VK_END) {
            novoMapa = pause;
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            mudarCamera = !mudarCamera;
        }

        if (e.getKeyCode() == KeyEvent.VK_G) {
            girarCamera = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_F) {
            follow = !follow;
        }

        if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            aumentar = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            diminuir = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            telaConfig = pause;
        }

        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            delete = !delete;
        }

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = !shift;
            click2 = !shift ? false : click2;
        }

        if (e.getKeyCode() == KeyEvent.VK_S )  {
            salvar = pause;
        }
        if (e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_EQUALS ) {
            fpsAdd = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SUBTRACT || e.getKeyCode() == KeyEvent.VK_MINUS) {
            fpsSub = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
            moverP1 = !moverP1;
            moverP2 = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            moverP2 = !moverP2;
            moverP1 = false;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (!shift || moverP1 || moverP2) {
            int x = e.getPoint().x;
            int y = e.getPoint().y;

            mousePoint.setX((int) (x / dim));
            mousePoint.setY((int) (y / dim));
            mouseClick = true;
        }

        click2 = shift;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shift && click2 && !moverP1 && !moverP2) {
            int x = e.getPoint().x;
            int y = e.getPoint().y;

            mousePoint.setX((int) (x / dim));
            mousePoint.setY((int) (y / dim));
        }
    }

}
