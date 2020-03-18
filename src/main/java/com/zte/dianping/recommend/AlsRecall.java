package com.zte.dianping.recommend;

import java.io.Serializable;

/**
 * ProjectName: dianping-com.zte.dianping.recommend
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 15:42 2020/3/18
 * @Description:
 */
public class AlsRecall implements Serializable {

    public static void main(String[] args) {
        //1.初始化Spark运行环境
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
