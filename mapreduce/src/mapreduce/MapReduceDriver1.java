package mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MapReduceDriver1 {

    public static void main(String[] args) throws Exception {
        System.setProperty("hadoop.home.dir", "C:\\OpenSrc\\soa_hadop\\hadoop-2.6.0");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "JobName");
        job.setJarByClass(mapreduce.MapReduceDriver1.class);
        // TODO: specify a mapper
        job.setMapperClass(WorkCountMapper.class);
        // TODO: specify a reducer
        job.setReducerClass(WorkCountReduce.class);

        // TODO: specify output types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // TODO: specify input and output DIRECTORIES (not files)
        FileInputFormat.setInputPaths(job, new Path("src"));
        FileOutputFormat.setOutputPath(job, new Path("out"));

        if (!job.waitForCompletion(true))
            return;
    }

}

