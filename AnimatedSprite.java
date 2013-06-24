
import java.awt.*;
import java.applet.*;
import java.util.*;
import java.awt.image.*;
import java.net.*;

public class AnimatedSprite extends Sprite {

	private Image animimage;
	private BufferedImage tempImage;
	private Graphics2D tempSurface;

	/*the current frame of animation*/
	private int currFrame;

	private int totFrames;

	/*the direction of animation (forward or backward)*/
	private int animDir;

	/*number of frames*/
	private int frCount;

	/*delay between frames*/
	private int frDelay;

	/*frame width and height*/
	private int frWidth;
	private int frHeight;

	/*number of columns in the animation file*/
	private int cols;

	/**
	 * @param applet the sprite's applet
	 * @param g2d the sprite's graphic window
	 */
	public AnimatedSprite( Applet applet, Graphics2D g2d ) {
		super( applet, g2d );
		currFrame = 0;
		totFrames = 0;
		animDir = 0;
		frCount = 0;
		frDelay = 0;
		frWidth = 0;
		frHeight = 0;
		cols = 0;
	}

	private URL getURL( String filename ) {
		URL url = null;
		try {
			url = this.getClass().getResource( filename );
		} catch( Exception e ) {
			/*do nothing*/
		}
		return url;
	}

	/**
	 * Load an image for animation.
	 *
	 * @param filename the name of the animation image file
	 * @param columns the number of frame columns in the image
	 * @param rows the number of frame rows in the image
	 * @param width the width of each frame
	 * @param height the height of each frame
	 */
	public void load( String filename, int columns, int rows, int width, int height ) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		animimage = tk.getImage( getURL( filename ) );
		setColumns( columns );
		setTotalFrames( columns * rows );
		setFrameWidth( width );
		setFrameHeight( height );

		//instantiate the temporary buffer for the image
		tempImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
		tempSurface = tempImage.createGraphics();
		super.setImage( tempImage );
	}

	/**
	 * Overrided. Use load( String, int, int, int, int ) instead.
	 */
	@Override
	public void load( String filename ) {
		/*do nothing*/
	}

	/**
	 * @return the current frame of animation
	 */
	public int getCurrentFrame() {
		return currFrame;
	}

	/**
	 * Set the current frame of animation. Animation frames are numbered
	 * from left to right, top to bottom.
	 *
	 * @param frame the desired frame number
	 */
	public void setCurrentFrame( int frame ) {
		currFrame = frame;
	}

	/**
	 * @return the width of each frame
	 */
	public int getFrameWidth() {
		return frWidth;
	}

	/**
	 * Set the width of each frame.
	 *
	 * @param width the desired frame width
	 */
	public void setFrameWidth( int width ) {
		frWidth = width;
	}

	public int getFrameHeight() {
		return frHeight;
	}

	/**
	 * Set the height of each frame.
	 *
	 * @param height the desired frame height
	 */
	public void setFrameHeight( int height ) {
		frHeight = height;
	}

	/**
	 * @return total frames in the image
	 */
	public int getTotalFrames() {
		return totFrames;
	}

	/**
	 * Set the total number of frames.
	 *
	 * @param total the desired number of frames
	 */
	public void setTotalFrames( int total ) {
		totFrames = total;
	}

	/**
	 * @return the direction of animation (forward or backward)
	 */
	public int getAnimationDirection() {
		return animDir;
	}

	/**
	 * Set the direction of animation.
	 *
	 * @param dir the direction of animation (forward or backward)
	 */
	public void setAnimationDirection( int dir ) {
		animDir = dir;
	}

	/**
	 * @return the counter for frame updating. This number increments on each animation
	 *         update. When it reaches the frame delay, the animation frame changes.
	 */
	public int getFrameCount() {
		return frCount;
	}

	/**
	 * Set the counter for frame updating.
	 *
	 * @param count the desired counter value for frame updating
	 */
	public void setFrameCount( int count ) {
		frCount = count;
	}

	/**
	 * @return the delay between frame updates
	 */
	public int getFrameDelay() {
		return frDelay;
	}

	/**
	 * Set the number of frames in the image.
	 *
	 * @param delay the desired delay between frame updates
	 */
	public void setFrameDelay( int delay ) {
		frDelay = delay;
	}

	/**
	 * @return number of columns of frames in the image
	 */
	public int getColumns() {
		return cols;
	}

	/**
	 * Set the number of columns in the image.
	 *
	 * @param cols the desired number of columns of frames in the image
	 */
	public void setColumns( int cols ) {
		this.cols = cols;
	}

	/**
	 * Update the animation.
	 */
	public void updateAnimation() {
		frCount++;
		if( getFrameCount() > getFrameDelay() ) {
			setFrameCount( 0 );
			setCurrentFrame( getCurrentFrame() + getAnimationDirection() );
			if( getCurrentFrame() > getTotalFrames() - 1 ) {
				setCurrentFrame( 0 );
			} else if( getCurrentFrame() < 0 ) {
				setCurrentFrame( getTotalFrames() - 1 );
			}
		}
	}

	/**
	 * Draw the sprite to its applet window.
	 */
	public void draw() {
		int frameX = ( getCurrentFrame() % getColumns() ) + getFrameWidth();
		int frameY = ( getCurrentFrame() / getColumns() ) + getFrameHeight();

		//copy the frame onto the temp image
		tempSurface.drawImage( animimage, 0, 0, getFrameWidth() - 1,
			getFrameHeight() -1, frameX, frameY, frameX+getFrameWidth(),
			frameY+getFrameHeight(), getApplet() );
		super.setImage( tempImage );
		super.transform();
		super.draw();
	}
}
