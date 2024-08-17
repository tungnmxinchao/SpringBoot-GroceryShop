package org.grocery.groceryshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/admin")
public class AdminController {
    @RequestMapping("/admin")
    public String admin() {
        return "admin/index";
    }

}
