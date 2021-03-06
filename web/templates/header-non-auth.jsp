<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 4) + 1 %></c:set>

<c:set var="SITE_URL" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="shortcut icon" href="${SITE_URL}/asset/img/favicon.ico">
    <link rel="stylesheet" href="${SITE_URL}/asset/css/bootstrap.min.css">
    <link rel="stylesheet" href="${SITE_URL}/asset/css/style.css">

    <script src="${SITE_URL}/asset/js/jquery-3.2.0.min.js"></script>
    <script src="${SITE_URL}/asset/js/bootstrap.min.js"></script>

    <style>
        html, body {
            height: 100%;
            width: 100%;
            background-color: rgba(255,255,255,0);
        }
        .error {
            blackground-color: red;
        }
    </style>

    <title>Usami | Marketplace of Creative</title>
</head>
<body>
    <div id="main-body">
        <div class="container col-sm-8 col-sm-offset-2 col-xs-12">
