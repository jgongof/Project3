import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    public String correctWord; // the word user has to guess
    public char[] currUserWord; // meant to display the forming word
    public int numGuesses = 6; //should always start with 6 guesses
    public int numTriesCat1 = 3; // number of tries for category 1
    public int numTriesCat2 = 3; // number of tries for category 2
    public int numTriesCat3 = 3; // number of tries for category 3
    public char userGuess; // keep getting updated when a user enters their guess
    public CreateCategories myCategories = new CreateCategories(); //allows access to guessing words
    public boolean isCorrectLetter = false; // to make sure that letter is in guessing word
    public boolean isCorrectWord = false; //checks that they got the word right
    public boolean alreadyGuessed = false; //Makes sure they already gussed that letter
    public String usedLetters = " "; //tracks used letters
    public String chosenCategory; //tracks what category player chose
    public ArrayList<Integer> corrLetterIndices= new ArrayList<Integer>(); //tracks indices of forming word
    public String userArrayToString = " "; //best way to output forming word

    //gets the players guess
    public void setUserGuess(char userInput)
    {
        this.userGuess = userInput;
    }

    // choose a random word in the speciefied category
    public String chooseRandomWord(int i){
        //for each category get a random word and then delete it so the user never gets the same word twice
        switch (i) {
            case 1:
                Random randomIndex = new Random();
                int indexRandomD = randomIndex.nextInt(myCategories.desserts.size());
                correctWord = myCategories.desserts.get(indexRandomD);
                myCategories.desserts.remove(indexRandomD);
                numTriesCat1--;
                break;
            case 2:
                Random randomIndexFT = new Random();
                int indexRandomFT = randomIndexFT.nextInt(myCategories.fairytales.size());
                correctWord = myCategories.fairytales.get(indexRandomFT);
                myCategories.fairytales.remove(indexRandomFT);
                numTriesCat2--;
                break;
            case 3:
                Random randomIndexC = new Random();
                int indexRandomC = randomIndexC.nextInt(myCategories.cities.size());
                correctWord = myCategories.cities.get(indexRandomC);
                myCategories.cities.remove(indexRandomC);
                numTriesCat3--;
                break;
            default:
                break;
        }
        // setUserWord(); //creates the forming word
        return correctWord;
    }

    //Initializes the forming word as all null but the size of the correct word
    //meaning the user guesses and the correct guesses form the correct word
    //hence forming word
    public void setUserWord(String correctWord)
    {
        currUserWord = new char[correctWord.length()]; // user's current word with the length of the correct word
        for (int i = 0; i< currUserWord.length; i++){
            currUserWord[i]= ' '; //non are null all are white spaces
        }

    }

    //Checks it is the right letter
    public void checkLetter(char userGuess)
    {
        // if letter already used make boolean true
        // then give error statement
        //and go back to playing game

        //First check if it is in usedLetters
        Character.toLowerCase(userGuess); //always make it lowercase
        if(!usedLetters.contains(String.valueOf(userGuess)))
        {
            //if not already used check if it is in correct word
            alreadyGuessed=false;
            isCorrectLetter = correctWord.contains(String.valueOf(userGuess)); //checks it's in the word

            corrLetterIndices.clear(); //clears array to add in new info

            for (int i = 0; i < correctWord.length(); i++) {
                if (correctWord.charAt(i) == userGuess) {
                    currUserWord[i] = userGuess; //checking where in the word it is at
                    corrLetterIndices.add(i); //as well as adds the position to another arraylist
                }
            }
            usedLetters+=userGuess; //add to already guessed arraylist

            if(!isCorrectLetter) {
                //if it is not the correct letter subtract a guess
                numGuesses--;
            }
        }
        else{
            //if it has already been guessed
            alreadyGuessed=true;
        }
    }

    //checks if forming word has been formed and is correct
    public void checkCorrectWord(char[] currUserWord){
        userArrayToString = new String(currUserWord); //make forming word array in to an actual string
        userArrayToString.toLowerCase(); //always lowercase it
        isCorrectWord = userArrayToString.equals(correctWord); //confirms its the same word
    }

    public void reset(){


        numGuesses = 6; //should always start with 6 guesses

        userGuess = ' ';

        isCorrectLetter = false; // to make sure that letter is in guessing word
        isCorrectWord = false; //checks that they got the word right
        alreadyGuessed = false; //Makes sure they already gussed that letter
        usedLetters = " "; //tracks used letters
        chosenCategory = " "; //tracks what category player chose

        userArrayToString = " "; //best way to output forming word
    }

}
