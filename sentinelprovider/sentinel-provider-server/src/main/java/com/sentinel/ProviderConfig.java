package com.sentinel;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.registry.integration.RegistryProtocol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@DubboComponentScan("com.sentinel")
@Configuration
public class ProviderConfig {

    @Bean
    public ApplicationConfig applicationConfig()
    {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("sentinel-cluster-provider");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig()
    {
        RegistryConfig config = new RegistryConfig();
        config.setAddress("zookeeper://120.79.226.150:2181");
        config.setCheck(false);
        return config;
    }

    @Bean
    public ProtocolConfig protocolConfig()
    {
        ProtocolConfig config = new ProtocolConfig();
        config.setPort(20880);
        config.setName("dubbo");
        return config;
    }




}
