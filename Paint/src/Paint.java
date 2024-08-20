/**
 * @author Fady Seha and Malcolm Ferguson
 * Date: 1/14/2021
 * Program: Paint Creator
 * Description: A program that 
 */

//Imports to make code do coding things
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.shape.Line;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

//code for all of the stuffs
public class Paint extends JFrame {
    
    //Drawing pannel that we use to draw stuff in
    DrawingPanel draw = new DrawingPanel();
    //this is where we put all of the buttons to control the pen and the master peace
    JPanel controls = new JPanel();
    //creates a slider to control the pen size
    JSlider size = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
    //button to all owsers to create their own pen colors using the RGB value sliders
    JButton userChoice = new JButton("❂");
    //Button brightens the color currently selected
    JButton brighten = new JButton("Brighten");
    //Button darkens the color currently selected
    JButton darken = new JButton("Darken");
    //This is the primary pen color button, selected by default
    JButton primaryC = new JButton("P");
    //this is the secondary pen color button, user has to intentially select this color
    JButton secondaryC = new JButton("S");
    //creates the red color button
    JButton R = new JButton();
    //creates the green color button
    JButton GREEN = new JButton();
    //creates the blue color button
    JButton BLUE = new JButton();
    //creates the orange color button
    JButton O = new JButton();
    //creates the white color button
    JButton W = new JButton();
    //creates the black color button
    JButton BLACK = new JButton();
    //creates the yellow color button
    JButton Y = new JButton();
    //creates the cyan color button
    JButton C = new JButton();
    //creates the magenta color button
    JButton M = new JButton();
    //creates the pink color button
    JButton P = new JButton();
    //creates the gray color button
    JButton GRAY = new JButton();
    //creates the purple color button
    JButton PURPLE = new JButton();
    //creates the button that clears the pen marks on the drawing pannel
    JButton clear = new JButton("Clear");
    //creates the button that turn the linke mode button onn and off
    JButton makeLine = new JButton("Line Mode: off");
    //creates the button that turns the fan mode on and off
    JButton fanMode = new JButton("Fan Mode: off");
    //creates the button that blands the primary and secondary buttons together to create a usable third mix color
    JButton blender = new JButton("Blend P & S");
    //creates the button that makes a snowman/snowman line that is outlined in the color selected
    JButton snowman = new JButton("☃ Mode: off");
    //creates the button that inituates the mirror mode, where users can draw on one side and a mirror version will appear on the other side
    JButton mirror = new JButton("Mirror Mode: off");
    //creates the button that saves a users painting/drawing to the paining file
    JButton save = new JButton("save");
    //creates the button that displays the blended primary and secondary colors together, this is what a user clicks to use the blended colors
    JButton blended = new JButton();
    //used to draw
    GeneralPath path = new GeneralPath();
    //sets the color button to primary
    boolean primary = true;
    //sets the fan mode to off
    boolean fan = false;
    //sets the line maker mode to off
    boolean makeALine = false;
    //sets the snowman drawer mode to off
    boolean snow = false;
    //sets the mirror mode to off
    boolean isMirror = false;
    //Array to create the button colors
    ArrayList<JButton> buttons = new ArrayList<>();
    //arraylist to create the drawn lines
    ArrayList<LineSegment> lines = new ArrayList<>();
    //Sets the current clor variable, the color the pen uses to draw
    Color currentC = new Color(0, 0, 0);
    //sets the cordinates to draw
    int x = 0, y = 0;
    //sets the color picker values to zero on the color selector
    int blue = 0, red = 0, green = 0;
    //crestes the main line segment
    LineSegment line;
    //creates the mirrored line segment
    LineSegment mirroredLine;
    
    /**
     * This is the helper method that initiates the JFrame setup sequence and sets up the basic fundamental compnents of the jFrame
     */
    public Paint() {
        setUpMain(); //method to set up the layout of the project
        setTitle("Paint Project"); //sets the projet title
        setVisible(true); //allows things show up
        setResizable(false); //forces people to live with the size I choose they may have for the application                                         
        validate(); //makes sure things show up, North Korean attendence
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fun stuff to make things go bye, bye
        //centers the frame
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (.5 * (screensize.width - getWidth())), (int) (.5 * (screensize.height - getHeight())), getWidth(), getHeight());
    }
    
    /**
     * 
     * @param args 
     * This is the main method that initiates the paint method
     */
    public static void main(String[] args) {
        new Paint();
    }

    /**
     *This method sets up the layout, settings, and functionality of the entire user interface
     */
    public void setUpMain() {
        //creates slider
        sliderCreator();
        //adds buttons to the control center and sets control center background and size
        addElmentsToControlCenter();
        //sets the drawing panels background color
        draw.setBackground(Color.WHITE);
        //sets the drawing panels size
        draw.setPreferredSize(new Dimension(800, 600));
        
        //sets the layout of the content pane
        getContentPane().setLayout(new FlowLayout());
        //sets the color of the content pane
        getContentPane().setBackground(Color.WHITE.darker().darker());
        //sets the size of the content pane
        getContentPane().setPreferredSize(new Dimension(1060, 608));
        //adds the drawing panel to the content pane 
        getContentPane().add(draw);
        //adds the control panel to the content pane
        getContentPane().add(controls);
        
        //sets up the color control settings and functions
        choicesSetUp();
        setUpOtherControles();
        pack();
        
        //adds a motionlistener to the drawing panel
        addMotionListinerToCanvase(draw);
        //adds a click listener to the drawing panel
        addClickListner(draw);

    }
    /**
     * This helper method creates the size slider, defines it, and styles it
     */
    public void sliderCreator(){
        //sets the display values of the slider
        size.setMajorTickSpacing(20);
        //sets all of the values in the slider
        size.setMinorTickSpacing(10);
        //shows the value placements
        size.setPaintTicks(true);
        //shows the values on the slider
        size.setPaintLabels(true);
        //sets the default value size for the pen
        size.setValue(25);
        //creates font for title
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 20);
        //cretes border arround the size slider
        Border l = BorderFactory.createEtchedBorder(Color.BLUE, Color.CYAN);
        //creates a second border and a title for the size slider
        Border sizeBorder = BorderFactory.createTitledBorder(l, "brush size", 0, 0, f, Color.BLUE.darker());
        //sets the border arround the pen size slider
        size.setBorder(sizeBorder);
    }
    /**
     * This method adds the top controls to the control center
     */
    public void addElmentsToControlCenter(){
        //sets the layout type
        controls.setLayout(new FlowLayout());
        //adds the primary pen color button
        controls.add(primaryC);
        //adds the secondary pen color button
        controls.add(secondaryC);
        //adds the pen color brightening tool button
        controls.add(brighten);
        //adds the pen color darkening` tool button
        controls.add(darken);
        //ads the pen size slider control
        controls.add(size);
        //sets the size of the control JPanel
        controls.setPreferredSize(new Dimension(250, 600));
        //sets the background of the control panel
        controls.setBackground(Color.WHITE.darker().darker().darker());
    }
    
    /**
     * 
     * @param d 
     * 
     * this method gets the value of the mouse and draws a line or other paint editor drawing feature where you click
     */
    public void addMotionListinerToCanvase(JPanel d){
        //adds the mouse listener
        d.addMouseMotionListener(new MouseMotionAdapter() {
            //determines what to do if the mouse is pressed and dragged
            public void mouseDragged(MouseEvent e) {
                //defines the line the we will use to draw when the mouse is clicked
                line = new LineSegment(x, y, e.getX(), e.getY(), currentC, size.getValue());
                //calculates the normal mouse value in the mirrired mode
                int mX = 800 - x;
                //this assists in the mirror effect, calculates the mirrored mouse x value
                int mMouseX = 800 - e.getX();
                //defines a mirrored line, incase the mirror feature is being used
                mirroredLine = new LineSegment(mX, y, mMouseX, e.getY(), currentC, size.getValue());
                if (!fan) { //if the fan mose is not engaged
                    x = e.getX(); //get the mouse x
                    y = e.getY(); //get the mouse y
                }
                if (isMirror == false || x <= 400) { //if mirror mode is off or the mouse is on the left side of the screen, draw
                    lines.add(line); //adds the drawn line to the screen and is stored in an array
                }
                if (isMirror && x <= 400) { //if mirror mirror is on and the mouse is on the lefy side of the screem, draw
                    lines.add(mirroredLine); //adds the mirror line to the screen and is stored in an array
                }
                if (snow) { addSnowman(x, y, x, y);} //if the snowman feature is activided, call the snow man method and add it to the screen
                if (snow && isMirror) {addSnowman(mX, y, mMouseX, y);} //if snowman feature os activated and mirror mode is activated, call the snow man method and add it to the screen (This creates the mirrir effect)
                repaint(); //repaints the entire screen

            }
            //when the mouse is moved, update the x and y values
            public void mouseMoved(MouseEvent e) {
                x = e.getX(); //gets the mouse x
                y = e.getY(); //gets the mouse y
            }
        });
    }
    
    /**
     * 
     * @param d 
     * 
     * This method determines the actions of the buttons on the mouse
     * all buttons when clicked will draw, however, when you click the middle and the right button on the mouse, it changes the color
     */
    public void addClickListner(JPanel d){
        d.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
                //controls button 1 on the mouse
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //creates the line to be drawn when the mouse is moved
                    line = new LineSegment(x, y, e.getX(), e.getY(), currentC, size.getValue());
                    //gets the regular g value for mirror mode
                    int mX = 800 - x;
                    //gets the mirror x value for the mirror mode
                    int mMouseX = 800 - e.getX();
                    //id mirror mode is off, do this
                    if (isMirror == false || x <= 400) {
                        lines.add(line); //add lines to line array
                    }
                    //if mirror mode is on and you or on the left side, do this
                    if (isMirror && x <= 400) {
                        lines.add(new LineSegment(mX, y, mMouseX, e.getY(), currentC, size.getValue())); //add line to lines array
                    }
                    //if the line mode is activated, do this
                    if (makeALine) {
                        if (lines.size() - 4 >= 0) { // if line points are at least semi speced out, create line
                            // create line and connect the two line points, then add it the lines array
                            lines.add(new LineSegment(800 - (int) lines.get(lines.size() - 3).getEndX(), (int) lines.get(lines.size() - 3).getEndY(), e.getX(), e.getY(), currentC, size.getValue())); 
                        }
                    }
                    //if line mode and mirror mode are both activated, do this
                    if (makeALine && isMirror) {
                        if (lines.size() - 4 >= 0) {// if line points are at least semi speced out, create line
                            //// create line and connect the two line points, then add it the lines array, but line is reated on the mirror doe of the drawing
                            lines.add(new LineSegment((int) lines.get(lines.size() - 4).getEndX(), (int) lines.get(lines.size() - 4).getEndY(), 800 - e.getX(), e.getY(), currentC, size.getValue()));
                        }
                    }
                    // if the snow mode is activared
                    if (snow) {addSnowman(x, y, x, y);} //calls the snowman method to add and store the snowman
                    //if the snowman and mirror method is envoked, then it calls the snowman method and adds it to the mirrored side
                    if (snow && isMirror && x <= 400) {
                        addSnowman(mX, y, mMouseX, y);
                    }
                    repaint(); //makes stuff appear
                }
                //if the middle/scroll wheel/ third button is pressed
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //if a second button (the second one if there is only one) is pressed, then change colors
                    if (!primary) { //checks to see which color is being used by the user, and then switches them
                        primaryC.doClick(); //changes colors to the primary color
                    } else {
                        secondaryC.doClick(); //changes color tot the secondary color
                    }
                }
            }
        });
    }
    
    /**
     * The long awaited snowman creation method
     * @param sX
     * @param sY
     * @param eX
     * @param eY 
     * 
     * this method creates the snowman when the snowman method is enabled and displays it in place of the pen, connected at the head by a line
     */
    public void addSnowman(int sX, int sY, int eX, int eY) {
        int s = size.getValue(); //gets the size of the pen
        lines.add(new LineSegment(sX, sY, eX, eY, currentC, s + 25)); //creates the head circle/border in the color of the pen
        lines.add(new LineSegment(sX, sY, eX, eY, Color.WHITE, s + 15)); //creates the white filling of the head
        lines.add(new LineSegment(sX, sY + 25 + s, eX, eY + 25 + s, currentC, s + 45)); //creates the body/body border in the color of the pen
        lines.add(new LineSegment(sX, sY + 25 + s, eX, eY + 25 + s, Color.WHITE, s + 35)); //creates the white filling of the body
        lines.add(new LineSegment(sX, sY + 50 + s + s, eX, eY + 50 + s + s, currentC, s + 65)); //creates the bottom circle/bottom border in the color of the pen
        lines.add(new LineSegment(sX, sY + 50 + s + s, eX, eY + 50 + s + s, Color.WHITE, s + 55)); //creates the white filling of the bottom
        lines.add(new LineSegment(sX, sY + 20 + s, eX, eY + 20 + s, currentC, (s / 4) + 10)); //creates the middle button on the snowman
    }
    
    /**
     * Helper method for "choicesSetUp"
     * This methods defines and sets up the two main pen color control buttons at the top of the control panel
     */
    public void DrawingColorButtons(){
        //sets up the primary color button size
        primaryC.setPreferredSize(new Dimension(100, 50));
        //sets up the secondary color button size
        secondaryC.setPreferredSize(new Dimension(100, 50));
        //sets the backrounds of the two buttons
        primaryC.setBackground(Color.BLACK);
        secondaryC.setBackground(Color.WHITE);
        
        
        //adds the action listeners to the buttons
        primaryC.addActionListener((ae) -> {
            primary = true; //sets this to the primary button
            currentC = primaryC.getBackground(); //sets background
            repaint(); //makes it appear the new color
        });
        secondaryC.addActionListener((ae) -> {
            primary = false; //sets it as the non-primary color
            currentC = secondaryC.getBackground(); //sets the background
            repaint(); //makes it appear the new color
        });
    }
    
    /**
     * sets up the color command controls in the control panel
     */
    public void choicesSetUp() {
        
        DrawingColorButtons();
        //sets the background colors of the buttons that change the pen color, to that color
        R.setBackground(Color.RED); //sets to red
        GREEN.setBackground(Color.GREEN); //sets to green
        BLUE.setBackground(Color.BLUE); //sets to blue
        W.setBackground(Color.WHITE); //sets to white
        BLACK.setBackground(Color.BLACK); //sets to black
        O.setBackground(Color.ORANGE); //sets to orange
        Y.setBackground(Color.YELLOW); //sets to yellow
        P.setBackground(Color.PINK); //sets to pink
        M.setBackground(Color.MAGENTA); //sets to magenta
        C.setBackground(Color.CYAN); //sets to cyan
        GRAY.setBackground(Color.GRAY); //sets to gray
        PURPLE.setBackground(new Color(150, 20, 150)); //sets to purple
        //adds the buttons to the button array
        buttons.add(R); //adds the red button
        buttons.add(O); //adds the orange button
        buttons.add(Y); //adds the yellow button
        buttons.add(GREEN); //adds the green button
        buttons.add(BLUE); //adds the blue button
        buttons.add(C); //adds the cyan button
        buttons.add(P); //adds the pink button
        buttons.add(PURPLE); //adds the purple button
        buttons.add(M); //adds the magenta button
        buttons.add(BLACK); //adds the black button
        buttons.add(GRAY); //adds the gray button
        buttons.add(W); //adds the white button
        
        //Goes throughbthe buttons array list and adds them to the control panel
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPreferredSize(new Dimension(70, 40)); //sets the button size
            controls.add(buttons.get(i)); //adds the buttons to the control panel
            makeListners(buttons.get(i)); //adds a listener function to the buttons, calls the make listeners function
        }

    }
    
    /**
     * Adds a listener function to the color buttons
     * @param b 
     * the buttons that are passed to this helper method are the color buttons and a listener function is added so they change the pen colors when clicked
     */
    public void makeListners(JButton b) {
        b.addActionListener((al) -> {
            if (primary) { //if user is using priimary colot
                primaryC.setBackground(b.getBackground()); //set the primary color button background to the button color
                currentC = b.getBackground(); //set the pen color to the button color
            } else {
                secondaryC.setBackground(b.getBackground()); //set the secondary color button background to the button color
                currentC = b.getBackground(); //set the pen color to the button color
            }
            repaint(); //makes magic happen
        });
    }
     /**
      * Sets up the button that allows user to create their own color, the blend colors button, and the lighten and darken color features
      */
    public void colorFun(){
        //The button that allows people to set their own colors
        userChoice.setBackground(Color.WHITE); //sets button background
        userChoice.setPreferredSize(new Dimension(70, 30)); //sets color chooser button size
        userChoice.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25)); //sets the font of the button color chooser
        
        //The button that blends the primpary and secondary colors
        blended.setPreferredSize(new Dimension(100, 50)); //sets the button size
        
        
        blended.setVisible(false); //hides the button until the user selects the blnd button
        userChoice.addActionListener((ae) -> { //tells the button what to do when clicked
            chose(); //choose method
        });
        
        blender.addActionListener((ae) -> { //when the blend button is selected find the average of the rgb values
            int R = (primaryC.getBackground().getRed() + secondaryC.getBackground().getRed()) / 2; //finds red average
            int G = (primaryC.getBackground().getGreen() + secondaryC.getBackground().getGreen()) / 2; //finds green average
            int B = (primaryC.getBackground().getBlue() + secondaryC.getBackground().getBlue()) / 2; //find blue average
            blended.setBackground(new Color(R, G, B)); //sets the button background to the new color
            blended.setVisible(true); // makes the blended color button poof into existance
        });

        blended.addActionListener((ae) -> {
            currentC = blended.getBackground(); //sets the curent pen color to the new blended color
        });

        brighten.addActionListener((ae) -> {
            currentC = currentC.brighter(); //sets up the brighter button function, so it will work
        });
        darken.addActionListener((ae) -> {
            currentC = currentC.darker(); //sets up the darker button function, so it will work
        });
    }
     /**
      * This helper method creates the fan drawing feature button
      */
    private void theFanMode(){
        fanMode.addActionListener((ae) -> { //when button is clicked
            if (fan) { //if the fan is true
                fan = false; //turn it off
                fanMode.setText("Fan Mode: off"); //change button text to off
            } else {
                fan = true; //set the fan feature to true
                fanMode.setText("Fan Mode: on"); //change the button text to on
                if (makeALine) { //if the user makes a line
                    makeLine.doClick(); //display the line on the drawing pannel, from initial point to users last point
                }
            }
        });
    }
    /**
     * This helper method creates the drawing line feature button
     */
    private void theLineMode(){
        makeLine.addActionListener((ae) -> { //when button is clicked
            if (makeALine) { //if the line feature is true
                makeALine = false; //turn it off
                makeLine.setText("Line Mode: off"); //change button text to off
            } else {
                makeALine = true; //set the line feature to true
                makeLine.setText("Line Mode: on"); //change the button text to on
                if (fan) { //if user makes a line
                    fanMode.doClick(); //display the line on the drawing pannel, connecting the two points
                }
            }
        });
    }
    /**
     * This helper method sets up the snowman drawing feature button
     */
    private void theSnowManMode(){
        snowman.addActionListener((ae) -> { //when button is clicked
            if (snow) { //if the line feature is on
                snow = false; //turn it off
                snowman.setText("☃ Mode: off"); //change button text to off
            } else {
                snow = true; //set the snowman feature to true
                snowman.setText("☃ Mode: on"); //change the button text to on
            }
        });
    }
    /**
     * This helper method sets up the save and clear buttons
     */
    private void clearAndSaveButtons(){
        clear.addActionListener((ae) -> { //when button is clicked
            lines.clear(); //clear everything on the drawing panel
            repaint(); //magic!
        });

        save.addActionListener((ae) -> { //when the button is selected
            BufferedImage bImg = new BufferedImage(draw.getWidth(), draw.getHeight(), BufferedImage.TYPE_INT_RGB); //save as this file name
            Graphics2D cg = bImg.createGraphics(); //capyures paint image
            draw.paintAll(cg); //captures the users drawing
            try { 
                Date d = new Date(); //gets the time and date for the saving process
                if (ImageIO.write(bImg, "png", new File("./drawn image " + d.getTime() + ".png"))) { //if we are able to save the file
                    System.out.println("-- saved"); //print that it was successful
                }
            } catch (IOException e) { //if image fails
                e.printStackTrace(); // try to figure out why
            }
        });
    }
    /**
     * This helper method sets up the mirror drawing feature button
     */
    private void mirrorMode(){ 
        mirror.addActionListener((ae) -> { //when button is clicked
            if (isMirror) { //if the mirror feature is on
                isMirror = false; //turn off the mirror feature
                mirror.setText("Mirror mode: off"); //change the button text
            } else {
                isMirror = true; //turn the mirror feature on
                mirror.setText("Mirror mode: on"); //change the button text
            }
            repaint(); //let the magic do its thing
        });
    }
    
    /**
     * This helper method sets up the other button functions in the control panel
     */
    public void setUpOtherControles() {
        //Sets up the user color picker and the blending colors button
        colorFun();
        //defines the fan drawing feature
        theFanMode();
        //defines the draw line feature
        theLineMode();
        //defines the draw snowman feature
        theSnowManMode();
        //defines the save and the clear buttons
        clearAndSaveButtons();
        //defines the mirror button/feature
        mirrorMode();
        
        //Adds the bottom control buttons to the control pannel
        controls.add(clear);
        controls.add(userChoice);
        controls.add(save);
        controls.add(makeLine);
        controls.add(fanMode);
        controls.add(snowman);
        controls.add(mirror);
        controls.add(blender);
        controls.add(blended);
    }
    
    
    /**
     * This method creates the user color picker
     * this creates a new frame, with sliders for the user to enter an RGB value and save it as a usable pen color
     */
    public void chose() {
        JFrame frame = new JFrame("Test"); //creates the color chooser frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //allows the frame to go away, unlike my problems

        JPanel disp = new JPanel(); //creates the display Jpanel, to show the color
        JButton ok = new JButton("Done"); //creates the done button

        JSlider r = new JSlider(JSlider.HORIZONTAL, 0, 255, 1); //creates the slider that controls the red value
        JSlider g = new JSlider(JSlider.HORIZONTAL, 0, 255, 1); //creates the slider that controls the green value
        JSlider b = new JSlider(JSlider.HORIZONTAL, 0, 255, 1); //creates the slider that controls the blue value

        frame.setLayout(new BoxLayout(frame.getContentPane(), 1)); //sets the frame layout
        disp.setPreferredSize(new Dimension(100, 100)); //sets the frames size
        disp.setBackground(new Color(red, green, blue)); //sets the frames displays background to the color
        r.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); //sets the red sliders border
        g.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3)); //sets the green sliders border
        b.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); //sets the blue sliders border
        b.addChangeListener((pl) -> { //when the blue slider is changed
            blue = b.getValue(); //set blue to the new blue value
            disp.setBackground(new Color(red, green, blue)); //sets the background to the new color
        });
        g.addChangeListener((pl) -> { //when the green slider is changed
            green = g.getValue(); //set green to the new green value
            disp.setBackground(new Color(red, green, blue)); //sets the background to the new color
        });
        r.addChangeListener((pl) -> { //when the red slider is changed
            red = r.getValue(); //set red to the new red value
            disp.setBackground(new Color(red, green, blue)); //sets the background to the new color
        });

        ok.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18)); //sets the done button font
        ok.addActionListener((al) -> { //when the done button is clicked
            currentC = disp.getBackground(); //set the pen color to the new user chosen color
            if (primary) { //if the user is using the primary color
                primaryC.setBackground(currentC); //set the background to the new color
            } else { //otherwise
                secondaryC.setBackground(currentC); //set the background to the new color
            }
            frame.dispose(); //delete the user chooser frame
        });
        //adds the buttons,, sliders, and display to the user color chooser frame
        frame.add(disp); //adds the display
        frame.add(r); //adds the red slider
        frame.add(g); //adds the green slider
        frame.add(b); //adds the blue slider
        frame.add(ok); //adds the done button
        frame.setPreferredSize(new Dimension(400, 300)); //sets the frame size
        frame.setResizable(true); //allows it to be resized
        frame.pack(); //fun stuff
        frame.setVisible(true); //makes sure we can see it
        frame.setLocation(x, y); //sets its location on the screen
    }
    
    /**
     * This class is the drawing panel, and it allows us to draw stuff in our project, extends the JPanel class
     */
    private class DrawingPanel extends JPanel {
        //this method is the pain compoment, and allows us to draw
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //gets the pain conponent properties of the entire class
            Graphics2D g2d = (Graphics2D) g; //defines the drawing graphichs 'g2d' object
            for (int i = 0; i < lines.size(); i++) { //while we are drawing a line
                g2d.setStroke(new BasicStroke(lines.get(i).getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); //round the end of the line
                g2d.setPaint(lines.get(i).getColor()); //set the line color to the pen color
                g2d.drawLine((int) lines.get(i).getStartX(), (int) lines.get(i).getStartY(), (int) lines.get(i).getEndX(), (int) lines.get(i).getEndY()); //set the loction of the line on the drawing pannel to the location of the start and end of the user
            }
            g2d.setPaint(currentC); //sets the paint color to the current color
            g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); //sets the stroke properties, so lines end with curves
            g2d.drawRect(0, 0, draw.getWidth() - 1, draw.getHeight() - 1); //set the line lize
            if (isMirror) { //if mirror is enaged
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0)); //create line on the mirrored end
                g2d.drawLine(400, 0, 400, 600); //draw the division line
            }
            g2d.dispose(); //destroy the panel once done and saved
        }
    }
    /**
     * This class sets the line compnents for the line that the user draws when they drag the mouse
     */
    private class LineSegment extends Line {

        private Color col; //creates a private color variable to be used to color the line
        private int width; //creates the width variable for obvious reasons
        private int strokeType; //creates the stroke variable, to document the users movements
        //constructors to set the private variables
        public LineSegment(int a, int b, int aa, int bb, Color c, int w) {
            super(a, b, aa, bb); //return values from parent class
            col = c; //color
            width = w; //width
        }
        //constructors to set the private variables
        public LineSegment(int a, int b, int aa, int bb, Color c, int w, int s) {
            super(a, b, aa, bb); //return values from parent class
            col = c; //color
            width = w; //width
            strokeType = s; //stroke type
        }

        public int getTheStrokeType() {
            return strokeType; //returns the stroke type when required
        }

        public Color getColor() { 
            return col; //returns the color when required
        }

        public int getWidth() {
            return width; //returns the width when required
        }
    }
}
