package vn.iotstar.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.ProductImage;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DataInitializer(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category c1 = categoryRepository.findByCategoryName("Vợt Cầu Lông").orElseGet(() -> {
            Category cat = new Category();
            cat.setCategoryName("Vợt Cầu Lông");
            cat.setIcon("badminton_racket.png");
            return categoryRepository.save(cat);
        });

        Category c2 = categoryRepository.findByCategoryName("Giày Cầu Lông").orElseGet(() -> {
            Category cat = new Category();
            cat.setCategoryName("Giày Cầu Lông");
            cat.setIcon("badminton_shoes.png");
            return categoryRepository.save(cat);
        });

        Category c3 = categoryRepository.findByCategoryName("Phụ Kiện Cầu Lông").orElseGet(() -> {
            Category cat = new Category();
            cat.setCategoryName("Phụ Kiện Cầu Lông");
            cat.setIcon("accessories.png");
            return categoryRepository.save(cat);
        });

        if (productRepository.count() > 0) {
            return;
        }

        Product p1 = Product.builder()
                .name("Yonex Astrox 100 ZZ")
                .brand("Yonex")
                .price(new BigDecimal("4500000"))
                .quantity(10)
                .description("Vợt cầu lông cao cấp Yonex Astrox 100 ZZ.")
                .category(c1)
                .images(new ArrayList<>())
                .build();
        p1.getImages().add(ProductImage.builder().product(p1).imageUrl("astrox100zz_main.jpg").primary(true).displayOrder(0).createdAt(LocalDateTime.now()).build());
        p1.getImages().add(ProductImage.builder().product(p1).imageUrl("astrox100zz_detail.jpg").primary(false).displayOrder(1).createdAt(LocalDateTime.now()).build());

        Product p2 = Product.builder()
                .name("Yonex Astrox 88D Pro")
                .brand("Yonex")
                .price(new BigDecimal("4200000"))
                .quantity(12)
                .description("Vợt cầu lông Yonex Astrox 88D Pro chuyên công.")
                .category(c1)
                .images(new ArrayList<>())
                .build();
        p2.getImages().add(ProductImage.builder().product(p2).imageUrl("astrox88dpro_main.jpg").primary(true).displayOrder(0).createdAt(LocalDateTime.now()).build());

        Product p3 = Product.builder()
                .name("Victor Thruster Ryuga II Pro")
                .brand("Victor")
                .price(new BigDecimal("4290000"))
                .quantity(8)
                .description("Vợt cầu lông Victor Thruster Ryuga II Pro đập cực nảy.")
                .category(c1)
                .images(new ArrayList<>())
                .build();
        p3.getImages().add(ProductImage.builder().product(p3).imageUrl("ryuga2pro_main.jpg").primary(true).displayOrder(0).createdAt(LocalDateTime.now()).build());

        Product p4 = Product.builder()
                .name("Giày Yonex Power Cushion 65Z3")
                .brand("Yonex")
                .price(new BigDecimal("2900000"))
                .quantity(15)
                .description("Giày cầu lông êm ái bám sân cao cấp.")
                .category(c2)
                .images(new ArrayList<>())
                .build();

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
    }
}
