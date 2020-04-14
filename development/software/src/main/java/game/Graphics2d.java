package game;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.*;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.*;


public class Graphics2d {
	protected JFrame frame;

	public Graphics2d() {
		frame = new JFrame("Golf");

		frame.setSize(800, 600);
		frame.setVisible(true);	
	}

	public static void main(String[] args) {
		Graphics2d obj = new Graphics2d();
	}
}
