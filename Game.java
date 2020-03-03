import java.awt.*;
import java.awt.image.*;
import java.awt.Event;

public class Game extends Canvas implements Runnable {

  private static final long serialVersionUID = 42l;
  private static double MS_PER_UPDATE = 120.0;

  public static Game gameInstance;
  public Handler handler;
  public GUIHandler gui;

  private Thread thread;
  private boolean running = false;
  private boolean paused = false;

  public Dimension size;
  public MouseMotionInput mmi;
  public MouseInput mi;
  public KeyInput ki;
  private TexturePaint paint = new TexturePaint(BufferedImageLoader.loadImage("backgroundtile.png"), new Rectangle(0, 0, 64, 64));

  public Game(){
    size = new Dimension(1000, 600);
    new Window("Game Jam", size, this);
    start();

    handler = new Handler();
    gui = new GUIHandler();
    gameInstance = this;

    mmi = new MouseMotionInput(handler);
    mi = new MouseInput(handler);
    ki = new KeyInput();

    this.addMouseListener(mi);
    this.addKeyListener(ki);
    this.addMouseMotionListener(mmi);

    BufferedImageLoader.loadSpriteSheet("spritesheet.png");

    createWorld();
  }

  public void start(){
    running = true;
    thread = new Thread(this);
    thread.start();
  }

  public void stop(){
    running = false;
    try{
      thread.join();
    } catch(InterruptedException e){
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    this.requestFocus();
    int frameCount = 0;
    int fps = 0;
    final double GAME_HERTZ = 60.0;
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    double lastUpdateTime = System.nanoTime();
    double lastRenderTime = System.nanoTime();

    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

    int lastSecondTime = (int) (lastUpdateTime / 1000000000);

    while(running){
      double now = System.nanoTime();
      int updateCount = 0;

      if(!paused){
        while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER){
          tick();
          lastUpdateTime += TIME_BETWEEN_UPDATES;
          updateCount++;
        }

        if(now - lastUpdateTime > TIME_BETWEEN_UPDATES){
          lastUpdateTime = now - TIME_BETWEEN_UPDATES;
        }

        //Render. To do so, we need to calculate interpolation for a smooth render.
        float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
        render(interpolation);
        lastRenderTime = now;

        //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
        while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES){
          Thread.yield();
          try {Thread.sleep(1);} catch(Exception e) {}
          now = System.nanoTime();
        }
      }
    }
  }

  public void render(double lag){
    BufferStrategy bs = this.getBufferStrategy();
    if(bs==null){
      this.createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    Graphics2D g2d = (Graphics2D) g;
    //////////////////////////////////
    ///////----DRAW IN HERE----///////
    //////////////////////////////////

    g.setColor(new Color(50, 50, 50));
    g2d.setPaint(paint);
    g.fillRect(0, 0, size.width, size.height);

    handler.render(g, lag / MS_PER_UPDATE);
    gui.render(g, lag / MS_PER_UPDATE);

    //////////////////////////////////
    g.dispose();
    bs.show();
  }


  //Runs every frame
  public void tick(){
    handler.tick();
    gui.tick();
  }


  //Runs before first tick method
  public void createWorld(){
    handler.addObject(new Player(50, 50));
  }

  public static void main(String[] args) {
    new Game();
  }
}
