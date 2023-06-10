package com.works.jpa.controllers;

import com.works.jpa.entities.Product;
import com.works.jpa.repositories.CustomerRepository;
import com.works.jpa.repositories.ProductRepository;
import com.works.jpa.service.CustomerService;
import com.works.jpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    final ProductService productService;
    private Long updateId = 0l;
    private final CustomerRepository customerRepository;
    final CustomerService customerService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(defaultValue = "0") int page){
        model.addAttribute("products", productService.allProduct(page));
        model.addAttribute("customer",customerService.user());
        updateId = 0l;
        return "dashboard";
    }

    @GetMapping("/dashboard/{pid}")
    public String dashboardUpdate(Model model,@PathVariable Long pid,Product product,@RequestParam(defaultValue = "0") int page){
        updateId = pid;
        model.addAttribute("product",productService.getSingleProduct(pid));
        model.addAttribute("products", productService.allProduct(page));
        model.addAttribute("customer",customerService.user());
        return "dashboardUpdate";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product){
        productService.save(product);
        return "redirect:/dashboard";
    }

    @GetMapping("/deleteProduct/{stPid}")
    public String deleteProduct(@PathVariable String stPid){
        boolean status = productService.deleteProduct(stPid);
        System.err.println("Status: " +status);
        return "redirect:/dashboard";
    }

    @PostMapping("/productUpdate")
    public String productUpdate (Product product ,Model model){
        model.addAttribute("customer",customerService.user());
        product.setPid(updateId);
        productService.updateProduct(product);
        System.out.println( product );
        return "redirect:/dashboard";
    }

}
