import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BFSscreen extends BorderPane {
	final static State[] POSSIBLE_ACTIONS = { new State(0, 1, 1), new State(1, 0, 1), new State(2, 0, 1),
			new State(0, 2, 1), new State(1, 1, 1) };
	static LinkedList<State> visited = new LinkedList<>();

	Button nextStepBtn = new Button("Next Step");
	Button prevStepBtn = new Button("Previous Step");
	Button runBtn = new Button("Run BFS");

	Label stepL = new Label("Step i of n");
	Label runTimeL = new Label("Executed in k ms");
	int direction = 1;
	int i = 1;
	static int steps = 0;

	static LinkedList<State> solutionSteps = new LinkedList<>();
	Pane p = new Pane();
	ImageView entityView = new ImageView();
	static boolean animationCompleted = true;
	public BFSscreen(Stage primaryStage, Scene scene) {

		Image missionaryImg = new Image("missionary-clear.png");
		ImageView missonary1View = new ImageView(missionaryImg);
		ImageView missonary2View = new ImageView(missionaryImg);
		ImageView missonary3View = new ImageView(missionaryImg);
		missonary1View.setFitHeight(150);
		missonary1View.setFitWidth(75);
		missonary2View.setFitHeight(150);
		missonary2View.setFitWidth(75);
		missonary3View.setFitHeight(150);
		missonary3View.setFitWidth(75);

		TranslateTransition translateM1 = new TranslateTransition();
		TranslateTransition translateM2 = new TranslateTransition();
		TranslateTransition translateM3 = new TranslateTransition();
		translateM1.setNode(missonary1View);
		translateM2.setNode(missonary2View);
		translateM3.setNode(missonary3View);

		Image cannibalImg = new Image("cannibal-clear.png");
		ImageView cannibal1View = new ImageView(cannibalImg);
		ImageView cannibal2View = new ImageView(cannibalImg);
		ImageView cannibal3View = new ImageView(cannibalImg);
		cannibal1View.setFitHeight(150);
		cannibal1View.setFitWidth(75);
		cannibal2View.setFitHeight(150);
		cannibal2View.setFitWidth(75);
		cannibal3View.setFitHeight(150);
		cannibal3View.setFitWidth(75);

		TranslateTransition translateC1 = new TranslateTransition();
		TranslateTransition translateC2 = new TranslateTransition();
		TranslateTransition translateC3 = new TranslateTransition();
		translateC1.setNode(cannibal1View);
		translateC2.setNode(cannibal2View);
		translateC3.setNode(cannibal3View);

		Image boatImg = new Image("boat-clear.png");
		ImageView boatView = new ImageView(boatImg);
		boatView.setFitHeight(250);
		boatView.setFitWidth(250);

		setStyle("-fx-background-image: url('background-clear1.png');-fx-background-position: center;");

		p.getChildren().addAll(missonary1View, missonary2View, missonary3View, cannibal1View, cannibal2View,
				cannibal3View, boatView);
		missonary1View.setX(450);
		missonary1View.setY(325);

		missonary2View.setX(400);
		missonary2View.setY(325);

		missonary3View.setX(350);
		missonary3View.setY(325);
		
		cannibal1View.setX(200);
		cannibal1View.setY(325);

		cannibal2View.setX(150);
		cannibal2View.setY(325);

		cannibal3View.setX(100);
		cannibal3View.setY(325);

		boatView.setX(500);
		boatView.setY(410);

		setCenter(p);
		nextStepBtn.setOnAction(e -> {
			if (visited.isEmpty() || !animationCompleted)
				return;
			if (i < steps) {
				i++;
				animateNextStep(solutionSteps.get(i - 1), solutionSteps.get(i), boatView);
				stepL.setText("Step: " + i + " of " + steps);
			}

		});

		prevStepBtn.setOnAction(e -> {
			if (visited.isEmpty() || !animationCompleted)
				return;
			if (i > 0 && i <= steps) {
				animateNextStep(solutionSteps.get(i), solutionSteps.get(i - 1), boatView);
				i--;
				stepL.setText("Step: " + i + " of " + steps);
			}
		});

		runBtn.setOnAction(e -> {
			if(!animationCompleted) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("Please wait for the current step to complete, then attempt to re run the search");
				alert.show();
				return;
			}
			i = 0;
			double before = System.currentTimeMillis();
			bfs(new State(3, 3, 1));
			double result = System.currentTimeMillis() - before;
			runTimeL.setText("Executed BFS in " + result + " ms");
			findSolutionSteps();
			stepL.setText("Step " + i + " of " + steps);
			missonary1View.setX(450);
			missonary1View.setY(325);

			missonary2View.setX(400);
			missonary2View.setY(325);

			missonary3View.setX(350);
			missonary3View.setY(325);
			
			cannibal1View.setX(200);
			cannibal1View.setY(325);

			cannibal2View.setX(150);
			cannibal2View.setY(325);

			cannibal3View.setX(100);
			cannibal3View.setY(325);

			boatView.setX(500);
			boatView.setY(410);
		});
		HBox controlsHB = new HBox(prevStepBtn, runBtn, nextStepBtn);
		controlsHB.setAlignment(Pos.CENTER);
		controlsHB.setSpacing(15);
		setBottom(controlsHB);
		VBox detailsVB = new VBox(runTimeL, stepL);
		detailsVB.setSpacing(15);
		detailsVB.setAlignment(Pos.CENTER);
		setTop(detailsVB);
		setAlignment(stepL, Pos.CENTER);
	}

	public void animateNextStep(State currentState, State nextState, ImageView boat) {
		animationCompleted = false;
		State action = currentState.subtract(nextState);
		if (currentState.getBoat() == 0) {
			action = nextState.subtract(currentState);
			action.setBoat(0);
		}

		// left to right ==============================
		if (isEqual(action, new State(0, 1, 1))) {
			ImageView closestCannibalView = getClosestCannibalLeft();
			KeyFrame moveToBoat = new KeyFrame(Duration.millis(700), new KeyValue(closestCannibalView.xProperty(), 580),
					new KeyValue(closestCannibalView.yProperty(), 390));
			KeyFrame moveBoatAndCannibal = new KeyFrame(Duration.seconds(4),
					new KeyValue(closestCannibalView.xProperty(), 870),
					new KeyValue(closestCannibalView.yProperty(), 390), new KeyValue(boat.xProperty(), 840),
					new KeyValue(boat.yProperty(), 410));
			ImageView getPosition = getClosestCannibalRight();
			double x = 1300;
			if(getPosition!=null) {
				x = getPosition.getX() - 50;
			}
			
			KeyFrame getToShore = new KeyFrame(Duration.seconds(4.5),
					new KeyValue(closestCannibalView.xProperty(), x),
					new KeyValue(closestCannibalView.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveBoatAndCannibal, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(1, 0, 1))) {
			ImageView closestMissionaryView = getClosestMissionaryLeft();
			KeyFrame moveToBoat = new KeyFrame(Duration.millis(700),
					new KeyValue(closestMissionaryView.xProperty(), 660),
					new KeyValue(closestMissionaryView.yProperty(), 390));
			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(4),
					new KeyValue(closestMissionaryView.xProperty(), 870),
					new KeyValue(closestMissionaryView.yProperty(), 390), new KeyValue(boat.xProperty(), 840),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestMissionaryRight();
			double x = 1500;
			if(getPosition!=null) {
				x = getPosition.getX() - 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(4.5),
					new KeyValue(closestMissionaryView.xProperty(), x),
					new KeyValue(closestMissionaryView.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(2, 0, 1))) {
			ImageView closestMissionaryView = getClosestMissionaryLeft();
			p.getChildren().remove(closestMissionaryView);
			ImageView closestMissionaryView2 = getClosestMissionaryLeft();
			p.getChildren().add(closestMissionaryView);
			closestMissionaryView.toBack();

			KeyFrame moveToBoat = new KeyFrame(Duration.millis(650),
					new KeyValue(closestMissionaryView.xProperty(), 710),
					new KeyValue(closestMissionaryView.yProperty(), 390));

			KeyFrame moveToBoat2 = new KeyFrame(Duration.millis(700),
					new KeyValue(closestMissionaryView2.xProperty(), 660),
					new KeyValue(closestMissionaryView2.yProperty(), 390));

			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(2.7),
					new KeyValue(closestMissionaryView.xProperty(), 880),
					new KeyValue(closestMissionaryView.yProperty(), 390),
					new KeyValue(closestMissionaryView2.xProperty(), 840),
					new KeyValue(closestMissionaryView2.yProperty(), 390), new KeyValue(boat.xProperty(), 860),
					new KeyValue(boat.yProperty(), 410));
			ImageView getPosition = getClosestMissionaryRight();
			double x = 1500;
			if(getPosition!=null) {
				x = getPosition.getX() - 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(5),
					new KeyValue(closestMissionaryView.xProperty(), x),
					new KeyValue(closestMissionaryView.yProperty(), 325),
					new KeyValue(closestMissionaryView2.xProperty(), x - 50),
					new KeyValue(closestMissionaryView2.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveToBoat2, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(0, 2, 1))) {
			ImageView closestCannibalView = getClosestCannibalLeft();
			p.getChildren().remove(closestCannibalView);
			ImageView closestCannibalView2 = getClosestCannibalLeft();
			p.getChildren().add(closestCannibalView);
			closestCannibalView.toBack();

			KeyFrame moveToBoat = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestCannibalView.xProperty(), 690),
					new KeyValue(closestCannibalView.yProperty(), 390));

			KeyFrame moveToBoat2 = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestCannibalView2.xProperty(), 660),
					new KeyValue(closestCannibalView2.yProperty(), 390));

			KeyFrame moveBoatAndCannibal = new KeyFrame(Duration.seconds(3),
					new KeyValue(closestCannibalView.xProperty(), 870),
					new KeyValue(closestCannibalView.yProperty(), 390),
					new KeyValue(closestCannibalView2.xProperty(), 840),
					new KeyValue(closestCannibalView2.yProperty(), 390), new KeyValue(boat.xProperty(), 850),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestCannibalRight();
			double x = 1300;
			if(getPosition!=null) {
				x = getPosition.getX() - 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(5),
					new KeyValue(closestCannibalView.xProperty(), x),
					new KeyValue(closestCannibalView.yProperty(), 325),
					new KeyValue(closestCannibalView2.xProperty(), x - 50),
					new KeyValue(closestCannibalView2.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveToBoat2, moveBoatAndCannibal, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(1, 1, 1))) {
			ImageView closestCannibalView = getClosestCannibalLeft();
			ImageView closestMissionaryView = getClosestMissionaryLeft();

			KeyFrame moveToBoat = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestCannibalView.xProperty(), 650),
					new KeyValue(closestCannibalView.yProperty(), 390));

			KeyFrame moveToBoat2 = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestMissionaryView.xProperty(), 700),
					new KeyValue(closestMissionaryView.yProperty(), 390));

			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(3),
					new KeyValue(closestCannibalView.xProperty(), 820),
					new KeyValue(closestCannibalView.yProperty(), 390),
					new KeyValue(closestMissionaryView.xProperty(), 870),
					new KeyValue(closestMissionaryView.yProperty(), 390), new KeyValue(boat.xProperty(), 850),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestMissionaryRight();
			double x1 = 1500;
			if(getPosition!=null) {
				x1 = getPosition.getX() - 30;
			}
			
			ImageView getPosition2 = getClosestCannibalRight();
			double x2 = 1300;
			if(getPosition2!=null) {
				x2 = getPosition2.getX() - 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(5),
					new KeyValue(closestCannibalView.xProperty(), x2),
					new KeyValue(closestCannibalView.yProperty(), 325),
					new KeyValue(closestMissionaryView.xProperty(), x1),
					new KeyValue(closestMissionaryView.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveToBoat2, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		}

		// right to left ===============================
		else if (isEqual(action, new State(0, 1, 0))) {
			ImageView closestCannibalView = getClosestCannibalRight();
			KeyFrame moveToBoat = new KeyFrame(Duration.millis(700), new KeyValue(closestCannibalView.xProperty(), 850),
					new KeyValue(closestCannibalView.yProperty(), 390));
			KeyFrame moveBoatAndCannibal = new KeyFrame(Duration.seconds(4),
					new KeyValue(closestCannibalView.xProperty(), 500),
					new KeyValue(closestCannibalView.yProperty(), 390), new KeyValue(boat.xProperty(), 500),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestCannibalLeft();
			double x = 50;
			if(getPosition!=null) {
				x = getPosition.getX() + 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(4.5),
					new KeyValue(closestCannibalView.xProperty(), x),
					new KeyValue(closestCannibalView.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveBoatAndCannibal, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(1, 0, 0))) {

			ImageView closestMissionaryView = getClosestMissionaryRight();
			KeyFrame moveToBoat = new KeyFrame(Duration.millis(700),
					new KeyValue(closestMissionaryView.xProperty(), 850),
					new KeyValue(closestMissionaryView.yProperty(), 390));
			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(4),
					new KeyValue(closestMissionaryView.xProperty(), 500),
					new KeyValue(closestMissionaryView.yProperty(), 390), new KeyValue(boat.xProperty(), 500),
					new KeyValue(boat.yProperty(), 410));
			ImageView getPosition = getClosestMissionaryLeft();
			double x = 300;
			if(getPosition!=null) {
				x = getPosition.getX() + 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(4.5),
					new KeyValue(closestMissionaryView.xProperty(), x),
					new KeyValue(closestMissionaryView.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});

		} else if (isEqual(action, new State(2, 0, 0))) {
			ImageView closestMissionaryView = getClosestMissionaryRight();
			p.getChildren().remove(closestMissionaryView);
			ImageView closestMissionaryView2 = getClosestMissionaryRight();
			p.getChildren().add(closestMissionaryView);
			closestMissionaryView.toBack();

			KeyFrame moveToBoat = new KeyFrame(Duration.millis(700),
					new KeyValue(closestMissionaryView.xProperty(), 890),
					new KeyValue(closestMissionaryView.yProperty(), 390));

			KeyFrame moveToBoat2 = new KeyFrame(Duration.millis(700),
					new KeyValue(closestMissionaryView2.xProperty(), 855),
					new KeyValue(closestMissionaryView2.yProperty(), 390));

			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(4),
					new KeyValue(closestMissionaryView.xProperty(), 530),
					new KeyValue(closestMissionaryView.yProperty(), 390),
					new KeyValue(closestMissionaryView2.xProperty(), 500),
					new KeyValue(closestMissionaryView2.yProperty(), 390), new KeyValue(boat.xProperty(), 500),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestMissionaryLeft();
			double x = 300;
			if(getPosition!=null) {
				x = getPosition.getX() + 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(5),
					new KeyValue(closestMissionaryView.xProperty(), x),
					new KeyValue(closestMissionaryView.yProperty(), 325),
					new KeyValue(closestMissionaryView2.xProperty(),
							x + 50),
					new KeyValue(closestMissionaryView2.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveToBoat2, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(0, 2, 0))) {
			ImageView closestCannibalView = getClosestCannibalRight();
			p.getChildren().remove(closestCannibalView);
			ImageView closestCannibalView2 = getClosestCannibalRight();
			p.getChildren().add(closestCannibalView);
			closestCannibalView.toBack();

			KeyFrame moveToBoat = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestCannibalView.xProperty(), 870),
					new KeyValue(closestCannibalView.yProperty(), 390));

			KeyFrame moveToBoat2 = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestCannibalView2.xProperty(), 840),
					new KeyValue(closestCannibalView2.yProperty(), 390));

			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(3),
					new KeyValue(closestCannibalView.xProperty(), 560),
					new KeyValue(closestCannibalView.yProperty(), 390),
					new KeyValue(closestCannibalView2.xProperty(), 530),
					new KeyValue(closestCannibalView2.yProperty(), 390), new KeyValue(boat.xProperty(), 500),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestCannibalLeft();
			double x = 50;
			if(getPosition!=null) {
				x = getPosition.getX() + 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(5),
					new KeyValue(closestCannibalView.xProperty(), x),
					new KeyValue(closestCannibalView.yProperty(), 325),
					new KeyValue(closestCannibalView2.xProperty(), x + 50),
					new KeyValue(closestCannibalView2.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveToBoat2, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		} else if (isEqual(action, new State(1, 1, 0))) {
			ImageView closestCannibalView = getClosestCannibalRight();
			ImageView closestMissionaryView = getClosestMissionaryRight();

			KeyFrame moveToBoat = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestCannibalView.xProperty(), 850),
					new KeyValue(closestCannibalView.yProperty(), 390));

			KeyFrame moveToBoat2 = new KeyFrame(Duration.seconds(0.5),
					new KeyValue(closestMissionaryView.xProperty(), 900),
					new KeyValue(closestMissionaryView.yProperty(), 390));

			KeyFrame moveBoatAndMissionary = new KeyFrame(Duration.seconds(3),
					new KeyValue(closestCannibalView.xProperty(), 500),
					new KeyValue(closestCannibalView.yProperty(), 390),
					new KeyValue(closestMissionaryView.xProperty(), 550),
					new KeyValue(closestMissionaryView.yProperty(), 390), new KeyValue(boat.xProperty(), 500),
					new KeyValue(boat.yProperty(), 410));
			
			ImageView getPosition = getClosestCannibalLeft();
			double x = 50;
			if(getPosition!=null) {
				x = getPosition.getX() + 50;
			}
			ImageView getPosition2 = getClosestMissionaryLeft();
			double x2 = 300;
			if(getPosition2!=null) {
				x2 = getPosition2.getX() + 50;
			}
			KeyFrame getToShore = new KeyFrame(Duration.seconds(5),
					new KeyValue(closestCannibalView.xProperty(), x),
					new KeyValue(closestCannibalView.yProperty(), 325),
					new KeyValue(closestMissionaryView.xProperty(), x2),
					new KeyValue(closestMissionaryView.yProperty(), 325));
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().addAll(moveToBoat, moveToBoat2, moveBoatAndMissionary, getToShore);
			timeline.play();
			timeline.setOnFinished(e1->{
				animationCompleted = true;
			});
		}

	}

	public ImageView getClosestCannibalLeft() {
		ImageView closestCannibalView = null;
		double max = Double.MIN_VALUE;
		for (Node n : p.getChildren()) {
			if (n instanceof ImageView) {
				ImageView imgView = (ImageView) n;
				if (imgView.getImage().getUrl().indexOf("cannibal-clear") >= 0 && imgView.getX() < 300
						&& imgView.getX() > max) {
					max = imgView.getX();
					closestCannibalView = imgView;
				}
			}
		}
		return closestCannibalView;
	}

	public ImageView getClosestMissionaryLeft() {
		ImageView closestMissionaryView = null;
		double max = Double.MIN_VALUE;
		for (Node n : p.getChildren()) {
			if (n instanceof ImageView) {
				ImageView imgView = (ImageView) n;
				if (imgView.getImage().getUrl().indexOf("missionary") >= 0 && imgView.getX() < 500
						&& imgView.getX() > max) {
					max = imgView.getX();
					closestMissionaryView = imgView;
				}
			}
		}
		return closestMissionaryView;
	}

	public ImageView getTwoClosestMissionaryLeft() {
		ImageView closestMissionaryView = null;
		double max = Double.MIN_VALUE;
		for (Node n : p.getChildren()) {
			if (n instanceof ImageView) {
				ImageView imgView = (ImageView) n;
				if (imgView.getImage().getUrl().indexOf("missionary") >= 0 && imgView.getX() < 500
						&& imgView.getX() > max) {
					max = imgView.getX();
					closestMissionaryView = imgView;
				}
			}
		}
		return closestMissionaryView;
	}

	public ImageView getClosestCannibalRight() {
		ImageView closestCannibalView = null;
		double min = Double.MAX_VALUE;
		for (Node n : p.getChildren()) {
			if (n instanceof ImageView) {
				ImageView imgView = (ImageView) n;
				if (imgView.getImage().getUrl().indexOf("cannibal") >= 0 && imgView.getX() > 800
						&& imgView.getX() < min) {
					min = imgView.getX();
					closestCannibalView = imgView;
				}
			}
		}
		return closestCannibalView;
	}

	public ImageView getClosestMissionaryRight() {
		ImageView closestMissionaryView = null;
		double min = Double.MAX_VALUE;
		for (Node n : p.getChildren()) {
			if (n instanceof ImageView) {
				ImageView imgView = (ImageView) n;
				if (imgView.getImage().getUrl().indexOf("missionary") >= 0 && imgView.getX() > 800
						&& imgView.getX() < min) {
					min = imgView.getX();
					closestMissionaryView = imgView;
				}
			}
		}
		return closestMissionaryView;
	}

	public void findSolutionSteps() {
		State state = solutionSteps.get(0);
		while (state.getParent() != null) {
			solutionSteps.addFirst(state.getParent());
			state = state.getParent();
		}
		System.out.println("Solution states: " + solutionSteps);
	}

	public static boolean bfs(State initialState) {
		solutionSteps = new LinkedList<>();
		visited = new LinkedList<>();
		Queue<State> q = new LinkedList<>();
		q.add(initialState);
		System.out.println(initialState);
		visited.add(initialState);
		steps = 0;
		while (!q.isEmpty()) {
			State s = q.poll();
			if (isGoal(s)) {
				System.out.println("We reached the solution using BFS!, " + s + " in " + steps + " steps!");
				solutionSteps.add(s);
				return true;
			}
			LinkedList<State> possibleStates = getNextStates(s);
			int size = q.size();
			for (State e : possibleStates) {
				if (!isVisited(e) && isValid(e)) {
					System.out.print(e + " ");
					q.add(e);
				}
			}
			if (size != q.size()) {
				steps++;
				System.out.println();
			}
		}
		return false;
	}

	public static boolean isVisited(State s) {
		for (State e : visited) {
			if (e.getCannibals() == s.getCannibals() && e.getBoat() == s.getBoat()
					&& e.getMissionaries() == s.getMissionaries())
				return true;
		}
		visited.add(s);
		return false;
	}

	public static LinkedList<State> getNextStates(State state) {
		LinkedList<State> possibleStates = new LinkedList<>();
		for (int i = 0; i < POSSIBLE_ACTIONS.length; i++) {
			if (state.getBoat() == 0) {
				State next = state.add(POSSIBLE_ACTIONS[i]);
				if (next != null) {
					if (isValid(next))
						possibleStates.add(next);
					next.setParent(state);
				}
			} else if (state.getBoat() == 1) {
				State next = state.subtract(POSSIBLE_ACTIONS[i]);
				if (next != null) {
					if (isValid(next)) {
						possibleStates.add(next);
						next.setParent(state);
					}
				}
			}
		}

		return possibleStates;

	}

	public static boolean isGoal(State state) {
		if (state == null)
			return false;
		if (state.getCannibals() == 0 && state.getBoat() == 0 && state.getMissionaries() == 0)
			return true;
		return false;
	}

	public static boolean isValid(State state) {
		if (state == null)
			return false;

		int missionariesLeft = state.getMissionaries();
		int cannibalsLeft = state.getCannibals();
		int missionariesRight = 3 - missionariesLeft;
		int cannibalsRight = 3 - cannibalsLeft;

		if ((missionariesLeft > 0 && missionariesLeft < cannibalsLeft)
				|| (missionariesRight > 0 && missionariesRight < cannibalsRight)) {
			return false;
		}

		return true;
	}

	public boolean isEqual(State s1, State s2) {
		return s1.getBoat() == s2.getBoat() && s1.getCannibals() == s2.getCannibals()
				&& s1.getMissionaries() == s2.getMissionaries();
	}
}
