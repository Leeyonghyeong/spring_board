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
    	<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
    	<sec:authorize access="isAnonymous()">
    		<input type="hidden" id="uid" value="">
    	</sec:authorize>
    	<sec:authorize access="isAuthenticated()">
    		<input type="hidden" id="uid" value="<sec:authentication property="principal.userid" />">
    	</sec:authorize>
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
        <div class="content-body text-center">
       		<div class="text-area my-3">${board.content }</div>
       		
       		<c:forEach items="${files }" var="file">
	       		<div class="img-area mb-5" data-src="${file}">
					<img src="/files/displayFile?fileName=${file }" class="img" alt="image" />
	       		</div>
	       	</c:forEach>
	       	
        </div>
    </div>
    <sec:authorize access="isAuthenticated()">
    	<c:set var="uid"><sec:authentication property='principal.userid' /></c:set>
    	
    	<c:if test="${board.writer == uid}">
    		<a class="btn btn-warning" href="/update/${bno }${pageMaker.makeQuery(pageMaker.cri.page) }">수정</a>
    		<button type="submit" class="btn btn-danger" onclick="boardDelete()">삭제</button>
    	</c:if>
    </sec:authorize>
    <a class="btn btn-primary" href="/${pageMaker.makeQuery(pageMaker.cri.page) }">목록보기 </a>
    
    
	<div class="row box">
		<sec:authorize access="isAuthenticated()">
	        <div class="box-header mt-5">
	            <h3 class="bos-title">댓글 달기</h3>
	            <hr>
	        </div>
	        
	        <div class="box-body">
	            <div class="mb-3">
	                <label for="replyer" class="form-label">작성자</label>
	                <input type="text" class="form-control" id="replyer" name="replyer" value="<sec:authentication property="principal.userid" />" readonly>
	            </div>
	            <div class="mb-3">
	                <label for="replytext" class="form-label">댓글</label>
	                <input type="text" class="form-control" id="replytext" name="replytext">
	            </div>
	            <button type="submit" class="btn btn-primary" onclick="addReply()">댓글등록</button>
	        	<hr>
	    	</div>
    	</sec:authorize>
	
	    <div class="box-footer mt-3" id="replies">
	    	
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
		const replyExist = document.querySelectorAll(".bi-chat-dots");
		
		if(replyExist.length > 0) {
			showModal("Exist Reply");
			return;
		}
		
		let files = [];
		
		document.querySelectorAll(".img-area").forEach(that => {
			files.push(that.getAttribute("data-src"));
		});
		
		console.log(files);
		
		if(files.length > 0) {
			fetch('http://172.30.1.9:8080/files/deleteAllFile/', {
		        method: 'POST',
		        headers: {
		            '${_csrf.headerName}': '${_csrf.token}',
		        },
		        body: files
		    }).then(function(response) {
		        return response.text();
		    }).then(function(data) {
		    	console.log(data);
		    }).catch(function(error) {
		        console.log("error ----------->" + error);
		    });;
		}
		
		form.action = "/delete";
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
        	let gridText = ""
        	
        	grid.setAttribute("class", "mb-3 bg-light border border-1 border-dark");
            
        	gridText += '<div class="mb-1"><div class="row mt-3"><div class="col-6 ps-4">' +
									'<span><i class="bi bi-chat-dots"></i> ' + 
									list[i].replyer + 
									'</span></div><div class="col-6 text-end pe-4"><span><i class="bi bi-stopwatch"></i> ' + 
									prettifyDate(list[i].regdate) + 
									'</span></div></div><hr></div><div class="mb-1"><div class="px-2"  id="reply' + list[i].rno + '">' +
									list[i].replytext +						       
						    		'</div><hr></div>';
			if(list[i].replyer == document.getElementById("uid").value) {
				gridText += '<div class="mb-3"><div class="px-2">' + 
							'<button type="submit" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="updateReply(' + list[i].rno + ')">댓글 수정</button> ' +
	        				'<button type="submit" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="deleteReply(' + list[i].rno + ')">댓글 삭제</button>' +
	        				'</div></div>';
			}
						    	
			grid.innerHTML = gridText;
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
		const div = document.getElementById("replies");
		
		fetch('http://172.30.1.9:8080/api/reply/', {
	        method: 'POST',
	        headers: {
	            '${_csrf.headerName}': '${_csrf.token}',
	            "content-Type" : "application/json"
	        },
	        body: JSON.stringify({"bno" : bno, "replyer" : replyer, "replytext" : replytext}),
	    }).then(function(response) {
	        return response.text();
	    }).then(function(data) {
	    	if(data == "SUCCESS") {
	    		showModal("Add Reply");
	    		if(totalCount < 1) {
	    			totalCount = 1;
	    		}
	    	} else {
	    		return;
	    	}
	    	
	    	div.innerHTML = '';
	    	count = 1;
	    		    	
	    	document.getElementById("replytext").value = '';
	    	
	    	getPage();
	    }).catch(function(error) {
	    	showModal("No Login");
	    });;
	    
	}
	
	function updateReply(rno) {
		const replytext = document.getElementById("reply" + rno).innerText;

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
		const kind = document.getElementById("modalCommit").innerText;
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
		const div = document.getElementById("replies");
		
		fetch('http://172.30.1.9:8080/api/reply/' + rno, {
	        method: 'PUT',
	        body: JSON.stringify({"rno" : rno, "replytext" : replytext}),
	        headers: {
	            '${_csrf.headerName}': '${_csrf.token}',
	            "content-Type" : "application/json"
	        },
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
	    		    	
	    	document.getElementById("replytext").value = '';
	    	
	    	getPage();
	    }).catch(function(error) {
	        console.log("error ----------->", error);
	    });;
	}
	
	function commitDelete() {
		const rno = document.getElementById("hiddenRno").value;
		const div = document.getElementById("replies");
		
		fetch('http://172.30.1.9:8080/api/reply/' + rno + '/' + bno, {
	        method: 'DELETE',
	        headers: {
	            '${_csrf.headerName}': '${_csrf.token}',
	            "content-Type" : "application/json"
	        },
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
	    		    	
	    	document.getElementById("replytext").value = '';
	    	
	    	getPage();
	    }).catch(function(error) {
	        console.log("error ----------->", error);
	    });;
	}
	
	function showModal(type) {
        const myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), '');

        myModal.show();
        
        if(type == "Add Reply") {
        	document.getElementById("modal-body").innerHTML = '댓글이 등록 되었습니다.';
        } else if(type == "No Login") {
        	document.getElementById("modal-body").innerHTML = '로그인 후 이용 바랍니다.';
        } else {
        	document.getElementById("modal-body").innerHTML = '댓글이 달린 게시물은 삭제할 수 없습니다.';
        }

        document.getElementById('staticBackdropLabel').innerText = '알림';
        document.getElementById("modalCommit").innerText = '확인';

        document.getElementById("modalClose").hidden = true;
    }
</script>    
    
<%@ include file="../include/footer.jsp" %>