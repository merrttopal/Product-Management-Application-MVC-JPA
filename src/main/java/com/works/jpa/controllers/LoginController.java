package com.works.jpa.controllers;

import com.works.jpa.entities.Customer;
import com.works.jpa.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class LoginController {

     final CustomerService customerService;
    final HttpServletRequest request;
    final HttpServletResponse response;
    String error = "";
    String success = "";

    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("error", error);
        model.addAttribute("success", success);

        error = "";
        success = "";
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(Customer customer){
        Customer c = customerService.login(customer.getEmail(),customer.getPassword());
        if(c == null){
            // If the customer is null, it means either the email or the password is wrong
            error = "Wrong email or password!";
            return "redirect:/";
        } else {
            // If the customer is not null, it means the login was successful
            // Create a cookie with the customer id and add it to the response
            Cookie cookie = new Cookie("customer", ""+c.getCid());
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            // Set the customer as a session attribute and redirect to the dashboard
            request.getSession().setAttribute("customer",c);
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        request.getSession().removeAttribute("customer");
        Cookie cookie = new Cookie("customer", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
