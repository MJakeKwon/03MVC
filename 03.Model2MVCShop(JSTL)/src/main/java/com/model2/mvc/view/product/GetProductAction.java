package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo =(Integer.parseInt(request.getParameter("prodNo")));
		
		ProductService service=new ProductServiceImpl();
		Product prod=service.getProduct(prodNo);
		
		Cookie[] cookies = request.getCookies();
		
		request.setAttribute("prod", prod);

		return "forward:/product/getProduct.jsp";
	}
}