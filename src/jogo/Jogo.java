/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import jogo.entidades.Parede;
import jogo.mapas.Mapa;

import jogo.controle.Controle;
import jogo.controle.ControlePlayer;
import jogo.controle.ControleGeral;
import jogo.entidades.Player;
import jogo.entidades.Teleport;
import jogo.estrategias.ai.FugirAI_Melhorado;
import jogo.estrategias.ai.SeguirAI;
import jogo.mapas.gerador.GeradorMapa;
import jogo.mapas.gerador.GeradorSimples;
import jogo.util.Ponto;

/**
 *
 * @author inmp
 */
public class Jogo extends SimpleApplication
        implements IJogo {

    private BulletAppState bulletAppState;

    private Node node_paredes = new Node();
    private Node node_teleports = new Node();
    private Node node_itens = new Node();

    private float item_rotation = 0.1f;
    private BitmapText pointsText;
    private int itens;
    private int points;

    private Spatial player1;
    private Spatial player2;

    private final Vector3f UP = Vector3f.UNIT_Y;

    private GeradorMapa gerador;
    private Controle controlePlayer1;
    private Controle controlePlayer2;

    private boolean novoMapa;

    //============
    public static long FPS = 60;
    public static int MULT = 1;

    public static int RES = 1000;

    private Mapa mapa;

    private Jogando jogando;

    private ControleGeral controleGeral;

    private boolean stop;

    public static void main(String[] args) {
        Jogo app = new Jogo();
        // app.showSettings = false;
        app.setSeguirAI_vs_Player();
        app.start();
    }

    public void setPlayer_vs_Player() {
        controlePlayer1 = new ControlePlayer();
        controlePlayer2 = new ControlePlayer();
    }

    public void setPlayer_vs_FugirAI() {
        controlePlayer1 = new ControlePlayer();
        controlePlayer2 = new FugirAI_Melhorado();
    }

    public void setSeguirAI_vs_SeguirAI() {
        controlePlayer1 = new SeguirAI();
        controlePlayer2 = new SeguirAI();
    }

    public void setSeguirAI_vs_FugirAI() {
        controlePlayer1 = new SeguirAI();
        controlePlayer2 = new FugirAI_Melhorado();
    }

    public void setSeguirAI_vs_Player() {
        controlePlayer1 = new SeguirAI();
        controlePlayer2 = new ControlePlayer();
    }

    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        initJogo();

        createLigth();

        desenharPiso();
        desenharMapa();
        desenharTeleports();
        createItens();

        desenharPlayer1();
        desenharPlayer2();

        System.out.println(getCamera().getLocation());
        int n = 1;
        int h = 55 * MULT;

        Vector3f camPos = new Vector3f(n * 22 * MULT, h, n * 22 * MULT);

        this.getCamera().setLocation(camPos);

        disableFlyCam();
        initPointsText();

        controleGeral = new ControleGeral();

    }

    private void disableFlyCam() {

        float sqrt2 = (float) (Math.sqrt(2) / 2);
        this.getCamera().setRotation(new Quaternion(0.0f, -sqrt2, sqrt2, 0.0f));
        flyCam.setEnabled(false);

        
    }

    private void initPointsText() {
        pointsText = new BitmapText(guiFont, false);
        pointsText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        pointsText.setColor(ColorRGBA.Blue);                             // font color
        //pointsText.setText("You can write any string here");             // the text
        pointsText.setLocalTranslation(300, pointsText.getLineHeight(), 0); // position
        guiNode.attachChild(pointsText);
        updatePointsText();
    }

    private void updatePointsText() {
        pointsText.setText("Itens : " + itens + " ::::: Pontos : " + points);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        jogar();
        updateItens();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void createLigth() {

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-.5f, -1f, 0)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

        /* this shadow needs a directional light */
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 1024, 2);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

    }

    private void detach(Spatial sp) {
        rootNode.detachChild(sp);
        RigidBodyControl r = sp.getControl(RigidBodyControl.class
        );
        bulletAppState.getPhysicsSpace().remove(r);
    }

    public Jogo() {
        this(new GeradorSimples(), new ControlePlayer(), new FugirAI_Melhorado());
//        gerador = new GeradorVazio();
    }

    public Jogo(GeradorMapa gerador, Controle controlePlayer1, Controle controlePlayer2) {
        this.gerador = gerador;
        this.controlePlayer1 = controlePlayer1;
        this.controlePlayer2 = controlePlayer2;
    }

    private void initJogo() {

        player1 = assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        player1.setName("p1");

        float dim = .25f;
        player1.setLocalScale(dim, dim, dim);

        player2 = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");

        player2.setName("p2");

        dim = .015f;
        player2.setLocalScale(dim, dim, dim);

        rootNode.attachChild(player1);
        rootNode.attachChild(player2);

        if (controlePlayer1 instanceof ControlePlayer) {
            ((ControlePlayer) controlePlayer1).setMappingPlayer1();
            ((ControlePlayer) controlePlayer1).initKeys(inputManager);
        }

        if (controlePlayer2 instanceof ControlePlayer) {
            ((ControlePlayer) controlePlayer2).setMappingPlayer2();
            ((ControlePlayer) controlePlayer2).initKeys(inputManager);
        }

        mapa = new Mapa(gerador, controlePlayer1, controlePlayer2);
        mapa.gerarMapa();

        jogando = new Jogando(mapa);

    }

    private void createItens() {
        Spatial item;
        for (Ponto ponto : mapa.getItens().values()) {
            item = assetManager.loadModel("Models/gltf/duck/Duck.gltf");
            item.setLocalTranslation(2 * ponto.getX(), ponto.getZ(), 2 * ponto.getY());
            item.setName("item");
            node_itens.attachChild(item);
        }
        rootNode.attachChild(node_itens);
        node_itens.setName("node_itens");
    }

    private void updateItens() {
        Spatial detach = null;
        Spatial player
                = mapa.getPlayer2().getControle() instanceof FugirAI_Melhorado
                || mapa.getPlayer2().getControle() instanceof ControlePlayer ? player2 : player1;
        Vector3f player1_position = player.getLocalTranslation().clone();
        Vector3f item_position;

        player1_position.setY(0);
        for (Spatial item : node_itens.getChildren()) {

            if (detach == null) {
                item_position = item.getLocalTranslation().clone();
                item_position.setY(0);
                if (player1_position.equals(item_position)) {
                    detach = item;
                }
            }
            item.rotate(0, item_rotation, 0);
        }

        if (detach != null) {
            node_itens.detachChild(detach);
            itens++;
            updatePointsText();
            if (node_itens.getChildren().size() == 0) {
                novoMapa = true;
            }
        }

    }

    private void restartGame() {
        detach(node_paredes);
        detach(node_teleports);
        detach(node_itens);

        node_paredes = new Node();
        node_teleports = new Node();
        node_itens = new Node();

        if (!novoMapa) {
            points = 0;

        } else {
            points += itens;
            novoMapa = false;
        }

        itens = 0;
        updatePointsText();
    }

    @Override
    public void jogar() {

        Player player1 = mapa.getPlayer1();
        Player player2 = mapa.getPlayer2();


        desenharPlayer1();
        desenharPlayer2();

        if (!controleGeral.isPause()) {
            if (!stop) {
                jogando.jogar();
            }
        } else {
     }

        if (novoMapa) {
            mapa.setNovoMapa(true);
        }

        if (mapa.isNovoMapa()) {

            if (controleGeral.isSempreReiniciar()) {

                restartGame();

                controleGeral.reset();
                mapa.gerarMapa();
                stop = false;

                desenharMapa();
                desenharTeleports();
                createItens();

                desenharPlayer1();
                desenharPlayer2();

                return;
            }
            stop = true;

        }


        if (controleGeral.isNovoMapa()) {
            controleGeral.reset();
            mapa.gerarMapa();
            stop = false;
            return;
        }

    }

    private void desenharPiso() {

        String name = "piso";
        Box boxMesh = new Box(22f * MULT + 1, 1, 22f * MULT + 1);

        Geometry boxGeo = new Geometry(name, boxMesh);
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setTexture("ColorMap", assetManager.loadTexture("Textures/chess.jpg"));

        boxGeo.setMaterial(boxMat);

        boxGeo.setLocalTranslation(22 * MULT, -2, 22 * MULT);

        rootNode.attachChild(boxGeo);

        RigidBodyControl boxPhysicsNode = new RigidBodyControl(CollisionShapeFactory.createMeshShape(boxGeo), 0);
        boxGeo.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);

    }

    private void desenharMapa() {

        float dim = 1f;

        for (Parede p : mapa.getParedes().values()) {
            String name = "parede";
            Box boxMesh = new Box(dim, dim, dim);

            Geometry wall = new Geometry(name, boxMesh);
            Material wallMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            wallMat.setTexture("ColorMap", assetManager.loadTexture("Textures/cedrinho.jpg"));

            wall.setMaterial(wallMat);

            wall.setLocalTranslation(2 * p.getX(), p.getZ(), 2 * p.getY());

            node_paredes.attachChild(wall);
        }
        rootNode.attachChild(node_paredes);
        node_paredes.setName("node_paredes");
    }

    private void desenharTeleports() {
        float z = 1f;
        float dim = .5f;

        for (Teleport tp : mapa.getTeleports().values()) {
            Spatial sp = assetManager.loadModel("Models/Sign Post/Sign Post.mesh.xml");
            sp.setLocalScale(dim, dim, dim);
            sp.setLocalTranslation((tp.getX()) * 2, z, (tp.getY()) * 2);
            sp.setName(tp.getNome(tp.getPonto()));

            Material boxMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            boxMat.setBoolean("UseMaterialColors", true);
//            boxMat.setColor("Ambient", ColorRGBA.Green);
            boxMat.setColor("Diffuse", new ColorRGBA(tp.getCor().getR(), tp.getCor().getG(), tp.getCor().getB(), .5f));
            sp.setMaterial(boxMat);

            node_teleports.attachChild(sp);

        }
        rootNode.attachChild(node_teleports);
        node_teleports.setName("node_teleports");

    }

    private void desenharPlayer1() {
        Vector3f position = new Vector3f((mapa.getPlayer1().getX()) * 2, .15f, (mapa.getPlayer1().getY()) * 2);
        Vector3f direction
                = position.subtract(player1.getLocalTranslation());

        translatedBase(player1, direction, position);
    }

    private void desenharPlayer2() {
        Vector3f position = new Vector3f((mapa.getPlayer2().getX()) * 2, -1f, (mapa.getPlayer2().getY()) * 2);
        Vector3f direction
                = player2.getLocalTranslation().subtract(position);

        translatedBase(player2, direction, position);
    }

    private void translatedBase(Spatial sp, Vector3f direction, Vector3f position) {

        if (!position.equals(sp.getLocalTranslation())) {
            sp.setLocalTranslation(position);

            direction = direction.normalize();
            direction = direction.add(position);
            sp.lookAt(direction, UP);
        }

    }

}
