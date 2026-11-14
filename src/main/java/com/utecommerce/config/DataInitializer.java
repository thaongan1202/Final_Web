package com.utecommerce.config;

import com.utecommerce.entity.Brand;
import com.utecommerce.entity.Category;
import com.utecommerce.entity.Product;
import com.utecommerce.entity.ProductImage;
import com.utecommerce.entity.ProductVariant;
import com.utecommerce.entity.Role;
import com.utecommerce.entity.User;
import com.utecommerce.repository.BrandRepository;
import com.utecommerce.repository.CategoryRepository;
import com.utecommerce.repository.ProductRepository;
import com.utecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            initUsers();
            initCategoriesAndProducts();
        } catch (Exception e) {
            log.warn("DataInitializer skipped or already populated: {}", e.getMessage());
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            log.info("Khởi tạo tài khoản mẫu...");

            User admin = User.builder()
                    .fullName("Quản trị viên UTE")
                    .email("admin@utecommerce.com")
                    .password(passwordEncoder.encode("admin123"))
                    .phone("0901234567")
                    .role(Role.ROLE_ADMIN)
                    .active(true)
                    .build();

            User user = User.builder()
                    .fullName("Nguyễn Văn Sinh Viên")
                    .email("user@utecommerce.com")
                    .password(passwordEncoder.encode("user123"))
                    .phone("0912345678")
                    .role(Role.ROLE_USER)
                    .active(true)
                    .build();

            userRepository.saveAll(List.of(admin, user));
            log.info("Đã tạo tài khoản admin (admin@utecommerce.com / admin123) và user (user@utecommerce.com / user123)");
        }
    }

    private void initCategoriesAndProducts() {
        if (categoryRepository.count() == 0) {
            log.info("Khởi tạo danh mục & sản phẩm thời trang mẫu...");

            Category catTShirt = categoryRepository.save(Category.builder()
                    .name("Áo Thun & Polo")
                    .slug("ao-thun-polo")
                    .description("Các mẫu áo thun thời trang nam nữ thoáng mát")
                    .imageUrl("https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=200")
                    .active(true)
                    .build());

            Category catShirt = categoryRepository.save(Category.builder()
                    .name("Áo Sơ Mi")
                    .slug("ao-so-mi")
                    .description("Sơ mi công sở và đi học thanh lịch")
                    .imageUrl("https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=200")
                    .active(true)
                    .build());

            Brand brand = brandRepository.save(Brand.builder()
                    .name("UTE Casual")
                    .slug("ute-casual")
                    .active(true)
                    .build());

            Product p1 = Product.builder()
                    .name("Áo Thun Nam Cotton Oversize UTE")
                    .slug("ao-thun-nam-cotton-oversize-ute")
                    .shortDescription("Áo thun 100% cotton thoáng mát, form rộng unisex.")
                    .description("Chất liệu cotton 2 chiều mềm mại, co giãn tốt, thấm hút mồ hôi, thích hợp cho sinh viên hoạt động cả ngày.")
                    .basePrice(new BigDecimal("189000"))
                    .originalPrice(new BigDecimal("250000"))
                    .category(catTShirt)
                    .brand(brand)
                    .status(Product.ProductStatus.ACTIVE)
                    .isFeatured(true)
                    .build();

            ProductVariant v1 = ProductVariant.builder()
                    .product(p1)
                    .sku("TSHIRT-UTE-BLK-L")
                    .size("L")
                    .color("Đen")
                    .price(new BigDecimal("189000"))
                    .stockQuantity(50)
                    .active(true)
                    .build();

            ProductImage img1 = ProductImage.builder()
                    .product(p1)
                    .secureUrl("https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600")
                    .isPrimary(true)
                    .altText("Áo thun UTE")
                    .sortOrder(1)
                    .build();

            p1.getVariants().add(v1);
            p1.getImages().add(img1);
            productRepository.save(p1);

            log.info("Đã khởi tạo xong dữ liệu mẫu cho sản phẩm & danh mục.");
        }
    }
}

