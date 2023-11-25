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


    int categoryNumber;

    String playerActivity;

    String category;

    char userLetter;

    int desserts_attempts;
    int fairytales_attempts;
    int cities_attempts;

    String command;

    char [] currUserWord; // array of characters in the user guessed word
}
