package test.spark

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("WordCount").getOrCreate()
  
    val hadoopConf = spark.sparkContext.hadoopConfiguration
    val fileSystem = FileSystem.get(hadoopConf)
  
    val path = new Path("/test-data")
    val files = fileSystem.listFiles(path, true)
  
    var fileList = Array[String]()
    while (files.hasNext) {
      val fileStatus = files.next()
      fileList = fileList :+ fileStatus.getPath().getName()
    }
  
    println("text files: " + fileList.mkString(", "))
  
    val rdd = spark.sparkContext.textFile("/test-data/message-processor.2019-03.log")
  
    rdd.flatMap(r => r.split("\\s"))
        .map(s => (s, 1))
        .reduceByKey(_ + _)
        .sortBy(_._2, false)
        .top(100)
        .foreach(t => println(s"${t._1}: ${t._2}"))
  
    spark.stop()
  }
  
}