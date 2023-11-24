import java.io.Serializable;
import java.util.ArrayList;
public class Connectivity implements Serializable{
    private static final long serialVersionUID = 1L;

    int categoryWordLength;
    int attempts = 6;

    boolean gotCorrectLetter;
    boolean gotCorrectWord;

    int categoryNumber = 0;

    String playerActivity;

    String category;

    char userLetter;

    int desserts_attempts = 3;
    int fairytales_attempts = 3;
    int cities_attempts = 3;
}
