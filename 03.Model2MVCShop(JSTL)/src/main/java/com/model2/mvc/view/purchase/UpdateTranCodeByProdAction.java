package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tranNo =request.getParameter("tranNo");
		String tranCode = request.getParameter("tranCode");
		
		PurchaseService service = new PurchaseServiceImpl();
		
		Purchase purchase = service.getPurchase(Integer.parseInt(tranNo));
		purchase.setTranCode(tranCode);
		
		System.out.println(purchase+"������Ʈ ����");
		
		service.updateTranCode(purchase);
		System.out.println("������Ʈ ��"+purchase);
		
		return "redirect:/listPurchase.do";
	}
}
