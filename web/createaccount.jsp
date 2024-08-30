<%-- 
    Document   : createaccount
    Created on : Jul 1, 2024, 10:19:14 PM
    Author     : ADMIN
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="shortcut icon" type="image/x-icon" href="./images/logo3.png">
        <style>
            .background-radial-gradient {
                background-color: hsl(218, 41%, 15%);
                background-image: radial-gradient(650px circle at 0% 0%,
                    hsl(218, 41%, 35%) 15%,
                    hsl(218, 41%, 30%) 35%,
                    hsl(218, 41%, 20%) 75%,
                    hsl(218, 41%, 19%) 80%,
                    transparent 100%),
                    radial-gradient(1250px circle at 100% 100%,
                    hsl(218, 41%, 45%) 15%,
                    hsl(218, 41%, 30%) 35%,
                    hsl(218, 41%, 20%) 75%,
                    hsl(218, 41%, 19%) 80%,
                    transparent 100%);
            }

            #radius-shape-1 {
                height: 220px;
                width: 220px;
                top: -60px;
                left: -130px;
                background: radial-gradient(#44006b, #ad1fff);
                overflow: hidden;
            }

            #radius-shape-2 {
                border-radius: 38% 62% 63% 37% / 70% 33% 67% 30%;
                bottom: -60px;
                right: -110px;
                width: 300px;
                height: 300px;
                background: radial-gradient(#44006b, #ad1fff);
                overflow: hidden;
            }

            .bg-glass {
                background-color: hsla(0, 0%, 100%, 0.9) !important;
                backdrop-filter: saturate(200%) blur(25px);
            }
        </style>
    </head>
    <body>
        <section class="background-radial-gradient overflow-hidden container-fluid">
            <div class="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
                <div class="row gx-lg-5 align-items-center mb-5">
                    <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
                        <h1 class="my-5 display-5 fw-bold ls-tight" style="color: hsl(218, 81%, 95%)">
                            The best website <br />
                            <span style="color: hsl(218, 81%, 75%)">for manage feedback and response</span>
                        </h1>
                        <p class="mb-4 opacity-70" style="color: hsl(218, 81%, 85%)">
                        </p>
                    </div>

                    <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
                        <div id="radius-shape-1" class="position-absolute rounded-circle shadow-5-strong"></div>
                        <div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>

                        <div class="card bg-glass">
                            <div class="card-body px-4 py-5 px-md-5">
                                <form action="createaccount" method="post">
                                    <!-- 2 column grid layout with text inputs for the first and last names -->
                                    <div class="row">
                                        <div class="col-md-6 mb-4">
                                            <div data-mdb-input-init class="form-outline">
                                                <input type="text" id="form3Example1" name="firstname" class="form-control" value="${requestScope.firstname}"/>
                                                <label class="form-label" for="form3Example1">First name</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6 mb-4">
                                            <div data-mdb-input-init class="form-outline">
                                                <input type="text" id="form3Example2" name="lastname" class="form-control" value="${requestScope.lastname}" />
                                                <label class="form-label" for="form3Example2">Last name</label>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Email input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="text" name="username" id="form3Example3" class="form-control"  value="${requestScope.username}"/>
                                        <label class="form-label" for="form3Example3">User name</label>
                                    </div>

                                    <!-- Password input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="password" name="password" id="form3Example4" class="form-control"  value="${requestScope.password}"/>
                                        <label class="form-label" for="form3Example4">Password</label>
                                    </div>

                                    <!-- Submit button -->
                                    <input type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4" value="Sign up"/>
                                    <c:if test="${not empty requestScope.error}">
                                        <h4 class="small fw-bold mt-2 pt-1 mb-0 link-danger">${requestScope.error}</h4>
                                    </c:if>
                                    <c:if test="${not empty requestScope.success}">
                                        <h4 class="small fw-bold mt-2 pt-1 mb-0 link-success">${requestScope.success}</h4>
                                    </c:if>
                                    <p class="small mt-2 pt-1 mb-0">Go back to <a href="login" style="color: rgb(11 86 198) !important;">Login</a></p>
                                    
                                    <div class="text-center">
                                        <p>or sign up with:</p>
                                        <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                                            <i class="fab fa-facebook"></i>
                                        </button>

                                        <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                                            <i class="fab fa-google"></i>
                                        </button>

                                        <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                                            <i class="fab fa-twitter"></i>
                                        </button>

                                        <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                                            <i class="fab fa-github"></i>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
