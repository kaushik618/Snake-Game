import java.util.*;
import java.awt.Color; 

public class DataOfSquare {
	
	//I'm defining a list that contains colors
	ArrayList<Color> C = new ArrayList<Color>();
	int color;
	//2: snake, 1: food, 0:empty
	SquarePanel square; 
	public DataOfSquare(int col) {
		//adding color to list
		C.add(Color.MAGENTA); //0
		C.add(Color.BLUE); //1
		C.add(Color.white); //2
		color = col; 
		square = new SquarePanel(C.get(color));
	}
	
	public void lightenUp(int c) {
		square.ChangeColor(C.get(c));
	}
}
