<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

<div class="container mt-5">
    <h1 class="display-5 mb-5" style="font-weight: bold;">게시물 수정</h1>
	<hr>
    <form role="form" method="POST" action="/update/" id="registerForm">
    	<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" id="tokenValue"/>
    	<input type="hidden" name="bno" value="${board.bno }">
    	<input type="hidden" name="page" value="${pageMaker.cri.page }">
    	<input type="hidden" name="perPageNum" value="${pageMaker.cri.perPageNum }">
    	<input type="hidden" name="searchType" value="${pageMaker.cri.searchType }">
    	<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" value="${board.title }">
        </div>
        <div class="mb-3">
            <label for="writer" class="form-label">작성자</label>
            <input type="text" class="form-control" id="writer" name="writer" value="${board.writer }" readonly>
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea class="form-control" id="content" name="content" style="height: 300px">${board.content }</textarea>
        </div>
        
        <div class="mb-3">
        	<div class="row">
	    		<label class="col-6">이미지 업로드</label><label class="text-end col-6" id="sizeCheck">업로드 용량 (0.00 M)</label>
	    	</div>
	    	<div class="dropZone text-center align-middle mt-2">Drag & Drop</div>
    	</div>
    	
		<div class="row uploadList">
		</div>
		
        <button type="submit" class="btn btn-warning">수정</button>
        <a href="/${pageMaker.makeQuery(pageMaker.cri.page) }" class="btn btn-primary">취소</a>
    </form>
</div>

<script src="/resources/js/fileUpload.js" crossorigin="anonymous"></script>

<%@ include file="../include/footer.jsp" %>