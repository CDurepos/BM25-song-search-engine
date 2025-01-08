package songpack;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main program for Assignment 6 of USM's COS285 Data Structures course with Professor Behrooz Mansouri
 * @author Jack Bergin
 */
public class program6 {
    public static void main(String[] args) throws IOException {
        ArrayList<Song> songs = MyDataReader.readFileToArrayList(args[0]);
        long start = System.currentTimeMillis();
        MySearchEngine searchEngine = new MySearchEngine(songs);
        long end = System.currentTimeMillis();
        System.out.println( (end-start) + " milliseconds to build the index");


        start = System.currentTimeMillis();
        searchEngine.search("We are the Champions");
        end = System.currentTimeMillis();
        System.out.println( (end-start) + " milliseconds to search for \"We are the Champions\"");

        start = System.currentTimeMillis();
        searchEngine.search("I will always love you");
        end = System.currentTimeMillis();
        System.out.println( (end-start) + " milliseconds to search for \"I will always love you\"");

        start = System.currentTimeMillis();
        searchEngine.search("walking on sunshine");
        end = System.currentTimeMillis();
        System.out.println( (end-start) + " milliseconds to search for \"walking on sunshine\"");

        start = System.currentTimeMillis();
        searchEngine.search("dancing in the rain");
        end = System.currentTimeMillis();
        System.out.println( (end-start) + " milliseconds to search for \"dancing in the rain\"");

        start = System.currentTimeMillis();
        searchEngine.search("put your hands in the jupitersky");
        end = System.currentTimeMillis();
        System.out.println( (end-start) + " milliseconds to search for \"put your hands in the jupitersky\"");

    }
}
