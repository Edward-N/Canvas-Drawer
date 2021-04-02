// Import Core Java packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Draw extends JFrame implements ActionListener, ItemListener {

	// Initial Frame size
	static final int WIDTH = 400;                // frame width
	static final int HEIGHT = 300;               // frame height

    // Color choices
    static final String COLOR_NAMES[] = {"None", "Red", "Blue", "Green"};
    static final Color COLORS[] = {null, Color.red, Color.blue, Color.green};

    // Button controls
    Button circle;
    Button roundRec;
    Button threeDRec;
    Button line;    
    Button square;  
    Button oval;   
    Button clear;   
    Button reset;	
    Button remove;  
    Button clearAll; 

    // Color choice box
    Choice colorChoice;
   
    // Created JList and Model that will hold the shapeList
    static JList<String> listView;
	static DefaultListModel<String> shapeList;
     

    // the canvas
    DrawCanvas canvas;

    /**
     * Constructor
     */
	public Draw() {
	    super("Java Draw");
        setLayout(new BorderLayout());

        // create panel for controls
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setBackground(Color.LIGHT_GRAY);
        add(topPanel, BorderLayout.NORTH);

        // create button control
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(buttonPanel);

        circle = new Button("Circle");
        buttonPanel.add(circle);
        roundRec = new Button("Rounded Rectangle");
        buttonPanel.add(roundRec);
        threeDRec = new Button("3D Rectangle");
        buttonPanel.add(threeDRec);
        
        // button control panel for the other buttons
        JPanel midbuttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(midbuttonPanel);
        line = new Button("Line");
        midbuttonPanel.add(line);
        square = new Button("Square");
        midbuttonPanel.add(square);
        oval = new Button("Oval");
        midbuttonPanel.add(oval);

        // button listener
        circle.addActionListener(this);
        roundRec.addActionListener(this);
        threeDRec.addActionListener(this);
        line.addActionListener(this);
        square.addActionListener(this);
        oval.addActionListener(this);
        
        
        // create panel for color choices
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(colorPanel);
        JLabel label = new JLabel("Filled Color:");
        colorPanel.add(label);
        colorChoice = new Choice();
        for(int i=0; i<COLOR_NAMES.length; i++) {
            colorChoice.add(COLOR_NAMES[i]);
        }
        colorPanel.add(colorChoice);
        colorChoice.addItemListener(this);
        
        // creating clear and reset button
        // adding the buttons to the colorPanel 
        clear = new Button("Clear"); 
        colorPanel.add(clear);
        reset = new Button("Reset");
        colorPanel.add(reset);
        
        // button listener for clear and reset
        clear.addActionListener(this);
        reset.addActionListener(this);
        
        
        // added panel for the list with a scroll
        JPanel listPanel = new JPanel(new GridLayout(3,1));
        add(listPanel, BorderLayout.WEST);
        shapeList = new DefaultListModel<String>();
        listView = new JList<String>(shapeList);
        JScrollPane listScroller = new JScrollPane(listView);
        listPanel.add(listScroller, BorderLayout.WEST);
         
        // remove button
        remove = new Button("Remove");
        listPanel.add(remove);
        remove.addActionListener(this);
       
        // clear all button
        clearAll = new Button("Clear All");
        listPanel.add(clearAll);
        clearAll.addActionListener(this);
       
        
        // create the canvas
        canvas = new DrawCanvas();
        canvas.setBackground(Color.LIGHT_GRAY);
        add(canvas, BorderLayout.CENTER);
        
	} // end of constructor


    /**
     *  Implementing ActionListener
     */
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == circle) { // circle button listener
            canvas.setShape(DrawCanvas.CIRCLE);
        }
        else if(event.getSource() == roundRec) { // round rectangle button listener
            canvas.setShape(DrawCanvas.ROUNDED_RECTANGLE);
        }
        else if(event.getSource() == threeDRec) { // 3D rectangle button listener
            canvas.setShape(DrawCanvas.RECTANGLE_3D);
        }
        else if(event.getSource() == line) { // line button listener
        	canvas.setShape(DrawCanvas.LINE);
        }
        else if(event.getSource() == square) { // square button listener
        	canvas.setShape(DrawCanvas.SQUARE);
        }
        else if(event.getSource() ==  oval) { // oval button listener   
        	canvas.setShape(DrawCanvas.OVAL);
        }
        else if(event.getSource() == clear) { // clear button listener
        	canvas.setShape(DrawCanvas.CLEAR);  
        	canvas.repaint();       			
        }
        else if(event.getSource() == reset) { // reset button listener  
        	canvas.setShape(DrawCanvas.RESET);  
        	colorChoice.select(0);
        	canvas.repaint();       			
        }
        else if(event.getSource()== remove) { // remove button listener
        	int number = listView.getSelectedIndex(); // gets the selected index
        	if (number >= 0) { // the list view has to contain an item
        		// since the last shape drawn is at the bottom of the list
        		// if selected and remove button is clicked it will be removed
        		// from the shapeList and the canvas reset 
        		if(number ==  shapeList.indexOf(shapeList.lastElement())) {
        			shapeList.removeElementAt(number);
        			canvas.setShape(DrawCanvas.RESET);
        			canvas.repaint();	
        		}
        		else {
        		shapeList.removeElementAt(number);
        		}
        	}
        }
        else if(event.getSource() == clearAll) {     // clear all button
        		shapeList.removeAllElements();
        		canvas.setShape(DrawCanvas.RESET);
        		canvas.repaint();
        	}
        }
        
    

    /**
     * Implementing ItemListener
     */
    public void itemStateChanged(ItemEvent event) {
        Color color = COLORS[colorChoice.getSelectedIndex()];
        canvas.setFilledColor(color);    
    }
    
    

    /**
     * the main method
     */
    public static void main(String[] argv) {
        // Create a frame
        Draw jframe = new Draw();
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setLocation(150, 100);

        // add window closing listener
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        // Show the frame
        jframe.setVisible(true);
    }
}