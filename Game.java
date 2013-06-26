
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

/**
 * A game engine for embedded applet based games 2D games.
 *
 * @author Catherine
 */
abstract class Game extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	//the main game loop
	private Thread mainloop;

	//list of sprites
	private LinkedList< Sprite > sprites;

	//double buffer variables
	private BufferedImage backbuffer;
	private Graphics2D g2d;
	private int screenWidth;
	private int screenHeight;

	//keep track of mouse position and buttons
	private Point2D mousePos;
	private boolean mouseButtons[];

	//frame counters and timing
	private int frameCount;
	private int frameRate;
	private int desiredRate;

	private boolean gamePaused;

	/**
	 * @param frameRate the number of frames per second
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public Game( int frameRate, int width, int height ) {
		desiredRate = frameRate;
		screenWidth = width;
		screenHeight = height;
		sprites = new LinkedList< Sprite >();
		mousePos = new Point2D( 0, 0 );
		mouseButtons = new boolean[ 4 ];
		gamePaused = false;
	}

	/**
	 * @return list of sprites currently in the game
	 */
	public LinkedList< Sprite > getSprites() {
		return sprites;
	}

	/**
	 * @return the applet
	 */
	public Applet getApplet() {
		return this;
	}

	/**
	 * @return is the game paused?
	 */
	public boolean isGamePaused() {
		return gamePaused;
	}

	/**
	 * Paused the game.
	 */
	public void pauseGame() {
		gamePaused = true;
	}

	/**
	 * Resume the game from pause.
	 */
	public void resumeGame() {
		gamePaused = false;
	}

	/**
	 * This method runs when the game starts up.
	 */
	public abstract void gameStartup();

	public abstract void gameTimedUpdate();

	public abstract void gameRefreshScreen();

	public abstract void gameShutdown();

	public abstract void gameKeyDown( int keycode );

	public abstract void gameKeyUp( int keycode );

	public abstract void gameMouseDown();

	public abstract void gameMouseUp();

	public abstract void gameMouseMove();

	public abstract void spriteUpdate( Sprite sprite );

	public abstract void spriteDraw( Sprite sprite );

	public abstract void spriteDying( Sprite sprite );

	public abstract void spriteCollision( Sprite spr1, Sprite spr2 );

	/**
	 * @return the game's graphics window
	 */
	public Graphics2D getGameGraphics() {
		return g2d;
	}

	/**
	 * @return frames per second
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * @param btn the desired mouse button (0 to 4)
	 *
	 * @return is the button currently pressed?
	 */
	public boolean getMouseButton( int btn ) {
		return mouseButtons[ btn ];
	}

	/**
	 * @return the mouse's current position on the screen
	 */
	public Point2D getGameMousePosition() {
		return mousePos;
	}

	@Override
	public void init() {
		backbuffer = new BufferedImage( screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB );
		g2d = backbuffer.createGraphics();

		//start the internal sprite list
		sprites = new LinkedList< Sprite >();

		//start the inpt listeners
		addKeyListener( this );
		addMouseListener( this );
		addMouseMotionListener( this );

		//this method will be implemented by the sub-class
		gameStartup();
	}

	@Override
	public void update( Graphics g ) {

		//calculate frame rate
		frameCount++;
		long startTime = System.currentTimeMillis();
		if( System.currentTimeMillis() > startTime + 1000 ) {
			startTime = System.currentTimeMillis();
			frameRate = frameCount;
			frameCount = 0;

			//once every second all dead sprites are deleted
			purgeSprites();
		}
		gameRefreshScreen();


		if( !isGamePaused() ) {
			drawSprites();
		}

		//redraw the screen
		paint( g );

	}

	@Override
	public void paint( Graphics g ) {
		g.drawImage( backbuffer, 0, 0, this );
	}

	@Override
	public void start() {

		//start the main thread
		mainloop = new Thread( this );
		mainloop.start();
	}

	@Override
	public void run() {

		//acquire the current thread
		Thread t = Thread.currentThread();

		//while the current thread is the mainloop...
		while( t == mainloop ) {
			try {
				Thread.sleep( 1000 / desiredRate );
			} catch( InterruptedException ie ) {
				ie.printStackTrace();
			}
		}

		//update the list of sprites
		if( !isGamePaused() ) {
			updateSprites();
			testCollisions();
		}

		//game updates
		gameTimedUpdate();

		//refresh the screen
		repaint();

	}


	@Override
	public void stop() {
		//kill the game loop
		mainloop = null;

		//this method implemented by sub-class
		gameShutdown();
	}

	public void keyTyped( KeyEvent k ) {
		//will be handled by keyPressed and keyReleased
	}

	public void keyPressed( KeyEvent k ) {
		gameKeyDown( k.getKeyCode() );
	}

	public void keyReleased( KeyEvent k ) {
		gameKeyUp( k.getKeyCode() );
	}

	private void checkButtons( MouseEvent e ) {

		switch( e.getButton() ) {
			case MouseEvent.BUTTON1:
				mouseButtons[1] = true;
				mouseButtons[2] = false;
				mouseButtons[3] = false;
				break;
			case MouseEvent.BUTTON2:
				mouseButtons[1] = false;
				mouseButtons[2] = true;
				mouseButtons[3] = false;
				break;
			case MouseEvent.BUTTON3:
				mouseButtons[1] = false;
				mouseButtons[2] = false;
				mouseButtons[3] = true;
				break;
		}

	}

	@Override
	public void mousePressed( MouseEvent e ) {
		checkButtons(e);
		mousePos.setX( e.getX() );
		mousePos.setY( e.getY() );
		gameMouseDown();
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		checkButtons( e );
		mousePos.setX( e.getX() );
		mousePos.setY( e.getY() );
		gameMouseUp();
	}

	@Override
	public void mouseMoved( MouseEvent e ) {
		mousePos.setX( e.getX() );
		mousePos.setY( e.getY() );
		gameMouseMove();
	}

	@Override
	public void mouseDragged( MouseEvent e ) {
		checkButtons( e );
		mousePos.setX( e.getX() );
		mousePos.setY( e.getY() );
		gameMouseDown();
		gameMouseMove();
	}

	@Override
	public void mouseEntered( MouseEvent e ) {
		mousePos.setX( e.getX() );
		mousePos.setY( e.getY() );
		gameMouseMove();
	}

	@Override
	public void mouseExited( MouseEvent e ) {
		mousePos.setX( e.getX() );
		mousePos.setY( e.getY() );
		gameMouseMove();
	}

	@Override
	public void mouseClicked( MouseEvent e ) {

	}

	/**
	 * Calculate the cosine of the angle in degrees.
	 *
	 * @param angle the angle
	 */
	protected double calcAngleMoveX( double angle ) {
		return (double)( Math.cos( angle * Math.PI / 180 ) );
	}

	/**
	 * Calculate the sine of the angle in degrees.
	 *
	 * @param angle the angle
	 */
	protected double calcAngleMoveY( double angle ) {
		return (double)( Math.sin( angle * Math.PI / 180 ) );
	}

	protected void updateSprites() {
		for( int i = 0; i < sprites.size(); i++ ) {
			Sprite spr = sprites.get( i );
			if( spr.isActive() ) {
				spr.updatePosition();
				spr.updateRotation();
				if( spr instanceof AnimatedSprite ) {
					((AnimatedSprite)spr).updateAnimation();
				}
				spriteUpdate( spr );
				if( !spr.isActive() ) {
					spriteDying( spr );
				}
			}
		}
	}

	protected void testCollisions() {
		for( int i = 0; i < sprites.size(); i++ ) {

			Sprite spr1 = sprites.get( i );
			if( spr1.isActive() ) {

				//look through all sprites again for collisions
				for( int j = 0; j < sprites.size(); j++ ) {

					//make sure this isn't the same sprite!
					if( i != j ) {
						Sprite spr2 = sprites.get( j );
						if( spr2.isActive() ) {
							if( spr2.collidesWith( spr1 ) ) {
								spriteCollision( spr1, spr2 );
								break;
							} else {
								//spr1.setCollided( false );
							}
						}
					}
				}
			}
		}
	}

	protected void drawSprites() {
		for( int i = 0; i < sprites.size(); i++ ) {
			Sprite spr = sprites.get( i );
			if( spr.isActive() ) {
				spr.transform();
				spr.draw();
				spriteDraw( spr );
			}
		}
	}

	//remove all inactive sprites from the internal list
	private void purgeSprites() {
		for( int i = 0; i < sprites.size(); i++ ) {
			Sprite spr = sprites.get( i );
			if( !spr.isActive() ) {
				sprites.remove( i );
			}
		}
	}
}
