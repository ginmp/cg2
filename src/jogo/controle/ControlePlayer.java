/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.controle;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import java.util.HashMap;
import java.util.Map;
import jogo.entidades.Parede;
import jogo.entidades.Player;
import jogo.entidades.Teleport;
import jogo.mapas.Mapa;
import jogo.util.Cor;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class ControlePlayer implements Controle, ActionListener, BuildWall {

    private HashMap<String, KeyTrigger> mapping;

    public static enum Tecla {
        UP, RIGHT, DOWN, LEFT, WALL
    }

    private Player player;
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    private boolean jogar;
    private boolean construirParede;

    private int maxParedes = 10;
    private int paredes;
    private boolean build;

    private Mapa mapa;

    private Ponto ultimaPosicao;

    private boolean teleport;
    private boolean entrar;

    public ControlePlayer() {
        mapping = new HashMap();
    }

    public void setMappingPlayer1() {
        mapping.put("CharLeft_1", new KeyTrigger(KeyInput.KEY_A));
        mapping.put("CharRight_1", new KeyTrigger(KeyInput.KEY_D));
        mapping.put("CharForward_1", new KeyTrigger(KeyInput.KEY_W));
        mapping.put("CharBackward_1", new KeyTrigger(KeyInput.KEY_S));
        mapping.put("TeleportEnter_1", new KeyTrigger(KeyInput.KEY_RETURN));
    }

    public void setMappingPlayer2() {
        mapping.put("CharLeft_2", new KeyTrigger(KeyInput.KEY_LEFT));
        mapping.put("CharRight_2", new KeyTrigger(KeyInput.KEY_RIGHT));
        mapping.put("CharForward_2", new KeyTrigger(KeyInput.KEY_UP));
        mapping.put("CharBackward_2", new KeyTrigger(KeyInput.KEY_DOWN));
        mapping.put("TeleportEnter_2", new KeyTrigger(KeyInput.KEY_END));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int getParedes() {
        return paredes;
    }

    @Override
    public boolean isBuild() {
        return build;
    }

    @Override
    public void setBuild(boolean build) {
        this.build = build;
    }

    @Override
    public boolean isJogar() {
        return jogar;
    }

    @Override
    public void setJogar(boolean jogar) {
        this.jogar = jogar;
    }

    @Override
    public void reset() {
        paredes = 0;
    }

    @Override
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
        ultimaPosicao = new Ponto(0, 0);
    }

    @Override
    public Mapa getMapa() {
        return mapa;
    }

    public HashMap<String, KeyTrigger> getMapping() {
        return mapping;
    }

    @Override
    public boolean mover() {

        if (up || down || right || left || entrar) {

            Ponto ponto = player.getPonto();
            ultimaPosicao = new Ponto(ponto.getX(), ponto.getY());
            Teleport tp = null;
            for (Teleport t : mapa.getTeleports().values()) {
                if (player.colisao(t)) {
                    teleport = true;
                    tp = t;
                    break;
                }
            }

            if (entrar) {
                if (teleport) {
                    player.setX(tp.getTeleport().getX());
                    player.setY(tp.getTeleport().getY());
                    System.out.println(tp);
                }
            } else if (up) {
                player.moveUP();
            } else if (down) {
                player.moveDOWN();
            } else if (right) {
                player.moveRIGHT();
            } else if (left) {
                player.moveLEFT();
            }

            teleport = entrar = up = down = right = left = false;

            return true;
        }

        return false;

    }

    @Override
    public void colisao() {

        Player player1 = mapa.getPlayer1();
        Player player2 = mapa.getPlayer2();

        if (!player.equals(player1)) {
            player2 = player1;
            player1 = player;
        }

        boolean colisao = false;

        if (player1.colisao(player2)) {
            construirParede = false;
            mapa.setNovoMapa(true);
        }

        for (Parede p : mapa.getParedes().values()) {
            if (player1.colisao(p)) {
                player1.cancelMove();
                colisao = true;
                break;
            }
        }

        if (!colisao) {
            jogar = false;
            if (construirParede && build) {
                if (paredes < maxParedes) {
                    construirParede = false;
                    if (mapa.construirParede(ultimaPosicao, new Cor(0, .6f, .5f))) {
                        paredes++;
                    }
                }
            }
        }

    }

    @Override
    public void jogar() {

        if (mover()) {
            colisao();
        }

    }

    public void initKeys(InputManager inputManager) {

        for (Map.Entry<String, KeyTrigger> kv : mapping.entrySet()) {
            inputManager.addMapping(kv.getKey(), kv.getValue());
            inputManager.addListener(this, kv.getKey());
        }

    }

    @Override
    public void onAction(String binding, boolean value, float tpf) {

        if (binding.contains("CharLeft")) {
            if (value) {
                left = jogar;
            } else {
                left = false;
            }
        } else if (binding.contains("CharRight")) {
            if (value) {
                right = jogar;
            } else {
                right = false;
            }

        } else if (binding.contains("CharForward")) {
            if (value) {
                up = jogar;
            } else {
                up = false;
            }
        } else if (binding.contains("CharBackward")) {
            if (value) {
                down = jogar;
            } else {
                down = false;
            }
        } else if (binding.contains("TeleportEnter")) {
            if (value) {
                entrar = jogar;
            } else {
                entrar = false;
            }
        }

    }
}
