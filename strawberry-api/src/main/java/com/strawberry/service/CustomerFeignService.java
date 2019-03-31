package com.strawberry.service;


import com.strawberry.bean.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("strawberry-service")
public interface CustomerFeignService {

    @GetMapping(value = "/customers")
    public Customer get(@RequestParam("name") String name, @RequestParam("telephone") String telephone);

}
