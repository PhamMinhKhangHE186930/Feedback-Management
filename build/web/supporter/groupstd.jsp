<%-- 
    Document   : groupstudent
    Created on : Jul 2, 2024, 3:23:09 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Group student</title>
        <link rel="shortcut icon" type="image/x-icon" href="./images/logo3.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="./CSS/menu.css"/>
        <link rel="stylesheet" href="./CSS/content.css"/>
        <link rel="stylesheet" href="./CSS/footer.css"/>
        <link rel="stylesheet" href="./CSS/supp.css"/>
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
                    <div class="col-md-2">
                        <div class="position-fixed" style="width: 15%;">
                            Danh mục:
                            <form action="groupstd" method="get" id="form-search-many" onchange="change()" style="width: 100%;">
                                <input name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="width: 100%;" value="${requestScope.search}">
                                <br>
                                Sort Date:
                                <select name="sortdate">
                                    <option value="none">None</option>
                                    <option ${requestScope.sortdate eq 'Newest' ?'selected':''}>Newest</option>
                                    <option ${requestScope.sortdate eq 'Oldest' ?'selected':''}>Oldest</option>
                                </select>
                                <br>
                                <input type="submit" value="Search" />
                            </form>
                        </div>
                    </div>
                    <div class="col-md-10 content-right">
                        <table class="stdgroup-table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Student</th>
                                    <th>Total Feedback</th>
                                    <th>Latest Feedback date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set value="${(requestScope.current-1)*5}" var="no"/>
                                <c:forEach items="${requestScope.alist}" var="acc">
                                    <c:set value="${no+1}" var="no"/>
                                    <c:set value="${acc.feedbackList.size()}" var="size"/>
                                    <c:if test="${size>0}">
                                        <fmt:formatDate value="${acc.feedbackList.get(size-1).createDate}" pattern="yyyy-MM-dd" var="date" />
                                    </c:if>
                                    <tr>
                                        <td>${no}</td>
                                        <td>${acc.displayname}</td>
                                        <td>${size}</td>
                                        <td>${size > 0 ? date: 'did not create anything'}</td>
                                        <td>
                                            <a href="/StudentFeedback/stddetailsfeedb?stdid=${acc.id}">
                                                <input class="createresponse-btn" type="submit" value="All Student's Response" style="width: 200px;"/>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${no == 0}">
                                    <tr>
                                        <td colspan="9">
                                            <h5>There is no feedback</h5>
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                        <c:if test="${requestScope.totalpage > 1}">
                            <nav aria-label="Page navigation example" class="dynamic-paging">
                                <ul class="pagination">
                                    <li class="page-item ${current == 1 ? 'disabled':''}">
                                        <a class="page-link" href="${requestScope.url}page=${current > 1 ? (current-1):current}" aria-label="Previous">
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
