
import java.awt.*;

class Badguy{
	private int bx,by; //coords of the bad guy
	private int LENGTH = 40;
	private int WIDTH = 40;
	private Image face;
	private int maxR,maxL;
	private boolean movingR = true;
	
	public Badguy(int x,int y, Image pic){
		bx = x;
		by = y;
		maxR = x+70;
		maxL = x-70;
		face = pic;
	}
	
	public void draw(Graphics g, GamePanel t){
		g.drawImage(face,bx,by,t);
	}
	
	public int getX(){
		return bx;
	}
	
	public int getY(){
		return by;
	}
	
	public Image getPic(){
		return face;
	}
	
	public void shift(){
		if(movingR){
			if(bx == maxR){
				by+=20;
				movingR = false;
			}
			else{
				bx+=10;
			}
		}
		else{
			if(bx == maxL){
				by+=20;
				movingR = true;
			}
			else{
				bx-=10;
			}
		}
	}	
}

class bullet{
	private int bx,by;
	
	public bullet(int x,int y){
		bx = x;
		by = y;
	}
	public int getX(){
		return bx;
	}
	public int getY(){
		return by;
	}
	public void updateB(){
		by-=10;
	}
}

	