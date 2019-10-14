package com.springcloud.consumerdemo1.rabbitmq.controller;

import com.springcloud.consumerdemo1.rabbitmq.config.CallbackConfig;
import com.springcloud.consumerdemo1.rabbitmq.entity.Message;
import com.springcloud.consumerdemo1.rabbitmq.service.MqService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping(value = "/countrySwitch")
@Api(value = "消息队列路由测试")
@Slf4j
public class CountryExchangeController {
    @Autowired
    MqService mqService;

    @RequestMapping(value = "/toCountry", method = RequestMethod.POST)
    public Integer toCountry(@RequestParam(name = "sendAddr",required = true) Integer sendAddr,
                             @RequestParam(name = "msg",required = true) String msg) {
        log.info("送达地址:[{}]", sendAddr);
        log.info("消息本体:[{}]",msg);
        if(sendAddr != 1 && StringUtils.isNotBlank(msg)){
            switchQueue(sendAddr,msg);
            return 200;
        }
        return 201;
    }

    private void switchQueue(Integer sendAddr, String msgBody) {
        String queueName = CallbackConfig.RESOURCE_MAP.get(sendAddr);
        if (queueName != null && !"".equals(queueName)) {
            Message<String> msg = new Message<>();
            msg.setCreateTime(Timestamp.from(new Date().toInstant()));
            msg.setQueueName(queueName);
            msg.addMessage(msgBody);
            //msg.addContext(CONTEXT_FACTORY, factoryType);
            msg.setExchange(CallbackConfig.EXCHANGE_NAME);
            //msg.addContext(CONTEXT_SECURITY, SecurityContextHolder.getContext());
            //msg.addContext(CONTEXT_OPERATION, resourceChangeRequest.getResourceOperation());
            mqService.push(msg);
        }
    }
}
