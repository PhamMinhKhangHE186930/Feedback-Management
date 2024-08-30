<%-- 
    Document   : otherfeedback
    Created on : Jun 23, 2024, 9:47:43 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="shortcut icon" type="image/x-icon" href="./images/logo3.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="./CSS/menu.css"/>
        <link rel="stylesheet" href="./CSS/content.css"/>
        <link rel="stylesheet" href="./CSS/std.css"/>
        <link rel="stylesheet" href="./CSS/footer.css"/>
    </head>
    <body>
        <%@include file="../menu.jsp"%>
        <c:if test="${requestScope.denied != null}">
            <div class="container-fluid content">
                <h4>${requestScope.denied}</h4>
            </div>
        </c:if>
        <c:if test="${empty requestScope.denied}">
            <div class="container-fluid content">
                <div class="row">
                    <div class="col-md-3 search-section">
                        <div class="position-fixed">
                            Danh mục:
                            <form action="otherfeedback" method="post" id="form-search-many" onchange="change()">
                                <input name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="width: 300px;" value="${requestScope.search}">
                                <br>
                                <table class="search-table">
                                    <tbody>
                                        <tr>
                                            <td>From date:</td>
                                            <td>
                                                <input type="date" name="fromdate" value="${requestScope.fromdate != null ? requestScope.fromdate : ''}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>To date:</td>
                                            <td>
                                                <input type="date" name="todate" value="${not empty requestScope.todate ? requestScope.todate : ''}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Sort Date:</td>
                                            <td>
                                                <select name="sortdate">
                                                    <option>None</option>
                                                    <option ${requestScope.sortdate eq 'Newest' ?'selected':''}>Newest</option>
                                                    <option ${requestScope.sortdate eq 'Oldest' ?'selected':''}>Oldest</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Type:</td>
                                            <td>
                                                <select name="fbtype" style="width: 150px;">
                                                    <option value="0" ${('0' eq requestScope.type)?'selected':''}>All Type</option>
                                                    <c:forEach items="${requestScope.tList}" var="t">
                                                        <option value="${t.id}" ${(t.id eq requestScope.type)?'selected':''}>${t.type_name}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <input type="submit" value="Search" />
                            </form>
                        </div>
                    </div>
                    <div class="col-md-9 content-right" style="padding: 20px;">
                        <c:set value="0" var="count"/>
                        <c:forEach items="${requestScope.fbList}" var="fb">
                            <c:set value="${count+1}" var="count"/>
                            <fmt:formatDate value="${fb.createDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="date" />
                            <table border="0" style="width: 70%;">
                                <thead>
                                    <tr>
                                        <th class="row">
                                            <div class="col-md-6"><h4><b>Feedback Tittle: ${fb.fTitle}</b></h4></div>
                                            <div class="col-md-6 d-flex flex-row-reverse"><p>Date:<span class="span-date"> ${date}</span></p></div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div><p>Type: ${fb.feedbackType.type_name}</p></div>
                                            <div><h6>Feedback Content: </h6></div>
                                            <div><p>${fb.fContent}</p></div>
                                            <hr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <br>
                        </c:forEach>
                        <c:if test="${requestScope.totalpage == 0}">
                            <h5>There is no another public feedback</h5>
                        </c:if>
                        <c:if test="${requestScope.totalpage > 1}">
                            <nav aria-label="Page navigation example" class="dynamic-paging">
                                <ul class="pagination">
                                    <li class="page-item">
                                        <a class="page-link ${current == 1 ? 'disabled':''}" href="${requestScope.url}page=${current > 1 ? (current-1):current}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${requestScope.totalpage}" var="page">
                                        <li class="page-item"><a class="page-link ${requestScope.current eq page?'active':''}" href="${requestScope.url}page=${page}">${page}</a></li>
                                        </c:forEach>
                                    <li class="page-item ${current == requestScope.totalpage ? 'disabled':''}">
                                        <a class="page-link" href="${requestScope.url}page=${current < requestScope.totalpage ? (current+1):current}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:if>
        <%@include file="../footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script src="./JS/code.js"></script>
        <script>
                                if (performance.navigation.type == 2) {
                                    location.reload(true);
                                }
        </script>
    </body>
</html>
