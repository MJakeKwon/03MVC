<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- product,user,purchase에 따라서 fnc${menu}List XX -->
	<!-- 모든 리스트의 스크립가 (fncGetUserList) 전부 똑같은 기능을 하고있다...-->
	<!-- 하나의 스크립트만 만들어서 사용을 해도 된다 -->
	<!-- calender.js 처럼 하나의 스크립트를 여러 곳에서 사용할 수 있는 것 처럼 이 페이징에 있는 스크립트도 
			캡슐화를 하는 방법을 추천 -->
	<!-- common.js 파일을 common package에 만들고 그 안에 fncGetLsit(currentPage) 스크립트를 하나 만들고 네비게이터를 통일시켜주는 방법으로 하면 좋다. -->
	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			◀ 이전
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetList('${ resultPage.currentPage-1}')">◀ 이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetList('${ i }');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')">이후 ▶</a>
	</c:if>