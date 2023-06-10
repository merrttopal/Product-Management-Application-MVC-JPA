package com.works.jpa.repositories;

import com.works.jpa.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {


    List<ProductImage> findByPidEquals(Long pid);

    long deleteByImgIdEquals(Long imgId);
    boolean existsByImgIdEquals(Long imgId);

}