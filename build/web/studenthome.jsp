<%-- 
    Document   : studenthome
    Created on : Jun 9, 2024, 3:08:52 PM
    Author     : ADMIN
--%>

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
        <%@include  file="menu.jsp"%>
        <c:if test="${requestScope.denied != null}">
            <div class="container-fluid content">
                <h4>${requestScope.denied}</h4>
            </div>
        </c:if>
        <c:if test="${empty requestScope.denied}">
            <div class="container-fluid content">
                <div class="row">
                    <div class="col-md-1">
                    </div>
                    <div class="col-md-11">
                        <form action="studenthome" method="post" class="row" enctype="multipart/form-data">
                            <div class="col-md-9">
                                <h1>Create Feedback</h1>
                                <h3>Send a feedback to Academic Administration dept</h3>
                                <p style="font-size: 0.8vw"><b>Note:</b> Regarding sending applications/emails to departments<br>
                                    The application processing department will respond to students' applications/emails within 48 hours (except withdrawal applications, appeal applications, facility transfer...).<br>
                                    To limit SPAM, the time to respond to SPAM applications/emails will be extended according to the principle: When students send N applications/emails (N>1) for the same request, the response time will be within <b>Nx48 hours</b>.<br>
                                    Therefore, students need to consider before sending applications/emails with the same content to receive the fastest answer/resolution according to regulations.</p>
                            </div>
                            <div class="col-md-9">
                                <table class="create-form">
                                    <thead>
                                        <tr class="title-input">
                                            <th style=" width: 8%; height: 30px;">Tittle:</th>
                                            <th><input type="text" name="fbtittle" value="" style="width: 100%;" placeholder="Enter Feedback Tittle"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td style="vertical-align: top;">Content:</td>
                                            <td>
                                                <textarea name="fbcontent" placeholder="Input your feedback content here"></textarea>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br>
                                <div class="sending-section">
                                    <input class="send-btn" type="submit" value="Send Feedback" />
                                    <p style="color: red;"><b>${requestScope.createfalse}</b></p>
                                    <p style="color: #8dc572;"><b>${requestScope.createsuccess}</b></p>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <ul class="choice-list">
                                    <li>
                                        <h4>Feedback Type</h4>
                                        <select class="fbtype" name="fbtype">
                                            <c:forEach items="${requestScope.typelist}" var="t">
                                                <option value="${t.id}">${t.type_name}</option>
                                            </c:forEach>
                                        </select>
                                    </li>
                                    <br>
                                    <li>
                                        <h4>Public Type</h4>
                                        <input type="radio" name="publictype" id="public" value="public" />
                                        <label for="public">Public</label>
                                        <input type="radio" name="publictype" id="private" value="private" />
                                        <label for="private">Private</label>
                                        <br>
                                        <h6><b>Note:</b> if you choose public type your name will never display to the other student</h6>
                                    </li>
                                    <br> 
                                    <li>
                                        <h4>Upload File</h4>
                                        <input class="choose-file" type="file" name="files" width="100%;"/>
                                    </li>
                                </ul>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <%@include file="./footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script src="./JS/code.js"></script>
        <script>
            if (performance.navigation.type == 2) {
                location.reload(true);
            }
        </script>
    </body>
</html>
