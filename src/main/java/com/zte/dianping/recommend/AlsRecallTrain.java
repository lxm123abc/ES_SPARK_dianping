package com.zte.dianping.recommend;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.Serializable;

/**
 * ProjectName: dianping-com.zte.dianping.recommend
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 15:42 2020/3/18
 * @Description:
 *
 * ALS召回算法训练
 */
public class AlsRecallTrain implements Serializable {

    public static void main(String[] args) throws IOException {
        //1.初始化Spark运行环境
        SparkSession session = SparkSession.builder().master("local").appName("DianpingApp").getOrCreate();
        //.textFile  读取换行符分割的数据
        JavaRDD<String> javaRDD = session.read().textFile(
                "file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\alsData\\behavior.csv").toJavaRDD();

        JavaRDD<Rating> rdd = javaRDD.map(new Function<String, Rating>() {
            @Override
            public Rating call(String s) throws Exception {//行数据字符串
                return Rating.parseRating(s);
            }
        });
        //Dataset<Row>  结构类型mysql表  ROW为行
        Dataset<Row> rating = session.createDataFrame(rdd, Rating.class);

        //将所有rating数据82分
        Dataset<Row>[] datasets = rating.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainData = datasets[0];//训练 80%
        Dataset<Row> testData = datasets[1];//测试 20%

        /**
         * setMaxIter  迭代次数
         * setRank   分解后的几个影响因子
         *
         * 问题解决：
         *    1. 过拟合：增大数据规模，减少RANK，增大正则化系数；
         *    2. 欠拟合：增大RANK，减少正则化系数
         */
        ALS als = new ALS().setMaxIter(10).setRank(5).setRegParam(0.01)
                .setUserCol("userId").setItemCol("shopId").setRatingCol("rating");

        ALSModel alsModel = als.fit(trainData);//训练

        //模型评测
        Dataset<Row> predistions = alsModel.transform(testData);

        //rmse 均方根误差，预测值与真实值的偏方的平方除观测次数，开根号
        //rating真实值
        //prediction预测值
        RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse")
                .setLabelCol("rating").setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predistions);
        System.out.println("rmse:"+rmse);

        alsModel.save("file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\alsData\\alsModel");

    }

    public static class Rating implements Serializable{
        private int userId;
        private int shopId;
        private int rating;//分数

        public static Rating parseRating(String str){
            str = str.replaceAll("\"","");
            String[] split = str.split(",");
            int userId = Integer.parseInt(split[0]);
            int shopId = Integer.parseInt(split[1]);
            int rating = Integer.parseInt(split[2]);
            return new Rating(userId,shopId,rating);
        }

        public Rating(int userId, int shopId, int rating) {
            this.userId = userId;
            this.shopId = shopId;
            this.rating = rating;
        }

        public int getUserId() {
            return userId;
        }

        public int getShopId() {
            return shopId;
        }

        public int getRating() {
            return rating;
        }
    }

}
