import java.io.Serializable;
import java.util.ArrayList;
public class Connectivity implements Serializable{
    private static final long serialVersionUID = 1L;

    int dessertWordLength;
    int ftWordLength;
    int citiesWordLength;
    int attempts;

    boolean gotCorrectLetter;
    boolean gotCorrectWord;

    String correctDessert;
    String correctFairyTale;
    String correctCity;

    int categoryNumber;

    String playerActivity;

    String category;

    char userLetter;

    int desserts_attempts;
    int fairytales_attempts;
    int cities_attempts;
}
