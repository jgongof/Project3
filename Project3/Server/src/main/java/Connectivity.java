import java.io.Serializable;
import java.util.ArrayList;
public class Connectivity implements Serializable{
    private static final long serialVersionUID = 1L;

    int dessertWordLength;
    int ftWordLength;
    int citiesWordLength;
    int wordLength;

    int numGuesses;

    String correctDessert;
    String correctFairyTale;
    String correctCity;



    boolean gotCorrectLetter;
    boolean gotCorrectWord;
    boolean alreadyGuessed;

    boolean wonDessert;
    boolean wonFairytale;
    boolean wonCities;

    int categoryNumber;

    String playerActivity;

    String category;

    char userLetter;

    int desserts_attempts;
    int fairytales_attempts;
    int cities_attempts;

    String command;

    char [] currUserWord; // array of characters in the user guessed word

    void reset(){
        numGuesses=6;
        userLetter= ' ';
        gotCorrectWord= false;
        gotCorrectLetter = false;
        alreadyGuessed= false;
        command = " ";
    }
}
