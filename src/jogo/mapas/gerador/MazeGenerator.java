/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.mapas.gerador;

/**
 *
 * @author inmp
 */
import java.util.Collections;
import java.util.Arrays;
import jogo.mapas.Mapa;
import jogo.util.Cor;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator implements GeradorMapa {

    private int x;
    private int y;
    private int[][] maze;

    private Cor cor;
    private float r = 12, g = 58, b = 128;

    public MazeGenerator() {
        r /= 256;
        g /= 256;
        b /= 256;
        cor = new Cor(r, g, b);
    }

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
    }

    public void display() {
        for (int i = 0; i < y; i++) {
            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < x; j++) {
            System.out.print("+---");
        }
        System.out.println("+");

//        System.out.println("\n\n\n\n");
//
//        for (int i = 0; i < y; i++) {
//            for (int j = 0; j < x; j++) {
//                System.out.print(maze[i][j] + " ");
//            }
//            System.out.println("");
//
//        }
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            //    N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

    public static void main(String[] args) {
        int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 3;
        int y = args.length == 2 ? (Integer.parseInt(args[1])) : 3;
        MazeGenerator maze = new MazeGenerator(x, y);
        maze.display();
    }

    @Override
    public void gerarMapa(Mapa mapa) {

        x = y = mapa.getMapa_dim() + 1;
        maze = new int[this.x][this.y];

        do {
            mapa.limparCollection();
            generateMaze(0, 0);

            for (int i = 0; i < mapa.getMapa_dim(); i += 1) {

                for (int j = 0; j < mapa.getMapa_dim(); j +=2) {
                    if ((maze[i][j] & 1) == 0) {
                        mapa.construirParede(i, j, cor);
                    }
                    if ((maze[i][j] & 8) == 0) {
                        mapa.construirParede(i, j, cor);
                    }
                }

            }
            for (int i = 0; i < x; i++) {
                mapa.construirParede(0, i, cor);
                mapa.construirParede(mapa.getMapa_dim(), i, cor);
                mapa.construirParede(i, 0, cor);
                mapa.construirParede(i, mapa.getMapa_dim(), cor);
            }

            mapa.removerParede(mapa.getPlayer1());
            mapa.removerParede(mapa.getPlayer2());
            System.out.println(mapa.getParedes().size());

        } while (!mapa.validarMapa());

        display();
    }
}
