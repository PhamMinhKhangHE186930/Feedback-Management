<%-- 
    Document   : responsedetails
    Created on : Jun 21, 2024, 1:59:20 PM
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

                    </div>
                    <div class="col-md-10">
                        <c:if test="${requestScope.format_error != null}">
                            <h2>${requestScope.format_error}</h2>
                        </c:if>
                        <c:if test="${requestScope.format_error == null}">
                            <c:set value="${requestScope.responseContent}" var="rp"/>
                            <c:set value="${requestScope.feedback}" var="fb"/>
                            <fmt:formatDate value="${rp.responseDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="reponsedate" />
                            <fmt:formatDate value="${rp.modifiedDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="modifiedDate" />
                            <fmt:formatDate value="${fb.createDate}" pattern="yyyy-MM-dd 'at' HH:mm" var="feedbackdate" />
                            <table border="0" style="width: 80%;">
                                <thead>
                                    <tr>
                                        <th class="row" style="position: relative">
                                            <div class="col-md-6"><h3>Response Tittle: ${rp.rTittle}</h3></div>
                                            <div class="col-md-12 d-flex row" style="position: absolute">
                                                <div class="d-flex flex-row-reverse">
                                                    <p>Response Date:<span class="span-date"> ${reponsedate}</span></p>
                                                </div>
                                                <c:if test="${reponsedate != modifiedDate}">
                                                    <div class="d-flex flex-row-reverse">
                                                        <p >Modified Date:<span class="span-date"> ${modifiedDate}</span></p>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div><p>Name: ${requestScope.supportname}</p></div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div><h6>Response Content: </h6></div>
                                            <div><p id="exist-content">${rp.rContent}</p></div>
                                                <c:if test="${requestScope.responseContent.accountid == sessionScope.account.id}">
                                                <form action="responsedetails" method="post">
                                                    <textarea id="editcontent" name="editcontent" style="display: none; width: 100%; min-height: 200px; resize:  vertical; ">${rp.rContent}</textarea>
                                                    <input type="hidden" name="id" value="${rp.id}"/>
                                                    <input type="hidden" name="action" value="update"/>
                                                    <div style="display: flex; column-gap: 20px;">
                                                        <input class="save-btn" id="savebtn" type="submit" value="Save" style="display: none;"/>
                                                        <button class="cancel-btn" id="cancelbtn" type="reset" style="display: none;">Cancel</button>
                                                    </div>
                                                </form>
                                                <input class="allresponse-btn" id="edit-btn" type="submit" value="Edit Response" />
                                                <form id="delete-form" action="responsedetails" method="post">
                                                    <input type="hidden" name="id" value="${rp.id}"/>
                                                    <input type="hidden" name="fid" value="${fb.id}"/>
                                                    <input type="hidden" name="action" value="delete"/>
                                                    <button class="delete-btn" onclick="dodelete()" type="button">Delete Response</button>
                                                </form>
                                            </c:if>
                                            <hr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <h4>Response to the Feedback: ${fb.fTitle}</h4>
                            <table border="0" style="width: 80%;">
                                <thead>
                                    <tr>
                                        <th class="row">
                                            <div><p>Type: ${fb.feedbackType.type_name}</p></div>
                                            <div class=""><p>Send by: ${name}</p></div>
                                            <div class=""><p>Send Date:<span class="span-date"> ${feedbackdate}</span></p></div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div><h6>Feedback Content: </h6></div>
                                            <div><p>${fb.fContent}</p></div>
                                            <c:if test="${requestScope.filename != null}">
                                                <div>
                                                    <form action="downloadfile" method="post">
                                                        <input type="hidden" name="fname" value="${requestScope.filename}"/>
                                                        <input type="hidden" name="stdid" value="${requestScope.stdid}"/>
                                                        <input class="allresponse-btn" type="submit" value="Get File" />
                                                    </form>
                                                </div>
                                            </c:if>
                                            <hr>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <a href="/StudentFeedback/createresponse?fbid=${fb.id}">
                                <input class="createresponse-btn" type="submit" value="Create Another Response" style="width: 225px; margin-bottom: 50px;"/>
                            </a>
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

                                                        function dodelete() {
                                                            if (confirm("'Are you sure want to delete this response, any change can't undo") == true) {
                                                                document.getElementById("delete-form").submit();
                                                            }
                                                        }

                                                        const editbtn = document.querySelector("#edit-btn");
                                                        const existContent = document.querySelector("#exist-content");
                                                        const editContent = document.querySelector("#editcontent");
                                                        const savebtn = document.querySelector("#savebtn");
                                                        const cancelbtn = document.querySelector("#cancelbtn");


                                                        editbtn.addEventListener("click", () => {
                                                            existContent.style.display = "none"; // Hide existing content
                                                            savebtn.style.display = "block";
                                                            cancelbtn.style.display = "block";
                                                            editContent.style.display = "block"; // Show textarea
                                                        });


                                                        cancelbtn.addEventListener("click", () => {
                                                            existContent.style.display = "block";
                                                            editContent.style.display = "none";
                                                            cancelbtn.style.display = "none";
                                                            savebtn.style.display = "none";
                                                        });

        </script>
    </body>
</html>
