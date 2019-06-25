//Badguy.java
//Badguy class, makes Badguy objects
import java.awt.*;

class Badguy{
	private int bx,by; //coordinites of the Badguy
	private int LENGTH = 40; //the length and width of the Badguy
	private int WIDTH = 40;
	private int row;	//the row the Badguy is in
	private int downT;	//the number of times the Badguy has shifted down
	private Image face;	//the image of the Badguy
	private int maxR,maxL;	//the maximum left and right x coordinites this Badguy moves
	private boolean movingR = true; //true if the Badguy is moving right
//	private boolean dead = false;	//true if the Badguy is dead, false if it is alive
	
	//Badguy constructor
	//takes the intial x and y coords of the Badguy, its row number, and its picture
	public Badguy(int x,int y, int r, Image pic){
		bx = x;	//set the coordinites of the Badguy
		by = y;
		row = r;// set row number
		maxR = x+70;//set the max left and right x coordinates
		maxL = x-70;
		face = pic;//set the picture
	}
	//draw draws the Badguy on the screen
	//takes a Graphic g and a GamePanel t
	public void draw(Graphics g, GamePanel t){
		g.drawImage(face,bx,by,t);
	}
	//getX returns the x coordinate of the Badguy
	public int getX(){
		return bx;
	}
	//getX returns the y coordinate of the Badguy
	public int getY(){
		return by;
	}
	//returns the row number of the Badguy
	public int getRow(){
		return row;
	}
	//shift moves the Badguys
	//takes a number, the larger the number the faster the enemies move
	public void shift(){
		if(movingR){
			if(bx == maxR){
				by+=20;
				downT+=1;
				movingR = false;
			}
			else{
				bx+=10;
			}
		}
		else{
			if(bx == maxL){
				by+=20;
				downT+=1;
				movingR = true;
			}
			else{
				bx-=10;
			}
		}
	}
	//returns the number of times the Badguy has shifted down
	public int down(){
		return downT;
	}
}

