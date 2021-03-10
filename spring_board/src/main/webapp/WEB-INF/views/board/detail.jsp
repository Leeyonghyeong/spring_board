<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>

<div class="container mt-5">
    <h1 class="display-5 mb-5" style="font-weight: bold;">상세보기</h1>
    <hr>
	
	<form id="form" method="POST">
    	<input type="hidden" name="bno" id="bno" value="${board.bno }">
    	<input type="hidden" name="page" value="${pageMaker.cri.page }">
    	<input type="hidden" name="perPageNum" value="${pageMaker.cri.perPageNum }">
    	<input type="hidden" name="searchType" value="${pageMaker.cri.searchType }">
    	<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
	</form>
    <div class="mb-3">
        <label for="title" class="form-label">제목</label>
        <input type="text" class="form-control" id="title" name="title" value="${board.title }" readonly>
    </div>
    <div class="mb-3">
        <label for="writer" class="form-label">작성자</label>
        <input type="text" class="form-control" id="writer" name="writer" value="${board.writer }" readonly>
    </div>
    <div class="mb-3">
        <label for="regdate" class="form-label">등록일</label>
        <input type="text" class="form-control" id="regdate" name="regdate" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${board.regdate }' />" readonly>
    </div>
    <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea class="form-control" id="content" name="content" style="height: 300px" readonly>${board.content }</textarea>
    </div>
    <a class="btn btn-warning" href="/update/${bno }${pageMaker.makeQuery(pageMaker.cri.page) }">수정</a>
    <button type="submit" class="btn btn-danger" onclick="boardDelete()">삭제</button>
    <a class="btn btn-primary" href="/${pageMaker.makeQuery(pageMaker.cri.page) }">목록보기</a>
    
    
	<div class="row box">
        <div class="box-header mt-5">
            <h3 class="bos-title">댓글 달기</h3>
            <hr>
        </div>
        
        <div class="box-body mb-5">
            <div class="mb-3">
                <label for="replyer" class="form-label">작성자</label>
                <input type="text" class="form-control" id="replyer" name="replyer">
            </div>
            <div class="mb-3">
                <label for="replytext" class="form-label">댓글</label>
                <input type="text" class="form-control" id="replytext" name="replytext">
            </div>
            <button type="submit" class="btn btn-primary" onclick="addReply()">댓글등록</button>
        	<hr>
    	</div>
	
	    <div class="box-footer" id="replies">
	    	
	    	<!-- 
	        <div class="mb-3 bg-light border border-1 border-dark" >
	            <div class="mb-1">
	                <div class="row mt-3">
	                    <div class="col-6 ps-4">
	                        <span><i class="bi bi-chat-dots"></i> USER</span> 
	                    </div>
	                    <div class="col-6 text-end pe-4">
	                        <span><i class="bi bi-stopwatch"></i> 2021/03/09</span>      
	                    </div>
	                </div>
	                <hr>
	            </div>
	            <div class="mb-1">
	                <div class="px-2">
	                    내용
	                </div>
	                <hr>
	            </div>
	            <div class="mb-3">
	                <div class="px-2">
	                    <button type="submit" class="btn btn-warning" onclick="">댓글 수정</button>
	                    <button type="submit" class="btn btn-danger" onclick="">댓글 삭제</button>
	                </div>
	
	            </div>
	        </div>
	        -->
	        
	    </div>
	    
	</div>
	
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
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="modalCommit" onclick="commitKind()">Understood</button>
            </div>
        </div>
        </div>
    </div>
    
    <input type="hidden" id="hiddenRno" value="">

</div>

<script>
	const form = document.getElementById("form");
	
	function boardDelete() {
	    form.action = "/delete/";
	    form.submit();
	}
  
</script>

<script>
	let count = 1;
	let totalCount = 1;
	
	const bno = document.getElementById("bno").value;
	
	function getPage() {
		if(count > totalCount) {
			return;
		}
		
		fetch('http://172.30.1.9:8080/api/reply/' + bno + '/' + count, {
	        method: 'GET'
	    }).then(function(response) {
	        return response.json();
	    }).then(function(data) {
	    	totalCount = Math.ceil(data.pageMaker.totalCount / data.pageMaker.cri.perPageNum);
	        gridReply(data.list);
	    }).catch(function(error) {
	        console.log("error ----------->", error);
	    });
	}
	
	function gridReply(list) {        
		let div = document.getElementById("replies");
		
        for(let i = 0; i < list.length; i++) {
        	let grid = document.createElement("div");
        	
        	grid.setAttribute("class", "mb-3 bg-light border border-1 border-dark");
            
        	grid.innerHTML = '<div class="mb-1"><div class="row mt-3"><div class="col-6 ps-4">' +
									'<span><i class="bi bi-chat-dots"></i> ' + 
									list[i].replyer + 
									'</span></div><div class="col-6 text-end pe-4"><span><i class="bi bi-stopwatch"></i> ' + 
									prettifyDate(list[i].regdate) + 
									'</span></div></div><hr></div><div class="mb-1"><div class="px-2"  id="reply' + list[i].rno + '">' +
									list[i].replytext +						       
						    		'</div><hr></div><div class="mb-3"><div class="px-2">' +
						        	'<button type="submit" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="updateReply(' + list[i].rno + ')">댓글 수정</button> ' +
						        	'<button type="submit" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="deleteReply(' + list[i].rno + ')">댓글 삭제</button>' +
						    	'</div></div>';
			div.appendChild(grid)
        }
        
        count++;
	  
	}
	
	
	function prettifyDate(regdate) {
		let dateObj = new Date(regdate);
		let year = dateObj.getFullYear();
		let month = dateObj.getMonth() + 1;
		let date = dateObj.getDate();
		
		return year + '/' + month + '/' + date;
	}
	
    //스크롤 바닥 감지
    window.onscroll = function(e) {
        if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
       		if(count == 1) {
       			return;
       		} else {
       			getPage();	
       		}
        }
    };
    
    getPage();
</script>

<script>
	function addReply() {
		const replyer = document.getElementById("replyer").value;
		const replytext = document.getElementById("replytext").value;
		let div = document.getElementById("replies");
		
		fetch('http://172.30.1.9:8080/api/reply/', {
	        method: 'POST',
	        body: JSON.stringify({"bno" : bno, "replyer" : replyer, "replytext" : replytext}),
	        headers: {"content-Type" : "application/json"}
	    }).then(function(response) {
	        return response.text();
	    }).then(function(data) {
	    	if(data == "SUCCESS") {
	    		showModal();
	    		if(totalCount < 1) {
	    			totalCount = 1;
	    		}
	    	} else {
	    		return;
	    	}
	    	
	    	div.innerHTML = '';
	    	count = 1;
	    		    	
	    	document.getElementById("replyer").value = '';
	    	document.getElementById("replytext").value = '';
	    	
	    	getPage();
	    }).catch(function(error) {
	        console.log("error ----------->", error);
	    });;
	    
	}
	
	function updateReply(rno) {
		let replytext = document.getElementById("reply" + rno).innerText;

        let modifyHTML = '<div class="mb-3">';
        modifyHTML += '<label for="recipient-name" class="col-form-label">내용:</label>';
        modifyHTML += '<input type="text" class="form-control" id="recipient-name" value="' + replytext +'">';
        modifyHTML += '</div>';
        
        document.getElementById("hiddenRno").value = rno;
		
        document.getElementById("modalClose").hidden = false;
        document.getElementById('staticBackdropLabel').innerText = '댓글 수정';
        document.getElementById("modal-body").innerHTML = modifyHTML;
        document.getElementById("modalCommit").innerText = '수정';
	}
	
	function deleteReply(rno) {
		document.getElementById("hiddenRno").value = rno;
		
		document.getElementById("modalClose").hidden = false;
		document.getElementById('staticBackdropLabel').innerText = '댓글 삭제';
        document.getElementById("modal-body").innerHTML = '댓글을 정말로 삭제 하시겠습니까?';
        document.getElementById("modalCommit").innerText = '삭제';	
	}
	
	function commitKind() {
		let kind = document.getElementById("modalCommit").innerText;
		if(kind == '수정') {
			commitUpdate();
		} else if(kind == '삭제') {
			commitDelete();
		} else {
			return;
		}
	}
	
	function commitUpdate() {
		const rno = document.getElementById("hiddenRno").value;
		const replytext = document.getElementById("recipient-name").value;
		let div = document.getElementById("replies");
		
		fetch('http://172.30.1.9:8080/api/reply/' + rno, {
	        method: 'PUT',
	        body: JSON.stringify({"rno" : rno, "replytext" : replytext}),
	        headers: {"content-Type" : "application/json"}
	    }).then(function(response) {
	        return response.text();
	    }).then(function(data) {
	    	if(data == "SUCCESS") {
	    		//alert("등록되었습니다.");
	    		if(totalCount < 1) {
	    			totalCount = 1;
	    		}
	    	} else {
	    		return;
	    	}
	    	
	    	div.innerHTML = '';
	    	count = 1;
	    		    	
	    	document.getElementById("replyer").value = '';
	    	document.getElementById("replytext").value = '';
	    	
	    	getPage();
	    }).catch(function(error) {
	        console.log("error ----------->", error);
	    });;
	}
	
	function commitDelete() {
		const rno = document.getElementById("hiddenRno").value;
		let div = document.getElementById("replies");
		
		fetch('http://172.30.1.9:8080/api/reply/' + rno + '/' + bno, {
	        method: 'DELETE'
	    }).then(function(response) {
	        return response.text();
	    }).then(function(data) {
	    	if(data == "SUCCESS") {
	    		//alert("등록되었습니다.");
	    		if(totalCount < 1) {
	    			totalCount = 1;
	    		}
	    	} else {
	    		return;
	    	}
	    	
	    	div.innerHTML = '';
	    	count = 1;
	    		    	
	    	document.getElementById("replyer").value = '';
	    	document.getElementById("replytext").value = '';
	    	
	    	getPage();
	    }).catch(function(error) {
	        console.log("error ----------->", error);
	    });;
	}
	
	function showModal() {
        var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), '');

        myModal.show();

        document.getElementById('staticBackdropLabel').innerText = '알림';
        document.getElementById("modal-body").innerHTML = '댓글이 등록 되었습니다.';
        document.getElementById("modalCommit").innerText = '확인';

        document.getElementById("modalClose").hidden = true;
    }
</script>
    
    
<%@ include file="../include/footer.jsp" %>