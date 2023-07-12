package com.edu.apidemo.repositories;

import com.edu.apidemo.models.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    List<CompanyEntity> findCompanyByName(String name);
}
