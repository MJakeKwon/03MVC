package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductServiceImpl implements ProductService {

	private ProductDAO	proDAO;
	
	public ProductServiceImpl() {
		proDAO = new ProductDAO();
	}
	
	public void addProduct(Product prod) throws Exception{
		proDAO.insertProduct(prod);
	}
	
	public Product getProduct(int prodNo) throws Exception{
		return proDAO.findProduct(prodNo);
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		return proDAO.getProductList(search);
	}
	
	public void updateProduct (Product prod) throws Exception {
		proDAO.updateProduct(prod);
	}
	
	
}
