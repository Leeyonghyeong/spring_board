<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>

<div class="container mt-5">
    <h1 class="display-5 mb-5" style="font-weight: bold;">게시판</h1>
    <hr>
    
	<div class="row mb-3">
	    <div class="col-2">
			<select id="searchSelect" class="form-select" aria-label="Default select example">
	            <option value="n" <c:out value="${pageMaker.cri.searchType == null ? 'selected':'' }"/>>검색 조건</option>
	            <option value="t" <c:out value="${pageMaker.cri.searchType eq 't' ? 'selected':'' }"/>>제목</option>
	            <option value="c" <c:out value="${pageMaker.cri.searchType eq 'c' ? 'selected':'' }"/>>내용</option>
	            <option value="w" <c:out value="${pageMaker.cri.searchType eq 'w' ? 'selected':'' }"/>>작성자</option>
	            <option value="tc" <c:out value="${pageMaker.cri.searchType eq 'tc' ? 'selected':'' }"/>>제목 + 내용</option>
	            <option value="cw" <c:out value="${pageMaker.cri.searchType eq 'cw' ? 'selected':'' }"/>>내용 + 작성자</option>
	            <option value="tcw" <c:out value="${pageMaker.cri.searchType eq 'tcw' ? 'selected':'' }"/>>제목 + 내용 + 작성자</option>
	        </select>
	    </div>
	    
	    <div class="col-4">
	    	<input type="text" class="form-control" name="query" id="query" placeholder="검색어를 입력해 주세요">
	    </div>
	    
	    <div class="col-4">
	    	<button class="btn btn-secondary" onclick="searchGetForm()">검색</button>
	    	<button class="btn btn-warning" onclick="reset()">초기화</button>
	    </div>	  
	    
	    <div class="col-2">
	        <select class="form-select" id="pageSelect" onchange="getForm()" aria-label="Default select example">
	            <option value="10" <c:out value="${pageMaker.cri.perPageNum == 10 ? 'selected':'' }"/>>10개씩 보기</option>
	            <option value="20" <c:out value="${pageMaker.cri.perPageNum == 20 ? 'selected':'' }"/>>20개씩 보기</option>
	            <option value="30" <c:out value="${pageMaker.cri.perPageNum == 30 ? 'selected':'' }"/>>30개씩 보기</option>
	        </select>
	    </div>
	</div>
        
    <table class="table table-dark table-striped">
        <thead>
            <tr>
                <th scope="col" class="text-center">BNO</th>
                <th scope="col">TITLE</th>
                <th scope="col" class="text-center">WRITER</th>
                <th scope="col" class="text-center">REGDATE</th>
                <th scope="col" class="text-center">VIEWCNT</th>
            </tr>
        </thead>
        <tbody>    
            <c:forEach items="${list }" var="list">
	            <tr>
	                <th scope="row" class="col-1 text-center">${list.bno }</th>
	                <td><a href="/board/list/${list.bno }${pageMaker.makeQuery(pageMaker.cri.page) }">${list.title }</a></td>
	                <td class="col-2 text-center">${list.writer }</td>
	                <td class="col-2 text-center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${list.regdate }" /></td>
	                <td class="col-1 text-center"><sapn class="badge bg-secondary">${list.viewcnt }</sapn></td>
	            </tr>
            </c:forEach>
        </tbody>
    </table>
    
	<div class="row">
		<div class="col-1">
		
		</div>
		
		<div class="col-10">
			<nav aria-label="Page navigation example">
		        <ul class="pagination justify-content-center">
		        
		            <li class="page-item <c:out value="${pageMaker.start ? '':'disabled' }"/>">
		                <a class="page-link" href="${pageMaker.makeQuery(pageMaker.startPage - 1) }" aria-label="start">
		                    <span aria-hidden="true"><i class="bi bi-chevron-double-left"></i></span>
		                </a>
		            </li>
		            
		            <li class="page-item <c:out value="${pageMaker.prev ? '':'disabled' }"/>">
		                <a class="page-link" href="${pageMaker.makeQuery(pageMaker.cri.page - 1) }" aria-label="Previous">
		                    <span aria-hidden="true"><i class="bi bi-chevron-left"></i></span>
		                </a>
		            </li>
		            
		            <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
		            	
		            	<li class="page-item <c:out value="${pageMaker.cri.page == idx? 'active':'' }" />">
		            		<a class="page-link" href="${pageMaker.makeQuery(idx) }">${ idx }</a>
		            	</li>
		            </c:forEach>
		            
		            <li class="page-item <c:out value="${pageMaker.next ? '':'disabled' }"/>">
		                <a class="page-link" href="${pageMaker.makeQuery(pageMaker.cri.page + 1) }" aria-label="Next">
		                    <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
		                </a>
		            </li>
		            
		            <li class="page-item <c:out value="${pageMaker.end && pageMaker.endPage > 0 ? '':'disabled' }"/>">
		                <a class="page-link" href="${pageMaker.makeQuery(pageMaker.endPage + 1) }" aria-label="end">
		                    <span aria-hidden="true"><i class="bi bi-chevron-double-right"></i></span>
		                </a>
		            </li>
		        </ul>
		    </nav>
		</div>
		       
	    <div class="col-1">
            <a class="btn btn-primary" href="/board/register">글등록</a>
        </div>
	</div>
	
	<form id="form">
		<input type="hidden" name="page" id="page" value="${pageMaker.cri.page }">
		<input type="hidden" name="perPageNum" id="perPageNum" value="${pageMaker.cri.perPageNum }">
		<input type="hidden" name="searchType" id="searchType" value="${pageMaker.cri.searchType }">
    	<input type="hidden" name="keyword" id="keyword" value="${pageMaker.cri.keyword }">
	</form>
	
</div>

<script>
	const form = document.getElementById("form");
	
	function getForm() {
	    const target = document.getElementById("pageSelect");
	    const perPageNum = target.options[target.selectedIndex].value;
	
	    document.getElementById("perPageNum").value = perPageNum;
	
	    form.action = "/board/";
	    form.method = "GET";
	    form.submit();
	}
	
	function searchGetForm() {
	    const target = document.getElementById("searchSelect");
	    const searchType = target.options[target.selectedIndex].value;	    
	    const keyword = document.getElementById("query").value;
	    
	    if(searchType == 'n') {
	    	alert("검색 조건을 선택해 주세요");
	    	return;
	    }
	    
	    document.getElementById("page").value = 1;
	    document.getElementById("searchType").value = searchType;
	    document.getElementById("keyword").value = keyword;	    
	
	    form.action = "/board/";
	    form.method = "GET";
	    form.submit();
	}
	
	function reset() {
		self.location = "/board/"
	}
</script>
    
<script>
	let result = '${result}';
	
	if(result == "InsertOK") {
		alert("글 등록이 완료 되었습니다.");
	} 
	
	if(result == "DeleteOK") {
		alert("글 삭제가 완료 되었습니다.");
	}
	
	if(result == "updateOK") {
		alert("글 수정이 완료 되었습니다.");
	}
</script>
    
    
<%@ include file="../include/footer.jsp" %>