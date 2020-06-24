package AI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;

public class VisualizeGrid extends JFrame {

	int[][] colors;

	public class ColorPanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			for (int i = 0; i < colors.length; i++) {
				for (int j = 0; j < colors[i].length; j++) {
					switch(colors[i][j]) {
						case 0: g2.setColor(Color.white);
						        break;
						case 1: g2.setColor(Color.red);
							    break;
						case 2: g2.setColor(Color.pink);
						        break;
						case 3: g2.setColor(Color.green);
						        break;
						case 4: g2.setColor(Color.yellow);
						        break;
						case 5: g2.setColor(Color.black);
					}
					g2.fillRect(i * oneDimError, j * oneDimError, oneDimError, oneDimError);
				}
			}
		}
	}

	private int oneDimError;
	private ColorPanel panel;

	public void build(int szx, int szy, double oneDimError) {
		colors = new int[szx][szy];
		this.oneDimError = (int)oneDimError;

		for (int i = 0; i < szx; i++)
			for (int j = 0; j < szy; j++) 
				colors[i][j] = 0;

		setSize(szx * this.oneDimError, szy * this.oneDimError);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
        setVisible(true);
	
		panel = new ColorPanel();
        getContentPane().add(panel);
	}

	public void destroyDisplay() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	public void setBall(int x, int y) {
		colors[x][y] = 1;
		panel.repaint();
	}

	public void setGoal(int x, int y) {
		colors[x][y] = 2;
		panel.repaint();
	}

	public void setUsed(int x, int y) {
		if (colors[x][y] != 1 && colors[x][y] != 2)
			colors[x][y] = 3;
		panel.repaint();
	}

	public void setPushed(int x, int y) {
		if (colors[x][y] != 1 && colors[x][y] != 2)
			colors[x][y] = 4;
		panel.repaint();
	}

	public void setForbidden(int x, int y) {
		colors[x][y] = 5;
		panel.repaint();
	}	
}