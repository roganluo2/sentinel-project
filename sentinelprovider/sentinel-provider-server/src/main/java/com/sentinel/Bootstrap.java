package com.sentinel;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Collections;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
//        initRules();
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProviderConfig.class);
        applicationContext.start();
        System.in.read();
    }

    public static void initRules()
    {
        FlowRule flowRule  = new FlowRule();
        flowRule.setCount(10);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setResource("com.sentinel.ISentinelService:sayHello(java.lang.String)");
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        flowRule.setClusterMode(true);
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }

}
