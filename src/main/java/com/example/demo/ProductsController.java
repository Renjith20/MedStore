package com.example.demo;




import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductsRepository;




@Controller
@RequestMapping("/products")
public class ProductsController {
@Autowired
private ProductsRepository repo;

@GetMapping({"","/"})
public String showProductList(Model model)
{
	List<Product>products=repo.findAll();
	model.addAttribute("products",products);
	return "products/index";
}

@GetMapping("/create")
public String showCreatePage(Model model) {
	ProductDto productDto=new ProductDto();
	model.addAttribute("productDto",productDto);
	return "products/CreateProduct";
}

@PostMapping("/create")
public String createProduct(@ModelAttribute ProductDto productDto) {
    Product product = new Product();
    product.setName(productDto.getName());
    product.setCompany(productDto.getCompany());
    product.setType(productDto.getType());
    product.setPrice(productDto.getPrice());

    repo.save(product);

    return "redirect:/products"; // Corrected the URL format
}

@GetMapping("/edit")
public String showEditPage(Model model , @RequestParam int id) {
	
	try {
		Product product = repo.findById(id).get();
		model.addAttribute("product" , product);
		
		
		ProductDto productDto = new ProductDto();
		productDto.setName(product.getName());
		productDto.setCompany(product.getCompany());
		productDto.setType(product.getType());
		productDto.setPrice(product.getPrice());
		
		model.addAttribute("productDto" , productDto);
				
	}
	catch(Exception ex) {
		System.out.println("Exception: " + ex.getMessage());
		return "redirect:/products";
	}
	return "products/EditProduct";
}

@PostMapping("/edit")
public String updateProduct(Model model , @RequestParam int id , 
		@Valid @ModelAttribute ProductDto productDto,
		BindingResult result) {
	try {
		Product product = repo.findById(id).get();
		model.addAttribute("product",product);
		
		if(result.hasErrors()) {
			return "products/EditProduct";
		}
		
		product.setName(productDto.getName());
		product.setCompany(productDto.getCompany());
		product.setType(productDto.getType());
		product.setPrice(productDto.getPrice());
		
		repo.save(product);
	}
	catch(Exception ex) {
		System.out.println("Exception: " + ex.getMessage());
	}
	return "redirect:/products";
}


	@GetMapping("/delete")
	public String deleteProduct(
			@RequestParam int id) {
		
		try {
			Product product = repo.findById(id).get();
			
//			Delete the Product
			repo.delete(product);
		}
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return "redirect:/products";
	}
	
//	Search
	
	@PostMapping("/search")
	public String searchProduct(Model model, @RequestParam String keyword) {
	    List<Product> products = repo.findByNameContainingIgnoreCase( keyword);
	    model.addAttribute("products", products);
	    return "products/index";
	}

}