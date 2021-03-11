<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

    
    <div class="container mt-5">
        <div class="row">
            <div class="col-4">
            </div>

            <div class="col-4">
                <h1 class="display-5 mb-5" style="font-weight: bold;">로그인</h1>
                <hr>
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
                                <button type="submit" class="btn btn-warning">회원가입</button>
                            </div>
                            <div class="col-6 text-end">
                                <button type="submit" class="btn btn-primary">로그인</button>
                            </div>
                        </div>
                    </div>
                    
                    <input type="hidden" name="${_csrf.parameterName}" value="${_crsf.token}" />
                </form>
            </div>

            <div class="col-4">

            </div>
        </div>
    </div>
    
<%@ include file="../include/footer.jsp" %>