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
				            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
				        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
				    </font>
				</c:if>

                <form role="form" method="POST">
                    <div class="mb-3">
                        <label for="userid" class="form-label">ID</label>
                        <input type="text" class="form-control" id="userid" name="userid" placeholder="ID를 입력해주세요">
                    </div>
                    <div class="mb-3">
                        <label for="userpw" class="form-label">PW</label>
                        <input type="password" class="form-control" id="userpw" name="userpw" placeholder="PW를 입력해주세요">
                    </div>
                    <div class="mb-3">
                        <div class="row">
                            <div class="col-6">
                                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#exampleModal">회원가입</button>
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
            
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">회원 가입</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        <form method="POST" action="/join">
			          <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
			          <div class="mb-3">
			            <label for="uid" class="col-form-label">아이디</label>
			            <input type="text" class="form-control" id="uid" name="upw">
			          </div>
			          <div class="mb-3">
			            <label for="uname" class="col-form-label">이름</label>
			            <input type="text" class="form-control" id="upw" name="uname">
			          </div>
			          <div class="mb-3">
			            <label for="upw" class="col-form-label">비밀번호</label>
			            <input type="password" class="form-control" id="upw" name="upw">
			          </div>
			          <div class="mb-3">
			            <label for="re-upw" class="col-form-label">비밀번호 확인</label>
			            <input type="password" class="form-control" id="re-upw" name="re-upw">
			          </div>
			          <div class="mb-3">
			            <label for="ueamil" class="col-form-label">이메일</label>
			            <input type="email" class="form-control" id="uemail" name="uemail">
			          </div>
			        </form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			        <button type="button" class="btn btn-primary">Send message</button>
			      </div>
			    </div>
			  </div>
			</div>
        </div>
    </div>
    
<%@ include file="../include/footer.jsp" %>