package cs1c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Jukebox {
    /*
    A variable called favoritePL of type Queue<SongEntry>
    which simulates the playlist referred to as "favorites"
    in the input file.
    */
    Queue<SongEntry> favoritePL;
    /*
    A variable called roadTripPL of type Queue<SongEntry>
    which simulates the playlist referred to as "road trip"
    in the input file.
    */
    Queue<SongEntry> roadTripPL;
    /*
    A variable called loungePL​ of type Queue<SongEntry> which simulates
    the playlist referred to as "lounge" in the input file.
     */
    Queue<SongEntry> loungePL;
    public Jukebox(){
        favoritePL = new Queue<SongEntry>("favorites:", null, null);
        roadTripPL = new Queue<SongEntry>("lounge:", null, null);
        loungePL = new Queue<SongEntry>("road trip:", null, null);
    }

    /*
    A method called fillPlaylists()  reads the test file and then adds
    songs to one of the three queues.
    For example, if the file contains line: favorites,title
    Then the first song found that equals the title will
    be placed in the favorites playlist.
     */
    public void fillPlaylists(String m, SongEntry[] allSongs) {
        Scanner r = null;
        // We sort the songs by title, so change the sort key
        int k = SongEntry.SORT_BY_TITLE;
        SongEntry.setSortType(k);
        Arrays.sort(allSongs);
        try {
            File f = new File(m);
            r = new Scanner(f);
            String scan;
            while (r.hasNextLine()) {
                scan = r.nextLine();
                int commaPosition = scan.indexOf(',');
                int nextIndex = commaPosition + 1;
                String playlistName = scan.substring(0, commaPosition);
                String songName = scan.substring(nextIndex, scan.length());
                SongEntry s = new SongEntry(songName, 0, "", "");
                //System.out.println("Searching for: " + songName + " " + playlistName);
                int i = Arrays.binarySearch(allSongs, s);
                if (playlistName.compareToIgnoreCase("favorites") == 0) {
                    //System.out.println("enqueue in favorites");
                    favoritePL.enqueue(allSongs[i]);
                }
                else if (playlistName.compareToIgnoreCase("lounge") == 0) {
                    //System.out.println("enqueue in lounge");
                    loungePL.enqueue(allSongs[i]);
                }
                else if (playlistName.compareToIgnoreCase("road trip") == 0) {
                    //System.out.println("enqueue in road trip");
                    roadTripPL.enqueue(allSongs[i]);
                }

            }
        } catch (FileNotFoundException e) {

        } catch (IOException t) {

        } finally {
            if (r != null) r.close();
        }
    }

    /*
    Accessor methods getFavoritePL(), getRoadTripPL() and getLoungePL()
    for each of the three queue structures favoritePL, roadTripPL
    and loungePL respectively
     */
    public Queue<SongEntry> getFavoritePL(){
        return favoritePL;
    }
    public Queue<SongEntry> getRoadTripPL(){
        return roadTripPL;
    }
    public Queue<SongEntry> getLoungePL(){
        return loungePL;
    }

}
