package mn7.spring.mn7_springboot.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import mn7.spring.mn7_springboot.domain.Products;
import mn7.spring.mn7_springboot.domain.User;
import mn7.spring.mn7_springboot.service.ProductService;
import mn7.spring.mn7_springboot.service.UploadService;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProductList(Model model) {
        List<Products> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductPage(Model model) {
        model.addAttribute("newProduct", new Products());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(Model model, @ModelAttribute("newProduct") @Valid Products product,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {

        List<FieldError> fieldErrors = newProductBindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            System.out.println(
                    ">>>>> Field: " + fieldError.getField() + ", Message: " + fieldError.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(avatar);

        this.productService.saveProducts(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Optional<Products> product = this.productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("currentProduct", product.get());
            model.addAttribute("id", id);
            return "admin/product/detail";
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + id);
        }
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Products> product = this.productService.getProductById(id);
        model.addAttribute("currentProduct", product.get());
        model.addAttribute("id", id);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(Model model,
            @ModelAttribute("currentProduct") @Valid Products product,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {

        if (newProductBindingResult.hasErrors()) {
            model.addAttribute("currentProduct", product); // ← THÊM DÒNG NÀY
            return "admin/product/update";
        }

        Products currentProduct = this.productService.getProductById(product.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + product.getId()));

        if (!file.isEmpty()) {
            String img = this.uploadService.handleSaveUploadFile(file, "product");
            currentProduct.setImage(img);
        }

        currentProduct.setName(product.getName());
        currentProduct.setQuantity(product.getQuantity());
        currentProduct.setDetailDes(product.getDetailDes());
        currentProduct.setShortDes(product.getShortDes());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setFactory(product.getFactory());
        currentProduct.setTarget(product.getTarget());

        this.productService.saveProducts(currentProduct);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        Optional<Products> product = this.productService.getProductById(id);
        model.addAttribute("currentProduct", product);
        // model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(Model model, @ModelAttribute("currentProduct") Products product) {

        productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }

}
