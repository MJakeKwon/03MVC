package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDAO {
	
	public ProductDAO() {
	}
	
	public void insertProduct(Product prod) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT "+ 
						"INTO product "+ 
						"VALUES(seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, prod.getProdName());
		stmt.setString(2, prod.getProdDetail());
		stmt.setString(3, prod.getManuDate());
		stmt.setInt(4, prod.getPrice());
		stmt.setString(5, prod.getFileName());
		
		System.out.println(prod);
		System.out.println("상품추가");
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	public Product findProduct(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
		Product prod = null;
		while (rs.next()) {
			prod = new Product();
			prod.setProdNo(rs.getInt("prod_no"));
			prod.setProdName(rs.getString("prod_name"));
			prod.setProdDetail(rs.getString("prod_detail"));
			prod.setManuDate(rs.getString("manufacture_day"));
			prod.setPrice(rs.getInt("price"));
			prod.setFileName(rs.getString("image_file"));
			prod.setRegDate(rs.getDate("REG_DATE"));
			System.out.println("find");
		}
		con.close();
		System.out.println("상품찾기완료");
		return prod;
	}
	public Map<String,Object> getProductList(Search search) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * "
							+ "FROM product ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("")) {
				sql += "WHERE prod_no='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
				sql += "WHERE prod_name LIKE ('%" + search.getSearchKeyword()  + "%')";
				
			}else if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("")){
				sql += "WHERE price='"+search.getSearchKeyword()+"'";
			}
		}
			sql += " ORDER BY prod_no";
			System.out.println(sql);
			
			//==> TotalCount GET
			int totalCount  = this.getTotalCount(sql);
			System.out.println("ProductDAO :: totalCount  :: " + totalCount);
			
			//==> CurrentPage 게시물만 받도록 Query 다시구성
			sql = makeCurrentPageSql(sql, search);
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			System.out.println(search);
			
			List<Product> list = new ArrayList<Product>();
			
			while(rs.next()){
					Product prod = new Product();
					prod.setProdNo(rs.getInt("prod_no"));
					prod.setProdName(rs.getString("prod_name"));
					prod.setProdDetail(rs.getString("prod_detail"));
					prod.setManuDate(rs.getString("manufacture_day"));
					prod.setPrice(rs.getInt("price"));
					prod.setFileName(rs.getString("image_file"));
					prod.setRegDate(rs.getDate("REG_DATE"));

					list.add(prod);
			}
			
			//==> currentPage 의 게시물 정보 갖는 List 저장
			map.put("list", list);
			//==> totalCount 정보 저장
			map.put("totalCount", new Integer(totalCount ));
			
			System.out.println("list test :"+list);
			System.out.println("list.size() : "+ list.size());
			System.out.println("map().size() : "+ map.size());

			con.close();
			rs.close();
			pStmt.close();
			
		System.out.println("찾기완료");
		return map;
	}
	
public void updateProduct(Product prod) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product"+
						" SET prod_name=?,prod_detail=?,price=?,manufacture_day=?, image_file =?,reg_date=SYSDATE "+
						"WHERE prod_no=?";

		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, prod.getProdName());
		pstmt.setString(2, prod.getProdDetail());
		pstmt.setInt(3, prod.getPrice());
		pstmt.setString(4, prod.getManuDate());
		pstmt.setString(5, prod.getFileName());
		pstmt.setInt(6, prod.getProdNo());
		pstmt.executeUpdate();
		
		con.close();
		System.out.println("업데이트완료");
	}

	private int getTotalCount(String sql) throws Exception {
	
	sql = "SELECT COUNT(*) "+
	          "FROM ( " +sql+ ") countTable";
	
	Connection con = DBUtil.getConnection();
	PreparedStatement pStmt = con.prepareStatement(sql);
	ResultSet rs = pStmt.executeQuery();
	
	int totalCount = 0;
	if( rs.next() ){
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

