import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

  double lastX, lastY;
  int as = 0;
  int d = 0;
  int count = 0;

  BufferedImage[][] animations = new BufferedImage[3][2];

  int velX = 0;
  int velY = 0;

  public Player(int x, int y){
    super(x, y, 64, 96, true, ID.PLAYER);
    animations[0][0] = BufferedImageLoader.getSprite(0, 0, 32, 48);
    animations[0][1] = BufferedImageLoader.getSprite(32, 0, 32, 48);
    animations[1][0] = BufferedImageLoader.getSprite(192, 0, 32, 48);
    animations[1][1] = BufferedImageLoader.getSprite(224, 0, 32, 48);
    animations[2][0] = BufferedImageLoader.getSprite(256, 0, 32, 48);
    animations[2][1] = BufferedImageLoader.getSprite(288, 0, 32, 48);
  }

  public void tick(){

    input();

    lastX = x;
    lastY = y;

    x+=velX;
    y+=velY;

    count++;
    if(count>20){
      count = 0;
      as = Math.abs(as-1);
    }
  }

  public void render(Graphics g, double p){
    g.setColor(Color.WHITE);
    g.drawImage(animations[d][as], (int)((x-lastX)* p + lastX - w/2), (int)((y-lastY)* p + lastY - h/2), w, h, null);
  }

  public void input(){
    //North/South
    if(KeyInput.get(0) == Key.DOWN){
      velY = -3;
    } else if(KeyInput.get(2) == Key.DOWN){
      velY = 3;
    } else {
      velY = 0;
    }

    //East/West
    if(KeyInput.get(1) == Key.DOWN){
      velX = -3;
      d = 2;
    } else if(KeyInput.get(3) == Key.DOWN){
      velX = 3;
      d = 1;
    } else {
      velX = 0;
      d = 0;
    }
  }
}
