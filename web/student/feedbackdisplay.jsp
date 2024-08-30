<%-- 
    Document   : feedbackdisplay
    Created on : Jun 11, 2024, 9:43:31 PM
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
        <jsp:include page="../menu.jsp" />
        <c:if test="${requestScope.denied != null}">
            <div class="container-fluid content">
                <h4>${requestScope.denied}</h4>
            </div>
        </c:if>
        <c:if test="${empty requestScope.denied}">
            <div class="container-fluid">
                <div class="row content">
                    <div class="col-lg-3 search-section">
                        <div class="position-fixed">
                            Danh mục:
                            <form action="feedbackdisplay" method="post" id="form-search-many" onchange="change()" accept-charset="UTF-8">
                                <input name="search" class="form-control me-2" type="search" placeholder="Search" style="width: 300px;" value="${requestScope.search}">
                                <br>
                                <table class="search-table">
                                    <thead>
                                        <tr>
                                            <td>From date:</td>
                                            <td><input type="date" name="fromdate" value="${requestScope.fromdate != null ? requestScope.fromdate : ''}" /></td>
                                        </tr>
                                        <tr>
                                            <td>To date:</td>
                                            <td><input type="date" name="todate" value="${not empty requestScope.todate ? requestScope.todate : ''}" /></td>
                                        </tr>
                                        <tr>
                                            <td>Sort Date:</td>
                                            <td><select name="sortdate">
                                                    <option>None</option>
                                                    <option ${requestScope.sortdate eq 'Newest' ?'selected':''}>Newest</option>
                                                    <option ${requestScope.sortdate eq 'Oldest' ?'selected':''}>Oldest</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Type:</td>
                                            <td><select name="fbtype" style="width: 150px;">
                                                    <option value="0" ${('0' eq requestScope.type)?'selected':''}>All Type</option>
                                                    <c:forEach items="${requestScope.tList}" var="t">
                                                        <option value="${t.id}" ${(t.id eq requestScope.type)?'selected':''}>${t.type_name}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Public Type:</td>
                                            <td><input type="checkbox" name="publics" id="publics" value="1" ${(requestScope.publicType.equals(param.publics))?'checked':''}/>
                                                <label for="publics">Public</label>
                                                <input type="checkbox" name="privates" id="privates" value="0" ${requestScope.privateType.equals(param.privates)?'checked':''}/>
                                                <label for="privates">Private</label>
                                                <input type="hidden" name="get" value="${requestScope.get}" />
                                            </td>
                                        </tr>
                                    </thead>
                                </table>
                                <input type="submit" value="Search" />
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-8 content-right" style="padding: 20px;">
                        <c:forEach items="${requestScope.fbList}" var="l">
                            <fmt:formatDate value="${l.createDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="date" />
                            <table border="0" style="width: 100%;">
                                <thead>
                                    <tr>
                                        <th class="row">
                                            <div class="col-md-6"><h4><b>Feedback Tittle: ${l.fTitle}</b></h4></div>
                                            <div class="col-md-6 d-flex flex-row-reverse"><p>Date:<span class="span-date"> ${date}</span></p></div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div><p>Type: ${l.feedbackType.type_name}</p></div>
                                            <div><h6>Feedback Content: </h6></div>
                                            <div><p>${l.fContent}</p></div>
                                            <c:if test="${l.status == true}">
                                                <div class="seeRes-btn">
                                                    <a href="feedbackresstudent?fbid=${l.id}"><button type="button" class="seeResponse-btn">See Response</button></a>
                                                </div>
                                            </c:if>
                                            <hr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <br>
                        </c:forEach>
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
                        <c:if test="${requestScope.totalpage == 0}">
                            <h5>There is no feedback</h5>
                        </c:if>
                    </div>
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
