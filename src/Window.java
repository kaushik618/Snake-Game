import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

class Window extends JFrame{
	private static final long serialVersionUID = -2542001418764869760L;
	public static ArrayList<ArrayList<DataOfSquare>> Grid; 
	public static int width = 20;
	public static int height = 20;
	public Window() {
		//Create AL that will conatin threads
		Grid = new ArrayList<ArrayList<DataOfSquare>>();
		ArrayList<DataOfSquare> data;
		
		//Create threads and add to AL
		for(int i=0; i<width; i++) {
			data = new ArrayList<DataOfSquare>();
			for(int j=0; j<height; j++) {
				DataOfSquare c = new DataOfSquare(2);
				data.add(c);
			}
			Grid.add(data);
		}
		
		//set up layout of the plane
		getContentPane().setLayout(new GridLayout(20,20,0,0));
		
		//start & pause all threads, then add every square of each thread to the panel
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				getContentPane().add(Grid.get(i).get(j).square);
			}
		}
		
		//Initial position of the snake
		Tuple position = new Tuple(10,10);
		//passing the value to the controller
		ThreadController c = new ThreadController(position); 
		//start the game
		c.start();
		
		//Link the window to the keyboard class
		this.addKeyListener((KeyListener) new keyboardListener());
	}
}
