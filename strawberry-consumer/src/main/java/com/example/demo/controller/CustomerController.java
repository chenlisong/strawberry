package com.example.demo.controller;

import com.strawberry.bean.Customer;
import com.strawberry.service.CustomerFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer/customers")
public class CustomerController {

    @Autowired
    private CustomerFeignService customerFeignService;

    @GetMapping("")
    public Customer get(@RequestParam("name") String name, @RequestParam("telephone") String telephone) {

        return customerFeignService.get(name, telephone);
    }
}
