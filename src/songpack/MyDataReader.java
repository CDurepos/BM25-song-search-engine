package songpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyDataReader {
    
    /**
     * Process a line from the TSV file and returns the corresponding song object
     * @param inputLine TSV line
     * @return Song object
     */
    private static Song lineToReport(String inputLine)
    {
        String[] items = inputLine.split("\t");
        String title= items[0];
        String tag= items[1];
        String artist= items[2];
        int year= Integer.parseInt(items[3]);
        int views= Integer.parseInt(items[4]);
        String lyrics= items[5];
        
        Song s = new Song(title, tag, artist, year, views, lyrics);
        return s;
    }

    public static ArrayList<Song> readFileToArrayList(String tsvFilePath) throws IOException {
        int counter = 0;
        ArrayList<Song> arrayList = new ArrayList<>();
        BufferedReader TSVReader = new BufferedReader( new FileReader(tsvFilePath));
        String line  = TSVReader.readLine();

        while ((line = TSVReader.readLine()) != null) {
            Song song = MyDataReader.lineToReport(line);
            arrayList.add(song);
            counter+=1;
            // using this to view progress
            if(counter%50000==0)
                System.out.println(counter + " records added");
        }

        return arrayList;
    }
}
