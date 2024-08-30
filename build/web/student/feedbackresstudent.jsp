<%-- 
    Document   : feedbackresstudent
    Created on : Jun 24, 2024, 9:06:56 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback's Response</title>
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
                    <div class="col-md-2">
                        <div>
                            <a class="nav-link" href="studenthome"><h5><b>Create Feedback</b></h5></a>
                        </div>
                        <div>
                            <h5><b>My Feedback</b></h5>
                            <ul class="navigation-side">
                                <li><a href="feedbackdisplay?get=all">All Feedback</a></li>
                                <li><a href="feedbackdisplay?get=notresponse">Not Response</a></li>
                                <li><a href="feedbackdisplay?get=responded">Responded</a></li>
                            </ul>
                        </div>
                        <div>
                            <a class="nav-link" href="otherfeedback"><h5><b>Other Feedback</b></h5></a>
                        </div>
                    </div>
                    <div class="col-md-10">
                        <c:if test="${requestScope.format_error != null}">
                            <h2>${requestScope.format_error}</h2>
                        </c:if>
                        <c:if test="${requestScope.format_error == null}">
                            <c:set value="${requestScope.feedback}" var="fb"/>
                            <fmt:formatDate value="${fb.createDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="feedbackdate" />
                            <h4>Your Feedback: ${fb.fTitle}</h4>
                            <table class="feedbackres-table" border="0">
                                <thead>
                                    <tr>
                                        <th class="row">
<!--                                            <div class=""><p>Send by: ${sessionScope.account.displayname}</p></div>-->
                                            <div class=""><p>Send Date:<span class="span-date"> ${feedbackdate}</span></p></div>
                                            <div class=""><p>Category: ${fb.feedbackType.type_name}</p></div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div><h6>Feedback Content: </h6></div>
                                            <div><p>${fb.fContent}</p></div>
                                            <hr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <c:set value="0" var="index"/>
                            <c:forEach items="${requestScope.rplist}" var="rp">
                                <fmt:formatDate value="${rp.responseDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="reponsedate" />
                                <fmt:formatDate value="${rp.modifiedDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="modifiedDate" />
                                <table class="feedbackres-table" border="0">
                                    <thead>
                                        <tr>
                                            <th class="row" style="position: relative">
                                                <div class="col-md-6"><h3>Response Tittle: ${rp.rTittle}</h3></div>
                                                <div class="col-md-12 d-flex row" style="position: absolute">
                                                    <div class="d-flex flex-row-reverse"><p>Response Date: <span class="span-date">${reponsedate}</span></p></div>
                                                            <c:if test="${reponsedate != modifiedDate}">
                                                        <div class="d-flex flex-row-reverse">
                                                            <p >Modified Date:<span class="span-date"> ${modifiedDate}</span></p>
                                                        </div>
                                                    </c:if>
                                                </div>
                                                <div><p>Name: ${requestScope.name.get(index)}</p></div>
                                                <c:set value="${index+1}" var="index"/>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div><h6>Response Content: </h6></div>
                                                <div><p>${rp.rContent}</p></div>
                                                <hr>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </c:forEach>
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
