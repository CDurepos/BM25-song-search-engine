README
    
    Songpack is a package built and designed for use in Assignment 6 of the University of Southern
    Maine's COS285 Data Structures course, taught by Dr. Behrooz Mansouri.

    This program is intended to operate using a provided file named 'song_lyrics.tsv'

Configuration
    
    In order to use this program, one must have a Java compiler and Runtime environment installed.

Operation
    
    In order to use the program as intended, one must first compile the java files within the
    package. Following compilation, one may run the program by executing 'program5' in 'songpack'
    and passing the file path of 'song_lyrics.tsv' to the console, as input.

    These are the only two lines that should be necessary to use the associated program...

    'javac ~/songpack/*.java'
    'java ~/songpack/program5 ~/song_lyrics.tsv'

    Upon execution, this program will print five queries and the five most relevant songs.
    Relevance is calculated using the BM25 method, and relevance scores are displayed with search results.

Included Files
    
    Within the songpack package, the following files are present...

    MyDataReader.java        (By Dr. Behrooz Mansouri -- Modified by Clayton Durepos, Jack Bergin)
    MySearchEngine.java
    program6.java
    Song.java                (By Dr. Behrooz Mansouri)

    Other files in this repository...
    
    README.md                (THIS FILE)
    song_lyrics.tsv          (Data)

Citations
    
    The following methods were written by Clayton Durepos
        MySearchEngine.calculateIDF()
        MySearchEngine.calculateAvgLength()
        MySearchEngine.search()

    The following methods were written by Jack Bergin
        MySearchEngine.calculateTF()
        MySearchEngine.calculateRelevance()
        program6.main()