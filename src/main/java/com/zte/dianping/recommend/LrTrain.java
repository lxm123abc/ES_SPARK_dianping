package com.zte.dianping.recommend;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;

/**
 * ProjectName: dianping-com.zte.dianping.recommend
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 12:39 2020/3/19
 * @Description:
 */
public class LrTrain {

    public static void main(String[] args) throws IOException {
        //1.初始化Spark运行环境
        SparkSession spark = SparkSession.builder().master("local").appName("DianpingApp").getOrCreate();

        //加载特征文件
        JavaRDD<String> csvFile = spark.read().textFile(
                "file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\lrData\\feature.csv").toJavaRDD();

        //转化为row的dataset
        JavaRDD<Row> rowJavaRDD = csvFile.map(new Function<String, Row>() {
            @Override
            public Row call(String str) throws Exception {
                str = str.replaceAll("\"","");
                String[] split = str.split(",");
                //12列的csv
                return RowFactory.create(new Double(split[11]), Vectors.dense(Double.valueOf(split[0]),Double.valueOf(split[1])
                        , Double.valueOf(split[2]),Double.valueOf(split[3]),Double.valueOf(split[4])
                        , Double.valueOf(split[5]),Double.valueOf(split[6])
                        , Double.valueOf(split[7]),Double.valueOf(split[8])
                        , Double.valueOf(split[9]),Double.valueOf(split[10])));
            }
        });
        StructType schema = new StructType(
                new StructField[]{
                        new StructField("label", DataTypes.DoubleType,false, Metadata.empty()),
                        new StructField("features", new VectorUDT(),false, Metadata.empty())
                }
        );

        //制造dataset Row
        Dataset<Row> data = spark.createDataFrame(rowJavaRDD, schema);
        //将所有rating数据82分
        Dataset<Row>[] datasets = data.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainData = datasets[0];//训练 80%
        Dataset<Row> testData = datasets[1];//测试 20%

        //初始化lr模型
        LogisticRegression lr = new LogisticRegression().setMaxIter(10).setRegParam(0.3)
                .setElasticNetParam(0.8).setFamily("multinomial");

        LogisticRegressionModel lrModel = lr.fit(trainData);

        lrModel.save("file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\lrData\\lrModel");

        //测试评估
        Dataset<Row> prediction = lrModel.transform(testData);

        //评价指标
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator();
        double accuracy = evaluator.setMetricName("accuracy").evaluate(prediction);
        System.out.println("评价指标 accuracy:"+accuracy);
    }


}
