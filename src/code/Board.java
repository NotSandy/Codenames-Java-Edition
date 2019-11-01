package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import guiJavaFX.MessageDialog;

public class Board {
	
	/**
	 * Integer which keeps track of the red team's score
	 */

	private int redScore;
	

	/**
	 * Integer which keeps track of the blue team's score
	 */
	
	private int blueScore;
	
	/**
	 * Integer which keeps track of the green team's score
	 */
	
	private int greenScore;
	
	/**
	 * String which is used to state which team have won the match
	 */
	
	private String winner;
	
	private String loser, loser2;
	
	/**
	 * Arraylist that stores the codenames that were read in from the file
	 */
	
	private ArrayList<String> codenames;
	
	/**
	 * Arraylist that stores the 25 randomly generated codenames
	 */
	
	private ArrayList<String> generatedCodes;
	
	/**
	 * Arraylist that stores the 25 randomly generated Persons
	 */
	
	private ArrayList<Person> people;
	
	/**
	 * Boolean value that determines if it's the red team's turn. If {@code true} then it's the red team's
	 * turn. If {code false} then it's the blue team's turn
	 */
	
	private boolean redTurn;
	
	/**
	 * Boolean value that determines if it's the green team's turn. If {@code true} then it's the green team's
	 * turn. If {code false} then {redTurn} determines the current team's turn
	 */
	
	private boolean greenTurn;
	
	/**
	 * String which contains the current clue
	 */
	
	private String clue;
	
	/**
	 * Integer which contains the number of locations associated with the clue
	 */
	
	private int count;
	
	/**
	 * Board which contains the board instance that's used to store locations
	 */
	
	private Board board;
	
	/**
	 * 2D-Array of location instances that's used to store board location instances
	 */
	
	private Location[][] grid;
	
	/**
	 * Integers to keep track of revealed assassin locations
	 */
	private int firstIndex, secondIndex;
	
	/**
	 * This creates a instance of board that contains 25 location instances
	 */
	
	public Board(String filename)
	{
		this.redScore = 0;
		this.blueScore = 0;
		this.greenScore = 0;
		this.winner = "";
		grid = generateGrid(5);
		codenames = readFile(filename);
//		_observers = new ArrayList<Observer>();
	}
	
	/**
	 * This creates a 2D-Array that contains 25 location instances
	 * @param x Determines the dimensions of the grid
	 * @return
	 */
	
	public Location[][] generateGrid(int x)
	{
		return new Location[x][x];
	}
	
	/**
	 * Correctly reads codenames from a file named GameWords.txt and stores them in a list
	 * 
	 * @param filename Name of the file which we are reading getting the codenames from
	 * @return {@code list} An arraylist containing the codenames that were read in from {@code filename}
	 */
	
	public ArrayList<String> readFile(String filename)
	{
		ArrayList<String> list = new ArrayList<String>();
		InputStream is = getClass().getResourceAsStream(filename);
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		try {
			String line;
			while ((line=r.readLine()) != null) {
			    list.add(line);
			}
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Creates list containing 25 distinct codenames selected at random
	 * 
	 * @param c An arraylist of codenames that needs to be shuffled in order to get 25 random codenames to use in the game
	 * @return {@code list} an arraylist of 25 codenames selected randomly from {@code c}
	 */
	
	public ArrayList<String> generateCode(ArrayList<String> c)
	{
		ArrayList<String> list = new ArrayList<String>();
		Collections.shuffle(c);
		for(int i=0; i<25; i++) {
			list.add(c.get(i));
		}
		return list;
	}
	
	/**
	 * Creates list containing randomly generated assignments for each of the 9 Red Agents, 8 Blue Agents,
	 * 7 Innocent Bystanders, and 1 Assassin
	 * 
	 * @return {@code list} An arrayList of type person containing the appropriate amounts of people with different roles
	 */
	
	public ArrayList<Person> generatePeople()
	{
		ArrayList<Person> list = new ArrayList<Person>();
		for(int i = 0; i < 9; i++)
		{
			list.add(new Person("Red"));
		}
		for(int i = 0; i < 8; i++)
		{
			list.add(new Person("Blue"));
		}
		for(int i = 0; i < 7; i++)
		{
			list.add(new Person("Bystander"));
		}
		for(int i = 0; i < 1; i++)
		{
			list.add(new Person("Assassin"));
		}
		Collections.shuffle(list);
		return list;
	}
	
	/**
	 * Creates list containing randomly generated assignments for each of the 6 Red Agents, 5 Blue Agents, 5 Green Agents
	 * 7 Innocent Bystanders, and 2 Assassins
	 * 
	 * @return {@code list} An arrayList of type person containing the appropriate amounts of people with different roles
	 */
	
	public ArrayList<Person> threeTeamGeneratePeople()
	{
		ArrayList<Person> list = new ArrayList<Person>();
		for(int i = 0; i < 6; i++)
		{
			list.add(new Person("Red"));
		}
		for(int i = 0; i < 5; i++)
		{
			list.add(new Person("Blue"));
		}
		for(int i = 0; i < 5; i++)
		{
			list.add(new Person("Green"));
		}
		for(int i = 0; i < 7; i++)
		{
			list.add(new Person("Bystander"));
		}
		for(int i = 0; i < 2; i++)
		{
			list.add(new Person("Assassin"));
		}
		Collections.shuffle(list);
		return list;
	}
	
	/**
	 * This method initializes so that when game starts, it is red team's move and each of Board's 25 Location 
	 * instances is assigned a codename, person, and is not revealed
	 */
	public void startNewGame()
	{
		generatedCodes = generateCode(codenames);
		people = generatePeople();
		redTurn = true;
		assignLocations(generatedCodes, people);
	}
	
	/**
	 * Three Team Mode
	 * This method initializes so that when game starts, it is red team's move and each of Board's 25 Location 
	 * instances is assigned a codename, person, and is not revealed for three teams
	 */
	public void threeTeamStartNewGame()
	{
		generatedCodes = generateCode(codenames);
		people = threeTeamGeneratePeople();
		redTurn = true;
		greenTurn = false;
		loser = "";
		loser2 = "";
		firstIndex = -1;
		secondIndex = -1;
		assignLocations(generatedCodes, people);
	}
	
	
	/**
	 * This method is written in order for future phases where the "Spymaster" would input a clue and
	 * the number of locations that are associated with the clue. This is then stored into instance
	 * variables for the accessibility of other classes.
	 * 
	 * @param c The clue which the Spymaster inputs
	 * @param cnt The amount of locations associated with the clue on the board the Spymaster inputs
	 */
	public void provideClueAndCount(String c, int cnt)
	{
		clue = c;
		count = cnt;
	}
	
	/**
	 * This method assigns each value on the board array a location
	 * 
	 * @param c An arraylist of type String which contains the 25 locations to be assigned on to the board
	 * @param p An arraylist of type Person that are to be assigned to a location on the board
	 */
	
	private void assignLocations(ArrayList<String> c, ArrayList<Person> p)
	{
		int index = 0;
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				grid[i][j] = new Location(c.get(index), p.get(index));
				index++;
			}
		}
	}
	
	/**
	 * This method correctly returns if a clue is legal or illegal
	 * @param c The clue for which we will test
	 * @return {@code true} if the {@code c} is equal to the codename on the board and is revealed. {@code false}
	 * if the {@code c} is equal to the codename and is not revealed. Otherwise {@code true} for all other {@code c}.
	 */
	
	public boolean clueIsLegal(String c)
	{	
		for(int i=0; i<grid.length; i++)
		{
			for(int j=0; j<grid[0].length; j++)
			{
				if(grid[i][j].getCodename().equalsIgnoreCase(c))
				{
					if(grid[i][j].isRevealed() == false)
					{
						return false;
					}
				}
				easterEggHertz(c);
			}
		}
		easterEggLOL(c);
		return true;
	}
	
	//checks if count is a legal integer
	
	public boolean countIsLegal(String c) {
		try {
			int count = Integer.parseInt(c);
			return count > 0;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * This method decrements the count, updates a Location when the Location's codename was selected,
	 * and returns if the Location contained the current team's Agent
	 * 
	 * @param row The row of the location being tested
	 * @param column The column of the location being tested
	 * @return {@code true} if the location selected contains the current teams agent, {@code false} otherwise.
	 */
	
	public boolean updateGameState(Location x)
	{
		count -= 1;
		x.setRevealed(true);
		if(x.getPerson().getRole().equals("Red") && redTurn) {
			redScore += 1;
			return true;
		}
		if(x.getPerson().getRole().equals("Red") && !redTurn) {
			redScore += 1;
			return false;
		}
		if(x.getPerson().getRole().equals("Blue") && redTurn) {
			blueScore += 1;
			return false;
		}
		if(x.getPerson().getRole().equals("Blue") && !redTurn) {
			blueScore += 1;
			return true;
		}
		return false;
	}

	/**
	 * This method decrements the count, updates a Location when the Location's codename was selected,
	 * and returns if the Location contained the current team's Agent for three teams
	 * 
	 * @param row The row of the location being tested
	 * @param column The column of the location being tested
	 * @return {@code true} if the location selected contains the current teams agent, {@code false} otherwise.
	 */
	
	public boolean threeTeamUpdateGameState(Location x)
	{
		count -= 1;
		x.setRevealed(true);
		if(x.getPerson().getRole().equals("Red") && redTurn) {
			redScore += 1;
			return true;
		}
		if(x.getPerson().getRole().equals("Red") && !redTurn) {
			redScore += 1;
			return false;
		}
		if(x.getPerson().getRole().equals("Blue") && (redTurn || greenTurn)) {
			blueScore += 1;
			return false;
		}
		if(x.getPerson().getRole().equals("Blue") && !redTurn && !greenTurn) {
			blueScore += 1;
			return true;
		}
		if(x.getPerson().getRole().equals("Green") && greenTurn) {
			greenScore += 1;
			return true;
		}
		if(x.getPerson().getRole().equals("Green") && !greenTurn) {
			greenScore += 1;
			return false;
		}
		return false;
	}
	
	/**
	 * This method correctly returns whether or not the Board is in one of the winning states
	 * 
	 * @param red Red's score
	 * @param blue Blue's score
	 * @return {@code true} if the red teams score is equal to 9 or the blue teams score is equal to 8 or
	 * if the assassin was revealed. {@code false} for any other conditions. 
	 */
	
	public boolean winningState(int red, int blue)
	{
		if(red == 9) 
		{
			winner = "Red";
			return true;
		}
		if(blue == 8)
		{
			winner = "Blue";
			return true;
		}
		for(int i=0; i<grid.length; i++)
		{
			for(int j=0; j<grid[i].length; j++)
			{
				if(grid[i][j].isRevealed() && grid[i][j].getPerson().getRole().equals("Assassin"))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * This method correctly returns whether or not the Board is in one of the winning states for three teams
	 * 
	 * @param red Red's score
	 * @param blue Blue's score
	 * @param green Green's score
	 * @return {@code true} if the red teams score is equal to 6, the blue teams score is equal to 5, the green teams score is equal to 5 or
	 * if the assassin was revealed. {@code false} for any other conditions. 
	 */
	
	public boolean threeTeamWinningState(int red, int blue, int green)
	{
		if(red == 6 && loser != "Red") 
		{
			winner = "Red";
			return true;
		}
		if(blue == 5 && loser != "Blue")
		{
			winner = "Blue";
			return true;
		}
		if(green == 5 && loser != "Green")
		{
			winner = "Green";
			return true;
		}
		if(loser.equals("Red") && loser2.equals("Blue") || loser2.equals("Red") && loser.equals("Blue"))
		{
			winner = "Green";
			return true;
		}
		if(loser.equals("Green") && loser2.equals("Blue") || loser2.equals("Green") && loser.equals("Blue"))
		{
			winner = "Red";
			return true;
		}
		if(loser.equals("Red") && loser2.equals("Green") || loser2.equals("Red") && loser.equals("Green"))
		{
			winner = "Blue";
			return true;
		}
		return false;
	}
	
	
	/**
	 * This method correctly returns which team did not lose (i.e., win) when the Assassin was revealed
	 * 
	 * @return {@code winner} which is a string indicating which team won the game
	 */
	
	public String assassinRevealed()
	{
		for(int i=0; i<grid.length; i++)
		{
			for(int j=0; j<grid[i].length; j++)
			{
				if(grid[i][j].isRevealed() && grid[i][j].getPerson().getRole().equals("Assassin"))
				{
					if(redTurn) {
						winner = "Blue";
						loser = "Red";
					}
					if(!redTurn) {
						winner = "Red";
						loser = "Blue";
					}
				}
			}
		}
		return winner;
	}
	
	/**
	 * Three Team Mode
	 * 
	 * This method correctly returns which team recently lost when the Assassin was revealed
	 * 
	 * @return {@code loser} which is a string indicating which team lost the game
	 */
	
	public String threeTeamAssassinRevealed()
	{
		String firstLoser = loser;
		
		for(int i=0; i<grid.length; i++)
		{
			for(int j=0; j<grid[i].length; j++)
			{
				if(grid[i][j].isRevealed() && grid[i][j].getPerson().getRole().equals("Assassin"))
				{
					if(!(firstIndex == i && secondIndex == j)) {
						if(redTurn) {
							if(loser.equals("")) {
								loser = "Red";
							} else {
								loser2 = "Red";
							}
						}
						if(!redTurn && !greenTurn) {
							if(loser.equals("")) {
								loser = "Blue";
							} else {
								loser2 = "Blue";
							}
						}
						if(greenTurn) {
							if(loser.equals("")) {
								loser = "Green";
							} else {
								loser2 = "Green";
							}
						}
						firstIndex = i;
						secondIndex = j;
					}
				}
			}
		}
		if(loser.equals("") && loser2.equals(""))
		{
			return " ";
		}
		else
		{
			if(loser2.equals(""))
			{
				if(firstLoser != loser)
				{
					return loser;
				}
				else
				{
					return " ";
				}
			}
			else
			{
				return loser2;
			}
		}
	}
	
	/**
	 * This method is a getter method for the current grid
	 * 
	 * @return {@code grid} a 2D-Array which the game is being played on
	 */

	public Location[][] getLocations(){
		return grid;
	}
	
	/**
	 * This method is a getter method for the codenames that were read in from the file
	 * 
	 * @return {@code codenames} containing the codenames that were read in
	 */
	
	public ArrayList<String> getCodeNames()
	{
		return codenames;
	}
	
	/**
	 * This method is a getter method for the 25 codenames that were generated
	 * 
	 * @return {@code generatedCodes} containing the 25 codenames that were generated
	 */
	
	public ArrayList<String> getGeneratedCodes()
	{
		return generatedCodes;
	}
	
	/**
	 * This method is a getter method for the 25 generated Person
	 * 
	 * @return {@code people} containing the 25 generated Persons
	 */
	
	public ArrayList<Person> getPeople()
	{
		return people;
	}
	
	/**
	 * Returns whether or not its red's turn
	 * 
	 * @return {@code redTurn} true or false for whether or not its red's turn
	 */
	public boolean isRedTurn()
	{
		return redTurn;
	}
	
	/**
	 * Returns whether or not its green's turn
	 * 
	 * @return {@code redTurn} true or false for whether or not its green's turn
	 */
	public boolean isGreenTurn()
	{
		return greenTurn;
	}
	
	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void switchTurn() {
		redTurn = !redTurn;
	}
	
	public void threeTeamSwitchTurn() {
		if(redTurn) {
			redTurn = !redTurn;
		}
		else if(!redTurn && !greenTurn) {
			greenTurn = !greenTurn;
		}
		else if(greenTurn) {
			greenTurn = !greenTurn;
			redTurn = !redTurn;
		}
		if(loser!="") {
			if(loser.equals("Red")) {
				if(redTurn) {
					redTurn = !redTurn;
				}
			}
			else if(loser.equals("Blue")) {
				if(!redTurn && !greenTurn) {
					greenTurn = !greenTurn;
				}
			}
			else {
				if(greenTurn) {
					greenTurn = !greenTurn;
					redTurn = !redTurn;
				}
			}
		}
	}

	public int getRedScore() {
		return redScore;
	}

	public void setRedScore(int redScore) {
		this.redScore = redScore;
	}

	public int getBlueScore() {
		return blueScore;
	}

	public void setBlueScore(int blueScore) {
		this.blueScore = blueScore;
	}
	
	public int getGreenScore() {
		return greenScore;
	}

	public void setGreenScore(int greenScore) {
		this.greenScore = greenScore;
	}
	
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getLoser() {
		return loser;
	}
	
	public String getLoser2()
	{
		return loser2;
	}

	public void setLoser(String loser) {
		this.loser = loser;
	}
	
	public void setLoser2(String loser) {
		this.loser2 = loser;
	}
	
	public String getTurn()
	{
		if(redTurn)
		{
			return "Red";
		}
		if(!redTurn && greenTurn)
		{
			return "Green";
		}
		return "Blue";
	}
	
	public void setTurn(boolean red, boolean green)
	{
		redTurn = red;
		greenTurn = green;
	}

	public void easterEggHertz(String c) {
		if(c.equalsIgnoreCase("Hertz")) {
			String url="https://www.cse.buffalo.edu/~mhertz/MatthewSimpson.png";
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			} catch (IOException e) {}
		}
	}
	
	public void easterEggLOL(String c) {
		if(c.equalsIgnoreCase("LOL")) {
			MessageDialog.display("Status", "Who are you laughing at?");
		}
	}
	
}