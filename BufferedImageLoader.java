import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class BufferedImageLoader{

  public static BufferedImage spriteSheet;

  public static BufferedImage loadImage(String path){
    BufferedImage image = null;
    try{
      image = ImageIO.read(new File(path));
    } catch(Exception e){
      e.printStackTrace();
    }
    return image;
  }

  public static void loadSpriteSheet(String path){
    spriteSheet = loadImage(path);
  }

  public static BufferedImage getSprite(int x, int y, int w, int h){
    if(spriteSheet!=null){
      return spriteSheet.getSubimage(x, y, w, h);
    } else {
      return null;
    }
  }

}
