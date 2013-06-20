
/**
 *
 * A basic game entity which will be the most basic
 * entity in the game, with a position, velocity, move
 * angle, and face angle. It also has an active/inactive
 * switch.
 *
 * @author Catherine
 *
 */
public class BaseGameEntity {

	protected boolean active;
	protected double x;
	protected double y;

	protected velX;
	protected velY;
	protected moveAngle;
	protected faceAngle;

	public BaseGameEntity() {
		setActive( active );
		setX( 0.0 );
		setY( 0.0 );
		setVelX( 0.0 );
		setVelY( 0.0 );
		setMoveAngle( 0.0 );
		setFaceAngle( 0.0 );
	}

	public boolean isActive() {
		return active;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getVelX() 
		return velX;
	}

	public double getVelY() {
		return velY;
	}

	public double getMoveAngle() {
		return moveAngle;
	}

	public double getFaceAngle() {
		return faceAngle;
	}

	public void setActive( boolean active ) {
		this.alive = alive;
	}

	public void setX( double x ) {
		this.x = x;
	}

	public void setY( double y ) {
		this.y = y;
	}

	public void incX( double x ) {
		this.x += x;
	}

	public void incY( double y ) {
		this.y += y;
	}

	public void setVelX( double x ) {
		this.x = x;
	}

	public void setVelY( double y ) {
		this.y = y;
	}

	public void incVelX( double x ) {
		this.x += x;
	}

	public void incVelY( double y ) {
		this.y += y;
	}

	public void setFaceAngle( double angle ) {
		this.faceAngle = angle;
	}

	public void incFaceAngle( double angle ) {
		this.faceAngle += angle;
	}

	public void setMoveAngle( double angle ) {
		this.moveAngle = angle;
	}

	public void incMoveAngle( double angle ) {
		this.moveAngle += angle;
	}
}
