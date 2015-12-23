package org.basic.hadooptest;

import org.apache.hadoop.examples.dancing.DistributedPentomino;
import org.apache.hadoop.examples.dancing.Sudoku;
import org.apache.hadoop.examples.terasort.TeraGen;
import org.apache.hadoop.examples.terasort.TeraSort;
import org.apache.hadoop.examples.terasort.TeraValidate;
import org.apache.hadoop.util.ProgramDriver;

//Referenced classes of package org.apache.hadoop.examples:
//         WordCount, AggregateWordCount, AggregateWordHistogram, Grep, 
//         RandomWriter, RandomTextWriter, Sort, PiEstimator, 
//         SecondarySort, SleepJob, Join, MultiFileWordCount, 
//         DBCountPageView

public class ExampleDriver {

  public static void main(String argv[]) {
    int exitCode = -1;
    ProgramDriver pgd = new ProgramDriver();
    try {
      pgd.addClass("wordcount", org.basic.hadooptest.WordCount.class,
          "A map/reduce program that counts the words in the input files.");
      // pgd.addClass("aggregatewordcount", org.basic.hadooptest.AggregateWordCount,
      // "An Aggregate based map/reduce program that counts the words in the input files.");
      // pgd.addClass("aggregatewordhist", org.basic.hadooptest.AggregateWordHistogram,
      // "An Aggregate based map/reduce program that computes the histogram of the words in the input files.");
      // pgd.addClass("grep", org.basic.hadooptest.Grep,
      // "A map/reduce program that counts the matches of a regex in the input.");
      // pgd.addClass("randomwriter", org.basic.hadooptest.RandomWriter,
      // "A map/reduce program that writes 10GB of random data per node.");
      // pgd.addClass("randomtextwriter", org.basic.hadooptest.RandomTextWriter,
      // "A map/reduce program that writes 10GB of random textual data per node.");
      // pgd.addClass("sort", org.basic.hadooptest.Sort,
      // "A map/reduce program that sorts the data written by the random writer.");
      // pgd.addClass("pi", org.basic.hadooptest.PiEstimator,
      // "A map/reduce program that estimates Pi using monte-carlo method.");
      // pgd.addClass("pentomino", org.basic.hadooptest.dancing / DistributedPentomino,
      // "A map/reduce tile laying program to find solutions to pentomino problems.");
      // pgd.addClass("secondarysort", org.basic.hadooptest.SecondarySort,
      // "An example defining a secondary sort to the reduce.");
      // pgd.addClass("sudoku", org.basic.hadooptest.dancing / Sudoku, "A sudoku solver.");
      // pgd.addClass("sleep", org.basic.hadooptest.SleepJob,
      // "A job that sleeps at each map and reduce task.");
      // pgd.addClass("join", org.basic.hadooptest.Join,
      // "A job that effects a join over sorted, equally partitioned datasets");
      // pgd.addClass("multifilewc", org.basic.hadooptest.MultiFileWordCount,
      // "A job that counts words from several files.");
      // pgd.addClass("dbcount", org.basic.hadooptest.DBCountPageView,
      // "An example job that count the pageview counts from a database.");
      // pgd.addClass("teragen", org.basic.hadooptest.terasort / TeraGen, "Generate data for the terasort");
      // pgd.addClass("terasort", org.basic.hadooptest.terasort / TeraSort, "Run the terasort");
      // pgd.addClass("teravalidate", org.basic.hadooptest.terasort / TeraValidate,
      // "Checking results of terasort");
      pgd.driver(argv);
      exitCode = 0;
    } catch (Throwable e) {
      e.printStackTrace();
    }
    System.exit(exitCode);
  }
}
