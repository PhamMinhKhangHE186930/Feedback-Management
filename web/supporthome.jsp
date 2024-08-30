<%-- 
    Document   : adminhome
    Created on : Jun 9, 2024, 3:08:44 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
        <link rel="stylesheet" href="./CSS/supp.css"/>
        <style>
            @keyframes progress1 {
                0% {
                    width: 0;
                }
                100% {
                    width: 100%;
                }
            }
            @keyframes progress2 {
                0% {
                    width: 0;
                }
                100% {
                    width: ${p1}%;
                }
            }
            @keyframes progress3 {
                0% {
                    width: 0;
                }
                100% {
                    width: ${p2}%;
                }
            }
        </style>
    </head>
    <body>
        <%@include  file="menu.jsp"%>
        <c:if test="${requestScope.denied != null}">
            <div class="container-fluid content">
                <h4>${requestScope.denied}</h4>
            </div>
        </c:if>
        <c:if test="${empty requestScope.denied}">
            <div class="container-fluid">
                <div class="row content" style=" height: 100%;">
                    <div class="col-md-2 navigation-side">
                        <h4 class="dropdown-toggle sidebar-link" data-bs-toggle="collapse" data-bs-target="#feedbackmore" aria-expanded="false" aria-controls="feedbackmore">Feedback</h4>
                        <ul class="collapse multi-collapse" id="feedbackmore">
                            <li><a href="fbdisplayforsupport?get=all">All Feedback</a></li>
                            <li><a href="fbdisplayforsupport?get=responded">Responded</a></li>
                            <li><a href="fbdisplayforsupport?get=notresponse">Not Response</a></li>
                        </ul>
                        <h4 class="dropdown-toggle sidebar-link" data-bs-toggle="collapse" data-bs-target="#feedbackmore2" aria-expanded="false" aria-controls="feedbackmore2">Response</h4>
                        <ul class="collapse multi-collapse" id="feedbackmore2">
                            <li><a href="responsedisplay?get=all">All Response</a></li>
                            <li><a href="responsedisplay?get=user">My Response</a></li>
                        </ul>

                        <h4><a class="nav-link active" aria-current="page" href="groupstd">Group Student</a></h4>

                    </div>
                    <div class="col-md-1"></div>
                    <div class="col-md-9">
                        <div>
                            <h1 class="dashboard">Dashboard</h1>
                            <h2>Total Feedback: ${requestScope.totalFB} Feedbacks</h2>
                            <div class="progress">
                                <div class="progress-bar bg-info" role="progressbar" style="width: ${requestScope.totalFB*100/requestScope.totalFB}%; background-color: rgb(11 86 198) !important; animation: progress1 1.2s linear forwards;" aria-valuenow="${requestScope.totalFB}" aria-valuemin="0" aria-valuemax="${requestScope.totalFB}"></div>

                            </div>
                            <!--<progress id="total" value="${requestScope.totalFB}" max="${requestScope.totalFB}"></progress>-->
                            <div>
                                <a href="fbdisplayforsupport?get=all"><input class="moreinfo-btn" type="submit" value="See more information" /></a>
                            </div>
                        </div>
                        <br>
                        <div>
                            <div class="row width-progress-bl">
                                <h2 class="col-md-10">Responded Feedback: ${requestScope.respondedFB}</h2>
                                <h5 class="col-md-2">
                                    <fmt:formatNumber value="${requestScope.respondedFB*100/requestScope.totalFB}" pattern="#.##" var="p1"/>
                                    ${p1}%
                                </h5>
                            </div>
                            <div class="progress">
                                <div class="progress-bar bg-info" role="progressbar" style="width: ${requestScope.respondedFB*100/requestScope.totalFB}%; background-color: rgb(11 86 198) !important; animation: progress2 1.2s linear forwards;" aria-valuenow="${requestScope.respondedFB}" aria-valuemin="0" aria-valuemax="${requestScope.totalFB}"></div>
                            </div>
                            <!--<progress id="total" value="${requestScope.respondedFB}" max="${requestScope.totalFB}" ></progress>-->
                            <div>
                                <a href="fbdisplayforsupport?get=responded"><input class="moreinfo-btn" type="submit" value="See more information" /></a>
                            </div>
                        </div>
                        <br> 
                        <div>
                            <div class="row width-progress-bl">
                                <h2 class="col-md-10">Not Response Feedback: ${requestScope.notRespondedFB}</h2>
                                <h5 class="col-md-2">
                                    <fmt:formatNumber value="${requestScope.notRespondedFB*100/requestScope.totalFB}" pattern="#.##" var="p2"/>
                                    ${p2}%
                                </h5>
                            </div>
                            <div class="progress">
                                <div class="progress-bar bg-info" role="progressbar" style="width: ${requestScope.notRespondedFB*100/requestScope.totalFB}%; background-color: rgb(11 86 198) !important; animation: progress3 1.2s linear forwards;" aria-valuenow="${requestScope.notRespondedFB}" aria-valuemin="0" aria-valuemax="${requestScope.totalFB}"></div>
                            </div>
                            <!--<progress id="total" value="${requestScope.notRespondedFB}" max="${requestScope.totalFB}"></progress>-->
                            <div>
                                <a href="fbdisplayforsupport?get=notresponse"><input class="moreinfo-btn" type="submit" value="See more information" /></a>
                            </div>
                        </div>
                        <br>    
                        <div>
                            <h2>Recent Feedback (in last 24 hours): ${requestScope.numerFBin24} feedback was sent in last 24 hours</h2>
                            <div>
                                <a href="fbdisplayforsupport?get=last24"><input class="moreinfo-btn" type="submit" value="See more information" /></a>
                            </div>
                        </div>
                    </div>
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
