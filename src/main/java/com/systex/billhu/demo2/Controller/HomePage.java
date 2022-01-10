package com.systex.billhu.demo2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePage {
    @GetMapping("/HomePage")
    public String processHomePage() {
        return "HomePage";
    }

    @GetMapping("/Demo1.in")
    public String proeccDem01() {
        return "Demo1input";
    }

    @GetMapping("/Demo2.in")
    public String proeccDem02() {
        return "Demo2input";
    }

    @GetMapping("/Demo4.in")
    public String proeccDem04() {
        return "Demo4input";
    }
    @GetMapping("/Demo3.in")
    public String proeccDem03() {
        return "Demo3input";
    }

}
