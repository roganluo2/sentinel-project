package com.sentinel;

import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;

@Service
public class SentinelServiceImpl implements  ISentinelService{
    @Override
    public String sayHello(String name) {
        String result = "sentinel-provider:hi " + name;
        System.out.println("execute time:" + LocalDateTime.now());
        return result;
    }
}
