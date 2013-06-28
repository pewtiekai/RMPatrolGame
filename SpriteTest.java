import java.awt.*;
import java.awt.event.*;

public class SpriteTest extends Game {

	private static final int screenWidth = 640;
	private static final int screenHeight = 640;

	private static final int NORTH = 0;
	private static final int WEST = 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;

	private AnimatedSprite sprite;

	private ImageEntity background;

	private int movementDir;

	public SpriteTest() {
		super( 40, screenWidth, screenHeight );
		movementDir = SOUTH;
	}

	public void gameStartup() {
		Graphics2D g2d = getGameGraphics();

		//initialize the background
		background = new ImageEntity( this );
		background.setGraphics( g2d );
		background.load( "house7.gif" );
		background.setX( 0 );
		background.setY( 0 );
		background.transform();
		background.draw();

		//initialize the Catherine sprite
		sprite = new AnimatedSprite( this, g2d );
		sprite.load( "CathySprite.png", 4, 4, 130/4, 194/4 );
		sprite.setCurrentFrame( 0 );
		sprite.setTotalFrames( 16 );
		sprite.setAnimationDirection( 0 );
		sprite.setFrameCount( 0 );
		sprite.setFrameDelay( 5 );
		sprite.setActive( true );

		Point2D vel = new Point2D( 1, 0 );
		sprite.setVelocity( vel );
		addSprite( sprite );
	}

	public void gameTimedUpdate() {

	}

	public void gameRefreshScreen() {
		background.draw();
	}

	public void gameShutdown() {

	}

	public void gameKeyDown( int keyCode ) {
		switch( keyCode ) {
			case KeyEvent.VK_UP:
				sprite.setCurrentFrame( 12 );
				movementDir = NORTH;
				break;
			case KeyEvent.VK_DOWN:
				sprite.setCurrentFrame( 0 );
				movementDir = SOUTH;
				break;
			case KeyEvent.VK_LEFT:
				sprite.setCurrentFrame( 4 );
				movementDir = WEST;
				break;
			case KeyEvent.VK_RIGHT:
				sprite.setCurrentFrame( 8 );
				movementDir = EAST;
				break;
		}

	}

	public void gameKeyUp( int keyCode ) {

	}


	public void gameMouseDown() {

	}

	public void gameMouseUp() {

	}

	public void gameMouseMove() {

	}

	public void spriteUpdate( Sprite sprite ) {
		switch( movementDir ) {
			case NORTH:
				if( sprite.getCurrentFrame() == 0 ) {
					sprite.setCurrentFrame( 12 );
				}
				break;
			case SOUTH:

			case WEST:

			case EAST:
			break;
		}
	}

	public void spriteDraw( Sprite sprite ) {

	}

	public void spriteDying( Sprite sprite ) {

	}

	public void spriteCollision( Sprite spr1, Sprite spr2 ) {

	}
}
