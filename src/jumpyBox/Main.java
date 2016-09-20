package jumpyBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements Serializable, KeyListener, Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8834633061228902538L;
	
	private static Main instance = null;
	protected static Integer width, height;
	protected static Integer groundPos;
	private static Box box;
	private static Image boxMonster, background;
	private static Pillar pillar0, pillar1, pillar2, pillar3;
	private static Integer score;
	private Integer highScore;
	private boolean firstStart;
	private DrawImage panel;

	private Main() {
		highScore = 0;
		
		firstStart = true;
		
		background = loadpics("space_background.png");
		
		panel = new DrawImage();
		
		groundPos = 500;
		width = 600;
		height = 600;
		setSize(width, height);
		setTitle("Jumpy Box");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		add(panel);
		
		initialize();
		
		try {
			downloadHighscore();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addKeyListener(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public static void initialize() {
		playerInitialize();
		pillarInitialize();
	}
	
	public static void playerInitialize() {
		score = 0;
		box = new Box();
		box.setMonster(loadpics("monster.png"));
		boxMonster = box.getMonster();
	}
	
	public static Image loadpics(String imageName) {
		return new ImageIcon(imageName).getImage();
	}
	
	public static void pillarInitialize() {
		pillar0 = new Pillar();
		pillar1 = null;
		pillar2 = null;
		pillar3 = null;
	}
	
	public void saveHighscore() throws IOException {
		FileOutputStream file = new FileOutputStream("jumpySave");
		BufferedOutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		
		output.writeObject(highScore);
		output.close();
		buffer.close();
		file.close();
	}
	
	public void downloadHighscore() throws IOException, ClassNotFoundException {
		InputStream file = new FileInputStream("jumpySave");
		InputStream buffer = new BufferedInputStream(file);
		
		if (file.available() > 0) {
			ObjectInput input = new ObjectInputStream(buffer);
		
			highScore = (Integer)input.readObject();
			file.close();
		}
	}
	
	public void checkScore(Pillar pillar) {
		if (box.isAlive() && pillar != null) {
			if (pillar.getX() == width / 3) {
				score++;
			}
		}
	}
	
	public void pillarRotation() {
		if (pillar0 != null) {
			pillar0.move();
		}
		if (pillar0 != null && pillar0.getRightPillar() == (width * 3 / 5)) {
			pillar1 = new Pillar();
		}
		if (pillar1 != null) {
			pillar1.move();
		}
		if (pillar1 != null && pillar1.getRightPillar() == (width * 3 / 5)) {
			pillar2 = new Pillar();
		}
		if (pillar2 != null) {
			pillar2.move();
		}
		if (pillar2 != null && pillar2.getRightPillar() == (width * 3 / 5)) {
			pillar3 = pillar0;
			pillar0 = pillar1;
			pillar1 = pillar2;
			pillar2 = new Pillar();
		}
		if (pillar3 != null) {
			pillar3.move();
		}
	}
	
	public static Main getInstance() {
		if (instance == null) {
			instance = new Main();
		}
		
		return instance;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (score > highScore) {
				highScore = score;
				try {
					saveHighscore();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (!firstStart) {
				box.move();
				box.checkGroundCollision();
				box.checkPillarCollision(pillar0);
				box.checkPillarCollision(pillar1);
				box.checkPillarCollision(pillar2);
				box.checkPillarCollision(pillar3);
				
				checkScore(pillar0);
				checkScore(pillar1);
				checkScore(pillar2);
				checkScore(pillar3);
			}
			
			pillarRotation();
			
			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			box.setJump(true);
		} 
		else if (!box.isAlive() && e.getKeyCode() == KeyEvent.VK_O) {
			initialize();
		}
		else if (!box.isAlive() && e.getKeyCode() == KeyEvent.VK_X) {
			System.exit(0);
		}
		if (firstStart && e.getKeyCode() == KeyEvent.VK_SPACE) {
			firstStart = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			box.setJump(false);
		} 
	}
	
	private class DrawImage extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8905353155827104079L;
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.drawImage(background, 0, 0, width, height, null);
			
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillRect(0, groundPos, width, height - groundPos);
			
			if (pillar0 != null) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(pillar0.getBottomShape());
				g2d.fill(pillar0.getTopShape());
			}
			
			if (pillar1 != null) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(pillar1.getBottomShape());
				g2d.fill(pillar1.getTopShape());
			}
			
			if (pillar2 != null) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(pillar2.getBottomShape());
				g2d.fill(pillar2.getTopShape());
			}
			
			if (pillar3 != null) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(pillar3.getBottomShape());
				g2d.fill(pillar3.getTopShape());
			}
			
			if (box.isAlive()) {
				g2d.drawImage(boxMonster, box.getX(), box.getY(), box.getWidth(), box.getHeight(), null);
			}
				
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2d.drawString("Highscore: " + highScore.toString(), 475, 15);
			g2d.drawString("Score: " + score.toString(), 475, 30);
			
			if (!box.isAlive()) {
				g2d.setColor(Color.WHITE);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN, 75));
				g2d.drawString("GAME OVER", width / 8, height / 2);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g2d.drawString("Press the o key to play again, or the x key to exit"
						, width / 7 , height * 3 / 5);
			}
			
			if (firstStart && box.isAlive()) {
				g2d.setColor(Color.WHITE);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN, 75));
				g2d.drawString("Tutorial", width / 3, height / 5);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g2d.drawString("Press the space bar to jump!"
						, width / 3 , height * 1 / 4);
			}
			
			
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			Toolkit.getDefaultToolkit().sync();
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setVisible(true);
	}
}
