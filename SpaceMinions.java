/**
 * SpaceMinions.java
 * Assignment: AP CS Final Project
 * Purpose: This program practices basically everything  
 *   we have learned this year. This is a remake of the
 *  very popular, and well known game, space invaders.
 *
 * @version 06/24/19
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
 
public class SpaceMinions extends JFrame implements ActionListener{
	javax.swing.Timer myTimer;	
	JPanel cards;	
	CardLayout cLayout = new CardLayout();	
	JButton playBtn = new JButton("Play");	
	GamePanel game;	
	
	public SpaceMinions(){
		super("Space Invaders"); 
		setSize(800,800);	
		game = new GamePanel(this);
		add(game);	
		
		playBtn.addActionListener(this);
		myTimer = new javax.swing.Timer(10, this);
		
		//mainmenu card
		ImageIcon mainBack = new ImageIcon("mainmenu.png");
		JLabel backLabel = new JLabel(mainBack);
		JLayeredPane mPage = new JLayeredPane();
		mPage.setLayout(null);
		backLabel.setSize(800,800);
		backLabel.setLocation(0,0);
		mPage.add(backLabel,1);
				
		//play button
		playBtn.setSize(100,30);
		playBtn.setLocation(350,400);
		mPage.add(playBtn,2);

		//the magic of adding cards
		cards = new JPanel(cLayout);
		cards.add(mPage, "menu");
		cards.add(game, "game");
		add(cards);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		setVisible(true);	
	}
	//starts the Timer
	public void start(){
		myTimer.start();
	}
  //start the game if play button is pressed 
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if(source==playBtn){
		    cLayout.show(cards,"game");
		    myTimer.start();
		    game.requestFocus();
		}
		//if the game is running, run game things
		else if(source==myTimer){
			if(!game.gameOver()){ //make sure the game isn't over
				if(game.nextLevel()){	//check if a new level needs to be made
					game.reset();	//if so reset some things for the next level
				}
				game.move(); //move the player
				game.moveBad(); //move the enemies
				game.pew();	//make player bullets
				game.badPew();	//make enemy bullets
				game.updateBullets(); //move the bullets
				game.checkDead();	//check if the player or any enemies are dead
				game.repaint();	
				game.add();	
			}
			 //if the game is over, reset all variables, go back to main screen
         else{	  
				game.resetGame();	
				cLayout.show(cards,"menu");	
			}
		}

	}
    
    //start the game
	public static void main(String[] args){
		SpaceMinions frame = new SpaceMinions();
	}

}

class GamePanel extends JPanel implements KeyListener{
	private int px,py; 
	private boolean[] keys;	
	private Image back;	
	private Image player; //the player's character picture
	private Image image1; //enemy images
	private Image image2;
	private SpaceMinions mainFrame; 
	private ArrayList<Badguy> baddies = new ArrayList<Badguy>(); //ArrayList of all bad guys
	private ArrayList<Bullet> shots = new ArrayList<Bullet>();	//ArrayList of all the player's bullets
	private ArrayList<Bullet> badShots = new ArrayList<Bullet>();//ArrayList of all the bad guys' bullets
	private boolean playerNotDead = true;	//true if the player has lost all three lives, false otherwise
	private int counter = 0; //counts loops
	private int shotcount = 0; //counts loops after the player shoots a shot
	private int level = 1;	//the level the player is on
	private int points = 0;	//the number of points the player has
	private int lives = 3;	//the number of lives the player has
	
	//constructor
	public GamePanel(SpaceMinions m){
		keys = new boolean [KeyEvent.KEY_LAST+1]; 
		back = new ImageIcon("background.png").getImage();	//the background of the game
		player = new ImageIcon("player.png").getImage(); //the player's character image
		mainFrame = m;	
		//player's coordinates
		px = 375;
		py = 680;
		
		//make all the badguys and add them to their ArrayList baddies
		Image image1 = new ImageIcon("greenbat.png").getImage();	//pictures of the Badguys
		Image image2 = new ImageIcon("alien.gif").getImage();
		
        for(int x = 0 ; x <10; x++){
			for(int y = 0; y<6; y++){
				
				if(y%2==0){
					Badguy baddie = new Badguy(128+x*56,75+y*60,y,image1);
					baddies.add(baddie);
				}
				else{
					Badguy baddie = new Badguy(128+x*56,75+y*60,y,image2);
					baddies.add(baddie);
				}	
				
			}
		}
		

		setSize(800,800);
		addKeyListener(this);
	}
	//reset resets the game vairables for a new level
	public void reset(){
		Image image1 = new ImageIcon("greenbat.png").getImage();	
		Image image2 = new ImageIcon("alien.gif").getImage();
		keys = new boolean [KeyEvent.KEY_LAST+1];
		baddies = new ArrayList<Badguy>();
		for(int x = 0 ; x <10; x++){
			for(int y = 0; y<6; y++){
				if(y%level==0){
					Badguy baddie = new Badguy(128+x*56,75+y*60,y,image1);
					baddies.add(baddie);
				}
				else{
					Badguy baddie = new Badguy(128+x*56,75+y*60,y,image2);
					baddies.add(baddie);
				}	
			}
		}	
		shots = new ArrayList<Bullet>();
		badShots = new ArrayList<Bullet>();
		counter = 0;
		shotcount = 0;
		//advance the level
		level +=1;
		//reset player position
		px = 375;
		py = 680;
	}
	//resetGame resets the game if the player has lost
	//resets all variables
	public void resetGame(){
		Image image1 = new ImageIcon("greenbat.png").getImage();	
		Image image2 = new ImageIcon("alien.gif").getImage();
		keys = new boolean [KeyEvent.KEY_LAST+1];
		baddies = new ArrayList<Badguy>();
		shots = new ArrayList<Bullet>();
		badShots = new ArrayList<Bullet>();
		playerNotDead = true;
		counter = 0;
		shotcount = 0;
		level = 1;
		points = 0;
		lives = 3;
		
		//make new badguys
		for(int x = 0 ; x <10; x++){
			for(int y = 0; y<6; y++){
				
				if(y%2==0){
					Badguy baddie = new Badguy(128+x*56,75+y*60,y,image1);
					baddies.add(baddie);
				}
				else{
					Badguy baddie = new Badguy(128+x*56,75+y*60,y,image2);
					baddies.add(baddie);
				}	
				
			}
		}
	
		px = 375;
		py = 680;
	}
	//starts the game
	public void addNotify(){
		super.addNotify();
		setFocusable(true);
		requestFocus();
		mainFrame.start();
	}
	//move moves the player, makes sure the player doesn't move off screen
	public void move(){
		if(keys[KeyEvent.VK_RIGHT]){
			if(px<760){	
				px += 5;
			}
		}
		if(keys[KeyEvent.VK_LEFT]){
			if(px>0){
				px -= 5;
			}	
		}
	}
	//moveBad moves the enemies
	public void moveBad(){
		int interval = 40-baddies.get(0).down()*(level)*2; //the interval of loops at which they moove at
		if(interval <=0){
			interval =1;
		}
		if(counter==interval){
			counter = 0;
			for(Badguy b : baddies){
				b.shift();
			}
			
		}
	}
	//makes Bullets if the player shoots
	public void pew(){
		if(keys[KeyEvent.VK_SPACE]){
			if(shotcount>25){ //shots are only fired every 25 loops
				Bullet s = new Bullet(px+20,py);
				shots.add(s);
				shotcount = 0;
			}
		}
	}
	//badPew randomly generates Bullets from the enemies
	public void badPew(){
		Random rand = new Random();
		if(counter==30-(level-1)*2){
			int unfairCoin = rand.nextInt(100);	
			if(unfairCoin>=75-level*5){
				int randE = rand.nextInt(baddies.size());
				Badguy baddie = baddies.get(randE);
				Bullet bad = new Bullet(baddie.getX()+20,baddie.getY());
				badShots.add(bad);
			}
			
		}
		
	}
	//updateBullets moves the Bullets of the player and the enemy
	public void updateBullets(){
		for(int i=shots.size()-1; i>-1; i--){
			Bullet temp = shots.get(i);
			if(!temp.updateB()){
				shots.remove(i);
			}
		}
		if(badShots.size()!=0){	
			for(int i = badShots.size()-1 ;i>-1; i--){
				Bullet temp = badShots.get(i);
				if(!temp.updateBadB()){
					badShots.remove(i);
				}
			}
		}
	}
	//checkDead checks if the player or any enemies are dead
	public void checkDead(){
		int[] deadBaddies = new int[baddies.size()];	
		int[] deadShots = new int[shots.size()];		
		//check if any of the player's Bullets hit an enemy
		for(int b = baddies.size()-1; b>-1; b--){
			for(int i = shots.size()-1; i>-1; i--){
				Bullet bullet = shots.get(i);
				Badguy badGuy = baddies.get(b);
				if(bullet.getY() >= badGuy.getY() && bullet.getY() <= badGuy.getY()+40 && bullet.getX()<= badGuy.getX()+40 && bullet.getX() >= badGuy.getX()){
					deadBaddies[b]= b+1;
					deadShots[i] = i+1;
				}
			}
		}
      
		for(int i =baddies.size()-1; i>-1;i--){
			if(deadBaddies[i]!=0){
				points+= (6-baddies.get(i).getRow())*5;
				baddies.remove(i);	
			}
			
		}

		for(int i = shots.size()-1; i>-1; i--){
			if(deadShots[i]!=0){
			shots.remove(i);
			}
		}
		//checks if any Bullets from the Badguys have hit the player
		if((badShots.size())!=0){
			for(int i = badShots.size()-1; i>-1; i--){
				Bullet badB = badShots.get(i);
				if(badB.getY()>=py && badB.getY()<=py+40 && badB.getX()>=px && badB.getX()<=px+40){
					badShots.remove(i);
					lives-=1;
					//the player loses if they have no more health
					if(lives==0){
						playerNotDead = false;
					}
					//if they still have lives, they lose a life and have a position reset
					else{
						px = 375;
					}
				}
			}
		}
	}
	
	public void add(){
		counter+=1;
		shotcount+=1;
	}
	//gameOver checks if the game is over
	public boolean gameOver(){
		if(!playerNotDead){
			return true;
		}
		for(Badguy b : baddies){
			if(b.getY()>= 640){
				return true;
			}
		}
		return false;
	}
	//nextLevel returns true if the next level needs to be made
	public boolean nextLevel(){
		if(baddies.size()==0){
			return true;
		}
		return false;
	}
	public void keyTyped(KeyEvent e) {}
	
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    //paintComponent draws all the pictures onto the screen
    public void paintComponent(Graphics g){ 	
    	g.drawImage(back,0,0,this);  //draw background
		g.drawImage(player,px,py,this);	//draw the player's character
        
    	//draws all the Badguys
        for(Badguy b : baddies){
        	b.draw(g,this);	
        }
        //draws all the player's Bullets
       	for(Bullet s: shots){
            g.setColor(Color.GREEN);
       		g.fillRect(s.getX()-2,s.getY(),4,10);
       	}
        //draws all the enemies' Bullets
        for(Bullet b: badShots){
        	g.setColor(Color.RED);
        	g.fillRect(b.getX()-2,b.getY()+40,4,10);
        }
              	       
        //draws text
        g.setColor(Color.WHITE);
		g.setFont(new Font("Comic Sans MS",Font.PLAIN,32));
		g.drawString("Level "+level, 10,40);	//the level
		g.drawString("Score: "+ points, 250,40);	//the score
		g.drawString("Lives",510,40);	//the number of lives the player has
		for(int i = 0; i<lives; i++){
			g.drawImage(player,610+i*65,10,this);
		}
    }
}