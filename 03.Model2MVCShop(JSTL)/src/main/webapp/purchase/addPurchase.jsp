<%@ page contentType="text/html; charset=euc-kr"%>
<%@ page pageEncoding="EUC-KR"%>

<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
<%
Purchase purchase = (Purchase) request.getAttribute("purchase");
%>
 --%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

	������ ���� ���Ű� �Ǿ����ϴ�.

	<table border=1>
		<tr>
			<td>��ǰ��ȣ</td>
			<td>${purchase.purchaseProd.prodNo}</td>
			<td></td>
		</tr>
		<tr>
			<td>�����ھ��̵�</td>
			<td>${purchase.buyer.userId}</td>
			<td></td>
		</tr>
		<tr>
			<td>���Ź��</td>
			<td>
				
				<c:when test= "${purchase.paymentOption ==1}">
					���ݱ���
				</c:when>
					<c:otherwise>
						�ſ뱸��
					</c:otherwise>
				
			</td>
			<td></td>
		</tr>
		<tr>
			<td>�������̸�</td>
			<td>${purchase.receiverName}</td>
			<td></td>
		</tr>
		<tr>
			<td>�����ڿ���ó</td>
			<td>${purchase.receiverPhone}</td>
			<td></td>
		</tr>
		<tr>
			<td>�������ּ�</td>
			<td>${purchase.divyAddr}</td>
			<td></td>
		</tr>
		<tr>
			<td>���ſ�û����</td>
			<td>${purchase.divyRequest}</td>
			<td></td>
		</tr>
		<tr>
			<td>����������</td>
        	<td>${purchase.divyRequest}</td>
			<td></td>
		</tr>
	</table>

</body>
</html>