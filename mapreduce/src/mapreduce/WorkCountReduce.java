package mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WorkCountReduce extends Reducer<LongWritable, Text, Text, Text> {

    public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {

    }

}
