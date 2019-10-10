package test.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.util.Progressable;

import java.net.URI;

public class MapFileTest {
    private static final String url = "file:///Users/lyh/Documents";
    
    public static void main(String[] args) {
        write();
        read();
    }
    
    private static void write() {
        try {
            //1.创建Configuration
            Configuration conf = new Configuration();
            //2.获取FileSystem
            FileSystem fileSystem = FileSystem.get(new URI(url), conf);
            //3.创建文件输出路径Path
            Path path = new Path(url + "/mapfile");
            //4.new一个MapFile.Writer对象
            MapFile.Writer writer = new MapFile.Writer(
                    conf,
                    fileSystem,
                    path.toString(),
                    Text.class,
                    Text.class,
                    SequenceFile.CompressionType.BLOCK,
                    new DefaultCodec(),
                    new Progressable() {
                        public void progress() {
                            System.out.println("progressing...");
                        }
                    });
            //5.调用MapFile.Writer.append()方法追加写入
            writer.append(new Text("key"), new Text("value"));
            writer.append(new Text("name"), new Text("liaozhongmin"));
            //关闭流
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void read() {
        try {
            //创建配置信息
            Configuration conf = new Configuration();
            //创建文件系统
            FileSystem fs = FileSystem.get(new URI(url), conf);
            //创建Path对象
            Path path = new Path(url + "/mapfile");
            //4.new一个MapFile.Reader进行读取
            MapFile.Reader reader = new MapFile.Reader(fs, path.toString(), conf);
            
            //创建Key和Value
            Text key = new Text();
            Text value = new Text();
            
            //遍历获取结果
            while (reader.next(key, value)) {
                System.out.println("key=" + key);
                System.out.println("value=" + value);
            }
            //关闭流
            IOUtils.closeStream(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}