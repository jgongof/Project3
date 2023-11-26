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
        desserts.add("brownies");
        desserts.add("cheesecake");
        desserts.add("candy");
        desserts.add("muffin");
        desserts.add("doughnut");
        desserts.add("pudding");
        desserts.add("macaron");
        desserts.add("eclair");
        desserts.add("tiramisu");
        desserts.add("cupcake");

        // adding fairytales to list
        fairytales.add("cinderella");
        fairytales.add("pinnochio");
        fairytales.add("shrek");
        fairytales.add("dumbo");
        fairytales.add("rapunzel");
        fairytales.add("bambi");
        fairytales.add("pocahontas");
        fairytales.add("aladdin");
        fairytales.add("frozen");
        fairytales.add("enchanted");
        fairytales.add("hercules");
        fairytales.add("tarzan");
        fairytales.add("mulan");
        fairytales.add("maleficent");
        fairytales.add("fantasia");

        // adding to cities list
        cities.add("chicago");
        cities.add("london");
        cities.add("istanbul");
        cities.add("tokyo");
        cities.add("mumbai");
        cities.add("sydney");
        cities.add("berlin");
        cities.add("madrid");
        cities.add("dubai");
        cities.add("seoul");
        cities.add("amsterdam");
        cities.add("cairo");
        cities.add("shanghai");
        cities.add("manila");
        cities.add("toronto");

    }


}
