package com.zte.dianping.recommend;

import com.zte.dianping.dao.RecommendDOMapper;
import com.zte.dianping.model.RecommendDO;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProjectName: dianping-com.zte.dianping.recommend
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 13:15 2020/3/19
 * @Description:
 */
@Service
public class RecommendSortService implements Serializable {

    private SparkSession spark;
    private LogisticRegressionModel lrModel;

    @PostConstruct
    public void init(){
        //加载lr模型
        //1.初始化Spark运行环境
        spark = SparkSession.builder().master("local").appName("DianpingApp").getOrCreate();
        lrModel = LogisticRegressionModel.load(
                "file:\\\\\\F:\\dianping\\src\\main\\java\\com\\zte\\dianping\\recommend\\lrData\\lrModel");
    }

    public List<Integer> sort(List<Integer> shopIdList,Integer userId){
        List<ShopSortModel> shopSortModelList = new ArrayList<>();
        //需要根据lrmodel所需要的11纬的X，生成特征，然后调用预测方法
        for (Integer shopid : shopIdList) {
            //假纬度数据，可以从数据库或者缓存中拿到对应的性别年龄，平均消费等等，做特征转化生成fature向量
            Vector v = Vectors.dense(1, 0, 0, 0, 0, 1, 1, 0.6, 1, 1, 1);
            Vector result = lrModel.predictProbability(v);
            double[] arr = result.toArray();
            double score = arr[1];
            ShopSortModel shopSortModel = new ShopSortModel();
            shopSortModel.setShopId(shopid);
            shopSortModel.setScore(score);
            shopSortModelList.add(shopSortModel);
        }
        shopSortModelList.sort(new Comparator<ShopSortModel>() {
            @Override
            public int compare(ShopSortModel o1, ShopSortModel o2) {
                if (o1.getScore()<o2.getScore()){
                    return 1;
                }else if (o1.getScore()>o2.getScore()){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        return shopSortModelList.stream().map(shopModel->{
            return shopModel.getShopId();
        }).collect(Collectors.toList());
    }



}
