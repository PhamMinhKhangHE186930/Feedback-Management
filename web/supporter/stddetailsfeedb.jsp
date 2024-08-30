<%-- 
    Document   : stddetails
    Created on : Jul 2, 2024, 10:21:29 PM
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
                    <div class="col-md-3 search-section">
                        <div class="position-fixed ">
                            Danh mục:
                            <form action="stddetailsfeedb" method="get" id="form-search-many" onchange="change()">
                                <input name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="width: 300px;" value="${requestScope.search}">
                                <br>
                                From date:
                                <input type="date" name="fromdate" value="${requestScope.fromdate != null ? requestScope.fromdate : ''}" />
                                <br>
                                To date:
                                <input type="date" name="todate" value="${not empty requestScope.todate ? requestScope.todate : ''}" />
                                <br>
                                Sort Date:
                                <select name="sortdate">
                                    <option>None</option>
                                    <option ${requestScope.sortdate eq 'Newest' ?'selected':''}>Newest</option>
                                    <option ${requestScope.sortdate eq 'Oldest' ?'selected':''}>Oldest</option>
                                </select>
                                <br>
                                Type:
                                <select name="fbtype">
                                    <option value="0" ${('0' eq requestScope.type)?'selected':''}>All Type</option>
                                    <c:forEach items="${requestScope.tList}" var="t">
                                        <option value="${t}" ${(t eq requestScope.type)?'selected':''}>${t}</option>
                                    </c:forEach>
                                </select>
                                <br>
                                Public Type:
                                <input type="checkbox" name="publics" id="publics" value="1" ${(requestScope.publicType.equals(param.publics))?'checked':''}/>
                                <label for="publics">Public</label>
                                <input type="checkbox" name="privates" id="privates" value="0" ${requestScope.privateType.equals(param.privates)?'checked':''}/>
                                <label for="privates">Private</label>
                                <input type="hidden" name="stdid" value="${requestScope.stdid}" />
                                <input type="submit" value="Search" />
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-7">
                        <c:if test="${empty requestScope.error}">
                            <c:forEach items="${requestScope.fbList}" var="l">
                                <fmt:formatDate value="${l.createDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="date" />
                                <table border="0" style="width: 100%;">
                                    <thead>
                                        <tr>
                                            <th class="row">
                                                <div class="col-md-6"><p>Feedback Tittle: ${l.fTitle}</p></div>
                                                <div class="col-md-6 d-flex flex-row-reverse"><p>Date:<span class="span-date"> ${date}</span></p></div>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div><h6>Feedback Content: </h6></div>
                                                <div><p>${l.fContent}</p></div>
                                                <c:if test="${l.status == true}">
                                                    <div class="seeRes-btn">
                                                        <a href="allresponsetofeedback?fbid=${l.id}"><button class="allresponse-btn">All Response</button></a>
                                                    </div>
                                                </c:if>
                                                <c:if test="${l.status == false}">
                                                    <div class="seeRes-btn">
                                                        <a href="/StudentFeedback/createresponse?fbid=${l.id}"><button class="createresponse-btn">Create Response</button></a>
                                                    </div>
                                                </c:if>
                                                <hr>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br>
                            </c:forEach>
                            <c:if test="${requestScope.totalpage != 0}">
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
                        </c:if> 
                        <c:if test="${not empty requestScope.error}">
                            <p>${requestScope.error}</p>
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
