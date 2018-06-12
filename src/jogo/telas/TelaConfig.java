/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.telas;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.JTextComponent;
import jogo.Jogo;
import jogo.controle.Controle;
import jogo.controle.ControlePlayer;
import jogo.estrategias.ai.FugirAI;
import jogo.estrategias.ai.FugirAI_Melhorado;
import jogo.estrategias.ai.FugirAI_Distante;
import jogo.estrategias.ai.SeguirAI;
import jogo.mapas.gerador.GeradorMapa;
import jogo.mapas.gerador.GeradorSimples;
import jogo.mapas.gerador.GeradorTxt;
import jogo.mapas.gerador.GeradorVazio;
import jogo.mapas.gerador.MazeGenerator;

/**
 *
 * @author inmp
 */
public class TelaConfig extends javax.swing.JFrame {

    /**
     * Creates new form TelaConfig
     */
    private String mapa;
    private String dir;
    private int mult;

    public TelaConfig() {
        initComponents();
        this.setResizable(false);
        this.setVisible(true);

        int w = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        int h = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());

        setLocation(w / 2 - getWidth() / 2, h / 2 - getHeight() / 2);

        radioCP1.setText(ControlePlayer.class.getSimpleName());
        radioSAI1.setText(SeguirAI.class.getSimpleName());
        radioFAI1.setText(FugirAI.class.getSimpleName());
        radioFAIM1.setText(FugirAI_Melhorado.class.getSimpleName());

        radioCP1.setMnemonic(0);
        radioSAI1.setMnemonic(1);
        radioFAI1.setMnemonic(2);
        radioFAIM1.setMnemonic(3);

        radioSAI2.setText(SeguirAI.class.getSimpleName());
        radioFAI2.setText(FugirAI.class.getSimpleName());
        radioFAIM2.setText(FugirAI_Melhorado.class.getSimpleName());
        radioFAIM4.setText(FugirAI_Distante.class.getSimpleName());

        radioSAI2.setMnemonic(1);
        radioFAI2.setMnemonic(2);
        radioFAIM2.setMnemonic(3);
        radioFAIM4.setMnemonic(4);

        btnGroupP1.add(radioCP1);
        btnGroupP1.add(radioFAI1);
        btnGroupP1.add(radioFAIM1);
        btnGroupP1.add(radioSAI1);

        btnGroupP2.add(radioFAI2);
        btnGroupP2.add(radioFAIM2);
        btnGroupP2.add(radioSAI2);
        btnGroupP2.add(radioFAIM4);

        radioRand.setMnemonic(0);
        radioVazio.setMnemonic(1);
        radioAbrir.setMnemonic(2);
        radioSemiLabirinto.setMnemonic(3);

        btnGroupGM.add(radioRand);
        btnGroupGM.add(radioVazio);
        btnGroupGM.add(radioAbrir);
        btnGroupGM.add(radioSemiLabirinto);

        SpinnerModel spm = new SpinnerNumberModel(60, 1, 300, 10);
        spinnerFPS.setModel(spm);

        spm = new SpinnerNumberModel(1, 1, 25, 1);
        spinnerMULT.setModel(spm);

        spm = new SpinnerNumberModel(h, 500, h, 100);
        spinnerRES.setModel(spm);

        cbMapas.removeAllItems();

        dir = System.getProperty("user.dir");
        dir += "\\src\\jogo\\mapas\\sv";
        abrirMapas();
        if (cbMapas.getItemCount() == 0) {
            cbMapas.setVisible(false);
            radioAbrir.setVisible(false);
            btnDelete.setVisible(false);
        }

//        cbMapas.setEditable(true);
//
//        JTextComponent editor = (JTextComponent) cbMapas.getEditor().getEditorComponent();
//        editor.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent evt) {
//                btnGroupGM.setSelected(radioAbrir.getModel(), true);
//                System.out.println("mouse");
//            }
//        });
    }

    private void abrirMapas() {

        final File folder = new File(dir);

        for (final File fileEntry : folder.listFiles()) {

            if (fileEntry.getName().matches("\\w+.txt")) {
                if (verificarMapa(dir + "\\" + fileEntry.getName())) {
                    cbMapas.addItem(fileEntry.getName());
                }
            }

        }
    }

    private void delete() {
        File file = new File(dir + "\\" + cbMapas.getSelectedItem());
        file.delete();
        cbMapas.removeItem(cbMapas.getSelectedItem());

    }

    private boolean verificarMapa(String file_dir) {
        BufferedReader br = null;
        FileReader fr = null;
        boolean b = true;
        try {

//            br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(file_dir);
            br = new BufferedReader(fr);

            String sCurrentLine;

            try {
                mult = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                b = false;
            }

            mapa = "";
            while ((sCurrentLine = br.readLine()) != null && b != false) {
                mapa += sCurrentLine;

                if (!sCurrentLine.contains(".") && !sCurrentLine.contains("*") || sCurrentLine.length() != 22 * mult + 1) {
                    b = false;
                    System.out.println(sCurrentLine);
                    System.out.println(mult);
                    System.out.println(file_dir);
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
        return b;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupP1 = new javax.swing.ButtonGroup();
        btnGroupP2 = new javax.swing.ButtonGroup();
        btnGroupGM = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        radioCP1 = new javax.swing.JRadioButton();
        radioSAI1 = new javax.swing.JRadioButton();
        radioFAI1 = new javax.swing.JRadioButton();
        radioFAIM1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        radioSAI2 = new javax.swing.JRadioButton();
        radioFAI2 = new javax.swing.JRadioButton();
        radioFAIM2 = new javax.swing.JRadioButton();
        radioFAIM4 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        btnJogar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        spinnerMULT = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        spinnerRES = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        spinnerFPS = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        radioVazio = new javax.swing.JRadioButton();
        radioRand = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        cbMapas = new javax.swing.JComboBox<>();
        radioAbrir = new javax.swing.JRadioButton();
        btnDelete = new javax.swing.JButton();
        radioSemiLabirinto = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        radioCP1.setText("Manual");

        radioSAI1.setSelected(true);
        radioSAI1.setText("SeguirAI");

        radioFAI1.setText("FugirAI");

        radioFAIM1.setText("FugirAI_Melhorado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(radioCP1)
                                        .addComponent(radioSAI1)
                                        .addComponent(radioFAI1)
                                        .addComponent(radioFAIM1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(radioCP1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioSAI1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioFAI1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioFAIM1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("     Controle Player 1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
        );

        radioSAI2.setText("SeguirAI");

        radioFAI2.setText("FugirAI");

        radioFAIM2.setSelected(true);
        radioFAIM2.setText("FugirAI_Melhorado");

        radioFAIM4.setText("FugirAI_Melhorado2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(radioSAI2)
                                        .addComponent(radioFAI2)
                                        .addComponent(radioFAIM2)
                                        .addComponent(radioFAIM4))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(radioSAI2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioFAI2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioFAIM2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(radioFAIM4))
        );

        jLabel2.setText("     Controle Player 2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnJogar.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        btnJogar.setText("Jogar");
        btnJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJogarActionPerformed(evt);
            }
        });

        jLabel3.setText("Multiplicador");

        jLabel4.setText("Resolucao");

        jLabel5.setText("FPS");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(spinnerFPS, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spinnerMULT, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spinnerRES, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(spinnerMULT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(spinnerRES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(spinnerFPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        radioVazio.setText("Vazio");

        radioRand.setSelected(true);
        radioRand.setText("Random");

        jLabel6.setText("Gerador de Mapa");

        cbMapas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        cbMapas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbMapasMousePressed(evt);
            }
        });

        radioAbrir.setText("Abrir");

        btnDelete.setText("Apagar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        radioSemiLabirinto.setText("Semi Labirinto");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(radioRand)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioVazio))
                                        .addComponent(radioSemiLabirinto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(radioAbrir)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnDelete))
                                        .addComponent(cbMapas, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(radioRand)
                                        .addComponent(radioVazio)
                                        .addComponent(radioAbrir)
                                        .addComponent(btnDelete))
                                .addGap(2, 2, 2)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbMapas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addComponent(radioSemiLabirinto)
                                                .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnJogar)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnJogar)
                                                .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJogarActionPerformed
        // TODO add your handling code here:

        Jogo.FPS = (int) spinnerFPS.getValue();
        Jogo.RES = (int) spinnerRES.getValue();
        Jogo.MULT = (int) spinnerMULT.getValue();

        int gp1 = btnGroupP1.getSelection().getMnemonic();
        int gp2 = btnGroupP2.getSelection().getMnemonic();

        int ggm = btnGroupGM.getSelection().getMnemonic();

        Controle controleP1, controleP2;
        controleP1 = getControle(gp1);
        controleP2 = getControle(gp2);

        GeradorMapa gerador = null;
        if (ggm != 2) {
            gerador = getGeradorMapa(ggm);
        } else {
            gerador = new GeradorTxt();
            verificarMapa(dir + "\\" + cbMapas.getSelectedItem());
            Jogo.MULT = mult;
            ((GeradorTxt) gerador).setTxt(mapa);
        }

        new Jogo(gerador, controleP1, controleP2);

        dispose();
    }//GEN-LAST:event_btnJogarActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
        if (cbMapas.getItemCount() == 0) {
            cbMapas.setVisible(false);
            radioAbrir.setVisible(false);
            btnDelete.setVisible(false);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cbMapasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbMapasMousePressed
        btnGroupGM.setSelected(radioAbrir.getModel(), true);
    }//GEN-LAST:event_cbMapasMousePressed

    private Controle getControle(int mnemonic) {
        return mnemonic == 0 ? new ControlePlayer()
                : mnemonic == 1 ? new SeguirAI()
                        : mnemonic == 2 ? new FugirAI()
                                : mnemonic == 3 ? new FugirAI_Melhorado()
                                        : new FugirAI_Distante();
    }

    private GeradorMapa getGeradorMapa(int m) {
        return m == 0 ? new GeradorSimples() : m == 1 ? new GeradorVazio() : new MazeGenerator();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConfig().setVisible(true);
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.ButtonGroup btnGroupGM;
    private javax.swing.ButtonGroup btnGroupP1;
    private javax.swing.ButtonGroup btnGroupP2;
    private javax.swing.JButton btnJogar;
    private javax.swing.JComboBox<String> cbMapas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton radioAbrir;
    private javax.swing.JRadioButton radioCP1;
    private javax.swing.JRadioButton radioFAI1;
    private javax.swing.JRadioButton radioFAI2;
    private javax.swing.JRadioButton radioFAIM1;
    private javax.swing.JRadioButton radioFAIM2;
    private javax.swing.JRadioButton radioFAIM4;
    private javax.swing.JRadioButton radioRand;
    private javax.swing.JRadioButton radioSAI1;
    private javax.swing.JRadioButton radioSAI2;
    private javax.swing.JRadioButton radioSemiLabirinto;
    private javax.swing.JRadioButton radioVazio;
    private javax.swing.JSpinner spinnerFPS;
    private javax.swing.JSpinner spinnerMULT;
    private javax.swing.JSpinner spinnerRES;
    // End of variables declaration//GEN-END:variables

}
