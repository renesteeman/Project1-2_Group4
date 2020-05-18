/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainGame;

import AI.*;
import javax.swing.*;
import java.awt.event.*;
import org.netbeans.lib.awtextra.*;
import java.io.*;

/**
 *
 * @author ivanp
 */
public class MainMenu extends javax.swing.JFrame {
    
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        enterPlay();
        
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterPlay();
            }
        });
        
        botButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterBots();
            }
        });
        
        courseButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               enterCourse();
           } 
        });
        
        shotsButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               enterShots();
           } 
        });
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter newCourse = new PrintWriter("./res/courses/default.txt", "UTF-8");

                    newCourse.printf("height = %s\n", jTextField1.getText());
                    newCourse.printf("flag = (%s, %s)\n", jTextField7.getText(), jTextField8.getText());
                    newCourse.printf("start = (%s, %s)\n", jTextField9.getText(), jTextField10.getText());
                    newCourse.printf("friction = %s\n", jTextField4.getText());
                    newCourse.printf("vmax = %s\n", jTextField5.getText());
                    newCourse.printf("tol = %s\n", jTextField6.getText());

                    newCourse.close();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    System.exit(0);
                }

                String currentCourse = "./res/courses/default.txt";

                if (courseFileCheck.isSelected())
                    currentCourse = "./res/courses/" + jTextField11.getText();

                String currentShots = "./res/shots/" + fileInputField.getText();
                boolean interactiveInput = !shotRadioButton2.isSelected();

                setVisible(false);
                System.out.printf("course = %s, shots = %s\n", currentCourse, currentShots);
                MainGame obj = new MainGame(currentCourse);
                obj.playGame(interactiveInput, currentShots);  
            }
        });

        startBotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter newCourse = new PrintWriter("./res/courses/default.txt", "UTF-8");

                    newCourse.printf("height = %s\n", jTextField1.getText());
                    newCourse.printf("flag = (%s, %s)\n", jTextField7.getText(), jTextField8.getText());
                    newCourse.printf("start = (%s, %s)\n", jTextField9.getText(), jTextField10.getText());
                    newCourse.printf("friction = %s\n", jTextField4.getText());
                    newCourse.printf("vmax = %s\n", jTextField5.getText());
                    newCourse.printf("tol = %s\n", jTextField6.getText());

                    newCourse.close();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    System.exit(0);
                }

                String currentCourse = "./res/courses/default.txt";

                if (courseFileCheck.isSelected()) 
                    currentCourse = "./res/courses/" + jTextField11.getText();

                System.out.println(currentCourse);

                setVisible(false);
                Mastermind obj = new Mastermind(true, currentCourse);
                obj.start();
            }
        });

        shotButtonGroup.add(shotRadioButton1);
        shotButtonGroup.add(shotRadioButton2);
    }

    public void enterPlay() {
        playPanel.setVisible(true);
        botPanel.setVisible(false);
        coursePanel.setVisible(false);
        shotsPanel.setVisible(false);    
    }
    
    public void enterBots() {
        playPanel.setVisible(false);
        botPanel.setVisible(true);
        coursePanel.setVisible(false);
        shotsPanel.setVisible(false);
    }
    
    public void enterCourse() {
        playPanel.setVisible(false);
        botPanel.setVisible(false);
        coursePanel.setVisible(true);
        shotsPanel.setVisible(false);
    }
    
    public void enterShots() {
        playPanel.setVisible(false);
        botPanel.setVisible(false);
        coursePanel.setVisible(false);
        shotsPanel.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shotButtonGroup = new javax.swing.ButtonGroup();
        sideMenu = new javax.swing.JPanel();
        emptySideMenuPanel = new javax.swing.JPanel();
        playButton = new javax.swing.JButton();
        botButton = new javax.swing.JButton();
        courseButton = new javax.swing.JButton();
        shotsButton = new javax.swing.JButton();
        //helpButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        playPanel = new javax.swing.JPanel();
        startGameButton = new javax.swing.JButton();
        botPanel = new javax.swing.JPanel();
        startBotButton = new javax.swing.JButton();
        coursePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        courseFileCheck = new javax.swing.JCheckBox();
        jTextField11 = new javax.swing.JTextField();
        shotsPanel = new javax.swing.JPanel();
        fileInputField = new javax.swing.JTextField();
        shotRadioButton1 = new javax.swing.JRadioButton();
        shotRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CrazyPutting Launcher");
        setName("jFrame"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideMenu.setBackground(new java.awt.Color(38, 166, 91));
        sideMenu.setToolTipText("");
        sideMenu.setMaximumSize(new java.awt.Dimension(290, 750));
        sideMenu.setMinimumSize(new java.awt.Dimension(290, 750));
        sideMenu.setName(""); // NOI18N

        emptySideMenuPanel.setBackground(new java.awt.Color(38, 166, 91));
        emptySideMenuPanel.setPreferredSize(new java.awt.Dimension(100, 120));

        javax.swing.GroupLayout emptySideMenuPanelLayout = new javax.swing.GroupLayout(emptySideMenuPanel);
        emptySideMenuPanel.setLayout(emptySideMenuPanelLayout);
        emptySideMenuPanelLayout.setHorizontalGroup(
            emptySideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        emptySideMenuPanelLayout.setVerticalGroup(
            emptySideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        sideMenu.add(emptySideMenuPanel);

        playButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playButton.setIcon(new javax.swing.ImageIcon("./res/image/default.png")); // NOI18N
        playButton.setText("Play");
        playButton.setAlignmentY(0.0F);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setMaximumSize(new java.awt.Dimension(290, 50));
        playButton.setPreferredSize(new java.awt.Dimension(290, 75));
        playButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hover.png")); // NOI18N
        playButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selected.png")); // NOI18N
        sideMenu.add(playButton);

        botButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        botButton.setIcon(new javax.swing.ImageIcon("./res/image/default.png")); // NOI18N
        botButton.setText("Bot");
        botButton.setAlignmentY(0.0F);
        botButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botButton.setMaximumSize(new java.awt.Dimension(290, 50));
        botButton.setPreferredSize(new java.awt.Dimension(290, 75));
        botButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hover.png")); // NOI18N
        botButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selected.png")); // NOI18N
        sideMenu.add(botButton);

        courseButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        courseButton.setIcon(new javax.swing.ImageIcon("./res/image/default.png")); // NOI18N
        courseButton.setText("Edit course");
        courseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        courseButton.setMaximumSize(new java.awt.Dimension(290, 50));
        courseButton.setPreferredSize(new java.awt.Dimension(290, 75));
        courseButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hover.png")); // NOI18N
        courseButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selected.png")); // NOI18N
        sideMenu.add(courseButton);

        shotsButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shotsButton.setIcon(new javax.swing.ImageIcon("./res/image/default.png")); // NOI18N
        shotsButton.setText("Configure shots");
        shotsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        shotsButton.setMaximumSize(new java.awt.Dimension(290, 50));
        shotsButton.setPreferredSize(new java.awt.Dimension(290, 75));
        shotsButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hover.png")); // NOI18N
        shotsButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selected.png")); // NOI18N
        sideMenu.add(shotsButton);

        /*helpButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        helpButton.setIcon(new javax.swing.ImageIcon("./res/image/default.png")); // NOI18N
        helpButton.setText("Help");
        helpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        helpButton.setMaximumSize(new java.awt.Dimension(290, 50));
        helpButton.setPreferredSize(new java.awt.Dimension(290, 75));
        helpButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hover.png")); // NOI18N
        helpButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selected.png")); // NOI18N
        sideMenu.add(helpButton);
        */
        exitButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        exitButton.setIcon(new javax.swing.ImageIcon("./res/image/default.png")); // NOI18N
        exitButton.setText("Exit");
        exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitButton.setMaximumSize(new java.awt.Dimension(290, 50));
        exitButton.setPreferredSize(new java.awt.Dimension(290, 75));
        exitButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hover.png")); // NOI18N
        exitButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selected.png")); // NOI18N
        sideMenu.add(exitButton);

        getContentPane().add(sideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 291, 750));
        sideMenu.getAccessibleContext().setAccessibleName("");

        playPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        playPanel.setPreferredSize(new java.awt.Dimension(710, 750));
        playPanel.setLayout(new java.awt.GridBagLayout());

        startGameButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        startGameButton.setIcon(new javax.swing.ImageIcon("./res/image/defaultStart.png")); // NOI18N
        startGameButton.setText("START");
        startGameButton.setAlignmentY(0.0F);
        startGameButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startGameButton.setMaximumSize(new java.awt.Dimension(1000, 1000));
        startGameButton.setMinimumSize(new java.awt.Dimension(0, 0));
        startGameButton.setPreferredSize(new java.awt.Dimension(300, 150));
        startGameButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hoverStart.png")); // NOI18N
        startGameButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selectedStart.png")); // NOI18N
        playPanel.add(startGameButton, new java.awt.GridBagConstraints());

        getContentPane().add(playPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 0, -1, -1));

        botPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botPanel.setPreferredSize(new java.awt.Dimension(710, 750));
        botPanel.setLayout(new java.awt.GridBagLayout());

        startBotButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        startBotButton.setIcon(new javax.swing.ImageIcon("./res/image/defaultStart.png")); // NOI18N
        startBotButton.setText("START AI");
        startBotButton.setAlignmentY(0.0F);
        startBotButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startBotButton.setMaximumSize(new java.awt.Dimension(1000, 1000));
        startBotButton.setMinimumSize(new java.awt.Dimension(0, 0));
        startBotButton.setPreferredSize(new java.awt.Dimension(300, 150));
        startBotButton.setRolloverIcon(new javax.swing.ImageIcon("./res/image/hoverStart.png")); // NOI18N
        startBotButton.setSelectedIcon(new javax.swing.ImageIcon("./res/image/selectedStart.png")); // NOI18N
        botPanel.add(startBotButton, new java.awt.GridBagConstraints());

        getContentPane().add(botPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 0, -1, -1));

        coursePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        coursePanel.setPreferredSize(new java.awt.Dimension(710, 750));
        coursePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Gravitational acceleration [m/s^2]");
        coursePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Mass of ball [g]");
        coursePanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Coefficient of friction (rolling ball)");
        coursePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 280, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Maximum initial ball speed [m/s]");
        coursePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Distance from hole for a successful putt [m]");
        coursePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Start");
        coursePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Goal");
        coursePanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Height function");
        coursePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setText("-0.01*x + 0.003*x^2 + 0.04 * y");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, 540, -1));

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField2.setText("9.81");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 70, -1));

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField3.setText("45.93");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 70, -1));

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField4.setText("0.131");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 70, -1));

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField5.setText("3");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 70, -1));

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField6.setText("0.02");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 70, -1));

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField7.setText("0");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, 70, -1));

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField8.setText("10");
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 400, 70, -1));

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField9.setText("0");
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 70, -1));

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField10.setText("0");
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        coursePanel.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 70, -1));

        getContentPane().add(coursePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 0, -1, -1));

        shotsPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        shotsPanel.setPreferredSize(new java.awt.Dimension(710, 750));
        shotsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fileInputField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fileInputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileInputFieldActionPerformed(evt);
            }
        });
        shotsPanel.add(fileInputField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 290, -1));

        shotRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shotRadioButton1.setText("File input");
        shotsPanel.add(shotRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        shotRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        shotRadioButton2.setText("Interactive input");
        shotsPanel.add(shotRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        courseFileCheck.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        courseFileCheck.setText("File Input");
        coursePanel.add(courseFileCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 500, -1, -1));

        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        coursePanel.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, 420, -1));

        getContentPane().add(shotsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileInputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileInputFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileInputFieldActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        MainMenu obj = new MainMenu();
        obj.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botButton;
    private javax.swing.JPanel botPanel;
    private javax.swing.JButton courseButton;
    private javax.swing.JPanel coursePanel;
    private javax.swing.JPanel emptySideMenuPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JTextField fileInputField;
    //private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JButton playButton;
    private javax.swing.JPanel playPanel;
    private javax.swing.ButtonGroup shotButtonGroup;
    private javax.swing.JRadioButton shotRadioButton1;
    private javax.swing.JRadioButton shotRadioButton2;
    private javax.swing.JButton shotsButton;
    private javax.swing.JPanel shotsPanel;
    private javax.swing.JPanel sideMenu;
    private javax.swing.JButton startBotButton;
    private javax.swing.JButton startGameButton;    
    private javax.swing.JCheckBox courseFileCheck;

    // End of variables declaration//GEN-END:variables
}
