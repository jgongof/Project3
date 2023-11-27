import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	//Helper Function to play the game automatically
	public int randomIndex(int range)
	{
		Random random = new Random();
		return random.nextInt(range);
	}

	//-----------------------------------------
	// Intializations Test
	@Test
	void initialization() {

		GameLogic game0 = new GameLogic();
		assertEquals(6, game0.numGuesses, "Initial number of guesses should be 6");
		assertEquals(3, game0.numTriesCat1, "Initial number of guesses should be 3");
		assertEquals(3, game0.numTriesCat2, "Initial number of guesses should be 3");
		assertEquals(3, game0.numTriesCat3, "Initial number of guesses should be 3");

		assertFalse(game0.isCorrectLetter, "Initialized to False");
		assertFalse(game0.isCorrectWord, "Initialized to False");
		assertFalse(game0.alreadyGuessed, "Initialized to False");

		CreateCategories categories = new CreateCategories();
		assertEquals(15, categories.desserts.size());
		assertEquals("cake", categories.desserts.get(0));
		assertEquals("icecream", categories.desserts.get(1));
		assertEquals("chocolate", categories.desserts.get(2));
		assertEquals("cookie", categories.desserts.get(3));
		assertEquals("pie", categories.desserts.get(4));
		assertEquals("brownies", categories.desserts.get(5));
		assertEquals("cheesecake", categories.desserts.get(6));
		assertEquals("candy", categories.desserts.get(7));
		assertEquals("muffin", categories.desserts.get(8));
		assertEquals("doughnut", categories.desserts.get(9));
		assertEquals("pudding", categories.desserts.get(10));
		assertEquals("macaron", categories.desserts.get(11));
		assertEquals("eclair", categories.desserts.get(12));
		assertEquals("tiramisu", categories.desserts.get(13));
		assertEquals("cupcake", categories.desserts.get(14));


		assertEquals(15, categories.fairytales.size());
		assertEquals("cinderella", categories.fairytales.get(0));
		assertEquals("pinnochio", categories.fairytales.get(1));
		assertEquals("shrek", categories.fairytales.get(2));
		assertEquals("dumbo", categories.fairytales.get(3));
		assertEquals("rapunzel", categories.fairytales.get(4));
		assertEquals("bambi", categories.fairytales.get(5));
		assertEquals("pocahontas", categories.fairytales.get(6));
		assertEquals("aladdin", categories.fairytales.get(7));
		assertEquals("frozen", categories.fairytales.get(8));
		assertEquals("enchanted", categories.fairytales.get(9));
		assertEquals("hercules", categories.fairytales.get(10));
		assertEquals("tarzan", categories.fairytales.get(11));
		assertEquals("mulan", categories.fairytales.get(12));
		assertEquals("maleficent", categories.fairytales.get(13));
		assertEquals("fantasia", categories.fairytales.get(14));


		assertEquals(15, categories.cities.size());
		assertEquals("chicago", categories.cities.get(0));
		assertEquals("london", categories.cities.get(1));
		assertEquals("istanbul", categories.cities.get(2));
		assertEquals("tokyo", categories.cities.get(3));
		assertEquals("mumbai", categories.cities.get(4));
		assertEquals("sydney", categories.cities.get(5));
		assertEquals("berlin", categories.cities.get(6));
		assertEquals("madrid", categories.cities.get(7));
		assertEquals("dubai", categories.cities.get(8));
		assertEquals("seoul", categories.cities.get(9));
		assertEquals("amsterdam", categories.cities.get(10));
		assertEquals("cairo", categories.cities.get(11));
		assertEquals("shanghai", categories.cities.get(12));
		assertEquals("manila", categories.cities.get(13));
		assertEquals("toronto", categories.cities.get(14));

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

	//Playing the game with category desserts, has to all be correct
	@Test
	void playGame()
	{

		GameLogic game4 = new GameLogic();
		String cw = game4.chooseRandomWord(1);
		game4.setUserWord(cw);

		while(!game4.isCorrectWord)
		{

			int randomIndex = randomIndex(game4.correctWord.length());

			// Get the random letter from correctWord using randomIndex
			char randomLetter = game4.correctWord.charAt(randomIndex);

			game4.setUserGuess(randomLetter);

			// Check if the guessed letter is correct
			game4.checkLetter(game4.userGuess);

			// Assert the correctness of the guessed letter
			if (game4.isCorrectLetter) {
				assertTrue(game4.isCorrectLetter);
			} else {
				assertFalse(game4.isCorrectLetter);
			}

			// Add the guessed letter to currUserWord
			game4.currUserWord[randomIndex] = randomLetter;

			// Check if currUserWord is equal to correctWord
			game4.checkCorrectWord(game4.currUserWord);

			// Assert the correctness of the word
			if (game4.isCorrectWord) {
				assertEquals(game4.correctWord, game4.userArrayToString);
			} else {
				assertFalse(game4.isCorrectWord);
			}
		}

	}

	//Playing the game with category fairy tale, has to all be correct
	@Test
	void playGame2()
	{

		GameLogic game5 = new GameLogic();
		String cw = game5.chooseRandomWord(2);
		game5.setUserWord(cw);

		while(!game5.isCorrectWord)
		{

			int randomIndex = randomIndex(game5.correctWord.length());

			// Get the random letter from correctWord using randomIndex
			char randomLetter = game5.correctWord.charAt(randomIndex);

			game5.setUserGuess(randomLetter);

			// Check if the guessed letter is correct
			game5.checkLetter(game5.userGuess);

			// Assert the correctness of the guessed letter
			if (game5.isCorrectLetter) {
				assertTrue(game5.isCorrectLetter);
			} else {
				assertFalse(game5.isCorrectLetter);
			}

			// Add the guessed letter to currUserWord
			game5.currUserWord[randomIndex] = randomLetter;

			// Check if currUserWord is equal to correctWord
			game5.checkCorrectWord(game5.currUserWord);

			// Assert the correctness of the word
			if (game5.isCorrectWord) {
				assertEquals(game5.correctWord, game5.userArrayToString);
			} else {
				assertFalse(game5.isCorrectWord);
			}
		}

	}

	//Playing the game with category world cities, has to all be correct
	@Test
	void playGame3()
	{

		GameLogic game6 = new GameLogic();
		String cw = game6.chooseRandomWord(3);
		game6.setUserWord(cw);

		while(!game6.isCorrectWord)
		{

			int randomIndex = randomIndex(game6.correctWord.length());

			// Get the random letter from correctWord using randomIndex
			char randomLetter = game6.correctWord.charAt(randomIndex);

			game6.setUserGuess(randomLetter);

			// Check if the guessed letter is correct
			game6.checkLetter(game6.userGuess);

			// Assert the correctness of the guessed letter
			if (game6.isCorrectLetter) {
				assertTrue(game6.isCorrectLetter);
			} else {
				assertFalse(game6.isCorrectLetter);
			}

			// Add the guessed letter to currUserWord
			game6.currUserWord[randomIndex] = randomLetter;

			// Check if currUserWord is equal to correctWord
			game6.checkCorrectWord(game6.currUserWord);

			// Assert the correctness of the word
			if (game6.isCorrectWord) {
				assertEquals(game6.correctWord, game6.userArrayToString);
			} else {
				assertFalse(game6.isCorrectWord);
			}
		}

	}

	// checks the checkLetter function in game logic
	@Test
	void checkLetterTest1()
	{

		GameLogic game7 = new GameLogic();
		game7.correctWord = "hello";
		game7.setUserWord(game7.correctWord);
		assertEquals((game7.currUserWord).length,5 );
		game7.userGuess = 'a';
		game7.checkLetter(game7.userGuess);
		assertEquals(game7.isCorrectLetter, false);
		assertEquals(game7.numGuesses, 5);

	}

	// checks the checkLetter function in game logic
	@Test
	void checkLetterTest2()
	{

		GameLogic game8 = new GameLogic();
		game8.correctWord = "hello";
		game8.setUserWord(game8.correctWord);
		assertEquals((game8.currUserWord).length,5 );
		game8.userGuess = 'l';
		game8.checkLetter(game8.userGuess);
		assertEquals(game8.isCorrectLetter, true);
		assertEquals(game8.numGuesses, 6);

	}

	// checks the currUserLetter in game logic
	@Test
	void checkCurrUserLetterArrayTest1()
	{

		GameLogic game9 = new GameLogic();
		game9.correctWord = "hello";
		game9.setUserWord(game9.correctWord);
		assertEquals((game9.currUserWord).length,5 );
		game9.userGuess = 'a';
		game9.checkLetter(game9.userGuess);
		
		assertEquals(game9.currUserWord[0], ' ');
		assertEquals(game9.currUserWord[1], ' ');
		assertEquals(game9.currUserWord[2], ' ');
		assertEquals(game9.currUserWord[3], ' ');
		assertEquals(game9.currUserWord[4], ' ');
		
		assertEquals(game9.isCorrectLetter, false);

	}
	
	// checks the currUserLetter in game logic
	@Test
	void checkCurrUserLetterArrayTest2()
	{

		GameLogic game10 = new GameLogic();
		game10.correctWord = "hello";
		game10.setUserWord(game10.correctWord);
		assertEquals((game10.currUserWord).length,5 );
		game10.userGuess = 'l';
		game10.checkLetter(game10.userGuess);

		assertEquals(game10.currUserWord[0], ' ');
		assertEquals(game10.currUserWord[1], ' ');
		assertEquals(game10.currUserWord[2], 'l');
		assertEquals(game10.currUserWord[3], 'l');
		assertEquals(game10.currUserWord[4], ' ');

		assertEquals(game10.isCorrectLetter, true);
		assertEquals(game10.numGuesses, 6);

	}

	// checks the currUserLetter in game logic
	@Test
	void checkCurrUserLetterArrayTest3()
	{

		GameLogic game11 = new GameLogic();
		game11.correctWord = "hello";
		game11.setUserWord(game11.correctWord);
		assertEquals((game11.currUserWord).length,5 );

		game11.userGuess = 'l';
		game11.checkLetter(game11.userGuess);
		assertEquals(game11.isCorrectLetter, true);

		game11.userGuess = 'p';
		game11.checkLetter(game11.userGuess);
		assertEquals(game11.isCorrectLetter, false);

		game11.userGuess = 'h';
		game11.checkLetter(game11.userGuess);
		assertEquals(game11.isCorrectLetter, true);

		assertEquals(game11.currUserWord[0], 'h');
		assertEquals(game11.currUserWord[1], ' ');
		assertEquals(game11.currUserWord[2], 'l');
		assertEquals(game11.currUserWord[3], 'l');
		assertEquals(game11.currUserWord[4], ' ');

		assertEquals(game11.numGuesses, 5);

	}


	//Check that everything does reset
	@Test
	void testReset() {
		GameLogic game12 = new GameLogic();

		// Set some values
		game12.numGuesses = 3;
		game12.userGuess = 'a';
		game12.isCorrectLetter = true;
		game12.isCorrectWord = true;
		game12.alreadyGuessed = true;
		game12.usedLetters = "abc";
		game12.userArrayToString = "test";

		// Reset
		game12.reset();

		// Check if values are reset
		assertEquals(6, game12.numGuesses);
		assertEquals(' ', game12.userGuess);
		assertFalse(game12.isCorrectLetter);
		assertFalse(game12.isCorrectWord);
		assertFalse(game12.alreadyGuessed);
		assertEquals(" ", game12.usedLetters);
		assertEquals(" ", game12.userArrayToString);
	}

	//Checks for the correct word and identifies which one is false
	@Test
	void testCheckCorrectWord() {
		GameLogic game13 = new GameLogic();
		game13.correctWord = "apple";
		game13.setUserWord(game13.correctWord);

		// Test correct word
		game13.checkCorrectWord("apple".toCharArray());
		assertTrue(game13.isCorrectWord);

		// Test incorrect word
		game13.checkCorrectWord("banana".toCharArray());
		assertFalse(game13.isCorrectWord);
	}

	//Initialization of the currUserWord variable 
	@Test
	void testSetUserWord() {
		GameLogic game14 = new GameLogic();
		game14.setUserWord("apple");

		assertNotNull(game14.currUserWord);
		assertEquals(5, game14.currUserWord.length);
		assertEquals(' ', game14.currUserWord[0]);
	}
}
