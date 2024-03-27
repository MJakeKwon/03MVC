<%@ page contentType="text/html; charset=euc-kr"%>
<%@ page pageEncoding="EUC-KR"%>

<%@ page import="java.util.*"%>
<%@ page import="com.model2.mvc.common.*"%>
<%@ page import="com.model2.mvc.service.domain.*"%>
<%@page import="com.model2.mvc.common.*"%>
<%@ page import="com.model2.mvc.common.util.CommonUtil"%>

<%--
 <%
	Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
	Search search = (Search) request.getAttribute("search");
	Page resultPage = (Page)request.getAttribute("resultPage");
   
  	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
   
	int maxPage = 0;
	List<Purchase> list = null;
	if (map != null) {
	maxPage = ((Integer) map.get("totalCount")).intValue();
	   list = (List<Purchase>) map.get("list");
	}
   
%> 
--%>

<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/listUser.do" method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">���� �����ȸ</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">ȸ��ID</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">ȸ����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">��ȭ��ȣ</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�����Ȳ</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">��������</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>

			
				
				<c:if test="${maxPage ==0 }">
					<tr class="ct_list_pop"> 
					<td align="center" colspan="100%">���� ������ �����ϴ�.</td>
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
				</c:if>
				<c:otherwise>
					<c:set var="i" value="0"/>
					<c:forEach var ="purchase" items="${list }">
						<tr class="ct_list_pop">
							<td align="center"><a href="/getPurchase.do?tranNo=${purchase.tranNo}">${i}</a>
							</td>
							<td></td>
							<td align="left"><a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a>
							</td>
							<td></td>
							<td align="left">${purchase.receiverName}</td>
							<td></td>
							<td align="left">${purchase.receiverPhone}</td>
							<td></td>
							<td align="left">
								<c:choose>
								
									<c:when test="${purchase.tranCode eq '1' }">
											���ſϷ� // ��� �غ����Դϴ�.
									</c:when>
									<c:when test="${purchase.tranCode eq '2' }">
											�����Ͻ� ��ǰ�� ������Դϴ�.
									</c:when>
									<c:when test="${purchase.tranCode eq '3' }">
											[��ǰ ���� �Ϸ�] == ��ǰ �ŷ��� �������ϴ�.
									</c:when>
									
								</c:choose>
							</td>
							<td></td>
							<td align="left">
								<c:if test="${purchase.tranCode eq '2' }">
									<a href="/updateTranCodeByProd.do?tranNo=${purchase.tranNo}$trancode=3">
										���� ����(���� Ȯ��)</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="11" bgcolor="D6D7D6" height="1"></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</table>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
				<td align="center">
				    <c:forEach var="i" begin="1" end="${maxPage}">
				        <a href="/listPurchase.do?page=${i}">${i}</a>
				    </c:forEach>
				</td>
				</tr>
			</table>
			<!--  ������ Navigator �� -->
		</form>

	</div>

</body>
</html>