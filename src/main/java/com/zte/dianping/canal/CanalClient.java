package com.zte.dianping.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * ProjectName: dianping-com.zte.dianping.canal
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 17:14 2020/3/16
 * @Description:
 *
 * 连接动作Bean
 */
@Component
public class CanalClient implements DisposableBean {


    private CanalConnector canalConnector;

    @Bean
    public CanalConnector getCanalConnector(){
        canalConnector = CanalConnectors.newClusterConnector(Lists.newArrayList(
                new InetSocketAddress("127.0.0.1",11111)),
                "example","canal","canal"
        );
        canalConnector.connect();//执行连接
        //指定filter，格式{database}.{table}
        canalConnector.subscribe();
        //回滚寻找上次中断位置
        canalConnector.rollback();
        return canalConnector;
    }


    // DisposableBean  实现方法，程序销毁时断开canal
    //防止连接泄露
    @Override
    public void destroy() throws Exception {
        if (canalConnector != null){
            canalConnector.disconnect();
        }
    }
}
