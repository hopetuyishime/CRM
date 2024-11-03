package rw.ac.auca.kuzahealth.controller.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rw.ac.auca.kuzahealth.core.product.domain.Product;
import rw.ac.auca.kuzahealth.core.product.repository.IProductRepository;
import rw.ac.auca.kuzahealth.core.product.service.ProductService;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final IProductRepository productRepository;
    private final ProductService productService;

    @GetMapping({"","/","/home"})
    public String productHomePage(Model model){
        model.addAttribute("products", productService.findAllProducts());
        return "product/productList";
    }
}
