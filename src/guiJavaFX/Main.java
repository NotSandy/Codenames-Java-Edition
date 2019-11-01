package guiJavaFX;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import code.Board;
import code.Location;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {
	
	/**
	 * Stage which shows the window that the game will be run on
	 */
	private Stage window;
	
	/**
	 * mainMenuScene - The scene that's used in the Main Menu
	 * blankScene - The scene that's used during transitions
	 * gameScene - The scene that's used when in a game
	 */
	private Scene mainMenuScene, blankScene, gameScene;
	
	/**
	 * mainMenuScreen - The root container of the Main Menu
	 * layout - The root container of the scene when in a game
	 */
	private BorderPane mainMenuScreen, layout;
	
	/**
	 * mainContainer - the main container when in a game
	 * boardContainer - container that hold the board/grid
	 */
	private VBox mainContainer, boardContainer;
	
	/**
	 * clueContainer - container that holds the clueAndCount label
	 * userInputContainer - container that holds the JTextField and submit button during Spymaster turn
	 * controlContainer - the container that holds the userInputContainer
	 */
	private HBox clueContainer, userInputContainer, controlContainer;
	
	/**
	 * GridPane that holds the all the buttons and labels for the location instances
	 */
	private GridPane grid;
	
	/**
	 * The current board instance used in the game
	 */
	private Board board;
	
	/**
	 * MenuBar used for the top menu
	 */
	private MenuBar menu;
	
	/**
	 * newGame - New Game menu item, when clicked starts a new game
	 * exit - Exit menu item, when clicked closes the window
	 */
	private MenuItem newGame, newThreeGame, exit;
	
	/**
	 * Label that shows the current clue and count
	 */
	private Label clueAndCount;
	
	// Integer that indicates a 3 person game
	private int threeTeamMode;
	
	/**
	 * 2D-Array of location instances that's currently being used in the game
	 */
	private Location[][] locations;
	
	/**
	 * giveup - Button that ends the current team's turn
	 * codename - Button that's used for all the locations on the GridPane
	 * startGame - Button that starts a new game
	 * rulesButton - Button that shows the rules to the game
	 * githubButton - Button that brings you to the github page of this game
	 * exitButton - Button that exits the window
	 * easterEggAutoWin - Button that lets you autowin the game
	 * easterEggHertzBG - Button that shows Hertz ;)
	 */
	private Button giveup, codename, startGame, rulesButton, githubButton, exitButton, threeTeamButton, easterEggAutoWin, easterEggHertzBG;
	
	/**
	 * This is main method of all the code. All the "magic" starts here
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * This method initializes the stage (window) of the program by adding necessary components to the stage when the game starts
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Codenames by TeamName");
		
		//initializes threeTeamMode to 0, indicating non-threeTeamMode
		threeTeamMode = 0;
		
		//Start Of Containers
		mainContainer = new VBox(10);
		mainContainer.setAlignment(Pos.CENTER);
		boardContainer = new VBox();
		clueContainer = new HBox();
		clueContainer.setMinHeight(75);
		clueContainer.setAlignment(Pos.CENTER);
		clueContainer.getStyleClass().add("cluecontainer");
		controlContainer = new HBox();
		controlContainer.setMinHeight(75);
		controlContainer.setAlignment(Pos.CENTER);
		controlContainer.getStyleClass().add("controlcontainer");
		mainContainer.getChildren().addAll(boardContainer, clueContainer, controlContainer);
		//End Of Containers
		
		//Start Of Menu Implementation
		menu = new MenuBar();
		menu.getStyleClass().add("menubar");
		Menu file = new Menu("File");
		newGame = new MenuItem("New 2-Team Game");
		newThreeGame = new MenuItem("New 3-Team Game");
		exit = new MenuItem("Exit");
		newGame.setOnAction(this::handle);
		newThreeGame.setOnAction(this::handle);
		exit.setOnAction(this::handle);
		file.getItems().addAll(newGame, newThreeGame, exit);
		menu.getMenus().addAll(file);
		//End Of Menu Implementation
		
		//Start Of Main Menu Scene Buttons
		startGame = new Button("Start 2-Team Game");
		startGame.setMinSize(340, 100);
		startGame.setOnAction(this::handle);
		startGame.getStyleClass().add("menubutton");
		rulesButton = new Button("Rules");
		rulesButton.setMinSize(340, 100);
		rulesButton.setOnAction(this::handle);
		rulesButton.getStyleClass().add("menubutton");
		githubButton = new Button("GitHub");
		githubButton.setMinSize(340, 100);
		githubButton.setOnAction(this::handle);
		githubButton.getStyleClass().add("menubutton");
		exitButton = new Button("Exit Game");
		exitButton.setMinSize(340, 100);
		exitButton.setOnAction(this::handle);
		exitButton.getStyleClass().add("menubutton");
		threeTeamButton = new Button("Start 3-Team Game");
		threeTeamButton.setMinSize(340, 100);
		threeTeamButton.setOnAction(this::handle);
		threeTeamButton.getStyleClass().add("menubutton");
		//End Of Main Menu Scene Buttons
		
		//Start Of Main Menu Scene Components
		VBox startGameContainer = new VBox(15);
		startGameContainer.getChildren().addAll(startGame, threeTeamButton, rulesButton, githubButton, exitButton);
		startGameContainer.setAlignment(Pos.BOTTOM_CENTER);
		VBox.setMargin(exitButton, new Insets(0, 0, 75, 0));
		mainMenuScreen = new BorderPane();
		mainMenuScreen.setTop(menu);
		mainMenuScreen.setCenter(startGameContainer);
		mainMenuScreen.getStyleClass().add("mainmenubackground");
		mainMenuScene = new Scene(mainMenuScreen, 1080, 950);
		mainMenuScene.getStylesheets().add(this.getClass().getResource("stylesheet.css").toURI().toString());
		//End Of Main Menu Scene Components
		
		//Start Of Music Player
		Media media = new Media(this.getClass().getResource("bg-music.mp3").toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		player.play();
		player.setCycleCount(Integer.MAX_VALUE);
		player.setVolume(0.5);
		//End Of Music Player
		
		//Start Of Stage (window) Components
		window.setScene(mainMenuScene);
		window.getIcons().add(new Image(this.getClass().getResource("Codename.png").toURI().toString()));
		window.show();
		//End Of Stage (window) Components
	}
	
	/**
	 * This method creates a new board with codenames from the GameWords.txt file. It then calls the startNewGame() method
	 * from board to initialize randomly generated Codenames and Person and sets it to the Red Team's turn. It then calls the spyMaster()
	 * method from Main.
	 */
	public void newBoard() {
		layout = new BorderPane();
		layout.setTop(menu);
		layout.setCenter(mainContainer);
		gameScene = new Scene(layout, 1080, 950);
		layout.getStyleClass().add("gamebackground");
		try {
			gameScene.getStylesheets().add(this.getClass().getResource("stylesheet.css").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		window.setScene(gameScene);
		
		board = new Board("/GameWords.txt");
		board.startNewGame();
		
		//This Scene Is Included For Visual Purpose Only
		BorderPane blankScreen = new BorderPane();
		blankScreen.setTop(menu);
		blankScreen.getStyleClass().add("gamebackground");
		blankScene = new Scene(blankScreen, 1080, 950);
		try {
			blankScene.getStylesheets().add(this.getClass().getResource("stylesheet.css").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//This Scene Is Included For Visual Purpose Only
		
		window.setScene(blankScene);
		spyMaster();
		window.setScene(gameScene);
	}
	
	/**
	 * This method creates a new board with codenames from the GameWords.txt file. It then calls the threeTeamStartNewGame() method
	 * from board to initialize randomly generated Codenames and Person and sets it to the Red Team's turn. It then calls the threeTeamSpyMaster()
	 * method from Main.
	 */
	public void threeTeamBoard() {
		layout = new BorderPane();
		layout.setTop(menu);
		layout.setCenter(mainContainer);
		gameScene = new Scene(layout, 1080, 950);
		layout.getStyleClass().add("gamebackground");
		try {
			gameScene.getStylesheets().add(this.getClass().getResource("stylesheet.css").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		window.setScene(gameScene);
		
		board = new Board("/GameWords.txt");
		board.threeTeamStartNewGame();
		
		//This Scene Is Included For Visual Purpose Only
		BorderPane blankScreen = new BorderPane();
		blankScreen.setTop(menu);
		blankScreen.getStyleClass().add("gamebackground");
		blankScene = new Scene(blankScreen, 1080, 950);
		try {
			blankScene.getStylesheets().add(this.getClass().getResource("stylesheet.css").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//This Scene Is Included For Visual Purpose Only
		
		window.setScene(blankScene);
		threeTeamSpyMaster();
		window.setScene(gameScene);
	}
	
	/**
	 * This method removes the {@code grid} from the boardContainer and updates it using a nested loop to set appropriate
	 * label text on the {@code codenames} buttons created for each location instance.
	 */
	public void refreshBoard() {
		layout.setTop(menu);
		boardContainer.getChildren().remove(easterEggAutoWin);
		easterEggAutoWin = new Button();
		easterEggAutoWin.getStyleClass().add("autowinbutton");
		easterEggAutoWin.setOnAction(this::handle);
		boardContainer.getChildren().add(easterEggAutoWin);
		easterEggHertzBG = new Button();
		easterEggHertzBG.setOnAction(this::handle);
		easterEggHertzBG.getStyleClass().add("hertzbutton");
		easterEggHertzBG.setPrefSize(1, 1);
		layout.setBottom(easterEggHertzBG);
		
		boardContainer.getChildren().remove(grid);
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		locations = board.getLocations();
		for(int i=0; i<locations.length; i++) {
			for(int j=0; j<locations[0].length; j++) {
				if(locations[i][j].isRevealed()) {
					Label role = new Label();
					role.setText("Role: " + locations[i][j].getPerson().getRole());
					role.setAlignment(Pos.CENTER);
					role.setMinSize(125, 125);
					role.getStyleClass().add("labeltile-revealed");
					grid.add(role, i, j);
				}
				else {
					codename = new Button();
					codename.setUserData(locations[i][j]);
					codename.setText(locations[i][j].getCodename());
					codename.setAlignment(Pos.CENTER);
					codename.setMinSize(125, 125);
					codename.setOnAction(this::handle);
					codename.getStyleClass().add("buttontile");
					grid.add(codename, i, j);
				}
			}
		}
		boardContainer.getChildren().add(grid);
		grid.setAlignment(Pos.CENTER);
		clueAndCount.setText("Clue: " + board.getClue() + "\nCount: " + board.getCount());
	}
	
	
	/**
	 * This method checks which team's turn it is and depending on that it displays a message dialog indicating who's turn it is.
	 * This method also removes the {@code grid} from the boardContainer and updates it using a nested loop to set appropriate
	 * label text on the Labels created for each location instance. This method also creates two JTextfields for the SpyMaster to input
	 * the clue and count. Then a submit button is created in order to handle if clue and count are legal.
	 */
	public void spyMaster() {
		controlContainer.getChildren().removeAll(giveup, userInputContainer);
		clueContainer.getChildren().remove(clueAndCount);
		if(board.isRedTurn()) {
			MessageDialog.display("Status", "It is now the Red Team's turn!\nPlease hand the device to the Red Team spymaster!");
		} else {
			MessageDialog.display("Status", "It is now the Blue Team's turn!\nPlease hand the device to the Blue Team spymaster!");
		}
		
		layout.setTop(menu);
		boardContainer.getChildren().remove(easterEggAutoWin);
		easterEggAutoWin = new Button();
		easterEggAutoWin.getStyleClass().add("autowinbutton");
		easterEggAutoWin.setOnAction(this::handle);
		boardContainer.getChildren().add(easterEggAutoWin);
		easterEggHertzBG = new Button();
		easterEggHertzBG.setOnAction(this::handle);
		easterEggHertzBG.getStyleClass().add("hertzbutton");
		easterEggHertzBG.setPrefSize(1, 1);
		layout.setBottom(easterEggHertzBG);
		
		boardContainer.getChildren().remove(grid);
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		locations = board.getLocations();
		for(int i=0; i<locations.length; i++) {
			for(int j=0; j<locations[0].length; j++) {
				if(locations[i][j].isRevealed()) {
					Label role = new Label();
					role.setText("Role: " + locations[i][j].getPerson().getRole());
					role.setAlignment(Pos.CENTER);
					role.setMinSize(125, 125);
					role.getStyleClass().add("labeltile-revealed");
					grid.add(role, i, j);
				}
				else {
					Label role = new Label();
					role.setText(locations[i][j].getCodename() + "\nRole: " + locations[i][j].getPerson().getRole());
					role.setAlignment(Pos.CENTER);
					role.setMinSize(125, 125);
					role.getStyleClass().add("labeltile");
					grid.add(role, i, j);
				}
			}
		}
		boardContainer.getChildren().add(grid);
		grid.setAlignment(Pos.CENTER);
		
		Label clueLabel = new Label();
		Label countLabel = new Label();
		clueLabel.setText("Clue:");
		countLabel.setText("Count:");
		TextField clueInput = new TextField();
		TextField countInput = new TextField();
		Button submit = new Button("Submit Clue");
		submit.setOnAction(e -> clueAndCount(clueInput, countInput));
		userInputContainer = new HBox(25);
		userInputContainer.getChildren().addAll(clueLabel, clueInput, countLabel, countInput, submit);
		controlContainer.getChildren().add(userInputContainer);
		userInputContainer.setAlignment(Pos.CENTER);
	}
	
	/**
	 * This method checks which team's turn it is and depending on that it displays a message dialog indicating who's turn it is.
	 * This method also removes the {@code grid} from the boardContainer and updates it using a nested loop to set appropriate
	 * label text on the Labels created for each location instance. This method also creates two JTextfields for the SpyMaster to input
	 * the clue and count. Then a submit button is created in order to handle if clue and count are legal.
	 */
	public void threeTeamSpyMaster() {
		controlContainer.getChildren().removeAll(giveup, userInputContainer);
		clueContainer.getChildren().remove(clueAndCount);
		if(board.isRedTurn()) {
			MessageDialog.display("Status", "It is now the Red Team's turn!\nPlease hand the device to the Red Team spymaster!");
		} else if (board.isGreenTurn()){
			MessageDialog.display("Status", "It is now the Green Team's turn!\nPlease hand the device to the Green Team spymaster!");
		} else {
			MessageDialog.display("Status", "It is now the Blue Team's turn!\nPlease hand the device to the Blue Team spymaster!");
		}
		
		layout.setTop(menu);
		boardContainer.getChildren().remove(easterEggAutoWin);
		easterEggAutoWin = new Button();
		easterEggAutoWin.getStyleClass().add("autowinbutton");
		easterEggAutoWin.setOnAction(this::handle);
		boardContainer.getChildren().add(easterEggAutoWin);
		easterEggHertzBG = new Button();
		easterEggHertzBG.setOnAction(this::handle);
		easterEggHertzBG.getStyleClass().add("hertzbutton");
		easterEggHertzBG.setPrefSize(1, 1);
		layout.setBottom(easterEggHertzBG);
		
		boardContainer.getChildren().remove(grid);
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		locations = board.getLocations();
		for(int i=0; i<locations.length; i++) {
			for(int j=0; j<locations[0].length; j++) {
				if(locations[i][j].isRevealed()) {
					Label role = new Label();
					role.setText("Role: " + locations[i][j].getPerson().getRole());
					role.setAlignment(Pos.CENTER);
					role.setMinSize(125, 125);
					role.getStyleClass().add("labeltile-revealed");
					grid.add(role, i, j);
				}
				else {
					Label role = new Label();
					role.setText(locations[i][j].getCodename() + "\nRole: " + locations[i][j].getPerson().getRole());
					role.setAlignment(Pos.CENTER);
					role.setMinSize(125, 125);
					role.getStyleClass().add("labeltile");
					grid.add(role, i, j);
				}
			}
		}
		boardContainer.getChildren().add(grid);
		grid.setAlignment(Pos.CENTER);
		
		Label clueLabel = new Label();
		Label countLabel = new Label();
		clueLabel.setText("Clue:");
		countLabel.setText("Count:");
		TextField clueInput = new TextField();
		TextField countInput = new TextField();
		Button submit = new Button("Submit Clue");
		submit.setOnAction(e -> clueAndCount(clueInput, countInput));
		userInputContainer = new HBox(25);
		userInputContainer.getChildren().addAll(clueLabel, clueInput, countLabel, countInput, submit);
		controlContainer.getChildren().add(userInputContainer);
		userInputContainer.setAlignment(Pos.CENTER);
	}
	
	/**
	 * This method takes two JTextFields as input and calls the countIsLegal() and clueIsLegal() methods to check if they are legal. If they are
	 * both legal it returns {@code true}, {@code clueAndCount} label is created to show the current clue and count. Then it calls the teamTurn to proceed onto the rest of
	 * the current team's turn. However, if the clue and count aren't legal then it creates a new message dialog stating that the clue and count
	 * entered isn't legal and returns {@code false}
	 * @param c Clue that the SpyMaster entered
	 * @param cnt Count that the SpyMaster entered
	 * @return {@code true} if the clue and count are legal, otherwise false.
	 */
	public boolean clueAndCount(TextField c, TextField cnt) {
		if(board.countIsLegal(cnt.getText()) && board.clueIsLegal(c.getText())) {
			board.setClue(c.getText());
			board.setCount(Integer.parseInt(cnt.getText()));
			clueContainer.getChildren().removeAll(clueAndCount);
			clueAndCount = new Label();
			clueAndCount.setText("Clue: " + board.getClue() + "\nCount: " + board.getCount());
			clueContainer.getChildren().add(clueAndCount);
			controlContainer.getChildren().remove(userInputContainer);
			teamTurn();
			return true;
		}
		else {
			MessageDialog.display("Invalid Clue/Count", "It appears that you have entered invalid entries, please try again!\n- Clue cannot equate to codename on the board\n- Count must be a positive integer > 0");
			return false;
		}
	}
	
	/**
	 * This method removes the {@code grid} from the boardContainer and updates it using a nested loop to set appropriate
	 * label text on the {@code codenames} buttons created for each location instance. When a button is pressed the handler
	 * would handle it accordingly. A {@code giveup} button is also created giving the team to have the opportunity to end
	 * their turn.
	 */
	public void teamTurn() {
		layout.setTop(menu);
		boardContainer.getChildren().remove(easterEggAutoWin);
		easterEggAutoWin = new Button();
		easterEggAutoWin.getStyleClass().add("autowinbutton");
		easterEggAutoWin.setOnAction(this::handle);
		boardContainer.getChildren().add(easterEggAutoWin);
		easterEggHertzBG = new Button();
		easterEggHertzBG.setOnAction(this::handle);
		easterEggHertzBG.getStyleClass().add("hertzbutton");
		easterEggHertzBG.setPrefSize(1, 1);
		layout.setBottom(easterEggHertzBG);
		
		boardContainer.getChildren().remove(grid);
		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		locations = board.getLocations();
		for(int i=0; i<locations.length; i++) {
			for(int j=0; j<locations[0].length; j++) {
				if(locations[i][j].isRevealed()) {
					Label role = new Label();
					role.setText("Role: " + locations[i][j].getPerson().getRole());
					role.setAlignment(Pos.CENTER);
					role.setMinSize(125, 125);
					role.getStyleClass().add("labeltile-revealed");
					grid.add(role, i, j);
				}
				else {
					codename = new Button();
					codename.setUserData(locations[i][j]);
					codename.setText(locations[i][j].getCodename());
					codename.setAlignment(Pos.CENTER);
					codename.setMinSize(125, 125);
					codename.setOnAction(this::handle);
					codename.getStyleClass().add("buttontile");
					grid.add(codename, i, j);
				}
			}
		}
		boardContainer.getChildren().add(grid);
		grid.setAlignment(Pos.CENTER);
		
		giveup = new Button("END TURN");
		giveup.setOnAction(this::handle);
		userInputContainer = new HBox(25);
		userInputContainer.getChildren().add(giveup);
		controlContainer.getChildren().add(userInputContainer);
		userInputContainer.setAlignment(Pos.CENTER);
	}
	
	
	/**
	 * This method handles all the button actions
	 * @param event
	 */
	public void handle(ActionEvent event) {
		
//      Handles the exit button in Menu to close the window
		if(event.getSource() == exit) {
			window.close();
		}
		
//      Handles the newGame button in Menu to call newBoard()
		if(event.getSource() == newGame) {
			threeTeamMode=0;
			newBoard();
		}
		
//      Handles the startGame button in Main Menu to call newBoard()
		if(event.getSource() == startGame) {
			threeTeamMode = 0;
			newBoard();
		}
		
//      Handles rulesButton in the Main Menu to display a message dialog to show the rules of Codenames
		if(event.getSource() == rulesButton) {
//			MessageDialog.display("Rules Of Codenames", "");
			String url="https://en.wikipedia.org/wiki/Codenames_(board_game)";
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			} catch (IOException e) {}
		}
		
//      Handles githubButton in the Main Menu to open a browser window displaying gitHub page of this project
		if(event.getSource() == githubButton) {
			String url="https://github.com/CSE116-Spring2018/s18semesterproject-b3-teamname";
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			} catch (IOException e) {}
		}
		
//		Handles exitButton in the Main Menu to close the window
		if(event.getSource() == exitButton) {
			window.close();
		}
		
//		Handles the threeTeam button in Menu to call threeTeamBoard()
		if(event.getSource() == threeTeamButton) {
			threeTeamMode = 1;
			threeTeamBoard();
		}
		
		if(event.getSource() == newThreeGame) {
			threeTeamMode = 1;
			threeTeamBoard();
		}
		
//      Handles the easterEggHertzBG button to change the current background to a new one ;) and Hertz kills all of blue team
		if(event.getSource() == easterEggHertzBG) {
				layout.getStyleClass().remove("gamebackground");
				layout.getStyleClass().add("gamebackgroundhertz");
			if(threeTeamMode==0) {	
				WinningDialog.display("Status", "Prof. Hertz Have Assassinated All Of Blue Team\nRED TEAM WINS!", this);
			} else {
				WinningDialog.display("Status", "Prof. Hertz Have Assassinated All Of Blue AND Green Team\nRED TEAM WINS!", this);
			}
		}
		
//		Handles the buttons on the GridPane. When a button on the grid is clicked it checks which button is clicked and depending on that
//		it calls the updateGameState() method from Board class which checks whether the location button clicked is a current team's agent if it
//		is, then the current team continues, but if it isn't then it calls switchTurn() on the board instance and calls the spyMaster() method
//		to start the other team's turn. This method also updates the grid by calling refreshBoard() method in Main. Then it checks if the game
//		is currently in a winning state by calling the winningState() and assassinRevealed() method on board from the Board class. The teams
//		turn is switched by calling the switchTurn method from Board class. It then creates a message dialog if the current game state is in a
//		winning state displaying who won the game. If assassinRevealed() returns true, it takes the current team and creates a message dialog
//		that states that the opposite team has won. This button also checks if the count is equal to 0, if it is it also calls the switchTurn method.
//		Also contains corresponding ThreeTeamMode handles
		if(grid != null) {
			for(Node node: grid.getChildren()) {
				if(event.getSource() == node) {
					if(threeTeamMode==0) {
						if(board.updateGameState(((Location) node.getUserData())) || board.getRedScore() == 9 || board.getBlueScore() == 8) {
							System.out.print(board.getRedScore());
							refreshBoard();
							if(board.winningState(board.getRedScore(), board.getBlueScore())){
								board.switchTurn();
								if (board.getRedScore() == 9) {
									board.switchTurn();
									WinningDialog.display("Status", "Red Team's Agents Were All Revealed!\nRED TEAM WINS!", this);
									break;
								}
								else if (board.getBlueScore() == 8) {
									board.switchTurn();
									WinningDialog.display("Status", "Blue Team's Agents Were All Revealed!\nBLUE TEAM WINS!", this);
									break;
								}
								else {
									System.out.println(board.assassinRevealed().toUpperCase() + " TEAM WINS!");
									break;
								}
							}
							if(board.getCount() < 0) {
								board.switchTurn();
								spyMaster();
							}
						}
						else {
							if(board.winningState(board.getRedScore(), board.getBlueScore())){
								refreshBoard();
								board.assassinRevealed();
								WinningDialog.display("Status", board.getLoser() + " Team's Operator Was Assassinated!\n" + board.assassinRevealed().toUpperCase() + " TEAM WINS!", this);
							}
							else {
								refreshBoard();
								board.switchTurn();
								spyMaster();
							}
						}
					}
					else{
						if(board.threeTeamUpdateGameState(((Location) node.getUserData())) || (board.getRedScore() == 6 && board.getLoser() != "Red")|| (board.getBlueScore() == 5 && board.getLoser() != "Blue") || (board.getGreenScore() == 5 && board.getLoser() != "Green")) {
							refreshBoard();
							if(board.threeTeamWinningState(board.getRedScore(), board.getBlueScore(),board.getGreenScore())){
								board.threeTeamSwitchTurn();
								if (board.getRedScore() == 6) {
									if(board.getLoser()=="") {
										board.threeTeamSwitchTurn();
										board.threeTeamSwitchTurn();
									}
									else {
										board.threeTeamSwitchTurn();
									}	
									board.threeTeamSwitchTurn();
									WinningDialog.display("Status", "Red Team's Agents Were All Revealed!\nRED TEAM WINS!", this);
									break;
								}
								else if (board.getBlueScore() == 5) {
									if(board.getLoser()=="") {
										board.threeTeamSwitchTurn();
										board.threeTeamSwitchTurn();
									}
									else {
										board.threeTeamSwitchTurn();
									}	
									WinningDialog.display("Status", "Blue Team's Agents Were All Revealed!\nBLUE TEAM WINS!", this);
									break;
								}
								else if (board.getGreenScore() == 5) {
									if(board.getLoser()=="") {
										board.threeTeamSwitchTurn();
										board.threeTeamSwitchTurn();
									}
									else {
										board.threeTeamSwitchTurn();
									}	
									WinningDialog.display("Status", "Green Team's Agents Were All Revealed!\nGREEN TEAM WINS!", this);
									break;
								}
								else {
									if(!board.getWinner().equals("")) {
										System.out.println(board.getWinner().toUpperCase() + " TEAM WINS!");
									}
									break;
								}
							}
							if(board.getCount() < 0) {
								board.threeTeamSwitchTurn();
								threeTeamSpyMaster();
							}
						}
						else {
							refreshBoard();
							String teamLost = board.threeTeamAssassinRevealed();
							if(board.getLoser().equals(teamLost)) {
								MessageDialog.display("Status", teamLost + " Team's Operator Was Assassinated!\n");
							}
							if(board.threeTeamWinningState(board.getRedScore(), board.getBlueScore(), board.getGreenScore())) {
								WinningDialog.display("Status", board.getLoser2() + " Team's Operator Was Assassinated!\n" + board.getWinner().toUpperCase() + " TEAM WINS!", this);
							}
							else {
								board.threeTeamSwitchTurn();
								threeTeamSpyMaster();
							}
						}
					}
				}
			}
		}
		
//		Handles the easterEggAutoWin button that displays a message dialog. It checks for which team's turn it is and displays whichever
//		team's turn it is wins.
		if(event.getSource() == easterEggAutoWin) {
			if(board.isRedTurn()) {
				WinningDialog.display("Status", "Huh? What's This?\nMagical Powers Were Given To The Red Team!\nRED TEAM WINS!", this);
			}
			else {
				WinningDialog.display("Status", "Huh? What's This?\nMagical Powers Were Given To The Blue Team!\nBLUE TEAM WINS!", this);
			}
		}
		
//		Handles the giveup button which calls the switchTurn method on the board and calls the spyMaster() method to start the other team's
//		turn
		if(event.getSource() == giveup) {
			if(threeTeamMode == 0) {
				board.switchTurn();
				spyMaster();
			} else {
				board.threeTeamSwitchTurn();
				threeTeamSpyMaster();
			}
		}
	}
	
	
	//Just a bunch of useful Accessor methods
	public Stage getWindow()
	{
		return window;
	}
	
	public Scene getMainMenuScene()
	{
		return mainMenuScene;
	}
	
	public BorderPane getMainMenuScreen() {
		return mainMenuScreen;
	}

	public void setMainMenuScreen(BorderPane mainMenuScreen) {
		this.mainMenuScreen = mainMenuScreen;
	}

	public MenuBar getMenu() {
		return menu;
	}

	public void setMenu(MenuBar menu) {
		this.menu = menu;
	}
	
	public int getThreeTeamMode() {
		return threeTeamMode;
	}

}