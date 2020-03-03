import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Point;

public class MouseMotionInput extends MouseMotionAdapter{

  private Handler handler;
  private Point mousePos = null;

  public MouseMotionInput(Handler handler){
    this.handler = handler;
  }

  public void mouseMoved(MouseEvent e){
    mousePos = e.getPoint();
  }

  public int getX(){
    return mousePos.x;
  }

  public int getY(){
    return mousePos.y;
  }

  public Point getPoint(){
    return mousePos;
  }

}
