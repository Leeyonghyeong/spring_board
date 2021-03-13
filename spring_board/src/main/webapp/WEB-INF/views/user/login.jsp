<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

    <sec:authorize access="isAuthenticated()">
    	<script>
    		window.location = "/";
    	</script>
    </sec:authorize>
    
    <div class="container mt-5">
        <div class="row">
            <div class="col-4">
            </div>

            <div class="col-4">
                <h1 class="display-5 mb-5" style="font-weight: bold;">로그인</h1>
                <hr>
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				    <font color="red">
				            아이디나 비밀번호가 맞지 않습니다.
				        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
				    </font>
				</c:if>

                <form role="form" method="POST">
                    <div class="mb-3">
                        <label for="id" class="form-label">ID</label>
                        <input type="text" class="form-control" id="id" name="id" placeholder="ID를 입력해주세요">
                    </div>
                    <div class="mb-3">
                        <label for="pw" class="form-label">PW</label>
                        <input type="password" class="form-control" id="pw" name="pw" placeholder="PW를 입력해주세요">
                    </div>
                    <div class="mb-3">
                        <div class="row">
                            <div class="col-6">
                                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#staticBackdrop">회원가입</button>
                            </div>
                            <div class="col-6 text-end">
                                <button type="submit" class="btn btn-primary">로그인</button>
                            </div>
                        </div>
                    </div>
                    
                    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
                </form>
            </div>

            <div class="col-4">

            </div>
            
            <!-- 회원 가입 modal -->
            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		        <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		            <h5 class="modal-title" id="staticBackdropLabel">회원 가입</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        <form method="POST" action="/join" id="joinForm">
			          <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
			          <div class="mb-3">
			            <label for="userid" class="col-form-label">아이디</label>
			            <input type="text" class="form-control" id="userid" name="userid" maxlength="20">
			            <span id="idValidate"></span>
			          </div>
			          <div class="mb-3">
			            <label for="username" class="col-form-label">이름</label>
			            <input type="text" class="form-control" id="username" name="username">
			            <span id="nameValidate"></span>
			          </div>
			          <div class="mb-3">
			            <label for="password" class="col-form-label">비밀번호</label>
			            <input type="password" class="form-control" id="password" name="password">
			            <span id="pwValidate"></span>
			          </div>
			          <div class="mb-3">
			            <label for="re-password" class="col-form-label">비밀번호 확인</label>
			            <input type="password" class="form-control" id="re-password" name="re-password">
			            <span id="repwValidate"></span>
			          </div>
			          <div class="mb-3">
			            <label for="email" class="col-form-label">이메일</label>
			            <input type="email" class="form-control" id="email" name="email">
			            <span id="emailValidate"></span>
			          </div>
			        </form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
			        <button type="button" class="btn btn-primary" id="joinSubmit">가입</button>
			      </div>
			    </div>
			  </div>
			</div>
			
			<!-- 알림 modal -->
			
			<!-- Modal -->
			<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">알림</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        회원가입이 완료 되었습니다.<br>
			        로그인 후 이용해 주시기 바랍니다.
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
			      </div>
			    </div>
			  </div>
			</div>
			
			
        </div>
    </div>
    
    <script>
    	const form = document.getElementById("joinForm");
    	const formSpan = form.getElementsByTagName("span")
    	const joinSubmit = document.getElementById("joinSubmit");
    	
    	const userid = document.getElementById("userid");
    	const username = document.getElementById("username");
		const password = document.getElementById("password");
		const repw = document.getElementById("re-password");
		const email = document.getElementById("email");
		
		let font = true;
		
    	joinSubmit.addEventListener("click", () => {
  			
    		for(let i = 0; i < formSpan.length; i++) {
    			if(font) {
    				formSpan[i].innerHTML = "";    				
    			}
    		}

    		if(userid.value == "") {
    			userid.focus();
    			document.getElementById("idValidate").innerHTML = '<small><font color="red">아이디를 입력해 주세요</font></small>';
    			return;
    		} 
    		
    		if(username.value == "") {
    			username.focus();
    			document.getElementById("nameValidate").innerHTML = '<small><font color="red">이름을 입력해 주세요</font></small>';
    			return;
    		}
    		
    		if(password.value == "") {
    			password.focus();
    			document.getElementById("pwValidate").innerHTML = '<small><font color="red">비밀번호를 입력해 주세요</font></small>';
    			return;
    		}
    		
    		if(repw.value == "") {
    			repw.focus();
    			document.getElementById("repwValidate").innerHTML = '<small><font color="red">비밀번호 확인을 입력해 주세요</font></small>';
    			return;
    		}
    		
    		if(email.value == "") {
    			email.focus();
    			document.getElementById("emailValidate").innerHTML = '<small><font color="red">이메일을 입력해 주세요</font></small>';
    			return;
    		}
    		
    		if(password.value != repw.value) {
    			repw.focus();
    			document.getElementById("repwValidate").innerHTML = '<small><font color="red">비밀번호가 일치하지 않습니다.</font></small>';
    			return;
    		}
    		
    		if(!validateEmail(email.value)) {
    			email.focus();
    			document.getElementById("emailValidate").innerHTML = '<small><font color="red">올바르지 않은 이메일 형식입니다.</font></small>';
    			return;
    		}

    		if(font) {
    			form.submit();
    		}
    		
    	});
    	
    	userid.addEventListener("keyup", () => {
    		fetch('http://172.30.1.9:8080/user/idValidate', {
    	        method: 'POST',
    	        body: userid.value,
    	        headers: {
    		    	"X-CSRF-TOKEN": "${_csrf.token}",
    		    }
    	    }).then(function(response) {
    	        return response.text();
    	    }).then(function(data) {
    	    	if(data == "ID is Exist") {
    	    		userid.focus();
    	    		document.getElementById("idValidate").innerHTML = '<small><font color="red">이미 존재하는 아이디 입니다.</font></small>';
    	    		font = false
    	    	} else {
    	    		document.getElementById("idValidate").innerHTML = '';
    	    		font = true;
    	    	}
    	    	
    	    }).catch(function(error) {
    	        console.log("error ----------->", error);
    	    });
    	}) 
    	
	    function validateEmail(email) {
		  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		  return re.test(email);
		}
    	
    	function showModal() {
            const myModal = new bootstrap.Modal(document.getElementById('exampleModal'), '');

            myModal.show();
        }
    </script>
    
    
    
<%@ include file="../include/footer.jsp" %>