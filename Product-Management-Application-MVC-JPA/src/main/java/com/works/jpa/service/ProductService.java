package com.works.jpa.service;

import com.works.jpa.entities.Customer;
import com.works.jpa.entities.Product;
import com.works.jpa.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;
    final HttpServletResponse response;
    final HttpServletRequest request;

    public Product save(Product product){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        product.setCid(customer.getCid());
        return productRepository.save(product);
    }

    public Page<Product> allProduct(int page){

        Customer customer = (Customer) request.getSession().getAttribute("customer");

        Pageable pageable = PageRequest.of(page,5);

        Page<Product> productPage =productRepository.findByCidEqualsAllIgnoreCase(customer.getCid(),pageable);


        return productPage;
    }

    public boolean deleteProduct(String stPid){
        try {
            long pid = Long.parseLong(stPid);
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            boolean status = productRepository.existsByPidEqualsAndCidEquals(pid,customer.getCid());
            if(status){
                productRepository.deleteById(pid);
                return true;
            }
        }catch (Exception exception){
            System.err.println(exception);
        }
        return false;
    }

    public Product getSingleProduct(Long pid){

        Optional<Product> optionalProduct = productRepository.findById(pid);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        return null;
    }

    public boolean updateProduct(Product product){
        try {
            productRepository.saveAndFlush(product);
            return true;
        }catch (Exception exception){
            System.out.println(exception);
        }

        return false;
    }

}
