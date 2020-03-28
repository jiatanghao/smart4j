<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>客户管理</title>
    <link rel="stylesheet" type="text/css" href="${BASE}/static/css/style.css">
</head>
<body>
<h1>客户列表</h1>
<table id="customers">
    <caption>客户信息</caption>
    <tr>
        <th id="name">客户名称</th>
        <th id="contact">联系人</th>
        <th id="telephone">电话号码</th>
        <th id="email">邮箱</th>
        <th id="operation">操作</th>
    </tr>
    <c:forEach var="customer" items="${requestScope.customers}">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telephone}</td>
            <td>${customer.email}</td>
            <td>
                <span><a href="${BASE}/customer_edit?id=${customer.id}">编辑</a></span>
                <span><a href="${BASE}/customer_delete?id=${customer.id}">删除</a></span>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
