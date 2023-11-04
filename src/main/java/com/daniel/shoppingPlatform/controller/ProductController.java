package com.daniel.shoppingPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import dto.ProductQueryParams;
import dto.ProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import util.Page;

@Validated
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getProducts(
				//filter
				@RequestParam(required = false) ProductCategory category,
				@RequestParam(required = false) String search,
				//排序
				@RequestParam(defaultValue = "created_date") String orderBy, 
				@RequestParam(defaultValue = "desc") String sort, 
				//分頁
				@RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
				@RequestParam(defaultValue = "0") @Min(0) Integer offset
				){
		//sql語法中, LIMIT限制取幾筆資料, OFFSET跳過幾筆
		//加上@mac與@min時, 須在class上加上@Validated才會生效
		//加上required = false是因為若使用者沒有填寫所要的category, 則會自動判定為全選, 若沒加上
		//required = false, 則會出現404 notFound, 此方法"常用"需複習!!!(4-7 no.1)
		
		ProductQueryParams productQueryParams =new ProductQueryParams();
		productQueryParams.setCategory(category);
		productQueryParams.setSearch(search);
		productQueryParams.setOrderBy(orderBy);
		productQueryParams.setSort(sort);
		productQueryParams.setLimit(limit);
		productQueryParams.setOffset(offset);
		//取得product list
		List<Product> productList = productService.getProducts(productQueryParams);
		
//		使用了productQueryParams之後不論有多少個條件要篩選, 直接塞到productQueryParams裡面就好, 不用再一個個去改controller, service, dao層的get參數
//		List<Product> productList = productService.getProducts(category, search);
		//取得product總數
		Integer total = productService.countProduct(productQueryParams);
		//分頁
		Page<Product> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setResult(productList);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
		
	}
	
//	@GetMapping("/products")
//	public ResponseEntity<List<Product>> getProducts(
//				//filter
//				@RequestParam(required = false) ProductCategory category,
//				@RequestParam(required = false) String search,
//				//排序
//				@RequestParam(defaultValue = "created_date") String orderBy, 
//				@RequestParam(defaultValue = "desc") String sort, 
//				//分頁
//				@RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
//				@RequestParam(defaultValue = "0") @Min(0) Integer offset
//				){
//		//sql語法中, LIMIT限制取幾筆資料, OFFSET跳過幾筆
//		//加上@mac與@min時, 須在class上加上@Validated才會生效
//		//加上required = false是因為若使用者沒有填寫所要的category, 則會自動判定為全選, 若沒加上
//		//required = false, 則會出現404 notFound, 此方法"常用"需複習!!!(4-7 no.1)
//		
//		ProductQueryParams productQueryParams =new ProductQueryParams();
//		productQueryParams.setCategory(category);
//		productQueryParams.setSearch(search);
//		productQueryParams.setOrderBy(orderBy);
//		productQueryParams.setSort(sort);
//		productQueryParams.setLimit(limit);
//		productQueryParams.setOffset(offset);
//		
//		List<Product> productList = productService.getProducts(productQueryParams);
//		
////		使用了productQueryParams之後不論有多少個條件要篩選, 直接塞到productQueryParams裡面就好, 不用再一個個去改controller, service, dao層的get參數
////		List<Product> productList = productService.getProducts(category, search);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(productList);
//		
//	}
	
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
