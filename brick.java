import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Graphics;
import javax.swing.JPanel;
 class main
{  public static void main(String[] args)
  {
  JFrame f=new JFrame("Breakout Ball");
  Gameplay game=new Gameplay();//for adding panel game  
   f.setBounds(10,10,700,600);
   f.add(game);//adding jpanel
   f.setResizable(false);
  f.setVisible(true);
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}

class Gameplay extends JPanel implements KeyListener,ActionListener
{ private boolean play=false;//game starting me play nahi
  private int score=0;//starting score 0
  private int totalbricks=32; 
  private Timer timer;//how fast ball move
  private int delay=5;
  private int playerx=310;//slider
  private int ballposx=120;//ball x
  private int ballposy=350;
  private int ballxdir=-1;
  private int ballydir=-2;
  private map1 y;

public Gameplay()
{  y=new map1(4,7);
  
  addKeyListener(this);
  setFocusable(true);
  setFocusTraversalKeysEnabled(false);
   timer=new Timer(delay,this);
   timer.start();
  }


public void paint(Graphics g)
{ //background
  g.setColor(Color.black);
  g.fillRect(1,1,692,592);
  //map
   y.draw((Graphics2D)g);
 //borders
  g.setColor(Color.red);
  g.fillRect(0,0,3,592);//x,y and 3 is wide 592 is height 
  g.fillRect(0,0,692,3);
  g.fillRect(691,0,3,592);
//scores
 g.setColor(Color.green);
 g.setFont(new Font("candara",Font.BOLD,25));
 g.drawString(""+score,590,30);

  //paddle
 g.setColor(Color.green);
 g.fillRect(playerx,550,100,8);

 //ball
 g.setColor(Color.red);
 g.fillOval(ballposx,ballposy,20,20);

if(totalbricks<=0)
{play=false;
                   ballydir=0;
                   ballxdir=0;
                   g.setColor(Color.RED);
                   g.setFont(new Font("candara",Font.BOLD,35));  
                   g.drawString("YOU WON!!!!!!!!"+score,190,300);
 
                   g.setFont(new Font("candara",Font.BOLD,35));  
                 g.drawString("Press Enter To Restart",190,370);
}
if(ballposy>570) { play=false;
                   ballydir=0;
                   ballxdir=0;
                   g.setColor(Color.red);
                   g.setFont(new Font("candara",Font.BOLD,35));  
                   g.drawString("Game Over,Score:"+score,190,300);
 
 g.setFont(new Font("candara",Font.BOLD,35));  
                  g.drawString("Press Enter To Restart",190,370);
 
}
g.dispose();


}


public void actionPerformed(ActionEvent e)
 {
    timer.start();//starting timer
   
//if we press ryt left game starts ball detecting with border nd paddle
 if(play)
 {  if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerx,550,100,8)))
  {ballydir=-ballydir;}
  A: for(int i=0;i<y.map.length;i++)
  {  for(int j=0;j<y.map[0].length;j++)
 { if(y.map[i][j]>0)
   { int brickx=j*y.brickwidth+40;
     int bricky=i*y.brickheight+50;
     int brickwidth=y.brickwidth;
     int brickheight=y.brickheight;  
     Rectangle rect=new Rectangle(brickx,bricky,brickwidth,brickheight);
     Rectangle ballrect=new Rectangle(ballposx,ballposy,20,20);
     Rectangle brickrect= rect;
     if(ballrect.intersects(brickrect)){
      y.setBrickValue(0,i,j);
       totalbricks--;
       score+=5;
      if(ballposx+19<=brickrect.x || ballposx+1>=brickrect.x+brickrect.width)
       { ballxdir=-ballxdir; }
      else{ballydir=-ballydir;}
       break A;
}
}
}
}    




ballposx+=ballxdir; 
   ballposy+=ballydir;
   if(ballposx<0){ ballxdir=-ballxdir;}//left border
   if(ballposy<0){ ballydir=-ballydir;}//top
   if(ballposx>670){ ballxdir=-ballxdir;}//right border
}
repaint();//call repaint bcoz position change
}

public void keyTyped(KeyEvent e)
{
}


public void keyPressed(KeyEvent e)
{ if(e.getKeyCode()==KeyEvent.VK_RIGHT)
    { 
       if(playerx>=600)
       {  playerx=600;}
      else 
      {
       moveright();
      } 
     }
 if(e.getKeyCode()==KeyEvent.VK_LEFT)
  {
     if(playerx<10)//outside panel
      { playerx=10;
       }
      else 
      {
       moveleft();
      }
 
  }
 
if(e.getKeyCode()==KeyEvent.VK_ENTER)
{if(!play){ play=true;
            ballposx=120;
            ballposy=350;
            ballxdir=-1;
            ballydir=-2;
            playerx=310;
            score=0;
             totalbricks=32;
            y=new map1(4,8);
            repaint();
} 
}
}
public void keyReleased(KeyEvent e)
{
}

public void moveleft()
{play=true; 
 playerx-=20;
}
public void moveright()
{ play=true;//bcoz it is false means game not start
 playerx+=20; 
}








}
class map1
{ public int map[][];//contain all bricks
  public int brickwidth;
  public int brickheight;
 
  map1(int row,int col){
    map=new int[row][col];
 for(int i=0;i<map.length;i++)
{for(int j=0;j<map[0].length;j++)
{ map[i][j]=1;
}
}
 brickwidth=610/col;
 brickheight=150/row;
}

public void draw(Graphics2D g){
 for(int i=0;i<map.length;i++)
{for(int j=0;j<map[0].length;j++)
 { if(map[i][j]>0)
 { g.setColor(Color.blue);
  g.fillRect(j*brickwidth+40,i*brickheight+50,brickwidth,brickheight);
 g.setStroke(new BasicStroke(3));
g.setColor(Color.red);
g.drawRect(j*brickwidth+40,i*brickheight+50,brickwidth,brickheight);
}

}
}
}
public void setBrickValue(int value,int row,int col)
{map[row][col]=value;
}
}