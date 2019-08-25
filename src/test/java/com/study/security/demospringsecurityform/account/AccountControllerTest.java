package com.study.security.demospringsecurityform.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;

    @Test
    public void indexAnonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void indexAnonymousWithAnnotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void indexUser() throws Exception {
        mockMvc.perform(get("/").with(user("nokcha").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "nokcha", roles = "USER")
    public void indexUserWithAnnotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void indexUserWithCustomizedAnnotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminUser() throws Exception {
        mockMvc.perform(get("/admin").with(user("nokcha").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional//db rollback을 위해
    public void login() throws Exception {
        Account user = createUser("nokchax", "123");

        mockMvc.perform(formLogin().user(user.getUsername()).password(user.getPassword()))
                .andExpect(authenticated());
    }

    @Test
    @Transactional//db rollback을 위해
    public void loginFail_WrongPw() throws Exception {
        Account user = createUser("nokchax", "123");

        mockMvc.perform(formLogin().user(user.getUsername()).password("1234"))
                .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        //save new mock user
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");

        accountService.save(account);

        Account dummyAccount = new Account();
        dummyAccount.setUsername(username);
        dummyAccount.setPassword(password);

        return dummyAccount;
    }
}