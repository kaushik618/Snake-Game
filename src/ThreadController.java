import java.util.*;

//Game logic is implemented in this class
public class ThreadController extends Thread {
	ArrayList<ArrayList<DataOfSquare>> Squares = new ArrayList<ArrayList<DataOfSquare>>();
	Tuple headSnakePos;
	int sizeofSnake = 3; 
	long speed=50; 
	public static int directionSnake; 
	
	ArrayList<Tuple> positions = new ArrayList<Tuple>(); 
	Tuple foodPosition; 
	
	//constructor of ControllerThreadClass
	ThreadController(Tuple positionDepart){
		//first we get all the threads
		Squares = Window.Grid;
		headSnakePos = new Tuple(positionDepart.x, positionDepart.y);
		directionSnake = 1;
		
		Tuple headPos = new Tuple(headSnakePos.getX(), headSnakePos.getY()); 
		positions.add(headPos);
		
		foodPosition = new Tuple(Window.height -1, Window.width -1);
		spawnFood(foodPosition); 
		
	}
	
	public void run() {
		while(true) {
			moveIntern(directionSnake);
			checkCollision();
			moveExtern();
			deleteTail();
			pauser(); 
		}
	}
	
	//1: right 2:left 3: top 4: bottom 0:nothing
	private void moveIntern(int dir) {
		// TODO Auto-generated method stub
		switch(dir) {
		case 4: 
			headSnakePos.ChangeData(headSnakePos.x,(headSnakePos.y + 1)%20);
			positions.add(new Tuple(headSnakePos.x, headSnakePos.y)); 
			break;
			
		case 3:
			if(headSnakePos.y -1 < 0) {
				headSnakePos.ChangeData(headSnakePos.x, 19);
			}
			else {
				headSnakePos.ChangeData(headSnakePos.x,Math.abs(headSnakePos.y - 1)%20);
			}
			
			positions.add(new Tuple(headSnakePos.x, headSnakePos.y)); 
			break;
			
		case 2: 
			if(headSnakePos.x - 1 < 0) {
				headSnakePos.ChangeData(19, headSnakePos.y);
			}
			else {
				headSnakePos.ChangeData(Math.abs(headSnakePos.x - 1)%20, headSnakePos.y);
			}
			positions.add(new Tuple(headSnakePos.x, headSnakePos.y)); 
			break;
			
		
		case 1:
			headSnakePos.ChangeData(Math.abs(headSnakePos.x + 1)%20, headSnakePos.y);
			positions.add(new Tuple(headSnakePos.x, headSnakePos.y)); 
			break;
		}
		
	}

	private void moveExtern() {
		// TODO Auto-generated method stub
		for(Tuple t : positions) {
			int y = t.getX();
			int x = t.getY();
			Squares.get(x).get(y).lightenUp(0);
		}
		
	}

	//Method to check if snake bites itself or its eating
	private void checkCollision() {
		// TODO Auto-generated method stub
		Tuple posCritique = positions.get(positions.size()-1);
		for(int i=0; i<= positions.size()-2; i++) {
			boolean biteItself = posCritique.getX() == positions.get(i).getX() && posCritique.getY() == positions.get(i).getY();
			if(biteItself) {
				stopTheGame();
			}
		}
		
		boolean eatingFood = posCritique.getX() == foodPosition.y && posCritique.getY() == foodPosition.x;
		if(eatingFood) {
			System.out.println("Eating");
			sizeofSnake = sizeofSnake +1; 
			foodPosition = getValAnyWhereNotInSnake();
			
			spawnFood(foodPosition); 
		}
		
	}
	
	//return any positon not occupied by snake
	private Tuple getValAnyWhereNotInSnake() {
		// TODO Auto-generated method stub
		Tuple p; 
		int ranX = 0 + (int)(Math.random() * 19);
		int ranY = 0 + (int)(Math.random() * 19); 
		p = new Tuple(ranX , ranY);
		for(int i=0; i<= positions.size()-1; i++) {
			if(p.getY() == positions.get(i).getX() && p.getX() == positions.get(i).getY()) {
				ranX = 0 + (int)(Math.random() * 19);
				ranY = 0 + (int)(Math.random() * 19);
				p = new Tuple(ranX , ranY);
				i=0;
			}
		}
		return p;
	}

	private void stopTheGame() {
		// TODO Auto-generated method stub
		System.out.println("Collision Occured! \n");
		while(true) {
			pauser();
		}
	}

	//delay between every move of the snake
	private void pauser() {
		// TODO Auto-generated method stub
		try {
			sleep(speed);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Put food in a position & display it
	private void spawnFood(Tuple foodPositionIn) {
		Squares.get(foodPositionIn.x).get(foodPosition.y).lightenUp(1);
		
	}
	
	//Refreshes the tail of the snake, by removing the superfluous data in arraylist
	//and refreshing the display of the things that is removed
	private void deleteTail() {
		// TODO Auto-generated method stub
		int temp = sizeofSnake; 
		for(int i=positions.size()-1; i>=0; i--) {
			if(temp ==0) {
				Tuple t =positions.get(i);
				Squares.get(t.y).get(t.x).lightenUp(2);
			}
			else {
				temp--;
			}
		}
		temp = sizeofSnake;
		for(int i = positions.size()-1; i>=0; i--) {
			if(temp == 0) {
				positions.remove(i);
			}
			else {
				temp--;
			}
		}
	}
}
