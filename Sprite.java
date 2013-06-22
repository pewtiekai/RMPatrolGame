
import java.awt.*;
import java.applet.*;

public class Sprite {

	private ImageEntity entity;
	protected Point2D pos;
	protected Point2D vel;
	protected double rotRate;
	protected int currentState;

	/**
	 * @param a the applet that the sprite will run in
	 * @param g2d the graphics window
	 */
	Sprite( Applet a, Graphics2D g2d ) {
		entity = new ImageEntity( a );
		entity.setGraphics( g2d );
		entity.setActive( false );
		pos = new Point2D( 0, 0 );
		vel = new Point2D( 0, 0 );
		rotRate = 0.0;
		currentState = 0;
	}

	/**
	 * Load an image from a file.
	 *
	 * @param filename the name of the file containing the image
	 */
	public void load( String filename ) {
		entity.load( filename );
	}

	/**
	 * Transform the sprite's image to the sprite's location.
	 */
	public void transform() {
		entity.setX( pos.getX() );
		entity.setY( pos.getY() );
		entity.transform();
	}

	/**
	 * Draw the sprite into its graphics window.
	 */
	public void draw() {
		entity.g2d.drawImage( entity.getImage(), entity.at, entity.applet );
	}

	/**
	 * Draw the bounding rectangle of the sprite.
	 *
	 * @param c the color of the bounding rectangle
	 */
	public void drawBounds( Color c ) {
		entity.g2d.setColor( c );
		entity.g2d.draw( getBounds() );
	}

	/**
	 * Update position based on velocity.
	 *
	 */
	public void updatePosition() {
		pos.setX( pos.getX() + vel.getX() );
		pos.setY( pos.getY() + vel.getY() );
	}

	/**
	 * Update the face angle based on the rotation rate.
	 */
	public void updateRotation() {
		setFaceAngle( getFaceAngle() + rotRate );
		if( getFaceAngle() < 0 ) {
			setFaceAngle( 360 - rotRate );
		} else if( getFaceAngle() > 360 ) {
			setFaceAngle( rotRate );
		}
	}

	/**
	 * @return the generic state variable for the sprite
	 */
	public int getState() {
		return currentState;
	}

	/**
	 * Set the generic state variable for the sprite.
	 *
	 * @param state the new state
	 */
	public void setState( int state ) {
		currentState = state;
	}

	/**
	 * @return get a rectangle bounding the sprite
	 */
	public Rectangle getBounds() {
		return entity.getBounds();
	}

	/**
	 * @return get the position of the sprite (top left corner)
	 */
	public Point2D getPosition() {
		return pos;
	}

	/**
	 * Set the position of the sprite.
	 *
	 * @param pos the position of the sprite (top left corner)
	 */
	public void setPosition( Point2D pos ) {
		this.pos = pos;
	}

	public Point2D getVelocity() {
		return vel;
	}

	public void setVelocity( Point2D vel ) {
		this.vel = vel;
	}

	public Point2D getCenter() {
		return new Point2D( entity.getCenterX(), entity.getCenterY() );
	}

	public boolean isActive() {
		return entity.isActive();
	}

	public void setActive( boolean alive ) {
		entity.setActive( alive );
	}

	//
	// face angle
	//

	public double getFaceAngle() {
		return entity.getFaceAngle();
	}

	public void setFaceAngle( float angle ) {
		entity.setFaceAngle( (double)angle );
	}

	public void setFaceAngle( int angle ) {
		entity.setFaceAngle( (double)angle );
	}

	public void setFaceAngle( double angle ) {
		entity.setFaceAngle( angle );
	}

	//
	// move angle
	//

	public void setMoveAngle( float angle ) {
		entity.setMoveAngle( (double)angle );
	}

	public void setMoveAngle( int angle ) {
		entity.setMoveAngle( (double)angle );
	}

	public void setMoveAngle( double angle ) {
		entity.setFaceAngle( angle );
	}

	public int imageWidth() {
		return entity.getWidth();
	}

	public int imageHeight() {
		return entity.getHeight();
	}

	public boolean collidesWith( Rectangle rect ) {
		return rect.intersects( getBounds() );
	}

	public boolean collidesWith( Sprite sprite ) {
		return getBounds().intersects( sprite.getBounds() );
	}

	public boolean collidesWith( Point2D point ) {
		return getBounds().contains( point.getX(), point.getY() );
	}

	public Applet getApplet() {
		return entity.applet;
	}

	public Graphics2D getGraphics() {
		return entity.g2d;
	}

	public void setImage( Image image ) {
		entity.setImage( image );
	}
}
