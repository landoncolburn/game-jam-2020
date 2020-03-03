public class Camera {

  private float x;
  private float y;

  public Camera(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void tick(GameObject object){

    x += ((object.getX() - x) - 1000/2) * 0.05f;
    y += ((object.getY() - y) - 600/2) * 0.05f;

  }

  public float getX(){
    return x;
  }

  public void setX(float x){
    this.x = x;
  }

  public void setY(float y){
    this.y = y;
  }

  public float getY(){
    return y;
  }
}
