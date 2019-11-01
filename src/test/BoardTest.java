package test;

import static org.junit.Assert.*;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import code.Board;
import code.Location;
import code.Person;

public class BoardTest {
	
	Board test;
	int redScore;
	int blueScore;
	int greenScore;
	
	@Before
	public void initialize()
	{
		test = new Board("src/GameWords.txt");
	}
	
	/**
	 * Checks that there are 25 location instances contained in board.
	 */
	
	@Test
	public void correctNumberOfLocationsTest() {
		Location[][] locations = test.getLocations();
		assertEquals(25, locations.length * locations[0].length, 0);
	}
	
	/**
	 * Checks to see if the codenames are correctly read in from the GameWords.txt file by comparing amount of
	 * lines that were read in. Since GameWords.txt has 400 words, then the list size should be 400 when read in
	 */
	
	@Test
	public void txtToListTest() {
		ArrayList<String> list = test.readFile("src/GameWords.txt");
		assertEquals(400, list.size(), 0);
	}
	
	/**
	 * This checks that the generateCode() method returns a list 25 distinct codenames selected at random
	 */
	
	@Test
	public void generateCodeTest()
	{
		ArrayList<String> gameWordsList = test.getCodeNames();
		ArrayList<String> randomCodes = test.generateCode(gameWordsList);
		
		boolean distinct = true;
		boolean random = false;
		
		String x = randomCodes.get(0);
		for(int i = 1; i < randomCodes.size(); i++)
		{
			String y = randomCodes.get(i);
			if(x.equals(y))
			{
				distinct = false;
			}
			x = y;
		}
		
		int idx = 0;
		while(random == false && idx < 25)
		{
			if(!randomCodes.get(idx).equals(gameWordsList.get(idx)));
			{
				random = true;
			}
			idx++;
		}
		
		assertTrue(distinct && random);
	}
	
	/**
	 * Checks that the generatePeople() method returns a arraylist of randomly generated Person with different
	 * roles and with the correct number of each role
	 */

	@Test
	public void generatePeopleTest()
	{
		ArrayList<Person> list = test.generatePeople();
		int red = 0;
		int blue = 0;
		int bystander = 0;
		int assassin = 0;
		for(Person p : list)
		{
			if(p.getRole().equals("Red"))
			{
				red++;
			}
			if(p.getRole().equals("Blue"))
			{
				blue++;
			}
			if(p.getRole().equals("Bystander"))
			{
				bystander++;
			}
			if(p.getRole().equals("Assassin"))
			{
				assassin++;
			}
		}
		assertTrue(red == 9 && blue == 8 && bystander == 7 && assassin == 1);
	}
	
	/**
	 * Checks when the game starts, it's the red team's turn, each of Board's 25 Location 
	 * instances is assigned a codename, person, and is not revealed
	 */
	
	@Test
	public void gameStartTest()
	{
		test.startNewGame();
		Location[][] locations = test.getLocations();
		boolean notRevealed = true;
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(locations[i][j].isRevealed() == true)
				{
					notRevealed = false;
				}
			}
		}
		assertTrue(notRevealed && test.isRedTurn());
	}
	
	/**
	 * Checks if the clueIsLegal() method works. Should return {@code true} if the {@code c} is equal to the codename 
	 * on the board and is revealed. {@code false} if the {@code c} is equal to the codename and is not revealed.
	 * Otherwise {@code true} for all other {@code c}.
	 */
	
	@Test
	public void clueIsLegalTest()
	{
		test.startNewGame();
		Location[][] locations = test.getLocations();
		locations[1][1].setRevealed(true);
		String legalClue = locations[1][1].getCodename();
		assertTrue(test.clueIsLegal(legalClue));
	}
	
	@Test
	public void clueIsIllegalTest() 
	{
		test.startNewGame();
		Location[][] locations = test.getLocations();
		locations[1][1].setRevealed(false);
		String illegalClue = locations[1][1].getCodename();
		assertFalse(test.clueIsLegal(illegalClue));
	}
	
	/**
	 * Checks to make sure method decrements the count, updates a Location when the Location's codename was selected,
	 * and returns if the Location contained the current team's Agent
	 */
	
	@Test
	public void updateGameStateTest()
	{
		test.startNewGame();
		Location[][] locations = test.getLocations();
		test.provideClueAndCount("", 3);
		int firstCount = test.getCount();
		locations[3][4].getPerson().setRole("Red");
		Location l = locations[3][4];
		boolean containedCurrentTeam = test.updateGameState(l);
		int secondCount = test.getCount();
		assertTrue(containedCurrentTeam);
		assertTrue(locations[3][4].isRevealed() == true);
		assertEquals(firstCount - 1, secondCount);
	}
	
	/**
	 * Checks if the current board is in one of the winning states
	 */
	
	@Test
	public void winningStateTestRed()
	{
		test.startNewGame();
		redScore = 9;
		assertTrue(test.winningState(9, 0));
	}
	
	@Test
	public void winningStateTestBlue()
	{
		test.startNewGame();
		blueScore = 8;
		assertTrue(test.winningState(0, 8));
	}
	
	@Test
	public void winningStateTestAssassin()
	{
		test.startNewGame();
		int row = 0;
		int column = 0;
		Location[][] locations = test.getLocations();
		for(int i=0; i<locations.length; i++)
		{
			for(int j=0; j<locations[i].length; j++)
			{
				if(locations[i][j].getPerson().getRole().equals("Assassin"))
				{
					row = i;
					column = j;
				}
			}
		}
		locations[row][column].setRevealed(true);
		assertTrue(test.winningState(0, 0));
	}
	
	
	/**
	 * Checks to see if when the assassin is revealed which team did not win or lose.
	 */
	
	@Test
	public void assassinRevealedTest()
	{
		test.startNewGame();
		Location[][] locations = test.getLocations();
		for(int i = 0; i < locations.length; i++) {
			for(int j = 0; j < locations[0].length; j++) {
				if(locations[i][j].getPerson().getRole().equals("Assassin")) {
					locations[i][j].setRevealed(true);
				}
			}
		}
		
		String winner = test.assassinRevealed();
		assertEquals("Blue", winner);
	}
	
	//Three Team Tests vvvvvv
	
	@Test
	public void generatePeopleThreeTest()
	{
		ArrayList<Person> list = test.threeTeamGeneratePeople();
		int red = 0;
		int blue = 0;
		int green = 0;
		int bystander = 0;
		int assassin = 0;
		for(Person p : list)
		{
			if(p.getRole().equals("Red"))
			{
				red++;
			}
			if(p.getRole().equals("Blue"))
			{
				blue++;
			}
			if(p.getRole().equals("Green"))
			{
				green++;
			}
			if(p.getRole().equals("Bystander"))
			{
				bystander++;
			}
			if(p.getRole().equals("Assassin"))
			{
				assassin++;
			}
		}
		assertTrue(red == 6 && blue == 5 && green == 5 && bystander == 7 && assassin == 2);
	}
	
	@Test
	public void winningStateTestRedThree()
	{
		test.threeTeamStartNewGame();
		redScore = 5;
		assertTrue(test.threeTeamWinningState(6, 0, 0));
	}
	
	@Test
	public void winningStateTestBlueThree()
	{
		test.threeTeamStartNewGame();
		blueScore = 5;
		assertTrue(test.threeTeamWinningState(0, 5, 0));
	}
	
	@Test
	public void winningStateTestGreenThree()
	{
		test.threeTeamStartNewGame();
		greenScore = 5;
		assertTrue(test.threeTeamWinningState(0, 0, 5));
	}
	
	@Test
	public void assassinRevealedThreeTest()
	{
		test.threeTeamStartNewGame();
		String loser = "";
		String loser2 = "";
		int losers = 0;
		test.setTurn(true, false);
		Location[][] locations = test.getLocations();
		for(int i = 0; i < locations.length; i++) {
			for(int j = 0; j < locations[0].length; j++) {
				if(locations[i][j].getPerson().getRole().equals("Assassin")) {
					locations[i][j].setRevealed(true);
					if(losers == 0)
					{
						loser = test.threeTeamAssassinRevealed();
						losers++;
						test.threeTeamSwitchTurn();
						test.threeTeamSwitchTurn();
					}
					else
					{
						loser2 = test.threeTeamAssassinRevealed();
						losers++;
					}
				}
			}
		}
		
		test.setLoser("Green");
		test.setLoser2("Red");
		test.threeTeamWinningState(0, 0, 0);
		String winner = test.getWinner();
		assertEquals("Red", loser);
		assertEquals("Green", loser2);
		assertEquals("Blue", winner);
	}
	
	@Test
	public void threeTeamSwitchTurnTest()
	{
		String turn1;
		String turn2;
		String turn3;
		String turn4;
		test.threeTeamStartNewGame();
		test.setTurn(true, false);
		turn1 = test.getTurn();
		test.threeTeamSwitchTurn();
		turn2 = test.getTurn();
		test.threeTeamSwitchTurn();
		turn3 = test.getTurn();
		test.setTurn(false, false);
		test.threeTeamAssassinRevealed(); //When this is called, it is currently blue's turn
		test.threeTeamSwitchTurn();
		turn4 = test.getTurn();
		assertEquals("Red", turn1);
		assertEquals("Blue", turn2);
		assertEquals("Green", turn3);
		assertEquals("Green", turn4); // This ends up skipping blue because blue was killed off
	}
}
