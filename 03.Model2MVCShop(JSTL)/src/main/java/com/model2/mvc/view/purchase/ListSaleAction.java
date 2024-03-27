package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListSaleAction extends Action {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        Search search = new Search();
        
        int currentPage=1;
		if (request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}//pagenumber를 눌렀을때 넘어가는 화면
        
		search.setCurrentPage(currentPage);
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));//5
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//3
		search.setPageSize(pageSize);
		
        PurchaseService service = new PurchaseServiceImpl();
        Map<String, Object> map = service.getSaleList(search);
        
        request.setAttribute("map", map);
        request.setAttribute("search", search);
            
        return "forward:/purchase/listPurchase.jsp";
    }
}

