package org.example.user.controllers;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class HTMLCustomerController {
    private final CustomerService customerService;

    @GetMapping("/customer/show/{id}")
    public String show(@PathVariable(value = "id") Long id, Model model) {
        var value = customerService.read(id);
        model.addAttribute("customer", value);
        model.addAttribute("orders", value.orders());
        return "customer/withOrders";
    }
}
