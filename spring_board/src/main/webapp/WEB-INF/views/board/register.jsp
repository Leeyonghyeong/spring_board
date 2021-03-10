<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

<div class="container mt-5">
    <h1 class="display-5 mb-5" style="font-weight: bold;">게시물 등록</h1>
	<hr>
    <form role="form" method="POST">
        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력해주세요">
        </div>
        <div class="mb-3">
            <label for="writer" class="form-label">작성자</label>
            <input type="text" class="form-control" id="writer" name="writer" placeholder="작성자를 입력해주세요">
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea class="form-control" id="content" name="content" style="height: 300px" placeholder="내용을 입력해주세요"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">등록</button>
    </form>
</div>

<%@ include file="../include/footer.jsp" %>