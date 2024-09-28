package com.suretrust.repository;

import com.suretrust.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    Optional<Brand> findByName(String name);

}
