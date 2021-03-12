<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="UTF-8">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="author" content="L.Dev">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><c:if test="${title eq null }">login</c:if> ${title} | My Board</title>

    <!-- Bootstrap CSS -->
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/img/favicon/favicon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">
	<link rel="stylesheet" href="/resources/css/base.css">
  </head>
  <body>
  	
  	<!-- 상단 navbar -->
  	<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
        <div class="container-fluid px-5">
            <a class="navbar-brand" href="/"><i class="bi bi-clipboard pe-2" style="font-size: 1.29rem; color: cornflowerblue;"></i>My Board</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse justify-content-end" id="navbarNavDropdown" >
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/">Home</a>
                    </li>
                    
                    <sec:authorize access="isAuthenticated()">
	                    <li class="nav-item">
	                        <a class="nav-link" href="#"><sec:authentication property="principal.username"></sec:authentication></a>
	                    </li>
	                </sec:authorize>
	                
                    <li class="nav-item">
                    	<sec:authorize access="isAnonymous()">
	                        <a class="nav-link" href="/login">Sign In</a>                    	
                    	</sec:authorize>
                    	<sec:authorize access="isAuthenticated()">
                    		<form action="/logout" method="POST" name="form">
                    			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                        </form>
		                    <a class="nav-link" href="#" onclick="document.form.submit()">Sign Out</a>
                    	</sec:authorize>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    

    