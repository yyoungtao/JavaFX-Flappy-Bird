import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PipeBox extends VBox {

	private Timeline obstacleline;
	private ImageView head;
	private ImageView body;
	
	public PipeBox(Image pipehead, Image pipebody, int height){
		super();
		this.setBorder(getBorder());
		this.setAlignment(Pos.CENTER);
		head = new ImageView();
		head.setImage(pipehead);
		head.setSmooth(true);
		body = new ImageView();
		body.setImage(pipebody);
		body.setSmooth(true);
		body.setFitHeight(height-pipehead.getHeight());
		this.getChildren().addAll(head, body);
		
		obstacleline = new Timeline();
		obstacleline.setCycleCount(1);
	}
    public void play() {
    	obstacleline.play();
    }
    
    public void reset(){
    	obstacleline.jumpTo(Duration.ZERO);
    }
    
    public void stop(){
    	obstacleline.stop();
    }
	
	public void runing(){
		obstacleline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(this.translateXProperty(), 400+78)),
        		new KeyFrame(new Duration(4000), new KeyValue(this.translateXProperty(), -78)));
	}
	
	public double getContentHeight(){
		return head.getImage().getHeight()+body.getFitHeight();
	}

}
