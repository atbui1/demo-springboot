package com.edu.apidemo.database;

import com.edu.apidemo.models.*;
import com.edu.apidemo.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
public class Database {
    public static final Logger logger = LoggerFactory.getLogger(Database.class);


    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, UserRepository userRepository,
                                   RoleRepository roleRepository, CategoryRepository categoryRepository,
                                   CompanyRepository companyRepository) {

        return new CommandLineRunner() {
            @Transactional
            @Override
            public void run(String... args) throws Exception {
                System.out.println("init data --------------------------------------------------------------------------------------------------------------------------------------");
                //user
                User user1 = new User("manager", "boss", 2000, "a123456", 1);
                User user2 = new User("member 2", "Nguyen van two", 1999, "b112233", 1);
                User user3 = new User("member 3", "Nguyen van two", 1999, "b112233", 1);


/**
                List<ProductEntity> products = productRepository.findAll();
                if (products.size() < 1) {
                    ProductEntity product1 = new ProductEntity("iphone 6", 2005, 10.5, "description 1", true);
                    ProductEntity product2 = new ProductEntity("iphone 6s", 2006, 15.5, "description 2", false);
                    ProductEntity product3 = new ProductEntity("iphone 6plus", 2007, 13.5, "description 3", true);
                    ProductEntity product4 = new ProductEntity("iphone 7", 2008, 10.0, "description 4", false);
                    ProductEntity product5 = new ProductEntity("iphone 7s", 1999, 20.5, "description 5", true);
                    ProductEntity product6 = new ProductEntity("iphone 7plus", 2010, 17.2, "description 6", false);
                    ProductEntity product7 = new ProductEntity("iphone 8", 2005, 14.3, "description 7", true);
                    ProductEntity product8 = new ProductEntity("iphone 8s", 2006, 19.7, "description 8", false);
                    ProductEntity product9 = new ProductEntity("iphone x", 2007, 30.5, "description 9", true);
                    ProductEntity product10 = new ProductEntity("iphone xs", 2008, 20.5, "description 10", false);
                    ProductEntity product11 = new ProductEntity("iphone 11", 2009, 25.6, "description 11", true);

                    logger.info("INSERT tblProduct PRODUCT 1: " + productRepository.save(product1));
                    logger.info("INSERT tblProduct PRODUCT 2: " + productRepository.save(product2));
                    logger.info("INSERT tblProduct PRODUCT 3: " + productRepository.save(product3));
                    logger.info("INSERT tblProduct PRODUCT 4: " + productRepository.save(product4));
                    logger.info("INSERT tblProduct PRODUCT 5: " + productRepository.save(product5));
                    logger.info("INSERT tblProduct PRODUCT 6: " + productRepository.save(product6));
                    logger.info("INSERT tblProduct PRODUCT 7: " + productRepository.save(product7));
                    logger.info("INSERT tblProduct PRODUCT 8: " + productRepository.save(product8));
                    logger.info("INSERT tblProduct PRODUCT 9: " + productRepository.save(product9));
                    logger.info("INSERT tblProduct PRODUCT 10: " + productRepository.save(product10));
                    logger.info("INSERT tblProduct PRODUCT 11: " + productRepository.save(product11));
                    System.out.println("END DATA PRODUCT --------------------------------------------------------------------------------------------------------------------------------------");
                }
 */

/**
                List<Role> roles = roleRepository.findAll();
                if (roles.size() < 1) {
                    Role manager = new Role("manager");
                    Role staff = new Role("staff");
                    Role employee = new Role("employee");

                    Role admin = new Role("admin");
                    Role mod = new Role("mod");
                    Role member = new Role("member");

                    logger.info("INSERT tblRole ROLE: " + roleRepository.save(manager));
                    logger.info("INSERT tblRole ROLE: " + roleRepository.save(staff));
                    logger.info("INSERT tblRole ROLE: " + roleRepository.save(employee));
                    logger.info("INSERT tblRole ROLE: " + roleRepository.save(admin));
                    logger.info("INSERT tblRole ROLE: " + roleRepository.save(mod));
                    logger.info("INSERT tblRole ROLE: " + roleRepository.save(member));
                    System.out.println("END DATA ROLE --------------------------------------------------------------------------------------------------------------------------------------");
                }
*/
/**
                List<CompanyEntity> companies = companyRepository.findAll();
                if (companies.size() < 1) {
                    CompanyEntity company1 = new CompanyEntity("Apple", "US");
                    CompanyEntity company2 = new CompanyEntity("Samsung", "Korea");
                    CompanyEntity company3 = new CompanyEntity("Oppo", "China");
                    CompanyEntity company4 = new CompanyEntity("LG", "Korea");
                    CompanyEntity company5 = new CompanyEntity("FPT", "Vietnam");

                    logger.info("INSERT INTO TBL COMPANY: " + companyRepository.save(company1));
                    logger.info("INSERT INTO TBL COMPANY: " + companyRepository.save(company2));
                    logger.info("INSERT INTO TBL COMPANY: " + companyRepository.save(company3));
                    logger.info("INSERT INTO TBL COMPANY: " + companyRepository.save(company4));
                    logger.info("INSERT INTO TBL COMPANY: " + companyRepository.save(company5));
                    System.out.println("END DATA COMPANY --------------------------------------------------------------------------------------------------------------------------------------");

                }
 */
/**
                List<CategoryEntity> categories = categoryRepository.findAll();
                if (categories.size() < 1) {
                    CompanyEntity company1 = companyRepository.findById(1L).get();
                    CompanyEntity company2 = companyRepository.findById(2L).get();
                    CompanyEntity company3 = companyRepository.findById(3L).get();
                    CompanyEntity company4 = companyRepository.findById(4L).get();
                    CompanyEntity company5 = companyRepository.findById(5L).get();
                    CategoryEntity category1 = new CategoryEntity("mobileA", "MB", company1);
                    CategoryEntity category2 = new CategoryEntity("laptopA", "LT", company1);
                    CategoryEntity category3 = new CategoryEntity("tabletA", "TL", company1);
                    CategoryEntity category4 = new CategoryEntity("watchA", "SW", company1);
                    CategoryEntity category5 = new CategoryEntity("mobileS", "MB5", company2);
                    CategoryEntity category6 = new CategoryEntity("laptopSS", "LT6", company2);
                    CategoryEntity category7 = new CategoryEntity("tabletS", "TL7", company2);
                    CategoryEntity category8 = new CategoryEntity("watchO", "SW8", company3);
                    CategoryEntity category9 = new CategoryEntity("mobileO", "MB9", company3);
                    CategoryEntity category10 = new CategoryEntity("laptopO", "LT10", company3);
                    CategoryEntity category11 = new CategoryEntity("tabletL", "TL11", company4);
                    CategoryEntity category12 = new CategoryEntity("watchL", "SW12", company4);
                    CategoryEntity category13 = new CategoryEntity("mobileF", "SW12", company5);
                    CategoryEntity category14 = new CategoryEntity("tabletF", "SW12", company5);
                    CategoryEntity category15 = new CategoryEntity("watchF", "SW12", company5);

                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category1));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category2));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category3));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category4));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category5));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category6));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category7));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category8));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category9));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category10));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category11));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category12));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category13));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category14));
                    logger.info("INSERT INTO TBL CATEGORY: " + categoryRepository.save(category15));
                    System.out.println("END DATA CATEGORY --------------------------------------------------------------------------------------------------------------------------------------");

                }
*/
/**
                List<ProductEntity> products = productRepository.findAll();
                if (products.size() < 1) {
                    CategoryEntity category1 = categoryRepository.findById(1L).get();
                    CategoryEntity category2 = categoryRepository.findById(2L).get();
                    CategoryEntity category3 = categoryRepository.findById(3L).get();
                    CategoryEntity category4 = categoryRepository.findById(4L).get();
                    ProductEntity product1 = new ProductEntity("iphone 6", 2005, 10.5, "description 1", true, category1);
                    ProductEntity product2 = new ProductEntity("iphone 6s", 2006, 15.5, "description 2", false, category1);
                    ProductEntity product3 = new ProductEntity("iphone 6plus", 2007, 13.5, "description 3", true, category1);
                    ProductEntity product4 = new ProductEntity("iphone 7", 2008, 10.0, "description 4", false, category2);
                    ProductEntity product5 = new ProductEntity("iphone 7s", 1999, 20.5, "description 5", true, category2);
                    ProductEntity product6 = new ProductEntity("iphone 7plus", 2010, 17.2, "description 6", false, category2);
                    ProductEntity product7 = new ProductEntity("iphone 8", 2005, 14.3, "description 7", true, category3);
                    ProductEntity product8 = new ProductEntity("iphone 8s", 2006, 19.7, "description 8", false, category3);
                    ProductEntity product9 = new ProductEntity("iphone x", 2007, 30.5, "description 9", true, category3);
                    ProductEntity product10 = new ProductEntity("iphone xs", 2008, 20.5, "description 10", false, category4);
                    ProductEntity product11 = new ProductEntity("iphone 11", 2009, 25.6, "description 11", true, category4);

                    logger.info("INSERT tblProduct PRODUCT 1: " + productRepository.save(product1));
                    logger.info("INSERT tblProduct PRODUCT 2: " + productRepository.save(product2));
                    logger.info("INSERT tblProduct PRODUCT 3: " + productRepository.save(product3));
                    logger.info("INSERT tblProduct PRODUCT 4: " + productRepository.save(product4));
                    logger.info("INSERT tblProduct PRODUCT 5: " + productRepository.save(product5));
                    logger.info("INSERT tblProduct PRODUCT 6: " + productRepository.save(product6));
                    logger.info("INSERT tblProduct PRODUCT 7: " + productRepository.save(product7));
                    logger.info("INSERT tblProduct PRODUCT 8: " + productRepository.save(product8));
                    logger.info("INSERT tblProduct PRODUCT 9: " + productRepository.save(product9));
                    logger.info("INSERT tblProduct PRODUCT 10: " + productRepository.save(product10));
                    logger.info("INSERT tblProduct PRODUCT 11: " + productRepository.save(product11));
                    System.out.println("END DATA PRODUCT --------------------------------------------------------------------------------------------------------------------------------------");
                }
*/
            }
        };
    }
}
