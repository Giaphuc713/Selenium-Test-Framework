# 📘 Documentation Automation Framework (Selenium Test Framework)

## 📌 1. Summary & Overview (Tóm Tắt Toàn Bộ Source Code)
Đây là một **Selenium Automation Framework** hoàn chỉnh được thiết kế theo mô hình **Page Object Model (POM)** kết hợp với **Data-Driven Testing** và hỗ trợ **Selenium Grid / Docker Grid**. 

### Các Thành Phần Chính của Codebase:
- **Kiến trúc Framework**: Maven, Java 17, TestNG.
- **Thiết kế Design Pattern**: **Page Object Model (POM)** tách biệt rõ rệt giữa logic xử lý trang (`com.orangehrm.pages`), logic tương tác Selenium (`com.orangehrm.actiondriver.ActionDriver`), base test driver (`com.orangehrm.base.BaseClass`) và kịch bản test (`com.orangehrm.test`).
- **Quản lý WebDriver**: Sử dụng `ThreadLocal<WebDriver>` hỗ trợ chạy kiểm thử song song (Parallel Execution) mượt mà và an toàn trên đa luồng.
- **Reporting**: Tích hợp **ExtentReports (v5.1.2)** xuất báo cáo HTML trực quan (`SparkReport.html`) kèm tính năng tự động chụp ảnh màn hình (screenshot) khi test case bị lỗi (FAILED).
- **Logging**: Tích hợp **Log4j2** kết hợp `LoggerManager` log quá trình chạy chi tiết.
- **Data-Driven & Database**: 
  - Hỗ trợ đọc dữ liệu test từ Excel thông qua `ExcelLibrary` và `ExcelReaderUtility` (Apache POI).
  - Tích hợp kiểm thử Database với MySQL qua `DBUtility` (`mysql-connector-j`).
- **API Testing**: Tích hợp **RestAssured** (`ApiUtility`) cho phép làm việc với API / HTTP requests.
- **CI/CD & Infrastructure**:
  - `Jenkinsfile`: Tự động hóa Pipeline checkout code, kích hoạt Docker Compose chạy Selenium Grid, thực thi `mvn test`, đẩy Extent Report và gửi Email thông báo kết quả.
  - `docker/docker-compose.yml`: Cấu hình Selenium Hub và các browser nodes (Chrome, Firefox).

---

## 🌐 2. Target Automation URLs (Thông tin URL Đang Automation)

Hệ thống được cấu hình để automation với các URL sau (được khai báo chi tiết tại [`config.properties`](file:///d:/automation-project/src/main/resources/config.properties) và các test classes):

### 🔴 URL Chính (Web App Automation Target)
- **Demo Live Web Target**: `https://opensource-demo.orangehrmlive.com/` *(Target mặc định chính để test ứng dụng OrangeHRM)*
- **Local Web Target**: `http://localhost/orangehrm/web/index.php/auth/login`
- **Docker/Grid Web Target**: `http://host.docker.internal/orangehrm/web/index.php/auth/login`

### 🔵 URL API Testing Target
- **API Test Endpoint**: `https://jsonplaceholder.typicode.com/users/1` *(Sử dụng trong `ApiTest.java` để demo / test REST API)*

### 🟡 Infrastructure & Grid URLs
- **Selenium Grid Hub**: `http://localhost:4444/wd/hub`

---

## 🛠️ 3. Structure & Key Packages

```text
src/main/java/com/orangehrm/
├── actiondriver/   # Class ActionDriver chứa các wrapper function tương tác với Element (click, type, screenshot, v.v.)
├── base/           # BaseClass khởi tạo WebDriver, load config properties, quản lý ThreadLocal Driver
├── listeners/      # TestListener ghi log ExtentReport & chụp màn hình khi thất bại
├── pages/          # Các Page Object (LoginPage, HomePage, ...)
└── utilities/      # Tiện ích bổ trợ (ApiUtility, DBUtility, ExcelLibrary, LoggerManager, ExtentManager)

src/test/java/com/orangehrm/test/
├── LoginPageTest.java       # Test cases đăng nhập vào OrangeHRM
├── HomePageTest.java        # Test cases điều hướng và giao diện Home Page
├── DBVerificationTest.java  # Test cases truy vấn kiểm tra Database
└── ApiTest.java             # Test cases kiểm tra API với RestAssured

docker/                     # Cấu hình Docker Compose cho Selenium Grid (Hub + Chrome Node + Firefox Node)
Jenkinsfile                 # CI/CD Pipeline script cho Jenkins
```

---

## 🚀 4. How to Run (Hướng Dẫn Chạy Test)

1. **Chạy Local thông thường**:
   ```bash
   mvn clean test
   ```
2. **Chạy song song qua Selenium Grid (Docker)**:
   ```bash
   docker compose -f docker/docker-compose.yml up -d
   mvn clean test -DseleniumGrid=true
   ```
