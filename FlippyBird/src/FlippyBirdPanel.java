import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlippyBirdPanel extends Application {


	private int screen_height=400;
	private int screen_width=400;
	private int gap = 100;
	private int min_pipe_height = 50;
	private int max_pipe_height = 250;
	
	public static List<PipeBox> pipeList;
	
	private Timeline pipeline;
	//private Timeline check;
	private AnimationTimer  check_timer;
	
	private Stage stage;
	 
	public boolean failed = false;
	public boolean pause = true;
	
	public static void main(String[] args) {
		launch(args);
	}

   @Override
    public void start(Stage primaryStage) throws Exception {
       //set scene to stage
        primaryStage.setScene(createContent());

        //set title to stage
        primaryStage.setTitle("Flippy Bird");

        //center stage on screen
        primaryStage.centerOnScreen();

        //show the stage
        primaryStage.show();
        
        stage = primaryStage;
    }
   
   @Override
   public void stop() {
	   //check.stop();
	   check_timer.stop();
       pipeline.stop();
   }

	public Scene createContent() {
		pipeline = new Timeline();
		pipeline.setCycleCount(Timeline.INDEFINITE);
		//check = new Timeline();
		//check.setCycleCount(Timeline.INDEFINITE);
	    
	    pipeList = new ArrayList<PipeBox>();
	    
    	//final Stage stage = new Stage();
    	Image bird_png = new Image(FlippyBirdPanel.class.getResource("/assets/bird.png").toExternalForm());

    	Bird bird = new Bird(bird_png);
    	bird.setLayoutX(screen_width/2);
    	//bird.setLayoutY(screen_height/2);

    	//pipe.setLayoutX(screen_width+20);
        //create root node of scene, i.e. group
        Group rootGroup = new Group();

        //create scene with set width, height and color
        Scene scene = new Scene(rootGroup, screen_width, screen_height, Color.WHITESMOKE);

        //add some node to scene
        Text text = new Text(20, 110, "Flappy Bird");
        text.setFill(Color.DODGERBLUE);
        text.setEffect(new Lighting());
        text.setFont(Font.font(Font.getDefault().getFamily(), 50));

        //add text to the main root group
        rootGroup.getChildren().add(text);
        bird.gravity();


        rootGroup.getChildren().add(bird);
        
        pipeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1.5),
                  event -> {
                  	//Image pipe_png = new Image(FlippyBirdPanel.class.getResource("/assets/pipeBody.png").toExternalForm());
                  	
                  	//int height = new Random().nextInt((max_pipe_height - min_pipe_height) + 1) + min_pipe_height;
                  	//int residual_height = screen_height-gap-height;
                  	
//                 	Pipe pipe_bottom = new Pipe(pipe_png);
//                 	pipe_bottom.setFitHeight(height);
//                 	Pipe pipe_up = new Pipe(pipe_png);
//                 	pipe_up.setFitHeight(residual_height);
//                 	pipe_up.setRotate(180);
//                 	pipe_up.runing();
//                 	pipe_up.play();
//                 	pipe_bottom.setLayoutY(screen_height-pipe_bottom.getFitHeight());
//                 	pipe_bottom.runing();
//                 	pipe_bottom.play();
                	
                  	PipeBox[] pipes = create_pipe();  
                 	//up pipe
                 	pipes[0].setRotate(180);
                 	pipes[0].runing();
                 	pipes[0].play();
                 	pipes[0].reset();
                 	//bottom pipe
                 	pipes[1].setLayoutY(screen_height-pipes[1].getContentHeight());
                 	pipes[1].runing();
                 	pipes[1].play();
                 	pipes[1].reset();
                 	
                 	pipeList.add(pipes[0]);
                 	pipeList.add(pipes[1]);

					rootGroup.getChildren().add(pipes[0]);
					rootGroup.getChildren().add(pipes[1]);

				}));

//        check.getKeyFrames().add(
//                new KeyFrame(Duration.seconds(0.2),
//                        event -> {
//                        	ArrayList<PipeBox> toRemove = new ArrayList<PipeBox>();
//                        	//System.out.println("bird:"+bird.getLayoutX()+" "+ bird.getLayoutY());
//                        	//System.out.println("bird:"+bird.getBoundsInParent().getMaxX()+" "+ bird.getBoundsInParent().getMaxY());
//        		        	for (PipeBox temp : pipeList) {
//        		        		//System.out.println("pipe:"+temp.getBoundsInParent().getMaxX()+" "+ temp.getBoundsInParent().getMaxY());
//        		                if(check_collision(temp, bird)){
//        		                	//createAlert();
//        		                	System.out.println("collision");
//        		                	//temp.getBoundsInParent()
//        		                	//System.out.println("pipe:"+temp.getLayoutX()+" "+ temp.getLayoutY());
//        		                	failed = true;
//        		                	//pipeline.stop();
//        		                }
//        		                if(temp.getLayoutX()<0){
//        		                	toRemove.add(temp);
//        		                }
//        		            }
//                    	    pipeList.removeAll(toRemove);
//                    	    
//                            if(failed){
//                            	check.stop();
//                            	pipeline.stop();
//                            	bird.pause();
//                            	for (PipeBox temp : pipeList) {
//                            		temp.stop();
//                            	}
//                            	pipeList.clear();
//                            	createAlert();
//                            }
//      				}));
        
        check_timer = new AnimationTimer() {
            public void handle(long now) {
            	ArrayList<PipeBox> toRemove = new ArrayList<PipeBox>();
            	//System.out.println("bird:"+bird.getLayoutX()+" "+ bird.getLayoutY());
            	//System.out.println("bird:"+bird.getBoundsInParent().getMaxX()+" "+ bird.getBoundsInParent().getMaxY());
	        	for (PipeBox temp : pipeList) {
	        		//System.out.println("pipe:"+temp.getBoundsInParent().getMaxX()+" "+ temp.getBoundsInParent().getMaxY());
	                if(check_collision(temp, bird)){
	                	//createAlert();
	                	System.out.println("collision");
	                	//temp.getBoundsInParent()
	                	//System.out.println("pipe:"+temp.getLayoutX()+" "+ temp.getLayoutY());
	                	failed = true;
	                	//pipeline.stop();
	                }
	                if(temp.getLayoutX()<0){
	                	toRemove.add(temp);
	                }
	            }
        	    pipeList.removeAll(toRemove);
        	    
                if(failed){
                	check_timer.stop();
                	pipeline.stop();
                	bird.pause();
                	createAlert();
                	for (PipeBox temp : pipeList) {
                		temp.stop();
                	}
                }
            }
        };
        
        bird.play();
        bird.reset();
        bird.pause();
        pipeline.pause();
        //check.pause();
        check_timer.stop();
        
        scene.setOnKeyPressed((KeyEvent ke) -> {
        	if(ke.getCode()==KeyCode.ENTER) {
        		if(pause){
        			//check.play();
        			check_timer.start();
                	pipeline.play();
                	bird.play();
                	bird.reset();
                	failed = false;
                	pause = false;
        		}
        		if(failed){
        			for (PipeBox temp : pipeList) {
                		rootGroup.getChildren().remove(temp);
                	}
                	pipeList.clear();
                	//check.play();
                	check_timer.start();
                	pipeline.play();
                	bird.play();
                	bird.reset();
                	failed = false;
                }

                
            }
        	if(ke.getCode()==KeyCode.SPACE) {
                bird.jump();
            }
        });
        
    	return scene;
    }
	
	
	
	
	public PipeBox[] create_pipe(){
		PipeBox[] pipes = new PipeBox[2];
		Image pipe_head = new Image(FlippyBirdPanel.class.getResource("/assets/pipeHead.png").toExternalForm());
      	Image pipe_body = new Image(FlippyBirdPanel.class.getResource("/assets/pipeBody.png").toExternalForm());
      	
      	int height = new Random().nextInt((max_pipe_height - min_pipe_height) + 1) + min_pipe_height;
      	int residual_height = screen_height-gap-height;
      	
      	pipes[0] = new PipeBox(pipe_head, pipe_body, height);
      	pipes[1] = new PipeBox(pipe_head, pipe_body, residual_height);
      	
      	return pipes;
	}
	
	protected Alert createAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText("You Lose!!!!!");
        alert.show();
        return alert;
    }
	
	public boolean check_collision(PipeBox temp, Bird bird){
		
		//temp.getBoundsInParent().intersects(bird.getBoundsInParent().getMinX(), bird.getBoundsInParent().getMinY(), bird.getBoundsInParent().getWidth(), bird.getBoundsInParent().getHeight())
		if(temp.getBoundsInParent().intersects(bird.getBoundsInParent())){
			return true;
		}
		
//		if(temp.getBoundsInParent().contains(new Point2D(bird.getBoundsInParent().getMaxX(),bird.getBoundsInParent().getMaxY()))
//				||temp.getBoundsInParent().contains(new Point2D(bird.getBoundsInParent().getMinX(),bird.getBoundsInParent().getMinY()))
//				||temp.getBoundsInParent().contains(new Point2D(bird.getBoundsInParent().getMinX()+bird.getBoundsInParent().getWidth(),bird.getBoundsInParent().getMinY()))
//				||temp.getBoundsInParent().contains(new Point2D(bird.getBoundsInParent().getMinX(),bird.getBoundsInParent().getMinY()+bird.getBoundsInParent().getHeight()))){
//			return true;
//		}
		return false;
	}

	 
}
