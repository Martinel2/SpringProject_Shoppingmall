package com.shoppingmall.controller;

import com.shoppingmall.domain.Products;
import com.shoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    @GetMapping(value = "/products/add")
    public String createForm() {
        return "product/productAdd";
    }
}

