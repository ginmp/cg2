/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estrategias.ai;

import jogo.entidades.Player;
import jogo.estrategias.Path;

/**
 *
 * @author inmp
 */
public interface AI {

    Path getPath();

    void setPath(Path path);

    Player getPlayerOponente();

    void setPlayerOponente(Player oponente);

    Player getPlayerControlado();

    void setPlayerControlado(Player controlado);

}
