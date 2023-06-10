package com.works.jpa.service;

import com.works.jpa.entities.Customer;
import com.works.jpa.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerService {

    final CustomerRepository customerRepository;
    final HttpServletRequest request;
    final HttpServletResponse response;

    public Customer login(String email,String password){
        try {
            Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCaseAndPasswordEquals(email,password);
            if(optionalCustomer.isPresent()){
                return optionalCustomer.get();}

        }catch (Exception exception){
            System.err.println("login error: "+exception);
        }

        return null;
    }
    public Customer loginCustomer(String email){
        Optional<Customer>optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(email);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();}
        return null;
    }

    public Customer save(Customer customer){
        customer.setStatus(true);
        try {

            return customerRepository.save(customer);
        }
        catch (DataIntegrityViolationException exception){
            System.out.println("Email Duplicate: "+exception);
            return customer;
        }
        catch (Exception ex){
            return null;
        }


    }
    public String user(){
        try {
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            String username =  customer.getName()+" "+customer.getSurname();
            return username;

        }catch (Exception exception){
            return null;
        }
    }

}
