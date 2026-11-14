# UTECommerce - Website Thương Mại Điện Tử Thời Trang

Đồ án môn học: **Xây dựng website thương mại điện tử thời trang UTECommerce**  
Trường: **Đại học Sư phạm Kỹ thuật TP.HCM (HCMUTE)**  
Quy mô: **Nhóm 4 thành viên**  
Phiên bản khung sườn: **v1.0.0-SNAPSHOT**

---

## 🛠️ Công Nghệ Sử Dụng (Tech Stack)

- **Ngôn ngữ & Nền tảng:** Java 17+, Spring Boot 3.x / 4.x
- **Bảo mật & Phiên:** Spring Security (Form Login + Remember-Me + CSRF Token + BCrypt PasswordEncoder)
- **Tầng dữ liệu:** Spring Data JPA, Hibernate ORM, MySQL Database (Cổng `3306`, DB: `utecommerce_db`)
- **Giao diện người dùng:** Spring MVC + Thymeleaf Template Engine + Bootstrap 5.3 + Bootstrap Icons
- **Thông báo Realtime:** Spring WebSocket + STOMP Broker + SockJS
- **Lưu trữ đám mây (Media/Images):** Cloudinary SDK (Upload ảnh sản phẩm, danh mục, avatar)
- **Công cụ phát triển & Build:** Maven (`./mvnw`), Git / GitHub

---

## 📂 Cấu Trúc Package Chuẩn Của Dự Án

```
src/main/java/com/utecommerce/
├── UtecommerceApplication.java        # Entry point khởi chạy ứng dụng
├── common/                            # Các class dùng chung
│   └── BaseEntity.java                # Base entity chứa ID, createdAt, updatedAt
├── config/                            # Cấu hình hệ thống
│   ├── SecurityConfig.java            # Cấu hình Spring Security & phân quyền
│   ├── CustomUserDetailsService.java  # Tải thông tin người dùng từ MySQL
│   ├── CloudinaryConfig.java          # Cấu hình Cloudinary SDK Bean
│   ├── WebSocketConfig.java           # Cấu hình STOMP WebSocket broker
│   └── DataInitializer.java           # Tự động nạp tài khoản admin/user & sản phẩm mẫu
├── controller/                        # Tầng MVC Controller xử lý Request
│   ├── HomeController.java            # Trang chủ (/)
│   ├── AuthController.java            # Đăng nhập (/login), Đăng ký (/register)
│   ├── ProductController.java         # Danh sách sản phẩm (/products), Chi tiết (/products/{slug})
│   ├── CartController.java            # Giỏ hàng (/cart)
│   └── admin/
│       └── AdminDashboardController.java # Quản trị (/admin/dashboard)
├── service/                           # Tầng nghiệp vụ (Interface & Impl)
│   ├── UserService.java & impl/
│   ├── ProductService.java & impl/
│   ├── CategoryService.java & impl/
│   ├── CartService.java
│   ├── OrderService.java
│   └── CloudinaryService.java         # Upload ảnh lên Cloudinary
├── repository/                        # Tầng DAO / Spring Data JPA Repositories
│   ├── UserRepository.java
│   ├── CategoryRepository.java
│   ├── BrandRepository.java
│   ├── ProductRepository.java
│   ├── ProductVariantRepository.java
│   ├── CartRepository.java & CartItemRepository.java
│   ├── OrderRepository.java & OrderItemRepository.java
│   ├── PromotionRepository.java
│   └── AddressRepository.java
├── entity/                            # Các thực thể JPA ánh xạ bảng MySQL
│   ├── User.java & Role.java
│   ├── Address.java
│   ├── Category.java
│   ├── Brand.java
│   ├── Product.java
│   ├── ProductVariant.java            # Biến thể (SKU, Size, Color, Stock, Price)
│   ├── ProductImage.java              # Ảnh Cloudinary
│   ├── Cart.java & CartItem.java
│   ├── Order.java, OrderItem.java & OrderStatus.java
│   └── Promotion.java                 # Voucher giảm giá
└── dto/                               # Data Transfer Objects
    ├── auth/                          # RegisterRequest, LoginRequest, UserResponse
    ├── product/                       # ProductDTO, ProductVariantDTO, CategoryDTO
    ├── cart/                          # CartDTO, CartItemDTO
    ├── order/                         # CheckoutRequest
    └── common/                        # ApiResponse<T>
```

---

## 🎨 Cấu Trúc Khung Giao Diện (Thymeleaf + Bootstrap 5)

Tất cả giao diện được đặt trong thư mục `src/main/resources/templates/`:
- **`layout/main.html`**: Master layout khung trang chuẩn bao gồm:
  - Header (`layout/header.html`): Logo UTECommerce, thanh tìm kiếm, giỏ hàng, menu tài khoản & quyền Admin.
  - Sidebar (`layout/sidebar.html`): Bộ lọc danh mục thời trang (Áo thun, Sơ mi, Quần jeans, Váy đầm...), mức giá, kích thước (S/M/L/XL), mini banner voucher.
  - Offcanvas Sidebar cho màn hình điện thoại/tablet.
  - Toast Notification Realtime: Pop-up thông báo STOMP WebSocket.
  - Footer (`layout/footer.html`): Giới thiệu, chính sách đổi trả, liên hệ cơ sở 1 Võ Văn Ngân.
- **`client/home.html`**: Trang chủ mẫu với Hero Carousel Banner, danh mục nổi bật, danh sách sản phẩm thời trang thịnh hành, ưu đãi sinh viên UTE.
- **`client/products.html`**: Trang danh sách sản phẩm mẫu kết hợp 2 cột với Sidebar bộ lọc bên trái và Grid sản phẩm bên phải.
- **`client/product-detail.html`**: Trang chi tiết sản phẩm chọn size/màu/số lượng và thêm vào giỏ.
- **`client/login.html` & `register.html`**: Trang đăng nhập và đăng ký tài khoản.
- **`admin/dashboard.html`**: Bảng điều khiển quản trị thống kê tổng quan (Sản phẩm, Đơn hàng, Khách hàng, Doanh thu).

---

## 👥 Phân Chia Nhiệm Vụ 4 Thành Viên (M1 - M4)

Theo đặc tả tài liệu kiến trúc UTECommerce:

| Thành viên | Trụ cột đảm nhận | Phạm vi package & files chính |
| :--- | :--- | :--- |
| **Thành viên 1 (M1)** | **Identity, Security & User** | `config/SecurityConfig`, `auth/`, `user/`, `address/`, `templates/client/login.html`, `register.html`, trang profile, phân quyền CUSTOMER/ADMIN. |
| **Thành viên 2 (M2)** | **Catalog, Variant & Cloudinary** | `catalog/`, `discovery/`, `media/` (Cloudinary), `Product`, `ProductVariant` (SKU/size/color), upload ảnh gallery, CRUD sản phẩm admin & catalog client. |
| **Thành viên 3 (M3)** | **Cart, Order, Payment & Voucher** | `cart/`, `order/`, `promotion/`, `payment/`, giỏ hàng, quy trình checkout COD, snapshot đơn hàng, trừ/hoàn tồn kho an toàn, quản lý đơn phía khách và admin. |
| **Thành viên 4 (M4)** | **Reviews, Notification, Realtime & Dashboard** | `review/`, `returns/`, `notification/`, `dashboard/`, `WebSocketConfig`, STOMP push thông báo trạng thái đơn, duyệt đánh giá sản phẩm, bảng điều khiển admin. |

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Dự Án

### 1. Khởi động MySQL Server
Đảm bảo MySQL đang chạy trên cổng `3306`. Chạy lệnh SQL sau để tạo database:
```sql
CREATE DATABASE IF NOT EXISTS utecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Cấu hình `src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/utecommerce_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true&characterEncoding=UTF-8
    username: root
    password: "" # Điền password MySQL của bạn (nếu có)

cloudinary:
  cloud-name: your_cloud_name
  api-key: your_api_key
  api-secret: your_api_secret
```

### 3. Khởi chạy ứng dụng
Mở terminal tại thư mục gốc của dự án và chạy:
```bash
./mvnw spring-boot:run
```
Hoặc mở dự án trong **IntelliJ IDEA / VS Code / Eclipse** và chạy class `com.utecommerce.UtecommerceApplication`.

Truy cập website tại:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 🔑 Tài Khoản Mẫu Tự Động Khởi Tạo (Seed Data)

Khi ứng dụng chạy lần đầu trên database trống, hệ thống tự động tạo 2 tài khoản:
1. **Quản trị viên (Admin):**
   - Email: `admin@utecommerce.com`
   - Mật khẩu: `admin123`
   - Quyền: `ROLE_ADMIN` (Truy cập được `/admin/dashboard`)
2. **Khách hàng mẫu (Customer):**
   - Email: `user@utecommerce.com`
   - Mật khẩu: `user123`
   - Quyền: `ROLE_USER`

---

## 🌿 Quy Trình Git & Quy Ước Nhóm

1. Nhánh `main`: Bản demo ổn định nộp bài.
2. Nhánh `develop`: Nhánh tích hợp thường xuyên.
3. Nhánh tính năng cá nhân:
   - `feature/auth-*` (Thành viên 1)
   - `feature/catalog-*` (Thành viên 2)
   - `feature/cart-*`, `feature/order-*` (Thành viên 3)
   - `feature/review-*`, `feature/notification-*`, `feature/admin-*` (Thành viên 4)
4. Chuẩn commit: `feat(...)`, `fix(...)`, `refactor(...)`, `docs(...)`.
