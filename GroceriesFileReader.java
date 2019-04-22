package subsetsum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GroceriesFileReader {
    GroceriesFileReader(){

    }
    /*
    This function will reads the CSV (Comma Seperated Value) file
    of groceries and creates a specified ArrayList of grocery prices.
     */

    public ArrayList<Double> readFile(String m){
       ArrayList<Double> a = new ArrayList<Double>();
       Scanner r = null;
       try {
           File f = new File(m);
           r = new Scanner(f);
           String scan;
           while(r.hasNextLine()) {
               scan = r.nextLine();
               //System.out.println(scan);
               // look for comma because our price starts after comma
               int commaPosition = scan.indexOf(',');
               //go to next index of the string in one line
               int nextIndex = commaPosition + 1;
               // make a string named price to keep the price from one line
               //substring method takes two parameters- start point and end point(which is integers)
               //price = smaller string = "2.50", scan = bigger string = "bananas,2.50"
               String price = scan.substring(nextIndex,scan.length());
               double doublePrice = Double.parseDouble(price);
               //System.out.println(doublePrice);
               a.add(doublePrice);
           }
       } catch (FileNotFoundException ex) {

       } catch (IOException ex) {

       } finally {
           if(r!=null) r.close();
       }
       return a;

   }

}
