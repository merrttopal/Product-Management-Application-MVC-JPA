package com.works.jpa.repositories;

import com.works.jpa.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCidEquals(Long cid);

    Page<Product> findByCidEqualsAllIgnoreCase(Long cid, Pageable pageable);

    boolean existsByPidEqualsAndCidEquals(Long pid, Long cid);


}