package com.study.security.demospringsecurityform.form;

import com.study.security.demospringsecurityform.account.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {
    private SampleService sampleService;
    private AccountRepository accountRepository;

    public SampleController(SampleService sampleService, AccountRepository accountRepository) {
        this.sampleService = sampleService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if(principal == null) {
            model.addAttribute("message", "Hello Spring Security");
        } else {
            model.addAttribute("message", "Hello, " + principal.getName());
        }

        return "index";
    }
    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");

        return "info";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        //로그인 하지 않았을 때는 principal 객체는 null이 들어간
        model.addAttribute("message", "Hello " + principal.getName());
        sampleService.dashboard();

        return "index";
    }
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin " + principal.getName());

        return "admin";
    }
}
