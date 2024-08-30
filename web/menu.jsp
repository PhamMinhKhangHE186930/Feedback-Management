<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="a" value="${sessionScope.account}"/>
<c:if test="${a.getRole() == 1}">
    <div class="sticky-top fixed-top">
        <header>
            <ul class="header-ul">
                <li><a href="#"><img src="./images/logo3.png" width="40px" alt="Logo"/></a></li>
                <li><h2><b>Manage and Response Student's Feedback System</b></h2></li> 
                <li><img class="user-logo" src="./images/user.svg" width="40px" alt="LogoUser" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="outside"/>
                    <form class="dropdown-menu p-4" style="width: 250px;">
                        <div class="mb-3">
                            <p>${sessionScope.account.displayname}</p>
                            <hr>
                        </div>
                        <div class="mb-3" style="padding-left: 15%;">
                            <a href="logout" ><p>Setting</p></a>    
                        </div>
                        <a href="logout" style="padding-left: 12%;" ><button type="button" class="btn btn-primary">Logout</button></a>
                    </form></li>
            </ul>
        </header>
        <nav class="navbar navbar-expand-md navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand col-md-2 text-center" href="supporthome">Response Dashboard</a>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-2">
                        <li class="nav-item dropdown text-center">
                            <a class="nav-link dropdown-toggle" href="" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Feedback
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="fbdisplayforsupport?get=all">All Feedback</a></li>
                                <li><a class="dropdown-item" href="fbdisplayforsupport?get=responded">Responded</a></li>
                                <li><a class="dropdown-item" href="fbdisplayforsupport?get=notresponse">Not Response</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown  text-center">
                            <a class="nav-link dropdown-toggle" href="" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Response
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="responsedisplay?get=all">All Response</a></li>
                                <li><a class="dropdown-item" href="responsedisplay?get=user">My Response</a></li>
                            </ul>
                        </li>
                        <li class="nav-item  text-center">
                            <a class="nav-link active" aria-current="page" href="groupstd">Group Student</a>
                        </li>
                    </ul>
                </div>
                <a class="navbar-brand text-center" href="supporthome">User: ${sessionScope.account.displayname}</a>
            </div>
        </nav>
    </div>
</c:if>
<c:if test="${a.getRole() == 2}">
    <div class="sticky-top fixed-top">
        <header>
            <ul class="header-ul">
                <li><a href="#"><img src="./images/logo3.png" width="40px" alt="Logo"/></a></li>
                <li><h2><b>Student's Feedback System</b></h2></li>
                <li><img class="user-logo" src="./images/user.svg" width="40px" alt="LogoUser" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="outside"/><div class="dropdown">
                        <form class="dropdown-menu p-4" style="width: 250px;">
                            <div class="mb-3">
                                <p>${sessionScope.account.displayname}</p>
                                <hr>
                            </div>
                            <div class="mb-3" style="padding-left: 15%;">
                                <a href="logout" ><p>Setting</p></a>    
                            </div>
                            <a href="logout" style="padding-left: 12%;" ><button type="button" class="btn btn-primary">Logout</button></a>
                        </form>
                    </div></li>
            </ul>
        </header>
        <nav class="navbar navbar-expand-md navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand col-md-2 text-center" href="studenthome">Create Feedback</a>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-2">
                        <li class="nav-item dropdown text-center">
                            <a class="nav-link dropdown-toggle" href="" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                My Feedback
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="feedbackdisplay?get=all">All Feedback</a></li>
                                <li><a class="dropdown-item" href="feedbackdisplay?get=notresponse">Not Response</a></li>
                                <li><a class="dropdown-item" href="feedbackdisplay?get=responded">Responded</a></li>
                            </ul> 
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="otherfeedback">Other Feedback</a>
                        </li>
                    </ul>
                </div>
                <a class="navbar-brand text-center" href="studenthome">User: ${sessionScope.account.displayname}</a>
            </div>
        </nav>
    </div>
</c:if>

