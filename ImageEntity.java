import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.applet.*;
import java.net.*;


public class ImageEntity extends BaseGameEntity {
	
	/*variables*/
	protected Image image;
	protected Applet applet;
	protected AffineTransform at;
	protected Graphics2D g2d;

	/*default constructor*/
	ImageEntity( Applet a ) {
		applet = a;
		setImage( null );
		setActive( true );
	}

	public Image getImage() {
		return image;
	}

	public int getWidth() {
		int width;
		if( image != null ) {
			width = image.getWidth( applet );
		} else {
			width = 0;
		}
		return width;
	}

	public int getHeight() {
		int height;
		if( image != null ) {
			height = image.getHeight( applet );
		} else {
			height = image.getHeight( applet );
		}
		return height;
	}

	public double getCenterX() {
		return getX() + ( double )getWidth()/2;
	}

	public double getCenterY() {
		return getY() + ( double )getHeight()/2;
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

	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle( (int)getX(), (int)getY(), getWidth(), getHeight() );
		return r;
	}

	public void setImage( Image image ) {
		this.image = image;
		double x = applet.getSize().width/2 - getWidth()/2;
		double y = applet.getSize().height/2 - getHeight()/2;
		at = AffineTransform.getTranslateInstance( x, y );
	}

	public void setGraphics( Graphics2D g ) {
		g2d = g;
	}

	public void load( String filename ) {
		image = applet.getImage( getURL( filename ) );
		while( getImage().getWidth(applet) <= 0 ) {
			/*busy wait*/
		}
		double x = applet.getSize().width/2 - getWidth()/2;
		double y = applet.getSize().height/2 - getHeight()/2;
		at = AffineTransform.getTranslateInstance( x, y );
	}

	public void transform() {

		/*transform the entity based on its position and angle. Due to 
		  similarity transformation, the transformations must be applied                  in reverse order: P^-1)AP*/
		at.setToIdentity();
		at.translate( (int)getX() + getWidth()/2, (int)getY() + getHeight()/2 );
		at.rotate( Math.toRadians( getFaceAngle() ) );
		at.translate( -getWidth()/2, -getHeight()/2 );
	}

	public void draw() {
		g2d.drawImage( getImage(), at, applet );
	}

}
