package com.sentinel;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class DataSourceInitFunc implements InitFunc {
    private static final String CLUSTER_SERVER_HOST = "localhost";

    private static final int CLUSTER_SERVER_PORT = 9999;//token-server 端口

    private static final int REQUST_TIME_OUT = 200000;//请求超时时间

    private static final String APP_NAME = "App-Demo";

    private static final String REMOTE_ADDRESS = "120.79.226.150";//NACOS服务的ip

    private static final String GROUP_ID = "SENTINEL_GROUP";

    private static final String FLOW_POSTFIX = "-flow-rules";//限流规则后缀

    @Override
    public void init() throws Exception {
        loadClusterClientConfig();
        registerClusterFlowRuleProperty();
    }
    /**
       * 注册动态规则Property
       * 当client与Server连接中断，退化为本地限流时需要用到的该规则
       * 该配置为必选项，客户端会从nacos上加载限流规则，请求tokenserver时，会戴上要check的规
     则id
       * {这里的动态数据源，我们稍后会专门讲到}
       */
    private void registerClusterFlowRuleProperty() {
        ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<List<FlowRule>>(
                REMOTE_ADDRESS,GROUP_ID,APP_NAME + FLOW_POSTFIX, source -> JSON.parseObject(source , new TypeReference<List<FlowRule>>(){})
        );
        FlowRuleManager.register2Property(ds.getProperty());

    }

    private void loadClusterClientConfig() {
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);
        ClusterClientConfig clientConfig = new ClusterClientConfig();
        clientConfig.setRequestTimeout(REQUST_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }
}
