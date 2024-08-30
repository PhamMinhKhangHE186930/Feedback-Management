<%-- 
    Document   : createresponse
    Created on : Jun 19, 2024, 9:24:30 PM
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
        <%@include  file="../menu.jsp"%>
        <c:if test="${requestScope.denied != null}">
            <div class="container-fluid content">
                <h4>${requestScope.denied}</h4>
            </div>
        </c:if>
        <c:if test="${empty requestScope.denied}">
            <div class="container-fluid content">
                <div class="row">
                    <div class="col-md-2">

                    </div>
                    <div class="col-md-10">
                        <div>
                            <c:set value="${requestScope.feedback}" var="fb"/>
                            <fmt:formatDate value="${fb.createDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="date" />
                            <h2>Student's Feedback</h2>
                            <table border="0" style="width: 90%;">
                                <thead>
                                    <tr>
                                        <th class="row" style="position: relative;">
                                            <div class="col-md-6"><p>Feedback Tittle: ${fb.fTitle}</p></div>
                                            <div class="col-md-6"style="position: absolute; left: 50%;">
                                                <div class="d-flex flex-row-reverse"><p>Date:<span class="span-date"> ${date}</span></p></div>
                                                <c:if test="${requestScope.filename != null}">
                                                    <div class="d-flex flex-row-reverse ">
                                                        <form action="downloadfile" method="post">
                                                            <input type="hidden" name="fname" value="${requestScope.filename}"/>
                                                            <input type="hidden" name="stdid" value="${requestScope.stdid}"/>
                                                            <input class="allresponse-btn" type="submit" value="Get File" />
                                                        </form>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div><p>Type: ${fb.feedbackType.type_name}</p></div>
                                            <div><p>Name: ${requestScope.name}</p></div>
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
                        </div>
                        <div>
                            <c:if test="${not empty requestScope.rList}">
                                <h2>Older Response</h2>
                                <c:forEach items="${requestScope.rList}" var="rlist">
                                    <fmt:formatDate value="${rlist.responseDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="rdate"/>
                                    <table border="0" style="width: 90%;">
                                        <thead>
                                            <tr>
                                                <th class="row">
                                                    <div class="col-md-6"><p>Response Tittle: ${rlist.rTittle}</p></div>
                                                    <div class="col-md-6 d-flex flex-row-reverse"><p>Response Date:<span class="span-date"> ${rdate}</span></p></div>
                                                    <c:forEach items="${requestScope.aList}" var="alist">
                                                        <c:if test="${rlist.accountid == alist.id}">
                                                            <div><p>Name: ${alist.displayname}</p></div>
                                                        </c:if>
                                                    </c:forEach>
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <div><h6>Response Content: </h6></div>
                                                    <div><p>${rlist.rContent}</p></div>
                                                    <hr>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </c:forEach>
                            </c:if>
                        </div>
                        <form action="/StudentFeedback/createresponse" method="post" class="row">
                            <div class="col-md-12">
                                <h1>Create Response</h1>
                                <table style="width: 90%; border: 2px solid black;">
                                    <thead>
                                        <tr style="border: 2px solid black;">
                                            <th style=" width: 8%;">Tittle:</th>
                                            <th><input type="text" name="rtittle" value="" style="width: 100%; border: 0;" placeholder="Enter Response Tittle"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td style="vertical-align: top;">Content:</td>
                                            <td>
                                                <textarea name="rcontent" style="min-width: 100%; min-height: 200px; max-width: 900px; resize: both;"></textarea>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br>
                                <input type="hidden" name="fbid" value="${fb.getId()}" />
                                <input class="createresponse-btn" type="submit" value="Send Feedback" />
                                <p style="color: red;">${requestScope.createfalse}</p>
                                <p style="color: #8dc572;">${requestScope.createsuccess}</p>
                            </div>
                        </form>
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
