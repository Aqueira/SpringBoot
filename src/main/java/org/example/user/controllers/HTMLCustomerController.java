package org.example.user.controllers;


import org.example.customer.application.CustomerService;
import org.example.customer.application.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class HTMLCustomerController {
    private final CustomerService customerService;

    @Autowired
    public HTMLCustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customer/show/{id}")
    public String show(@PathVariable(value = "id") Long id, Model model) {
        return customerService.read(id).map(value -> {
            model.addAttribute("customer", value);
            model.addAttribute("orders", value.orders());
            return "customer/withOrders";
        }).orElseThrow(RuntimeException::new);
    }
}
