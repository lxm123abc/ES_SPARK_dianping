package com.zte.dianping.recommend;

import com.google.common.collect.Lists;
import com.zte.dianping.dao.RecommendDOMapper;
import com.zte.dianping.model.RecommendDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.recommend
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 13:15 2020/3/19
 * @Description:
 */
@Service
public class RecommendService implements Serializable {

    @Autowired
    private RecommendDOMapper recommendDOMapper;


    /**
     * @param userId
     * @return  shopId LIST
     */
    public List<Integer> recall(Integer userId){
        RecommendDO recommendDO = recommendDOMapper.selectByPrimaryKey(userId);
        if (recommendDO == null){
            recommendDO = recommendDOMapper.selectByPrimaryKey(0);
        }
        String[] split = recommendDO.getRecommend().split(",");
        List<Integer> shopIds = new ArrayList<>();
        for (String shopId : split) {
            shopIds.add(Integer.parseInt(shopId));
        }
        return shopIds;
    }

}
