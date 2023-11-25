import java.sql.Array;
import java.util.ArrayList;

public class CreateCategories {

    //3 arraylist for each category and initalize them as well
    ArrayList <String> desserts = new ArrayList<String>();
    ArrayList <String> fairytales = new ArrayList<String>();
    ArrayList <String> cities = new ArrayList<String>();

    // creates categories
    public CreateCategories(){

        // adding desserts to list
        desserts.add("cake");
        desserts.add("icecream");
        desserts.add("chocolate");
        desserts.add("cookie");
        desserts.add("pie");

        // adding fairytales to list
        fairytales.add("cinderella");
        fairytales.add("pinnochio");
        fairytales.add("shrek");
        fairytales.add("snowwhite");
        fairytales.add("rapunzel");

        // adding to cities list
        cities.add("chicago");
        cities.add("london");
        cities.add("istanbul");
        cities.add("tokyo");
        cities.add("newyork");
    }


}
