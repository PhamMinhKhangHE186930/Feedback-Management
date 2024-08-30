<%-- 
    Document   : login
    Created on : Jun 9, 2024, 12:38:37 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="shortcut icon" type="image/x-icon" href="./images/logo3.png">
        <style>
            .divider:after,
            .divider:before {
                content: "";
                flex: 1;
                height: 1px;
                background: #eee;
            }
            .h-custom {
                height: calc(100% - 73px);
            }
            @media (max-width: 450px) {
                .h-custom {
                    height: 100%;
                }
            }
        </style>
    </head>
    <body>
        <!--        <h3>Login</h3>
                <form action="login" method="post">
        <c:set var="cookie" value="${pageContext.request.cookies}"/>
        <h4>${message}</h4>
        <label for="name">Username</label>
        <br>
        <input type="text" name="name" id="name" value="${cookie.cname.value}" />
        <br>
        <label for="password">Password</label>
        <br>
        <input type="password" name="password" id="password" value="${cookie.cpassword.value}" />
        <br>
        <input type="checkbox" name="remember" id="remember" value="ON" ${(cookie.cremember!=null)?'checked':''}/>
        <label for="remember">Remember me</label>
        <br>
        <input type="submit" value="Login" />
        <br>
    </form>-->
        <form action="login" method="post">
            <section class="vh-100">
                <div class="container-fluid h-custom">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="col-md-9 col-lg-6 col-xl-5">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                                 class="img-fluid" alt="Sample image">
                        </div>
                        <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                            <form>
                                <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
                                    <p class="lead fw-normal mb-0 me-3">Sign in with</p>
                                    <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-floating mx-1">
                                        <i class="fab fa-facebook-f"></i>
                                    </button>

                                    <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-floating mx-1">
                                        <i class="fab fa-twitter"></i>
                                    </button>

                                    <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-floating mx-1">
                                        <i class="fab fa-linkedin-in"></i>
                                    </button>
                                </div>

                                <div class="divider d-flex align-items-center my-4">
                                    <p class="text-center fw-bold mx-3 mb-0">Or</p>
                                </div>

                                <!-- Email input -->
                                <div data-mdb-input-init class="form-outline mb-4">
                                    <input type="text" id="name" name="name" value="${cookie.cname.value}" class="form-control form-control-lg"
                                           placeholder="Enter a valid account name" />
                                    <label class="form-label" for="name">Account Name</label>
                                </div>

                                <!-- Password input -->
                                <div data-mdb-input-init class="form-outline mb-3">
                                    <input type="password" name="password" id="password" value="${cookie.cpassword.value}" class="form-control form-control-lg"
                                           placeholder="Enter password" />
                                    <label class="form-label" for="password">Password</label>
                                </div>

                                <div class="d-flex justify-content-between align-items-center">
                                    <!-- Checkbox -->
                                    <div class="form-check mb-0">
                                        <input class="form-check-input me-2" type="checkbox" name="remember" id="remember" value="ON" ${(cookie.cremember!=null)?'checked':''} />
                                        <label class="form-check-label" for="remember">
                                            Remember me
                                        </label>
                                    </div>
                                    <a href="#!" class="text-body">Forgot password?</a>
                                </div>

                                <div class="text-center text-lg-start mt-4 pt-2">
                                    <input  type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-lg"
                                            style="padding-left: 2.5rem; padding-right: 2.5rem;" value="Login"></input>
                                    <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <a href="createaccount" class="link-danger">Register</a></p>
                                </div>
                                <h4 class="small fw-bold mt-2 pt-1 mb-0 link-danger">${message}</h4>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- footer -->
                <div style="background-color: rgb(11, 86, 198) !important;"
                     class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5">
                    <!-- Copyright -->
                    <div class="text-white mb-3 mb-md-0">
                        Copyright © 2020. All rights reserved.
                    </div>
                    <!-- Copyright -->

                    <!-- Right -->
                    <div>
                        <a href="#!" class="text-white me-4">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a href="#!" class="text-white me-4">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a href="#!" class="text-white me-4">
                            <i class="fab fa-google"></i>
                        </a>
                        <a href="#!" class="text-white">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                    </div>
                    <!-- Right -->
                </div>
            </section>
        </form>
    </body>
</html>
