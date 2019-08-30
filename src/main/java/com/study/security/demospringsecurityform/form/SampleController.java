package com.study.security.demospringsecurityform.form;

import com.study.security.demospringsecurityform.account.AccountRepository;
import com.study.security.demospringsecurityform.common.SecurityLogger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {
    private SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
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

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "Hello User " + principal.getName());

        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                SecurityLogger.log("Callable");
                return "Async Handler";
            }
        };
        /*
         * 이렇게 리턴을 하면
         * 이 응답을 처리하고 있던 스레드를 반환하고, 그 다음에 call()이 완료가 됐을때에 응답을 그때 보냄
         */
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC Before Async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC After Async service");

        return "Async Service";
    }
}
