<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

<div class="container mt-5">
    <h1 class="display-5 mb-5" style="font-weight: bold;">게시물 등록</h1>
	<hr>
    <form role="form" method="POST" id="registerForm">
    	<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요">
        </div>
        <div class="mb-3">
            <label for="writer" class="form-label">작성자</label>
            <input type="text" class="form-control" id="writer" name="writer" placeholder="작성자를 입력해주세요">
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">내용</label >
            <textarea class="form-control" id="content" name="content" style="height: 300px" placeholder="내용을 입력해주세요"></textarea>
        </div>
        
        <div class="mb-3">
        	<div class="row">
	    		<label class="col-6">이미지 업로드</label><label class="text-end col-6" id="sizeCheck">업로드 용량 (0.00 M)</label>
	    	</div>
	    	<div class="dropZone text-center align-middle mt-2">Drag & Drop</div>
    	</div>
    	
		<div class="row uploadList">
		</div>
    
        <button type="submit" class="btn btn-primary">등록</button>
    </form>
    
    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">Modal title</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="modal-body">
                
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="modalClose">취소</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="modalCommit">Understood</button>
            </div>
        </div>
        </div>
    </div>
    
    
</div>

<script src="/resources/js/fileUpload.js" crossorigin="anonymous"></script>

<%@ include file="../include/footer.jsp" %>