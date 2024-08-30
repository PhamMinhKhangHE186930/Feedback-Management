<%-- 
    Document   : responsedisplay
    Created on : Jun 15, 2024, 1:40:08 PM
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/menu.css"/>
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
                            <form action="responsedisplay" method="post" id="form-search-many" onchange="change()" style="width: 100%;">
                                <input name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="width: 100%;" value="${requestScope.search}">
                                <br>
                                <div>
                                    <table>
                                        <tbody>
                                            <tr><b>Receive Date</b></tr>
                                            <tr>
                                                <td>From:</td>
                                                <td><input type="date" name="receivefrom" value="${requestScope.receivefrom != null ? requestScope.receivefrom : ''}" /></td>
                                            </tr>
                                            <tr>
                                                <td>To:</td>
                                                <td><input type="date" name="receiveto" value="${not empty requestScope.receiveto ? requestScope.receiveto : ''}" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div>
                                    <table>
                                        <tbody>
                                            <tr><b>Response Date</b></tr>
                                            <tr>
                                                <td>From:</td>
                                                <td><input type="date" name="responsefrom" value="${requestScope.responsefrom != null ? requestScope.responsefrom : ''}" /></td>
                                            </tr>
                                            <tr>
                                                <td>To:</td>
                                                <td><input type="date" name="responseto" value="${not empty requestScope.responseto ? requestScope.responseto : ''}" /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>Sort Date:</td>
                                                <td>
                                                    <select name="sortdate">
                                                        <option>None</option>
                                                        <option value="newestReceive" ${requestScope.sortdate eq "newestReceive" ?'selected':''}>Newest Receive</option>
                                                        <option value="oldestReceive" ${requestScope.sortdate eq "oldestReceive" ?'selected':''}>Oldest Receive</option>
                                                        <option value="newestResponse" ${requestScope.sortdate eq "newestResponse" ?'selected':''}>Newest Response</option>
                                                        <option value="oldestResponse" ${requestScope.sortdate eq "oldestResponse" ?'selected':''}>Oldest Response</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Type:</td>
                                                <td>
                                                    <select name="fbtype" style="width: 160px;">
                                                        <option value="0" ${('0' eq requestScope.type)?'selected':''}>All Type</option>
                                                        <c:forEach items="${requestScope.tList}" var="t">
                                                            <option value="${t.id}" ${(t.id eq requestScope.type)?'selected':''}>${t.type_name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Public Type:</td>
                                                <td>
                                                    <input type="checkbox" name="publics" id="publics" value="1" ${(requestScope.publicType.equals(param.publics))?'checked':''}/>
                                                    <label for="publics">Public</label>
                                                    <br>
                                                    <input type="checkbox" name="privates" id="privates" value="0" ${requestScope.privateType.equals(param.privates)?'checked':''}/>
                                                    <label for="privates">Private</label>
                                                </td>
                                            </tr> 
                                        </tbody>
                                    </table>
                                </div>
                                <input type="hidden" name="get" value="${requestScope.get}" />
                                <input type="submit" value="Search" />
                            </form>
                        </div>
                    </div>
                    <div class="col-md-10 content-right">
                        <table class="rpdisplay-table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Tittle(Purpose)</th>
                                    <th>Content</th>
                                    <th>Receive Date</th>
                                    <th>Response Date</th>
                                    <th>Feedback Tittle</th>
                                    <th>Feedback Type</th>
                                    <th>Public Type</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set value="${(requestScope.current-1)*5}" var="no"/>
                                <c:forEach items="${requestScope.map}" var="m">
                                    <fmt:formatDate value="${m.key.createDate}" pattern="yyyy-MM-dd" var="receivedate" />
                                    <c:forEach items="${m.value}" var="rd">
                                        <fmt:formatDate value="${rd.responseDate}" pattern="yyyy-MM-dd" var="responsedate" />
                                        <c:set value="${no+1}" var="no"/>
                                        <tr>
                                            <td>${no}</td>
                                            <td>${rd.rTittle}</td>
                                            <td id="contents" style="width: 20%" >
                                                <p style="width: 190px;
                                                   white-space: nowrap;
                                                   overflow: hidden;
                                                   text-overflow: ellipsis;" >${rd.rContent}</p>
                                            </td>
                                            <td>${receivedate}</td>
                                            <td>${responsedate}</td>
                                            <td>${m.key.fTitle}</td>
                                            <td>${m.key.feedbackType.type_name}</td>
                                            <td>${m.key.checkPublic?'public':'private'}</td>
                                            <td>
                                                <a href="/StudentFeedback/createresponse?fbid=${m.key.id}">
                                                    <input class="createresponse-btn" type="submit" value="Another Response" />
                                                </a>
                                                <a href="/StudentFeedback/responsedetails?rid=${rd.id}">
                                                    <input class="allresponse-btn" type="submit" value="See Response" />
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </c:forEach>
                                <c:if test="${no == 0}">
                                    <tr>
                                        <td colspan="9">
                                            <h5>There is no response</h5>
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                        <c:if test="${requestScope.totalpage > 1}">
                            <c:set value="${requestScope.current}" var="current"/>
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
