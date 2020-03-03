import javax.swing.JFrame;
import java.awt.Dimension;

public class Window{
  public Window(String title, Dimension size, Game game){
    JFrame f = new JFrame(title);
    f.setPreferredSize(size);
    f.setMinimumSize(size);
    f.setMaximumSize(size);

    f.add(game);
    f.setResizable(false);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}
