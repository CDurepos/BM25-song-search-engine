package songpack;

import java.util.*;

/**
 * A BM25 Search Engine
 * Written for Assignment 6 of COS285 Data Structures w/ Dr. Behrooz Mansouri @ The University of Southern Maine
 * @author Clayton Durepos, Jack Bergin
 * @version 01.08.2025
 */
public class MySearchEngine {
    TreeMap<Song, TreeMap<String, Double>> tf = new TreeMap<>();
    TreeMap<String, Double> idf = new TreeMap<>();
    TreeMap<Song, Double> avgLength =  new TreeMap<>();

    /**
     * Index a document
     * @param songs
     *      an ArrayList of 'Song' objects
     */
    public MySearchEngine(ArrayList<Song> songs) {
        calculateTF(songs);
        calculateIDF(songs);
        calculateAvgLength(songs);
    }

    /**
     * Calculate Term Frequencies
     * @author Jack Bergin
     * @param songs
     *      an ArrayList of 'Song' objects
     */
    private void calculateTF(ArrayList<Song> songs) {
        for (Song song : songs) {
            TreeMap<String, Double> terms = new TreeMap<>();
            String[] words = song.getLyrics().trim().toLowerCase().split("\\s+");
            double songLength = words.length;

            for (String word : words) {
                terms.put(word, terms.getOrDefault(word, 0.0) + 1);
            }

            terms.replaceAll((_, v) -> v / songLength);

            tf.put(song, terms);
        }
    }

    /**
     * Calculate Inverse Document Frequencies
     * @author Clayton Durepos
     * @param songs
     *      an ArrayList of 'Song' objects
     */
    private void calculateIDF(ArrayList<Song> songs) {
        for (Song song : songs) {
            String[] uniqueWords = new HashSet<>(Arrays.asList(song.getLyrics().trim().toLowerCase().split("\\s+"))).toArray(new String[0]);

            for (String word : uniqueWords) {
                idf.put(word, idf.getOrDefault(word, 0.0) + 1);
            }
        }

        idf.replaceAll((_, v) -> Math.log( ( ( songs.size() - v + 0.5) / (v + 0.5) ) + 1));
    }

    /**
     * Calculate length ratios
     * @author Clayton Durepos
     * @param songs
     *      an ArrayList of 'Song' objects
     */
    private void calculateAvgLength(ArrayList<Song> songs) {
        double totalLength = 0;
        for (Song song : songs) {
            totalLength += song.getLyrics().split("\\s+").length;
        }

        double allAvgLength = totalLength / songs.size();
        for (Song song : songs) {
            avgLength.put(song, song.getLyrics().split("\\s+").length/allAvgLength);
        }
    }

    /**
     * Calculate relevance scores
     * @author Jack Bergin
     * @param song
     *      An instance of Song
     * @param query
     *      An array of Words in a query
     * @return
     *      the relevance score for song to query
     */
    private double calculateRelevance(Song song, String[] query) {
        final double K_CONSTANT = 1.2, B_CONSTANT = 0.75;
        double score = 0.0;

        for (String term : query) {
            double termFrequency = tf.get(song).getOrDefault(term, 0.0);
            double inverseDocFrequency = idf.getOrDefault(term, 0.0);

            double num = termFrequency * (K_CONSTANT + 1);
            double denom = termFrequency + (K_CONSTANT * (1 - B_CONSTANT + (B_CONSTANT * ( avgLength.get(song) ) ) ) );
            score += inverseDocFrequency * (num / denom);
        }

        return score;
    }

    /**
     * Search and print most relevant songs
     * @author Clayton Durepos
     * @param query
     *      a query to compare songs to
     */
    public void search(String query) {
        TreeMap<Song, Double> relevanceScores = new TreeMap<>();
        String[] queryTerms = query.trim().toLowerCase().split("\\s+");

        for (Song song : tf.keySet()) {
            relevanceScores.put(song, calculateRelevance(song, queryTerms));
        }

        List<Map.Entry<Song, Double>> sorted = sortedByValue(relevanceScores, 5);
        printSearchResults(query, sorted);
    }

    /**
     * Sort a TreeMap by Value, ascending order
     * @param treeMap
     *      the treeMap to be sorted
     * @param topK
     *      the amount of relevant Songs to be kept
     * @return
     *      the top K songs with the highest relevance score, sorted in ascending order
     */
    public List<Map.Entry<Song, Double>> sortedByValue(TreeMap<Song, Double> treeMap, int topK){
        List<Map.Entry<Song, Double>> list = new ArrayList<>(treeMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Song, Double>>() {
            @Override
            public int compare(Map.Entry<Song, Double> o1, Map.Entry<Song, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        int counter = 0;
        List<Map.Entry<Song, Double>> results = new ArrayList<Map.Entry<Song, Double>>();
        while(counter<topK){
            results.add(Map.entry(list.get(counter).getKey(),list.get(counter).getValue()));
            counter+=1;
        }
        return results;
    }

    /**
     * Print search results
     * @param query
     *      the query that is used in search
     * @param results
     *      the sorted list of top K songs
     */
    public void printSearchResults(String query, List<Map.Entry<Song, Double>> results){
        System.out.println("Results for "+ query);
        int rank = 1;
        for(Map.Entry<Song, Double> entry: results){
            System.out.println(rank+": " + entry.getKey().getTitle() + " by " + entry.getKey().getArtist() + "\t"
                    + entry.getValue());
            rank+=1;
        }
    }
}
