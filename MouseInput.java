import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Point;

public class MouseInput extends MouseAdapter{

  private Handler handler;
  private Point mousePos;

  public MouseInput(Handler handler){
    this.handler = handler;
  }

  public void mousePressed(MouseEvent e){
    int mx = (int)(e.getX());
    int my = (int)(e.getY());
  }

  public void mouseClicked(MouseEvent e){
    mousePos = e.getPoint();
  }

  public Point getPoint(){
    return mousePos;
  }

}
