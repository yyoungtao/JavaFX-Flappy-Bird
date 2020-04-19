import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Bird extends ImageView {

	private double lastX, lastY;
	private Timeline flipline;

	
	public Bird(Image img){
		super();
		this.setImage(img);
		this.setSmooth(true);
		
		flipline = new Timeline();
		flipline.setCycleCount(Timeline.INDEFINITE);
		//cheat****************************
		setOnMousePressed((MouseEvent event) -> {
            lastX = event.getX();
            lastY = event.getY();
            toFront();
        });
		
        setOnMouseDragged((MouseEvent event) -> {
            double layoutX = getLayoutX() + (event.getX() - lastX);
            double layoutY = getLayoutY() + (event.getY() - lastY);
 
            if ((layoutX >= 0) && (layoutX <= (getParent().getLayoutBounds().getWidth()))) {
                setLayoutX(layoutX);
            }
 
            if ((layoutY >= 0) && (layoutY <= (getParent().getLayoutBounds().getHeight()))) {
                setLayoutY(layoutY);
            }
 
            if ((getLayoutX() + (event.getX() - lastX) <= 0)) {
                setLayoutX(0);
            }
        });
        //cheat**************************
	}
	
    public void play() {
    	flipline.play();
    }
    
    public void reset(){
    	flipline.jumpTo(Duration.millis(2000));
    }
    
    public void stop(){
    	flipline.stop();
    }
    
    public void pause(){
    	flipline.pause();
    }
	
	public void gravity(){
		flipline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,new KeyValue(this.translateYProperty(), 0)),
        		new KeyFrame(new Duration(2000),new KeyValue(this.translateYProperty(), 200)),
        		new KeyFrame(new Duration(4000),new KeyValue(this.translateYProperty(), 400)));
	}
	
	public void jump(){		
		flipline.jumpTo(flipline.getCurrentTime().subtract(Duration.seconds(0.6)));
	}
	
}
