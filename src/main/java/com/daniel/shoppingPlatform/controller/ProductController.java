package com.daniel.shoppingPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shoppingPlatform.constant.ProductCategory;
import com.daniel.shoppingPlatform.model.Product;
import com.daniel.shoppingPlatform.service.ProductService;

import dto.ProductRequest;
import jakarta.validation.Valid;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(
				@RequestParam(required = false) ProductCategory category,
				@RequestParam(required = false) String search){
		//加上required = false是因為若使用者沒有填寫所要的category, 則會自動判定為全選, 若沒加上
		//required = false, 則會出現404 notFound, 此方法"常用"需複習!!!(4-7 no.1)
		List<Product> productList = productService.getProducts(category, search);
		
		return ResponseEntity.status(HttpStatus.OK).body(productList);
		
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
		Product product = productService.getProductById(productId);
		
		if(product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
		//@RequestBody獲取在requestBody中的值, @Valid驗證vo的annotation
		Integer productId = productService.createProduct(productRequest);
		
		Product product = productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(product);

	}
	
	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, 
												@RequestBody @Valid ProductRequest productRequest){
		//step1:判斷此須更新id是否存在資料庫中
		Product product = productService.getProductById(productId);
		
		if(product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		//step2:經由此id及json內容更新sql資料
		productService.updateProduct(productId, productRequest);
		
		Product updateProduct = productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
		
	}
	
	@DeleteMapping("products/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
		
		productService.deleteProductById(productId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
