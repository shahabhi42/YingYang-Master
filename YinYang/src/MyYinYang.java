import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.PathIterator;

public class MyYinYang implements Shape {
	
	private Area yinYang;
	
	public MyYinYang(double outerCircleX, double outerCircleY, double outerCircleHeight, double outerCircleWidth) {
			
			// New blank Area yingYang which will add and subtract shapes from it to get the final shape
			yinYang = new Area();

			// Outer circle to form the outer edge for the entire shape
			Ellipse2D outerCircle = new Ellipse2D.Double(outerCircleX, outerCircleY, outerCircleHeight, outerCircleWidth);

			// Inner circle that is slightly smaller than the outer one so that there
			// is an outside border to the circle
			Ellipse2D innerCircle = new Ellipse2D.Double((outerCircle.getX()+1), (outerCircle.getY()+1), (outerCircle.getWidth()-1), (outerCircle.getHeight()-1));

			// Rectangle to cover right half of the panel so that when a set subtraction
			// is performed it will leave the right hand side of the ellipse filled in black
			Rectangle2D rightRectangle = new Rectangle2D.Double(outerCircle.getX() + outerCircle.getWidth()/2, outerCircle.getY(), outerCircle.getWidth()/2, outerCircle.getHeight());

			// Top white portion of the circle which is unfilled
			// Added a +1 to the height so that it touches the outer circle since
			// an additional draw statement can't be used
			Ellipse2D topCircle = new Ellipse2D.Double(outerCircle.getX()+(outerCircle.getHeight()/4)+2,outerCircle.getY()+1, (outerCircle.getWidth()/2)-1, (outerCircle.getHeight()/2)-2);

			// Bottom black portion of the circle which is filled
			// Added a +1 to the height so that it touches the outer circle since
			// an additional draw statement can't be used
			Ellipse2D bottomCircle = new Ellipse2D.Double(outerCircle.getX()+outerCircle.getWidth()/4, outerCircle.getY()+outerCircle.getHeight()/2, outerCircle.getWidth()/2, outerCircle.getHeight()/2);

			// Small ellipse to be subtracted from  the bottom filled in portion
			// I subtracted from for the starting points so that the middle of the circle falls
			// in the middle by off-setting the origin of the ellipse by half of he width & height
			Ellipse2D smallLowerCircle = new Ellipse2D.Double((bottomCircle.getX()+bottomCircle.getWidth()/2)-bottomCircle.getWidth()/6, (bottomCircle.getY()+bottomCircle.getHeight()/2), bottomCircle.getWidth()/6, bottomCircle.getHeight()/6);


			// Small filled circle on the top side of the yin yang symbol
			Ellipse2D smallUpperCircle = new Ellipse2D.Double((topCircle.getX()+topCircle.getWidth()/2)-topCircle.getWidth()/6, topCircle.getY()+bottomCircle.getHeight()/2,topCircle.getWidth()/6, topCircle.getHeight()/6);

			// Addition and subtraction statements to manipulate the shape
			yinYang.add(new Area(outerCircle));
			yinYang.subtract(new Area(innerCircle));
			yinYang.add(new Area(rightRectangle));
			yinYang.add(new Area(bottomCircle));
			yinYang.add(new Area(topCircle));
			yinYang.subtract(new Area(smallLowerCircle));
			yinYang.subtract(new Area(topCircle));
			yinYang.add(new Area(smallUpperCircle));
			yinYang.intersect(new Area(outerCircle));
			
	}
	
	@Override
	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return yinYang.contains(arg0);
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return yinYang.contains(arg0);
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return yinYang.contains(arg0,arg1);
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return yinYang.contains(arg0,arg1,arg2,arg3);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return yinYang.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return yinYang.getBounds2D();
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		//return path.getPathIterator(arg0);
		return yinYang.getPathIterator(arg0);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		//return path.getPathIterator(arg0, arg1);
		return yinYang.getPathIterator(arg0, arg1);
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return yinYang.intersects(arg0);
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return yinYang.intersects(arg0, arg1, arg2, arg3);
	}
	public int getX(){
		return (int) yinYang.getBounds2D().getMinX();
	}
	
	public int getY(){
		return (int) yinYang.getBounds2D().getMinY();
	}
	
	public int setX(int xValue){
		
		return xValue;
	}
	
	public int setY(int yValue){
		return yValue;
	}
	
	public int getHeight(){
		return yinYang.getBounds().height;
	}
	
	public int getWidth(){
		return yinYang.getBounds().width;
	}
}