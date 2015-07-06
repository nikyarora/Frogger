import java.awt.*;
import java.awt.event.*;	
import javax.swing.*;
import java.awt.Color;
/******************************************************************************
 * 
 * Name:		Nikhar Arora
 * Block:		B
 * Date:		5/1/14
 ******************************************************************************/ 
public class Frogger
extends JFrame 
implements ActionListener,
KeyListener
{
	// DATA:
	private static Frog froggy;	
	private static Log[] logs = new Log[12];
	private static Car[] carsRight = new Car[8];
	private static Car[] carsLeft = new Car[8];
	
	//Screen Size Variables
	private static final int MAX_WIDTH = 800;	
	private static final int MIN_WIDTH = 0;
	private static final int MAX_HEIGHT = 600;		
	private static final int MIN_HEIGHT = 22;

	//Other Finals
	private static final int FROG_SIZE = 35;
	private static final int FROG_START_VELOCITY = 0;
	private static final int START_X = 390;
	private static final int START_Y = 560;
	private static final int DELAY_IN_MILLISEC = 30;
	
	//Count variables
	private static int lossCount = 0;
	private static int winCount = 0;
	
	//Log Variables
	private static final int LOG_START_Y = 170;
	public static final int LOG_ACTUAL_START_Y = 15;
	private static final int LOG_START_X = 5;
	private static final int LOG_IMAGE_LENGTH = 175;
	public static final int LOG_ACTUAL_LENGTH = 120;
	public static final int LOG_ACTUAL_HEIGHT = 35;
	private static final int LOG_VELOCITY = 7;
	private static final int STEP_SIZE = 60;

	//Car Variables
	public static final int CAR_START_Y = 400;
	public static final int CAR_LENGTH = 40;
	private static final int CAR_VELOCITY = 1;
	private static final int STEP_SIZE_CAR = 200;
	private static final int CAR_Y_STEP_SIZE = 60;
	private static final int CAR_VELOCITY_STEPSIZE = 1;
	private static final int NEGATIVE_ONE = -1;
	private static final int NUMBER_OF_CARS = 4;
	
	//Booleans
	private static boolean carLeftOverlap = false;
	private static boolean logOverlap = true;
	private static boolean carRightOverlap = false;
	private static boolean frogHitSide = false;

	//Images
	private static Image background;
	private static Image log;
	private static Image carRight;
	private static Image carLeft;
	private static Image frog;

	// METHODS:

	/**
	 * main -- Start up the window.
	 * 
	 * @param	args
	 */
	public static void main(String args[])
	{
		// Create the window.
		Frogger gp = new Frogger();
		gp.setSize(MAX_WIDTH, MAX_HEIGHT);
		gp.setVisible(true);
		background = new ImageIcon("screen.png").getImage();
		log = new ImageIcon("log.png").getImage();
		carRight= new ImageIcon("oldcar_right.gif").getImage();
		carLeft = new ImageIcon("taxi_left.gif").getImage();
		frog = new ImageIcon("froggy.gif").getImage();
		
		gp.addKeyListener(gp);

	}

	/**
	 * Constructor for Static class.  
	 * Creates one Spaceship object, starts up the window and starts the timer.
	 */
	public Frogger()
	{
		for(int i = 0; i < logs.length; i++)
		{
			int logLength = LOG_IMAGE_LENGTH;
			int logX = LOG_START_X + i * STEP_SIZE;
			int logY = LOG_START_Y;
			int logDx = LOG_VELOCITY;	
			if (i % 3 == 0)
			{
				logY = LOG_START_Y + 0 * STEP_SIZE;
				logDx = LOG_VELOCITY + 0;
			}		
			else if (i % 3 == 1)
			{
				logY = LOG_START_Y + 1 * STEP_SIZE;
				logLength = LOG_IMAGE_LENGTH + 4;
				logDx = LOG_VELOCITY * NEGATIVE_ONE;
			}	
			else if (i % 3 == 2)
			{
				logY = LOG_START_Y + 2 * STEP_SIZE;
				logDx = LOG_VELOCITY + 0;
				
			}	

			logs[i] = new Log(logX, logY, logLength, logDx);
			
		}
		
		
		for(int i = 0; i < carsRight.length; i++)
		{
			int carLength = CAR_LENGTH;
			int carRightX = MIN_WIDTH + i * STEP_SIZE_CAR;
			int carRightY = CAR_START_Y;
			int carDx = CAR_VELOCITY;	
			
			if(i >= NUMBER_OF_CARS)
			{
				carRightY = CAR_START_Y + CAR_Y_STEP_SIZE;
				carDx = CAR_VELOCITY + CAR_VELOCITY_STEPSIZE;
			}
			
			carsRight[i] = new Car(carRightX, carRightY, carLength, carDx);	
		}
		
		for(int i = 0; i < carsLeft.length; i++)
		{
			int carLength = CAR_LENGTH;
			int carLeftX = MIN_WIDTH + i * STEP_SIZE_CAR;
			int carLeftY = CAR_START_Y + (CAR_Y_STEP_SIZE / 3);
			int carDx = CAR_VELOCITY * - CAR_VELOCITY_STEPSIZE;	
			
			if(i >= NUMBER_OF_CARS)
			{
				carLeftY = CAR_START_Y + CAR_Y_STEP_SIZE + (CAR_Y_STEP_SIZE/2);
				carDx = CAR_VELOCITY * NEGATIVE_ONE - 3;
			}
			
			carsLeft[i] = new Car(carLeftX, carLeftY, carLength, carDx);
		}	
		
		int frogSize = FROG_SIZE;
		int x = START_X;
		int y = START_Y;
		int frogVelocityX = FROG_START_VELOCITY;
		
		froggy = new Frog(x, y, frogSize, frogVelocityX);

		setSize(MAX_WIDTH, MAX_HEIGHT);
		setVisible(true);	

		Timer clock= new Timer(DELAY_IN_MILLISEC, this);			
		clock.start();				
	}

	/**
	 * Method called when a key is pressed. 
	 * Calls the methods
	 */
	public void keyPressed(KeyEvent e)			
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			froggy.moveRight();
		}
		else if(keyCode == KeyEvent.VK_LEFT)
		{
			froggy.moveLeft();
		}
		else if(keyCode == KeyEvent.VK_UP)
		{
			froggy.moveUp();
		}
		else if(keyCode == KeyEvent.VK_DOWN)
		{
			froggy.moveDown();
		}
		if(froggy.getY() < LOG_START_Y + STEP_SIZE * 3 && froggy.getY() > LOG_START_Y - 25)
		{
			logOverlap = froggy.checkIfLogsOverlap(logs, MAX_WIDTH, MIN_WIDTH, LOG_START_Y, STEP_SIZE);

			if(logOverlap == false)
			{
				System.out.println("Lost");
				int frogSize = FROG_SIZE;
				int x = START_X;
				int y = START_Y;
				int frogVelocityX = FROG_START_VELOCITY;
				
				froggy = new Frog(x, y, frogSize, frogVelocityX);
				lossCount ++;
				logOverlap = true;
			}
		}
		else if(froggy.getY() < CAR_START_Y + STEP_SIZE_CAR * 2 && froggy.getY() > CAR_START_Y)
		{
			carLeftOverlap = froggy.checkIfCarsLeftOverlap(carsLeft, MAX_WIDTH, MIN_WIDTH, CAR_START_Y, STEP_SIZE_CAR);
			
			if(carLeftOverlap == true)
			{
				System.out.println("Lost");
				int frogSize = FROG_SIZE;
				int x = START_X;
				int y = START_Y;
				int frogVelocityX = FROG_START_VELOCITY;
		
				froggy = new Frog(x, y, frogSize, frogVelocityX);
				lossCount ++;
				carLeftOverlap = false;
			}
		}
		else if(froggy.getY() < CAR_START_Y + STEP_SIZE_CAR * 2 && froggy.getY() > CAR_START_Y)
		{
			carRightOverlap = froggy.checkIfCarsRightOverlap(carsRight, MAX_WIDTH, MIN_WIDTH, CAR_START_Y, STEP_SIZE_CAR);
			
			if(carLeftOverlap == true)
			{
				System.out.println("Lost");
				int frogSize = FROG_SIZE;
				int x = START_X;
				int y = START_Y;
				int frogVelocityX = FROG_START_VELOCITY;
				
				froggy = new Frog(x, y, frogSize, frogVelocityX);
				lossCount ++;
				carRightOverlap = false;
			}
		}
		else if(froggy.getY() > MIN_HEIGHT && froggy.getY() < LOG_START_Y - 25)
		{
			System.out.println("Won");
			int frogSize = FROG_SIZE;
			int x = START_X;
			int y = START_Y;
			int frogVelocityX = FROG_START_VELOCITY;
			
			froggy = new Frog(x, y, frogSize, frogVelocityX);
			winCount ++;
			
		}
		else
		{
			froggy.stop();
		}
	}
	

	/**
	 * Called when typing of a key is completed
	 * Required for any KeyListener
	 * 
	 * @param e		Contains info about the key typed
	 */
	public void keyTyped(KeyEvent e)
	{
	}

	/**
	 * Called when a key is released
	 * Required for any KeyListener
	 * 
	 * @param e		Contains info about the key released
	 */
	public void keyReleased(KeyEvent e)	
	{
	}


	/**
	 * actionPerformed() is called automatically by the timer every time the requested 
	 * delay has elapsed.  It will keep being called until the clock is stopped or the
	 * program ends.  All actions that we want to happen then should be performed here.
	 * Any class that implements ActionListener MUST have this method.
	 * 
	 * In this example it is called to move the ball every DELAY_IN_MILLISEC.
	 * 
	 * @param e		Contains info about the event that caused this method to be called
	 */
	public void actionPerformed(ActionEvent e)		
	{
		//Check if the frog hit the side
		frogHitSide = froggy.hitSide(MIN_WIDTH, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
		if(frogHitSide == true)
		{
			System.out.println("Lost");
			int frogSize = FROG_SIZE;
			int x = START_X;
			int y = START_Y;
			int frogVelocityX = FROG_START_VELOCITY;
			
			froggy = new Frog(x, y, frogSize, frogVelocityX);
			lossCount ++;
			frogHitSide = false;
		}
		if(logOverlap == true)
		{
			froggy.move();
		}
		
		for(int i = 0; i < logs.length; i++)
		{
			//move the logs
			logs[i].move();
			logs[i].wrap(0, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
		}
		
		for(int i = 0; i < carsRight.length; i++)
		{
			// Move the cars going right
			carsRight[i].move();
			carsRight[i].wrap(0, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
		}
			
		for(int i = 0; i < carsLeft.length; i++)
		{
			// Move the cars going left
			carsLeft[i].move();
			carsLeft[i].wrap(0, MAX_WIDTH, MIN_HEIGHT, MAX_HEIGHT);
		}
		
		// Update the window.
		repaint();
	}

	/**
	 * paint 		draw the window
	 * 
	 * @param g		Graphics object to draw on
	 */
	public void paint(Graphics g)
	{
		// Clear the window.
		g.drawImage(background, 0, 0, 800, 600, this);
		
		for(int i = 0; i < logs.length; i++)
		{
			// Tell the balls to draw themselves.
			logs[i].draw(g, i, log);
		}
		
		for(int i = 0; i < carsRight.length; i++)
		{
			// Tell the balls to draw themselves.
			carsRight[i].drawRight(g, i, carRight);
		}
		for(int i = 0; i < carsLeft.length; i++)
		{
			// Tell the balls to draw themselves.
			carsLeft[i].drawRight(g, i, carLeft);
		}
		// Tell the balls to draw themselves.
		froggy.draw(g, frog);
		
		g.drawString("Number of Lives Left: " + (3 - lossCount), 650, 40);
		g.drawString("Number of Wins: " + (winCount) + "/3", 450, 40);
		if((3 - lossCount) == 0)
		{
			g.drawImage(background, 0, 0, 800, 600, this);
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.setColor(Color.black);
			g.drawString("YOU LOSE", 275, 300);
		}

		if(winCount == 3)
		{
			g.drawImage(background, 0, 0, 800, 600, this);
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.setColor(Color.blue);
			g.drawString("YOU WIN", 275, 300);
		}
	}
}

/*********************************************************************************
 * Frog class
 * Stores all of the information about a single spaceship including:
 * 		center, velocity, radius and color
 * It provides methods to move the spaceship, draw the spaceship and control the cannon 
 * of the spaceship
 ***********************************************************************************/
class Frog
{
	// DATA:
	private int x, y;						// Center of the spaceship
	private static int frogSize;				// Radius of the ball
	private static final int STEP_SIZE = 65;
	private static boolean logOverlap, carLeftOverlap, carRightOverlap;
	private static boolean anyLogOverlap = false;
	private static boolean anyCarLeftOverlap = false;
	private static boolean anyCarRightOverlap = false;
	private static int frogVelocityX;


	// METHODS:

	/**
	 * Spaceship constructor initializes the Spaceship object
	 * 
	 * @param xIn		x coordinate of center
	 * @param yIn		y coordinate of center
	 * @param dxIn		x velocity
	 * @param dyIn		y velocity
	 * @param radiusIn	radius
	 * @param colorIn	color
	 */
	public Frog(int xIn, int yIn, int frogSizeIn, int frogVelocityXIn)
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn;
		y = yIn;
		frogSize = frogSizeIn;
		frogVelocityX = frogVelocityXIn;
	}

	/**
	 * Method that makes the frog die if hits the side
	 * @param xLow	The lowest that the x can be
	 * @param xHigh	The highest that the x can be
	 * @param yLow	The lowest that the y can be
	 * @param yHigh The highest that the y can be
	 */
	public boolean hitSide(int xLow, int xHigh, int yLow, int yHigh)
	{
		if ((x + frogSize >= xHigh) || (x <= xLow))
		{
			return true;
		}
			
		if ((y <= yLow) || (y + frogSize >= yHigh))
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	/**
	 * Gets the y location of the frog
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Makes the frog stop
	 */
	public void stop()
	{
		frogVelocityX = 0;
	}

	/**
	 * Makes the frog move right
	 */
	public void moveRight()
	{
		x = x + STEP_SIZE;
	}

	/**
	 * Makes the frog move left
	 */
	public void moveLeft()
	{
		x = x - STEP_SIZE;
	}

	/**
	 * Makes the spaceship thrust forwards in the direction of the cannon
	 */
	public void moveUp()
	{
		y = y - STEP_SIZE;
	}

	/**
	 * Makes the frog move down
	 */
	public void moveDown()
	{
		y = y + STEP_SIZE;
	}
	
	/**
	 * Makes the frog move up
	 */
	public void move()
	{
		x = x + frogVelocityX;
	}
	
	/**
	 * Test to see whether the frog is on the logs
	 * @param logs		The array of logs
	 * @param maxWidth	The highest x location	
	 * @param minWidth	The highest y location
	 * @param logStartY	The y location of the first log
	 * @param logStepsize	The space between the logs
	 * @return
	 */
	public boolean checkIfLogsOverlap(Log [] logs, int maxWidth, int minWidth, int logStartY, int logStepsize)
	{
		if(x > minWidth && x < maxWidth && y >= logStartY && y <= logStartY + logStepsize * 3)
		{
			anyLogOverlap = false;
			for(int i = 0; i < logs.length; i++)
			{
				int bottomRightLogY = logs[i].getY() + Frogger.LOG_ACTUAL_HEIGHT + Frogger.LOG_ACTUAL_START_Y;
				int bottomRightFrogY = y + frogSize;
				int bottomRightLogX = logs[i].getX() + Frogger.LOG_ACTUAL_LENGTH;
				int bottomRightFrogX = x + frogSize;
				int topLeftLogY = logs[i].getY() + Frogger.LOG_ACTUAL_START_Y;
				int topLeftLogX = logs[i].getX();
				
				if(bottomRightLogY < y || topLeftLogY > bottomRightFrogY
					|| bottomRightLogX < x || topLeftLogX > bottomRightFrogX)
				{
					logOverlap = false;
				}
				else
				{
					logOverlap = true;
					frogVelocityX = logs[i].getVelocity();
				}
				
				if(logOverlap == false)
				{
				}
				else
				{
					anyLogOverlap = true;
				}
			}
			
		}
		return anyLogOverlap;
	}
	
	/**
	 * Test to see if the cars going to the left overlap the frog
	 * @param carsLeft		Array of cars going left
	 * @param maxWidth		The size of the screen horizontally
	 * @param minWidth		The smallest x location
	 * @param carStartY		The starting location of the first car
	 * @param carStepsize	The space between cars
	 * @return
	 */
	public boolean checkIfCarsLeftOverlap(Car [] carsLeft, int maxWidth, int minWidth, int carStartY, int carStepsize)
	{
		if(x > minWidth && x < maxWidth && y >= carStartY && y <= carStartY + carStepsize * 2)
		{
			anyCarLeftOverlap = false;
			for(int i = 0; i < carsLeft.length; i++)
			{
				int bottomRightCarY = carsLeft[i].getY() + Frogger.CAR_LENGTH;
				int bottomRightFrogY = y + frogSize;
				int bottomRightCarX = carsLeft[i].getX() + Frogger.CAR_LENGTH;
				int bottomRightFrogX = x + frogSize;
				int topLeftCarY = carsLeft[i].getY() + Frogger.LOG_ACTUAL_START_Y;
				int topLeftCarX = carsLeft[i].getX();
				
				if(bottomRightCarY < y || topLeftCarY > bottomRightFrogY
					|| bottomRightCarX < x || topLeftCarX > bottomRightFrogX)
				{
					carLeftOverlap = false;
				}
				else
				{
					carLeftOverlap = true;
				}
				
				if(carLeftOverlap == false)
				{
				}
				else
				{
					anyCarLeftOverlap = true;
				}
			}
			
		}
		return anyCarLeftOverlap;
	}
	
	/**
	 * Test to see if the cars going to the right overlap the frog
	 * @param carsRight		Array of cars going right
	 * @param maxWidth		The size of the screen horizontally
	 * @param minWidth		The smallest x location
	 * @param carStartY		The starting location of the first car
	 * @param carStepsize	The space between cars
	 * @return
	 */
	public boolean checkIfCarsRightOverlap(Car [] carsRight, int maxWidth, int minWidth, int carStartY, int carStepsize)
	{
		if(x > minWidth && x < maxWidth && y >= carStartY && y <= carStartY + carStepsize * 2)
		{
			anyCarRightOverlap = false;
			for(int i = 0; i < carsRight.length; i++)
			{
				int bottomRightCarY = carsRight[i].getY() + Frogger.CAR_LENGTH;
				int bottomRightFrogY = y + frogSize;
				int bottomRightCarX = carsRight[i].getX() + Frogger.CAR_LENGTH;
				int bottomRightFrogX = x + frogSize;
				int topLeftCarY = carsRight[i].getY() + Frogger.LOG_ACTUAL_START_Y;
				int topLeftCarX = carsRight[i].getX();
				
		
				if(bottomRightCarY < y || topLeftCarY > bottomRightFrogY
					|| bottomRightCarX < x || topLeftCarX > bottomRightFrogX)
				{
					carRightOverlap = false;
				}
				else
				{
					carRightOverlap = true;
				}
				if(carRightOverlap == false)
				{
				}
				else
				{
					anyCarRightOverlap = true;
				}
			}
			
		}
		return anyCarRightOverlap;
	}
	
	/**
	 * Draw the frog
	 * @param g	Graphics parameter
	 */
	public void draw(Graphics g, Image frog)
	{
		g.drawImage(frog, x, y, frogSize, frogSize, null);
	}

}
/*********************************************************************************
 *	Log	class
 * Stores all of the information about a single spaceship including:
 * 		center, velocity, radius and color
 * It provides methods to move the spaceship, draw the spaceship and control the cannon 
 * of the spaceship
 ***********************************************************************************/
class Log
{
	private int x, y;
	private int length;
	private int logDx;
	
	/**
	 * Log Class constructor
	 * @param xIn	The x location of the log
	 * @param yIn	The y location of the log
	 * @param lengthIn		The length of the log
	 * @param logDxIn		The x velocity of the log
	 */
	public Log(int xIn, int yIn, int lengthIn, int logDxIn)
	{
		x = xIn;
		y = yIn;
		length = lengthIn;
		logDx = logDxIn;	
	}
	
	/**
	 * Makes the log move horizontally
	 */
	public void move()
	{
		x = x + logDx;
	}
	
	/**
	 * Get the x coordinate of the log
	 * @return
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Get the y location of the log
	 * @return
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Get the length of the log
	 * @return
	 */
	public int getLength()
	{
		return length;
	}
	
	/**
	 * Get the velocity of the log
	 * @return
	 */
	public int getVelocity()
	{
		return logDx;
	}
	
	/**
	 * Makes the logs wrap around the screen
	 * @param xLow		The smallest x location
	 * @param xHigh		The largest x location
	 * @param yLow		The smallest y location
	 * @param yHigh		The largest y location
	 */
	public void wrap(int xLow, int xHigh, int yLow, int yHigh)
	{
		if(x >= xHigh)
		{
			x = x - xHigh;
		}
		else if(x <= xLow)
		{
			x = x + xHigh;
		}
		else if(y >= yHigh)
		{
			y = y - yHigh;
		}
		else if(y <= yLow)
		{
			y = y + yHigh;
		}
	}
	
	/**
	 * Draw the log
	 * @param g		Graphics variable
	 * @param logNumber		The log being drawn	
	 * @param log		The log image
	 */
	public void draw(Graphics g, int logNumber, Image log)
	{
		g.setColor(Color.black);
		g.drawImage(log, x, y, length, length, null); 
	}
	

}
/*********************************************************************************
 * Car class
 * Stores all of the information about a single spaceship including:
 * 		center, velocity, radius and color
 * It provides methods to move the spaceship, draw the spaceship and control the cannon 
 * of the spaceship
 ***********************************************************************************/
class Car
{
	private int x, y;
	private int length;
	private int carDx;
	
	public Car(int xIn, int yIn, int lengthIn, int carDxIn)
	{
		x = xIn;
		y = yIn;
		length = lengthIn;
		carDx = carDxIn;	
	}
	
	public void move()
	{
		x = x + carDx;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public int getVelocity()
	{
		return carDx;
	}
	
	
	public void wrap(int xLow, int xHigh, int yLow, int yHigh)
	{
		if(x >= xHigh)
		{
			x = x - xHigh;
		}
		else if(x <= xLow)
		{
			x = x + xHigh;
		}
		else if(y >= yHigh)
		{
			y = y - yHigh;
		}
		else if(y <= yLow)
		{
			y = y + yHigh;
		}
	}
	
	public void drawRight(Graphics g, int logNumber, Image carRight)
	{
		g.drawImage(carRight, x, y, length, length, null);
	}

	public void drawLeft(Graphics g, int logNumber, Image carLeft)
	{
		g.drawImage(carLeft, x, y, length, length, null);
	}
}