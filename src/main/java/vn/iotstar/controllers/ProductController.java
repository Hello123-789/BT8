package vn.iotstar.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<ProductDTO> productPage = productService.findAll(keyword, page, size);
        model.addAttribute("products", productPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "products/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("formTitle", "Thêm sản phẩm");
        return "products/form";
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public String create(
            @Valid @ModelAttribute("product") ProductDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Thêm sản phẩm");
            return "products/form";
        }
        try {
            productService.create(dto);
            redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("formTitle", "Thêm sản phẩm");
            model.addAttribute("error", "Không thể thêm sản phẩm: " + e.getMessage());
            return "products/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ProductDTO product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("formTitle", "Cập nhật sản phẩm");
        return "products/form";
    }

    @PostMapping(value = "/edit/{id}", consumes = "multipart/form-data")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") ProductDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Cập nhật sản phẩm");
            return "products/form";
        }
        try {
            productService.update(id, dto);
            redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("formTitle", "Cập nhật sản phẩm");
            model.addAttribute("error", "Không thể cập nhật sản phẩm: " + e.getMessage());
            return "products/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa sản phẩm");
        }
        return "redirect:/products";
    }

    @PostMapping("/image/delete/{imageId}")
    public String deleteImage(@PathVariable Long imageId, RedirectAttributes redirectAttributes) {
        try {
            Long productId = productService.deleteImage(imageId);
            redirectAttributes.addFlashAttribute("success", "Xóa hình ảnh thành công");
            return "redirect:/products/edit/" + productId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa hình ảnh: " + e.getMessage());
            return "redirect:/products";
        }
    }
}
