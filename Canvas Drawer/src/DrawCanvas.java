// Import Core Java packages
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawCanvas extends Canvas implements MouseListener,
                                                  MouseMotionListener {

    // Constants for shapes
    public static final int CIRCLE = 1;
    public static final int ROUNDED_RECTANGLE = 2;
    public static final int RECTANGLE_3D = 3;
    public static final int LINE = 4;
    public static final int SQUARE = 5;
    public static final int OVAL = 6;
    public static final int CLEAR = 7;  
    public static final int RESET = 8;

    // Coordinates of points to draw
    private int x1, y1, x2, y2;
      
    // shape to draw
    private int shape = CIRCLE;
    
    // Arraylist to hold the constants that are called from the action listener
    ArrayList<Integer> numList = new ArrayList <Integer>();	
    
    /**
     * Method to set the shape
     * Method will also add the value of x in the numList Arraylist
     */
    public void setShape(int x) {
    	this.shape =  x;
    	numList.add(x);  	
    }
    
    /**
     * Method will return previous selected shape before setting everything to clear
     * @return the last constant from the action listener before clear constant
     */
    public int getShape() {
    	return numList.get(numList.size() - 2);
    }
     
    /**
     *  Method will erase current drawing, however keep current shape and color.
     * @param g, is current graphic that is being used and changed
     */
    public void clear(Graphics g) {
    	setShape(getShape());	
    	g.setColor(getBackground());
    	g.fillRect(0,0, getWidth(),getHeight());
    }
    
    /**
     * Method will erase existing drawing, revert to circle shape w/ no filled color
     * @param g, the current graphic that is being used and changed 
     */
    public void reset(Graphics g) {
    	g.setColor(getBackground());
    	g.fillRect(0,0, getWidth(),getHeight());
    	setShape(1);
    	filledColor = null;
    }
    

    // filled color
    private Color filledColor = null;
     
    /**
     * Method to set filled color
     */
    public void setFilledColor(Color color) {
        filledColor = color;
    }  
    
    
    /**
     * Constructor
     */
	public DrawCanvas() {
	    addMouseListener(this);
	    addMouseMotionListener(this);
	} // end of constructor
	
	
    /**
     * painting the component
     */
    public void paint(Graphics g) {

        // the drawing area
        int x, y, width, height;

        // determine the upper-left corner of bounding rectangle
        x = Math.min(x1, x2);
        y = Math.min(y1, y2);

        // determine the width and height of bounding rectangle
        width = Math.abs(x1 - x2);
        height = Math.abs(y1 - y2);

        if(filledColor != null)
            g.setColor(filledColor);
        	
        switch (shape) {
            case ROUNDED_RECTANGLE :
                if(filledColor == null) {
                    g.drawRoundRect(x, y, width, height, width/4, height/4);
                }
                else
                    g.fillRoundRect(x, y, width, height, width/4, height/4);
                break;
            case CIRCLE :
                int diameter = Math.max(width, height);
                if(filledColor == null)
                    g.drawOval(x, y, diameter, diameter);
                else
                    g.fillOval(x, y, diameter, diameter);
                break;
            case RECTANGLE_3D :
                if(filledColor == null)
                    g.draw3DRect(x, y, width, height, true);
                else
                    g.fill3DRect(x, y, width, height, true);
                break;
            case LINE:
            	if(filledColor == null)
            		g.drawLine(x1, y1, x2, y2);
            	else
            		g.setColor(filledColor);
            	    g.drawLine(x1, y1, x2, y2);
            	break;
            case SQUARE:
            	if(filledColor == null)
            		g.drawRect(x,y,height,height);
            	else
            		g.fillRect(x, y, height, height);
            	break;
            case OVAL:
            	if(filledColor == null)
            		g.drawOval(x, y, width, height);
            	else
            		g.fillOval(x, y, width, height);
            	break;
            case CLEAR:
            	clear(g);
            	break;
            case RESET:
            	reset(g);
            	break;
        }

    }

    /**
     * Implementing MouseListener
     */
    
    public void mousePressed(MouseEvent event) {
        x1 = event.getX();
        y1 = event.getY();
        
    }

    public void mouseReleased(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        
        // created switch cases, to determine what shape was selected
        // This will use the coordinated given from mousePressed event
        // and mouseReleased
        
        int y = Math.min(y1, y2);
        int x = Math.min(x1, x2);
        switch(shape) {
        case ROUNDED_RECTANGLE:
        	Draw.shapeList.addElement("Rounded Rectangle " + "x = " + x + " y = " + y);  	
        	break;
        case CIRCLE: 
        	Draw.shapeList.addElement("Circle " + "x = " + x + " y = " + y);  	
        	break;
        case RECTANGLE_3D:
        	Draw.shapeList.addElement("Rectangle 3D " + "x = " + x + " y = " + y);  	
        	break;
        case LINE:
        	Draw.shapeList.addElement("Line " + "x = " + x + " y = " + y);  	
        	break;
        case SQUARE:
        	Draw.shapeList.addElement("Square " + "x = " + x + " y = " + y);  	
        	break;
        case OVAL:
        	Draw.shapeList.addElement("Oval " + "x = " + x + " y = " + y);  	
        	break;
        }
        repaint();
    }
 

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    /**
     * Implementing MouseMotionListener
     */
    public void mouseDragged(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        repaint();
    }

    public void mouseMoved(MouseEvent e) {}
}