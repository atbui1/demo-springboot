package com.edu.apidemo.controllers;

import com.edu.apidemo.models.CompanyEntity;
import com.edu.apidemo.models.ResponseObject;
import com.edu.apidemo.repositories.CompanyRepository;
import com.edu.apidemo.request.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * get all company
     * --> ok
     */
    @GetMapping("company")
    ResponseEntity<?> getAllCompany() {
        List<CompanyEntity> companies = companyRepository.findAll();
        if (companies.size() < 1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "not found company", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "get all companies successfully", companies)
        );
    }

    /**
     * get company by id
     * --> ok
     */
    @GetMapping("company/{id}")
    ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found company's id: " + id, "")
            ) ;
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Found company's id: " +id, company)
        );
    }

    /**
     * add new company
     * --> ok
     */
    @PostMapping("company")
    ResponseEntity<?> addNewCompany(@RequestBody CompanyRequest companyRequest) {
        List<CompanyEntity> companies = companyRepository.findCompanyByName(companyRequest.getName().trim());
        if (companies.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResponseObject("failed", "Company's name is exist", "")
            );
        }
        CompanyEntity company = new CompanyEntity(
                companyRequest.getName(),
                companyRequest.getCountry()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "add new company successfully", companyRepository.save(company))
        );
    }

    /**
     * update company
     * --> ok
     */
    @PutMapping("company/{id}")
    ResponseEntity<?> updateCompany(@RequestBody CompanyRequest companyRequest, @PathVariable Long id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "no found company's id: " + id, "")
            );
        }
        company.map((item) -> {
            item.setName(companyRequest.getName());
            item.setCountry(companyRequest.getCountry());
            return companyRepository.save(item);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "update company successfully", company)
        );
    }

    /**
     * upsert company
     * --> ok
     */
    @PutMapping("company/upsert/{id}")
    ResponseEntity<?> upsertCompany(@RequestBody CompanyRequest companyRequest, @PathVariable Long id) {
        CompanyEntity company = companyRepository.findById(id).map((item) -> {
            item.setName(companyRequest.getName());
            item.setCountry(companyRequest.getCountry());
            return companyRepository.save(item);
        }).orElseGet(()->{
           CompanyEntity newCom = new CompanyEntity(
                   companyRequest.getId(),
                   companyRequest.getName(),
                   companyRequest.getCountry()
           );
            return companyRepository.save(newCom);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "upsert -->  company successfully", company)
        );
    }

    /**
     * delete company
     * --> ok
     */
    @DeleteMapping("company/{id}")
    ResponseEntity<?> deleteCompanyById(@PathVariable Long id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);
        if (!company.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Not found company's id: " + id, "")
            );
        }
        companyRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete company successfully", "")
        );
    }
}
