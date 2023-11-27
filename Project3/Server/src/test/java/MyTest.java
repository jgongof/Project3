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

	//Helper function to get the callback messages
	public class CallbackCapture implements Consumer<Serializable> {

		private List<String> messages = new ArrayList<>();

		@Override
		public void accept(Serializable message) {
			messages.add(message.toString());
		}

		public List<String> getMessages() {
			return messages;
		}
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

	//Playing the game with category desserts, has to all be correct
	@Test
	void playGame()
	{

		GameLogic game4 = new GameLogic();
		game4.chooseRandomWord(1);

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
		game5.chooseRandomWord(2);

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
		game6.chooseRandomWord(3);

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


	//Testing the server connection
	@Test
	void testPlayerConnection(Consumer<Serializable> callback) {
		// Create an instance of the Server class with the provided callback and port
		Server server = new Server(callback, 5555);

		CallbackCapture callbackCapture = new CallbackCapture();

		// Simulate player connection by starting the server thread
		server.server.start();

		List<String> actualOutput = callbackCapture.getMessages();

		String expectedOutput = "Player Connected To Server: Player #1";

		// Check the output by inspecting the callback's messages
		assertEquals(expectedOutput, actualOutput.get(0));
	}

	//Check that the player disconnected from the server
	@Test
	void testPlayerDisconnect(Consumer<Serializable> callback)
	{
		Server server = new Server(callback, 5555);

		server.players.get(0).interrupt(); // Simulate interrupting the client thread

		CallbackCapture callbackCapture = new CallbackCapture();

		// Expected output
		String expectedOutput = "Player #1 Disconnected. Closing Down.";

		// Get the actual output from the callback
		List<String> actualOutput = callbackCapture.getMessages();

		// Check the output
		assertEquals(expectedOutput, actualOutput.get(0));
	}

	//Check that the user in a certian playing state
	@Test
	void testPlayingState(Consumer<Serializable> callback) {
		Server server = new Server(callback, 8080);

		// Simulate player in the playing state for Desserts
		Server.ClientThread playerThread = server.new ClientThread(new Socket(), 1);
		playerThread.initialization(1); //player choosing Desserts category
		playerThread.start();

		Connectivity playingConnectivity = new Connectivity();
		playingConnectivity.command = "Playing";
		playingConnectivity.userLetter = 'A';
		server.sendUpdatedConnectivity(playingConnectivity);

		CallbackCapture callbackCapture = new CallbackCapture();

		String expectedOutput = "layer #1 Is Playing: Desserts";

		List<String> actualOutput = callbackCapture.getMessages();

		assertEquals(expectedOutput, actualOutput.get(0));
	}

	@Test
	void testPlayerWinningCategory(Consumer<Serializable> callback) {
		Server server = new Server(callback, 5555);

		Server.ClientThread client = server.new ClientThread(new Socket(), 1);
		server.players.add(client); // Add the client to the list of players

		// Simulate the server sending a message about the player winning in a category
		Connectivity winningConnectivity = new Connectivity();
		winningConnectivity.command = "WonCategory";
		winningConnectivity.categoryNumber = 1;
		winningConnectivity.wonDessert = true;
		server.sendUpdatedConnectivity(winningConnectivity);

		CallbackCapture callbackCapture = new CallbackCapture();

		String expectedOutput = "Player #1 Won Desserts";

		List<String> actualOutput = callbackCapture.getMessages();

		assertEquals(expectedOutput, actualOutput.get(0));

	}

	@Test
	void testPlayerChoosingCategory(Consumer<Serializable> callback) {
		Server server = new Server(callback, 8080);

		Server.ClientThread client = server.new ClientThread(new Socket(), 1);
		server.players.add(client);

		// Simulate the player choosing a category
		Connectivity choosingCategoryConnectivity = new Connectivity();
		choosingCategoryConnectivity.command = "category";
		choosingCategoryConnectivity.categoryNumber = 2; //player chose the Fairy Tales category
		server.sendUpdatedConnectivity(choosingCategoryConnectivity);

		CallbackCapture callbackCapture = new CallbackCapture();

		String expectedOutput = "Player #1 Has Chosen Category: Fairy Tales";

		List<String> actualOutput = callbackCapture.getMessages();

		assertEquals(expectedOutput, actualOutput.get(0));
	}

}
