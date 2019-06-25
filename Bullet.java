//Bullet.java
//Bullet class, makes a Bullet object

public class Bullet{
	private int bx,by; //the coordinates of the Bullet
	
	//Bullet constructor
	//takes the x and y coordinate of the Bullet
	public Bullet(int x,int y){
		//sets x and y coordinates of the Bullet
		bx = x;
		by = y;
	}
	//getX returns the x coordinate of the Bullet
	public int getX(){
		return bx;
	}
	//getY returns the y coordinate of the Bullet
	public int getY(){
		return by;
	}
	//returns true if the Bullet is still on screen, false if it isn't
	//if true, the Bullet is moved up
	public boolean updateB(){
		if(by<-10){
			return false;
		}
		by-=10;
		return true;
	}
	public boolean updateBadB(){
		if(by>807){
			return false;
		}
		by+=7;
		return true;
	}
}
	