package com.sentinel.sentinelconsumer;

import com.sentinel.ISentinelService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelController {

    @Reference
    private ISentinelService iSentinelService;

    @RequestMapping("/sayHello")
    public String sayHello()
    {
        System.out.println("rest controller");
        return iSentinelService.sayHello("consumer");
    }

}
