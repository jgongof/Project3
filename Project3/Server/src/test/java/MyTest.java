import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {


	//Helper Function to play the game automatically
	public char getRandomLetter()
	{
		Random random = new Random();
		char randomLetter = (char) (random.nextInt(26) + 'A');
		return randomLetter;
	}


	//-----------------------------------------
	// Intializations Test
	@Test
	void initialization() {

		GameLogic game0 = new GameLogic();
		assertEquals(6, game0.numGuesses, "Inital number of guesses should be 6");

		CreateCategories categories = new CreateCategories();
		assertEquals(5, categories.desserts.size());
		assertEquals("cake", categories.desserts.get(0));
		assertEquals("icecream", categories.desserts.get(1));
		assertEquals("chocolate", categories.desserts.get(2));
		assertEquals("cookie", categories.desserts.get(3));
		assertEquals("pie", categories.desserts.get(4));

		assertEquals(5, categories.fairytales.size());
		assertEquals("cinderella", categories.fairytales.get(0));
		assertEquals("pinnochio", categories.fairytales.get(1));
		assertEquals("shrek", categories.fairytales.get(2));
		assertEquals("snowwhite", categories.fairytales.get(3));
		assertEquals("rapunzel", categories.fairytales.get(4));

		assertEquals(5, categories.cities.size());
		assertEquals("chicago", categories.cities.get(0));
		assertEquals("london", categories.cities.get(1));
		assertEquals("istanbul", categories.cities.get(2));
		assertEquals("tokyo", categories.cities.get(3));
		assertEquals("newyork", categories.cities.get(4));

	}

	@Test
	void randomWordText()
	{
		GameLogic game1 = new GameLogic();
		GameLogic game2 = new GameLogic();
		GameLogic game3 = new GameLogic();

		game1.chooseRandomWord(1);
		game2.chooseRandomWord(2);
		game3.chooseRandomWord(3);

		assertNotNull(game1.correctWord, "Should not be null");
		assertNotNull(game2.correctWord, "Should not be null");
		assertNotNull(game3.correctWord, "Should not be null");

		assertTrue(game1.correctWord.length() > 0, "Should be a valid string");
		assertTrue(game2.correctWord.length() > 0, "Should be a valid string");
		assertTrue(game3.correctWord.length() > 0, "Should be a valid string");

	}

	@Test
	void playGame()
	{

		GameLogic game4 = new GameLogic();
		game4.chooseRandomWord(1);

		game4.setUserGuess('E');
		game4.checkLetter(game4.userGuess);
		assertTrue(game4.isCorrectLetter);
		game4.checkCorrectWord();
		assertFalse(game4.isCorrectWord);

		game4.setUserGuess('l');
		assertFalse(game4.isCorrectLetter);
		game4.checkCorrectWord();
		assertFalse(game4.isCorrectWord);
		assertEquals(5,game4.numGuesses);

		while(game4.numGuesses > 0)
		{
			char randomLetter = getRandomLetter();
			game4.setUserGuess(randomLetter);
			game4.checkLetter(game4.userGuess);

			if(game4.isCorrectLetter)
			{
				assertTrue(game4.isCorrectLetter);
			}
			else {
				assertFalse(game4.isCorrectLetter);
			}

			game4.checkCorrectWord();

			if(game4.isCorrectWord)
			{
				assertEquals(game4.correctWord, game4.userArrayToString);
			}
			else {
				assertFalse(game4.isCorrectWord);
			}
		}

	}

}
