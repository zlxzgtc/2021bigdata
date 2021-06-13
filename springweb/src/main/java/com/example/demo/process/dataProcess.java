package com.example.demo.process;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.*;
public class dataProcess {
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public static void main(String[] args) {
//        String path = "J:\\Myfiles\\20-2\\大数据计算技术\\data\\Task2_data_0804\\course_info.json";
//        File file = new File(path);
//        StringBuilder result = new StringBuilder();
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));//构造一个BufferedReader类来读取文件
//            String s = null;
//            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
//                result.append(System.lineSeparator() + s);
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//        String s = readJsonFile(path);
//        JSONObject jobj = JSON.parseObject(s);
//        JSONArray movies = jobj.getJSONArray("RECORDS");//构建JSONArray数组
//        for (int i = 0 ; i < movies.size();i++){
//            JSONObject key = (JSONObject)movies.get(i);
//            String name = (String)key.get("name");
//            String director = (String)key.get("director");
//            String scenarist=((String)key.get("scenarist"));
//            String actors=((String)key.get("actors"));
//            String type=((String)key.get("type"));
//            String ratingNum=((String)key.get("ratingNum"));
//            String tags=((String)key.get("tags"));
//            System.out.println(name);
//            System.out.println(director);
//            System.out.println(scenarist);
//            System.out.println(actors);
//            System.out.println(type);
//            System.out.println(director);
//            System.out.println(ratingNum);
//            System.out.println(tags);
//        }
//    }
    public dataProcess() {
    }
    public static void main(String[] args) throws Exception {

        System.setProperty("hadoop.home.dir", "/usr/local/hadoop");
        String path = "J:\\Myfiles\\20-2\\大数据计算技术\\data\\Task2_data_0804\\course_info.json";
        Configuration conf = new Configuration();
        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "data process");
        job.setJarByClass(dataProcess.class);
        job.setMapperClass(dataProcess.TokenizerMapper.class);
//        job.setCombinerClass(dataProcess.IntSumReducer.class);
        job.setReducerClass(dataProcess.IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(path));

        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
    public static class TokenizerMapper extends Mapper<Object, Text, Text, Text> {
//        private static final IntWritable one = new IntWritable(1);
        private Text word = new Text();
        public TokenizerMapper() {
        }
        public void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context) throws IOException, InterruptedException {

            JSONObject jobj = JSON.parseObject(String.valueOf(value));
            JSONObject keys = (JSONObject)jobj;
            Text course_id = (Text)keys.get("course_id");
            List<String> list3= JSON.parseArray((String)keys.get("item"), String.class);

            context.write(course_id, new Text(list3.toString()));



        }
    }
    public static class IntSumReducer extends Reducer<Text, Text, Text, Text> {
//        private IntWritable result = new IntWritable();
        public IntSumReducer() {
        }
        public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            IntWritable val;
            for(Iterator i$ = values.iterator(); i$.hasNext(); sum += val.get()) {
                val = (IntWritable)i$.next();
            }
//            this.result.set(sum);
//            context.write(key, this.result);
        }
    }
}
