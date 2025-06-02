
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class MainFrame extends javax.swing.JFrame {

	Asteroids asteroids = null;
	Asteroid currentAsteroid = null;
	private int selectedPointIndex = -1;
	private boolean ctrlPressed = false;

	public MainFrame() {
		initComponents();
		asteroids = new Asteroids();
		drawPanel1.setAsteroids(asteroids);
		drawPanel1.repaint();
	}

	private void initComponents() {

		drawPanel1 = new DrawPanel();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		drawPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				drawPanel1MouseDragged(evt);
			}
		});
		drawPanel1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
				drawPanel1MouseWheelMoved(evt);
			}
		});
		drawPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				drawPanel1MouseClicked(evt);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				drawPanel1MousePressed(evt);
			}
		});
		drawPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				drawPanel1KeyPressed(evt);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				drawPanel1KeyReleased(evt);
			}
		});

		javax.swing.GroupLayout drawPanel1Layout = new javax.swing.GroupLayout(drawPanel1);
		drawPanel1.setLayout(drawPanel1Layout);
		drawPanel1Layout.setHorizontalGroup(drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 362, Short.MAX_VALUE));
		drawPanel1Layout.setVerticalGroup(drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		jButton1.setText("New Asteroid");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("Center Asteroid");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton3.setText("Clear Asteroids");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		jButton4.setText("Save Asteroids");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		jButton5.setText("Load Asteroids");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		jList1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jList1MouseClicked(evt);
			}
		});
		jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				jList1ValueChanged(evt);
			}
		});
		jScrollPane1.setViewportView(jList1);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jScrollPane1))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jButton1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jButton4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton5)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(drawPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(drawPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));

		pack();
	}

	private void repaintAll() {
		drawPanel1.repaint();
		jList1.setListData(asteroids.toArray());
	}

	private void drawPanel1MouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getButton() == 1) {
			if (currentAsteroid == null) {
				System.out.println("No asteroid selected");
			} else {
				int index = currentAsteroid.isPointNearLine(evt.getPoint(), 6);
				if (index != -1) {
					currentAsteroid.addPointOnEdge(evt.getPoint(), index);
				} else {
					currentAsteroid.add(evt.getPoint());
				}
			}
		} else if (evt.getButton() == 3) {
			System.out.println("MainFrame.drawPanel1MouseClicked()");
			currentAsteroid.delPoint(selectedPointIndex);
		}

		repaintAll();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		Asteroid asteroid = new Asteroid();
		asteroids.add(asteroid);
		currentAsteroid = asteroid;
		asteroids.setVisible(asteroids.size() - 1);
		repaintAll();
	}// GEN-LAST:event_jButton1ActionPerformed

	private void drawPanel1MouseDragged(java.awt.event.MouseEvent evt) {
		if (selectedPointIndex != -1) {
			currentAsteroid.setPoint(evt.getPoint(), selectedPointIndex);
			repaintAll();
		}
	}// GEN-LAST:event_drawPanel1MouseDragged

	private void drawPanel1MousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_drawPanel1MousePressed
		selectedPointIndex = currentAsteroid.getPoint(evt.getPoint(), 4);
	}// GEN-LAST:event_drawPanel1MousePressed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		asteroids.clear();
		repaintAll();
	}// GEN-LAST:event_jButton3ActionPerformed

	private void drawPanel1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {// GEN-FIRST:event_drawPanel1MouseWheelMoved
		if (evt.isControlDown() && evt.getWheelRotation() < 0) {
			currentAsteroid.zoom(1.2);
		} else if (evt.isControlDown() && evt.getWheelRotation() > 0) {
			currentAsteroid.zoom(0.8);
		} else {
			currentAsteroid.rotate();
		}
		repaintAll();
	}// GEN-LAST:event_drawPanel1MouseWheelMoved

	private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_jList1ValueChanged

	}// GEN-LAST:event_jList1ValueChanged

	private void jList1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jList1MouseClicked
		int selectedIndex = jList1.getSelectedIndex();
		if (selectedIndex >= 0) {
			currentAsteroid = asteroids.getAsteroid(selectedIndex);
			asteroids.setVisible(selectedIndex);
			repaintAll();

		}
	}// GEN-LAST:event_jList1MouseClicked

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		currentAsteroid.center(drawPanel1.getWidth(), drawPanel1.getHeight());
		repaintAll();
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only allow directory selection

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedDirectory = chooser.getSelectedFile();
			try {
				asteroids.saveAsteroids(selectedDirectory.getAbsolutePath() + "/test.txt");
			} catch (IOException ex) {
				Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}// GEN-LAST:event_jButton4ActionPerformed

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Only allow directory selection

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			try {
				asteroids.loadAsteroids(selectedFile.getAbsolutePath());
			} catch (IOException ex) {
				Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		repaintAll();
	}// GEN-LAST:event_jButton5ActionPerformed

	private void drawPanel1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_drawPanel1KeyPressed
		if (evt.getKeyCode() == KeyEvent.VK_CONTROL)
			ctrlPressed = true;
	}// GEN-LAST:event_drawPanel1KeyPressed

	private void drawPanel1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_drawPanel1KeyReleased
		if (evt.getKeyCode() == KeyEvent.VK_CONTROL)
			ctrlPressed = false;
	}// GEN-LAST:event_drawPanel1KeyReleased

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private DrawPanel drawPanel1;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JList jList1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	// End of variables declaration//GEN-END:variables
}
