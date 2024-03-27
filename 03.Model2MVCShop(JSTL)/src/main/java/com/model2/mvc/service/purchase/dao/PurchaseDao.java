package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.user.dao.UserDao;


public class PurchaseDao {

	public Purchase findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE tran_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);

		ResultSet rs = pStmt.executeQuery();
		
		Purchase purchase = null;
		while (rs.next()) {
		    if (purchase == null) {
		        purchase = new Purchase(); // purchase 객체 생성
		    }
		    purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(new ProductDao().findProduct(rs.getInt("prod_no")));
			purchase.setBuyer(new UserDao().findUser(rs.getString("buyer_id")));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(rs.getString("dlvy_date"));

		    System.out.println("find");
		}

		con.close();
		System.out.println("purchaseFind: " + purchase);
		return purchase;
	}
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql = 
				"SELECT * FROM "
				+ "("
				+ "	SELECT ROW_NUMBER() OVER (ORDER BY user_id) AS rn, "
				+ "	ts.*, u.user_id ,NVL(ts.tran_status_code, 0) AS tran_code, "
				+ "	COUNT(*) OVER () AS count "
				+ "	FROM users u "
				+ "	INNER JOIN transaction ts ON "
				+ "	u.user_id = ts.buyer_id "
				+ "	WHERE u.user_id = ? "
				+ ") "
				+ "WHERE rn BETWEEN ? AND ?";
		
		PreparedStatement pStmt = con.prepareStatement
									(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		int searchRowStart = search.getCurrentPage() * search.getPageSize() - search.getPageSize() + 1;
		System.out.println("searchVO.getPageUnit() : " + search.getPageSize());

		int searchRowEnd = searchRowStart + search.getPageSize() - 1;
		System.out.println("searchRowStart : " + searchRowStart);
		System.out.println("searchRowEnd : " + searchRowEnd);

		pStmt.setString(1, buyerId);
		pStmt.setInt(2, searchRowStart);
		pStmt.setInt(3, searchRowEnd);
		
		int totalCount  = this.getTotalCount(sql, search,  buyerId);
		System.out.println("totalCount :: " + totalCount);
		
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("PurchaseDao :: totalCount  :: " + totalCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Purchase> list = new ArrayList<Purchase>();
		while(rs.next()){
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setPurchaseProd(new ProductDao().findProduct(rs.getInt("prod_no")));
				purchase.setBuyer(new UserDao().findUser(rs.getString("user_id")));
				purchase.setPaymentOption(rs.getString("payment_option"));
				purchase.setReceiverName(rs.getString("receiver_name"));
				purchase.setReceiverPhone(rs.getString("receiver_phone"));
				purchase.setDivyAddr(rs.getString("demailaddr"));
				purchase.setDivyRequest(rs.getString("dlvy_request"));
				purchase.setTranCode(rs.getString("tran_code").trim());
				purchase.setOrderDate(rs.getDate("order_data"));
				purchase.setDivyDate(rs.getString("dlvy_date"));
				list.add(purchase);
			}
		System.out.println("list.size() : "+ list.size());
		System.out.println("map().size() : "+ map.size());
		
		map.put("list", list);
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount ));
		System.out.println(map.get("list"));
		
		con.close();
		rs.close();
		pStmt.close();
		System.out.println("찾기완료");
		
		return map;
	}
	
	public Map<String, Object> getSaleList(Search search) throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql ="SELECT * FROM transaction";
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		rs.last();
		int totalCount = rs.getRow();

		System.out.println("PurcahseDao :: totalCount  :: " + totalCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Purchase> list = new ArrayList<Purchase>();

		while(rs.next()){
				Purchase purchase = new Purchase();

				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setBuyer(new UserDao().findUser(rs.getString("buyer_id")));
				purchase.setDivyAddr(rs.getString("demailaddr"));
				purchase.setDivyDate(rs.getString("dlvy_date"));
				purchase.setDivyRequest(rs.getString("dlvy_request"));
				purchase.setOrderDate(rs.getDate("order_date"));
				purchase.setPaymentOption(rs.getString("payment_option"));
				purchase.setPurchaseProd(new ProductDao().findProduct(rs.getInt("prod_no")));
				purchase.setReceiverName(rs.getString("receiver_name"));
				purchase.setReceiverPhone(rs.getString("receiver_phone"));
				purchase.setTranCode(rs.getString("tran_status_code"));

				list.add(purchase);

				if (!rs.next())
					break;
		}

		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		System.out.println("list test :"+list);
		System.out.println("list.size() : "+ list.size());
		System.out.println("map().size() : "+ map.size());
		
		con.close();
		rs.close();
		pStmt.close();

		return map;
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,1,SYSDATE,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2,  purchase.getBuyer().getUserId());
		stmt.setString(3,  purchase.getPaymentOption());
		stmt.setString(4,  purchase.getReceiverName());
		stmt.setString(5,  purchase.getReceiverPhone());
		stmt.setString(6,  purchase.getDivyAddr());
		stmt.setString(7,  purchase.getDivyRequest());
		stmt.setString(8,  purchase.getDivyDate());
	
		stmt.executeUpdate();
		System.out.println("거래 등록 완료");
		con.close();
	}
	
	public void updatePurchase(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET payment_option=?,receiver_name=?,receiver_phone=?,demailaddr=?,dlvy_request=?,dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());

		stmt.executeUpdate();

		System.out.println("DB Update 완료");

		con.close();
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		String sql ="UPDATE transaction SET tran_status_code=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		
		stmt.executeUpdate();
		System.out.println("배송상태 번호 : "+purchase.getTranCode());
		con.close();
	}
	
	private int getTotalCount(String sql,Search search,String buyerId) throws Exception {
	    sql = "SELECT COUNT(*) FROM (" +sql+ ") countTable";
	    
	    int searchRowStart = search.getCurrentPage() * search.getPageSize() - search.getPageSize() + 1;
		System.out.println("searchVO.getPageUnit() : " + search.getPageSize());

		int searchRowEnd = searchRowStart + search.getPageSize() - 1;
		System.out.println("searchRowStart : " + searchRowStart);
		System.out.println("searchRowEnd : " + searchRowEnd);

	    Connection con = DBUtil.getConnection();
	    PreparedStatement pStmt = con.prepareStatement(sql);
	    
	    pStmt.setString(1, buyerId);
		pStmt.setInt(2, searchRowStart);
		pStmt.setInt(3, searchRowEnd);
		
	    ResultSet rs = pStmt.executeQuery();
	    
	    int totalCount = 0;
	    if (rs.next()) {
	        totalCount = rs.getInt(1);
	    }

	    pStmt.close();
	    con.close();
	    rs.close();

	    return totalCount;
	}

	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
