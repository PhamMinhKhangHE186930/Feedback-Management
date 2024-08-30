<%-- 
    Document   : fbdisplayforsupport
    Created on : Jun 18, 2024, 8:55:46 PM
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
                    <div class="col-md-2">
                        <div class="position-fixed">
                            Danh mục:
                            <form action="fbdisplayforsupport" method="get" id="form-search-many" onchange="change()" style="width: 100%;">
                                <input oninput="" name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" style="width: 100%;" value="${requestScope.search}">
                                <br>
                                <table>
                                    <tbody>
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
                                                    <option value="none">None</option>
                                                    <option ${requestScope.sortdate eq 'Newest' ?'selected':''}>Newest</option>
                                                    <option ${requestScope.sortdate eq 'Oldest' ?'selected':''}>Oldest</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Type:</td>
                                            <td><select name="fbtype" style="width: 145px">
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
                                    </tbody>
                                </table>
                                <input type="submit" value="Search" />
                            </form>
                        </div>
                    </div>
                    <div class="col-md-10 content-right">
                        <table class="fbdisplay-table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Student</th>
                                    <th>Type</th>
                                    <th>Tittle(Purpose)</th>
                                    <th>Content</th>
                                    <th>Create Date</th>
                                    <th>Status</th>
                                    <th>Public Type</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody id="ajaxcontent">
                                <c:set value="${(requestScope.current-1)*5}" var="no"/>
                                <c:forEach items="${requestScope.fbList}" var="l">
                                    <c:set value="${no+1}" var="no"/>
                                    <fmt:formatDate value="${l.createDate}" pattern="yyyy-MM-dd 'at' HH:mm"  var="date" />
                                    <tr>
                                        <td>${no}</td>
                                        <td>
                                            <c:forEach items="${requestScope.aList}" var="a">
                                                <c:if test="${a.getId() == l.getAccountID()}">
                                                    ${a.displayname}
                                                    <!-- change accountname to display name -->
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>${l.feedbackType.type_name}</td>
                                        <td>${l.fTitle}</td>
                                        <td id="contents" style="width: 20%;">
                                            <p id="content" style="width: 190px;
                                               white-space: nowrap;
                                               overflow: hidden;
                                               text-overflow: ellipsis;" >${l.fContent}</p>
                                        </td>
                                        <td>${date}</td>
                                        <td>${l.status?'responded':'not response'}</td>
                                        <td>${l.checkPublic?'public':'private'}</td>
                                        <td>
                                            <a href="/StudentFeedback/createresponse?fbid=${l.id}">
                                                <input class="createresponse-btn" type="submit" value="${!l.status?'Create Response':'Another Response'}" />
                                            </a>
                                            <c:if test="${l.status}">
                                                <a href="/StudentFeedback/allresponsetofeedback?fbid=${l.id}">
                                                    <input class="allresponse-btn" type="submit" value="All Response" />
                                                </a>
                                            </c:if>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
                                function searchByName(param) {
                                    var txtsearch = param.search.value;
                                    var afromdate = param.fromdate.value;
                                    var atodate = param.todate.value;
                                    var asortdate = param.sortdate.value;
                                    var afbtype = param.fbtype.value;
                                    if ($('#publics').is(":checked")) {
                                        var apublics = param.publics.value;
                                    }
                                    if ($('#privates').is(":checked")) {
                                        var aprivates = param.privates.value;
                                    }

                                    var aget = param.get.value;
                                    $.ajax({
                                        url: "/StudentFeedback/searchajax",
                                        data: {
                                            txt: txtsearch,
                                            fromdate: afromdate,
                                            todate: atodate,
                                            sortdate: asortdate,
                                            fbtype: afbtype,
                                            publics: apublics,
                                            privates: aprivates,
                                            get: aget
                                        },
                                        cache: false,
                                        type: "get",
                                        success: function (data) {
                                            var row = document.getElementById("ajaxcontent");
                                            row.innerHTML = data;
                                        },
                                        error: function (xhr) {

                                        }
                                    });
                                }

                                if (performance.navigation.type == 2) {
                                    location.reload(true);
                                }
        </script>
    </body>
</html>
