package com.example.demo.controller;

import com.example.demo.service.CustomerService;
import com.strawberry.bean.Customer;
import com.strawberry.service.CustomerFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class CustomerController implements CustomerFeignService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/customers")
    public Customer get(@RequestParam("name") String name, @RequestParam("telephone") String telephone) {
        logger.info("execute name is {}, telephone is {}", name, telephone);
        return customerService.selectByName(name, telephone);
    }

    @GetMapping("/customers/cache")
    public Object getCache() {

        redisTemplate.opsForValue().set("spring-boot-demo-redis-test-info", "hello world", 5, TimeUnit.SECONDS);
        return redisTemplate.opsForValue().get("spring-boot-demo-redis-test-info");
    }

}
