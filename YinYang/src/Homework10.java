import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import java.util.Random;

/**
 * @author Abhi Shah
 *	Homework 10/11
 */
@SuppressWarnings("serial")
public class Homework10 extends JApplet {
	public static void main(String s[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Homework 10");
		JApplet applet = new Homework10();
		applet.init();
		frame.getContentPane().add(applet);
		frame.pack();
		frame.setVisible(true);
	}

	public void init() {
		JPanel panel = new PanelForSquare();
		getContentPane().add(panel);
	}
}

class PanelForSquare extends JPanel implements Runnable, MouseListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Random randomNumber = new Random();
	// Int variable maxChange is the largest amount by which dX and dY
	// can change by when they collide.  This speed limiter is to prevent
	// Them from bouncing around the screen in an absurd manner, which
	// would prevent them from being clicked properly
	int maxChange = 3;

	// Starting parameters for the first MyYinYang object
	int yin1X = 100;
	int yin1Y = 100;
	int yin1Width = 150;
	int yin1Height = 150;

	// The four random directions in which the yinYangs will travel.
	// The deltas for X and Y 1 are multiplied by -1 so that it goes to the
	// top left to prevent them from colliding and scaling too early
	int deltaX1 = (randomNumber.nextInt(maxChange)+1)*-1;
	int deltaY1 = (randomNumber.nextInt(maxChange)+1)*-1;
	int deltaX2 = randomNumber.nextInt(maxChange)+1;
	int deltaY2 = randomNumber.nextInt(maxChange)+1;

	// Initial parameters for the second yinYang object
	int yin2X = 600;
	int yin2Y = 600;
	int yin2Width = 90;
	int yin2Height = 90;

	// Four int variables which track if the mouse click is 
	// inside of either shape
	int x1Clicked, y1Clicked, x2Clicked, y2Clicked;

	// Points currently scored in the game
	int points = 0;

	// Remaining amount of times you can miss a target before the game ends
	int misses = 3;

	// New font object to alter how the text is displayed on the panel
	Font font = new Font("Sans-Serif", Font.BOLD, 35);
	
	// The two MyYinYang objects being initially created with the 
	// previously defined stats
	MyYinYang yin1 = new MyYinYang(yin1X, yin1Y, yin1Width, yin1Height);
	MyYinYang yin2 = new MyYinYang(yin2X, yin2Y, yin2Width, yin2Height);

	//Frame for game over screen
	final JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	// Two color objects which start out being randomly generated
	Color color1 = new Color(Color.HSBtoRGB(randomNumber.nextFloat(), randomNumber.nextFloat(), randomNumber.nextFloat()));
	Color color2 = new Color(Color.HSBtoRGB(randomNumber.nextFloat(), randomNumber.nextFloat(), randomNumber.nextFloat()));
	
	// Panel size
	public static final int PANEL_WIDTH = 1000;
	public static final int PANEL_HEIGHT = 800;
	
	// Variable to display how much time is left in seconds as the time value is in the thousands
	Double timeDisplay = 0.0;
	
	// Degrees by which to rotate the shape
	double degrees = 0;
	
	// How much time is left in the game
	Integer timeLeft = 12000;
	
	private void initTimer(){
		Timer timer;
		timer = new Timer(timeLeft, this);
		timer.setInitialDelay(0);
		timer.start();
	}

	
	public PanelForSquare() {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.white);

		Thread thread = new Thread(this);
		initTimer();
		thread.start();
	}


	@Override
	// TODO Auto-generated method stub
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		// Draws the two initial two YinYang symbols on the frame
		g2.setColor(color1);
		if(timeLeft < 6000){
			g2.rotate(degrees, yin1X+(yin1Width/2), yin1Y+(yin1Height/2));
		}
		g2.fill(new MyYinYang(yin1X, yin1Y, yin1Width, yin1Height));
		if(timeLeft < 6000){
			g2.rotate(-degrees, yin1X+(yin1Width/2), yin1Y+(yin1Height/2));
		}
		g2.setColor(color2);
		if(timeLeft < 6000){
			g2.rotate(degrees, yin2X+(yin2Width/2), yin2Y+(yin2Height/2));
		}
		g2.fill(new MyYinYang (yin2X,yin2Y,yin2Width,yin2Height));
		if(timeLeft < 6000){
			g2.rotate(-degrees, yin2X+(yin2Width/2), yin2Y+(yin2Height/2));
		}
		
		// Alters and prints the text on the screen
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		g2.drawString("Score", 700, 25);
		g2.drawString(Integer.toString(points), 700, 75);
		g2.drawString("Misses", 800, 25);
		g2.drawString(Integer.toString(misses), 800, 75);
		g2.drawString("Time Left", 700, 110);
		timeDisplay = timeLeft/100.0;
		g2.drawString(timeDisplay.toString(), 700, 160);
	}

	public void run(){
		// Mouse listener to check if the mouse has been clicked
		addMouseListener(this);
		while(true){
			// Changes the next X and Y value for each
			// shape according the their defined delta values
			yin1X+=deltaX1;
			yin1Y+=deltaY1;
			yin2X+=deltaX2;
			yin2Y+=deltaY2;

			// Makes the shapes bounce faster the longer the game elapses
			// Also decreases the score appropriately
			if(timeLeft < 9000){
				maxChange = 4;
				points-=1;
			}
			else if(timeLeft < 7500){
				maxChange = 6;
				points-=2;
			}
			else if(timeLeft < 6000){
				maxChange = 7;
				points-=3;
			}
			else if(timeLeft < 4500){
				maxChange = 8;
				points-=4;
			}
			else if(timeLeft < 3000){
				maxChange = 10;
				points-=5;
			}
			
			if (timeLeft ==0 || misses<=0){
				JOptionPane.showMessageDialog(frame, "Game over. Your final score is:  " + points);
				int status = 0;
				System.exit(status);
			}

			
			// Method to check if either shape has hit the edge of the panel yet
			calculateEdges();

			// Method to check if the two objects have collided with each other
			checkForCollision();

			degrees+=.2;
			timeLeft--;
			// Calls PaintComponent again to redraw the screen
			repaint();
			try{
				Thread.sleep(10);
			}
			catch (InterruptedException ex){}
		}
	}

	// Method to check if the two YinYang shapes have collided with each other,
	// and subsequently perform scaling operations if they have
	private void checkForCollision() {
		// Creates two MyYinYang objects with the already used coordinates
		// for use in the changing of color, direction, and scale once they
		// have collided
		yin1 = new MyYinYang(yin1X, yin1Y, yin1Width, yin1Height);
		yin2 = new MyYinYang(yin2X, yin2Y, yin2Width, yin2Height);

		if(hasCollided(yin1)){
			// If the two shapes have collided, then the
			// shapes will reverse direction to go on until they 
			// hit the edges of the screen
			// The reason the directions are not random
			// is because if they don't bounce away
			// from each other, they keep colliding
			// until they fly off of the screen
			
			deltaX1 = deltaX1*-1;
			deltaY1 = deltaY1*-1;
			deltaX2 = deltaX2*-1;
			deltaY2 = deltaY2*-1;

			// Commands to scale the two Yin Yang shapes based on the current
			// size when being compared to each other
			// IF SECTION IS IF YIN1 IS LARGER, ELSE IS IF THE SECOND IS LARGER
			double scale = 0;
			if(yin1.getWidth()>= yin2.getWidth()){
				// Uses a do while loop so that it executes once, but if
				// the scale ends up being 0, it does it again
				do{
					// Scales by a random double between .5 and 1
					scale = ((randomNumber.nextDouble()+.5)-randomNumber.nextDouble());
				}while(scale<=0);
				
				// Yin1Width is now set to it's original value times the scale
				// which has been set
				yin1Width = (int) (scale*yin1Width);
				// Simple equality so that the height and width are the same, since it is a circular object
				yin1Height = yin1Width;
				
				do{
					// Scales by a factor between 1 and 2
					scale = randomNumber.nextDouble()+1;
				}while(scale<=0);
				yin2Width = (int) (yin2Width * scale);
				yin2Height = yin2Width;
			}
			
			else{
				do{
					scale = randomNumber.nextDouble()+1;
				}while(scale<=0);
				yin1Width = (int) (yin1Width * scale);
				yin1Height = yin1Width;
				
				do{
					scale = ((randomNumber.nextDouble()+.5)-randomNumber.nextDouble());
				}while(scale<=0);
				yin2Width = (int) (yin2Width * scale);
				yin2Height = yin2Width;
			}
		}
	}

	public boolean hasCollided(MyYinYang y1){
		// Checks to see if the bounds of the first yinYang intersect anywhere within
		// the bounds of the second yinYang
		return y1.getBounds().intersects(yin2X, yin2Y, yin2Width, yin2Height);
	}

	private void calculateEdges() {

		// Calculates the new X and Y values to become the 
		// destination once the edges are hit, taking
		// into account that they are randomly
		// scaled so that the speed changes
		// once the edges are hit

		int destX1 = randomNumber.nextInt(maxChange);
		int destY1 = randomNumber.nextInt(maxChange);
		int destX2 = randomNumber.nextInt(maxChange);
		int destY2 = randomNumber.nextInt(maxChange);

		// Checks to see if the right side of yin1 
		// has hit right edge.  Bounces off on
		// the inverse angle in a different color if it has
		if((yin1X+yin1Width) >= PANEL_WIDTH){
			destX1 = randomNumber.nextInt(maxChange);
			deltaX1 = destX1 * -1;
			color1 = changeColor();
		}

		// Checks if the right side of yin2 has hit the right edge
		if((yin2X+yin2Width) >= PANEL_WIDTH){
			destX2 = randomNumber.nextInt(maxChange);
			deltaX2 = destX2 * -1;
			color2 = changeColor();
		}

		// Checks if the left side of yin1 has hit the left edge
		if(yin1X <= 0){
			destX1 = randomNumber.nextInt(maxChange);
			deltaX1 = destX1;
			color1 = changeColor();
		}

		// Checks if the left side of yin2 has hit the left edge
		if(yin2X <= 0){
			destX2 = randomNumber.nextInt(maxChange);
			deltaX2 = destX2;
			color2 = changeColor();
		}

		// Checks if the bottom side of yin1 has hit the bottom edge
		if((yin1Y+yin1Height) >= PANEL_HEIGHT){
			destY1 = randomNumber.nextInt(maxChange);
			deltaY1 = destY1 * -1;
			color1 = changeColor();
		}

		// Checks if the bottom side of yin2 has hit the bottom edge
		if((yin2Y+yin2Height) >= PANEL_HEIGHT){
			destY2 = randomNumber.nextInt(maxChange);
			deltaY2 = destY2 * -1;
			color2 = changeColor();
		}

		// Checks if the top side of yin1 has hit the top edge
		if(yin1Y <= 0){
			destY1 = randomNumber.nextInt(maxChange);
			deltaY1 = destY1;
			color1 = changeColor();
		}

		// Checks if the top side of yin2 has hit the top edge
		if(yin2Y <= 0){
			destY2 = randomNumber.nextInt(maxChange);
			deltaY2 = destY2;
			color2 = changeColor();
		}
	}

	private Color changeColor() {
		// Commands to change the colors of the two Yin Yangs randomly
		// to some other color
		return new Color(Color.HSBtoRGB(randomNumber.nextFloat(), randomNumber.nextFloat(), randomNumber.nextFloat()));

	}
	
	@Override
	public void mouseClicked(MouseEvent event) {

	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		x1Clicked = event.getX();
		y1Clicked = event.getY();
		x2Clicked = event.getX();
		y2Clicked = event.getY();
		
		// Conditions to update the score depending on speed and size of shapes
		if (yin1.getBounds().contains(x1Clicked, y1Clicked)){
			if(yin1.getWidth()>250){
				if(deltaX1<2){
					points+=1;
				}
				else if(deltaX1<3){
					points+=3;
				}
				else if(deltaX1 < 6){
					points +=5;
				}
				else{
					points+=7;
				}
			}
			else if(yin1.getWidth()>150){
				if(deltaX1<2){
					points+=15;
				}
				else if(deltaX1<3){
					points+=35;
				}
				else if(deltaX1 < 6){
					points +=55;
				}
				else{
					points+=75;
				}
			}
			else{
				if(deltaX1<2){
					points+=125;
				}
				else if(deltaX1<3){
					points+=225;
				}
				else if(deltaX1 < 6){
					points +=325;
				}
				else{
					points+=425;
				}
			}
		}
		else if(yin2.getBounds().contains(x2Clicked, y2Clicked)){
			if(yin2.getWidth()>250){
				if(deltaX2<2){
					points+=1;
				}
				else if(deltaX2<3){
					points+=3;
				}
				else if(deltaX2 < 6){
					points +=5;
				}
				else{
					points+=7;
				}
			}
			else if(yin2.getWidth()>150){
				if(deltaX2<2){
					points+=15;
				}
				else if(deltaX2<3){
					points+=35;
				}
				else if(deltaX2 < 6){
					points +=55;
				}
				else{
					points+=75;
				}
			}
			else{
				if(deltaX2<2){
					points+=125;
				}
				else if(deltaX2<3){
					points+=225;
				}
				else if(deltaX2 < 6){
					points +=325;
				}
				else{
					points+=425;
				}
			}
		}
		else{
			misses--;
		}	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}
