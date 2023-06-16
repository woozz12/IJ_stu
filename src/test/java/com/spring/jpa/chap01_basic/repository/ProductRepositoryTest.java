package com.spring.jpa.chap01_basic.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach //테스트 돌리기 전에 실행
    void insertDummyData(){
        //given
        Product p1 = Product.builder()
                .name("아이폰")
                .category(Product.Category.ELECTRONIC)
                .price(1200000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(Product.Category.FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(Product.Category.FASHION)
                .price(140000)
                .build();
        Product p4 = Product.builder()
                .name("쓰레기")
                .category(Product.Category.FOOD)
                .build();

        //when
        Product saved1 = productRepository.save(p1);
        Product saved2 = productRepository.save(p2);
        Product saved3 = productRepository.save(p3);
        Product saved4 = productRepository.save(p4);

        //then
        assertNotNull(saved1);
        assertNotNull(saved3);
    }

    @Test
    @DisplayName("5번째 상품을 데이터베이스에 저장해야 한다.")
    void testSave() {
        Product product = Product.builder()
                .name("정장")
                .price(1000000)
                .category(Product.Category.FASHION)
                .build();

        Product saved = productRepository.save(product);

        assertNotNull(saved);
    }

    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야 한다")
    void testRemove() {
        //given
        long id = 2L;

        //when
        productRepository.deleteById(id);

        //then

    }

    @Test
    @DisplayName("상품 전체조회를 하면 상품의 개수가 4개여야 한다.")
    void testFindAll() {
        //given

        //when
        List<Product> productList = productRepository.findAll();

        //then
        productList.forEach(System.out::println);

        assertEquals(4, productList.size());
    }

    @Test
    @DisplayName("3번 상품을 조회하면 상품명이 '구두'여야 한다.")
    void testFindOne() {
        //given
        long id = 3L;
        //when
        Optional<Product> product = productRepository.findById(id);

        //then
        product.ifPresent(p -> {
            assertEquals("구두", p.getName());
        });

        Product foundProduct = product.get();
        assertNotNull(foundProduct);
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 변경해야 한다.")
    void testModify() {
        //given
        long id = 2L;
        String newName = "짜장면";
        int newPrice = 6000;

        //when
        //jpa에서 update는 따로 메서드를 지원하지 않고
        //조회를 한 후 setter로 변경하면 자동으로 update문이 나간다.
        //변경 후 다시 save를 호출한다.
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            p.setName(newName);
            p.setPrice(newPrice);

            productRepository.save(p);
        });

        //then
        assertTrue(product.isPresent());

        Product p = product.get();
        assertEquals("짜장면", p.getName());
    }
}