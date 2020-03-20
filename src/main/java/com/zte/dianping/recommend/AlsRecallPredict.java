package com.zte.dianping.recommend;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * ProjectName: dianping-com.zte.dianping.recommend
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:33 2020/3/19
 * @Description:
 */
public class AlsRecallPredict {

    public static void main(String[] args) {
        //1.初始化Spark运行环境
        SparkSession spark = SparkSession.builder().master("local").appName("DianpingApp").getOrCreate();

        //加载已训练模型
        ALSModel alsModel = ALSModel.load("file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\alsData\\alsModel");


        JavaRDD<String> javaRDD = spark.read().textFile(
                "file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\alsData\\behavior.csv").toJavaRDD();
        JavaRDD<AlsRecallTrain.Rating> rdd = javaRDD.map(new Function<String, AlsRecallTrain.Rating>() {
            @Override
            public AlsRecallTrain.Rating call(String s) throws Exception {
                return AlsRecallTrain.Rating.parseRating(s);
            }
        });
        Dataset<Row> rating = spark.createDataFrame(rdd, AlsRecallTrain.Rating.class);
        //给5个用户做离线额召回结果预测
        Dataset<Row> users = rating.select(alsModel.getUserCol()).distinct().limit(5);

        Dataset<Row> userRecs = alsModel.recommendForUserSubset(users, 20);

        userRecs.foreachPartition(new ForeachPartitionFunction<Row>() {
            @Override
            public void call(Iterator<Row> iterator) throws Exception {

                //数据库连接
                Connection sql = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dianping?" +
                                "user=root&password=123456&useUnicode=true&characterEncoding=utf-8");
                PreparedStatement preparedStatement = sql.prepareStatement("insert into recommend(id,recommend) values (?,?)");

                List<Map<String, Object>> data = new ArrayList<>();

                iterator.forEachRemaining(action->{
                    int userId = action.getInt(0);
                    List<GenericRowWithSchema> rowWithSchemaList = action.getList(1);
                    List<Integer> shopIds = new ArrayList<>();
                    rowWithSchemaList.forEach(row->{
                        int shopId = row.getInt(0);
                        shopIds.add(shopId);
                    });
                    String recommendData = StringUtils.join(shopIds,",");
                    Map<String,Object> map = new HashMap<>();
                    map.put("userId",userId);
                    map.put("recommend",recommendData);
                    data.add(map);
                });

                data.forEach(map ->{
                    try {
                        preparedStatement.setInt(1,(Integer) map.get("userId"));
                        preparedStatement.setString(2,(String)map.get("recommend"));

                        preparedStatement.addBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                preparedStatement.executeBatch();//执行
                //关流
                preparedStatement.close();
                sql.close();
            }
        });



    }

    public static class Rating implements Serializable {
        private int userId;
        private int shopId;
        private int rating;//分数

        public static AlsRecallTrain.Rating parseRating(String str){
            str = str.replaceAll("\"","");
            String[] split = str.split(",");
            int userId = Integer.parseInt(split[0]);
            int shopId = Integer.parseInt(split[1]);
            int rating = Integer.parseInt(split[2]);
            return new AlsRecallTrain.Rating(userId,shopId,rating);
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
