package com.earth.message.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${earth-order-service}", name = "ConsumerFeignService")
public interface ConsumerFeignService {
    @RequestMapping(value = "/TestMessageAck", method = RequestMethod.GET)
    String getOrder();
}
