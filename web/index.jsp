<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="/css/style.css">

</head>
<body>

<!-- Slideshow container -->
<div class="slideshow-container">

    <!-- Full-width images with number and caption text -->
    <div class="mySlides fade">
        <img src="/img/img1.jpg" style="width:100%" height="95%">
    </div>

    <div class="mySlides fade">
        <img src="/img/img2.jpg" style="width:100%" height="95%">
    </div>
    <div class="mySlides fade">
        <img src="/img/img3.jpg" style="width:100%" height="95%">
    </div>

    <!-- Next and previous buttons -->
    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
    <a class="next" onclick="plusSlides(1)">&#10095;</a>
</div>
<br>

<!-- The dots/circles -->
<div style="text-align:center">
    <span class="dot" onclick="currentSlide(1)"></span>
    <span class="dot" onclick="currentSlide(2)"></span>
    <span class="dot" onclick="currentSlide(3)"></span>
</div>


    <%
    String msg = "";
    if (session.getAttribute("msg") != null) {
        msg = (String) session.getAttribute("msg");
        session.removeAttribute("msg");
    }
%>

<p style="color: #ff0000">
    <%=msg%>
</p>


<div id="flip">LOGIN</div>
<div id="panel">
    <form action="/login" method="post">
        <input type="text" name="email" placeholder="Input your email" required><br>
        <input type="password" name="password" placeholder="Input your password" required><br>
        <input type="submit" value="Login">
    </form>
</div>

<script src="/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script src="/js/sliderJs.js" type="text/javascript"></script>

<script>
    $(document).ready(function () {
        $("#flip").click(function () {
            $("#panel").slideToggle("slow");
        });
    });
</script>
<style>
    #panel, #flip {
        padding: 5px;
        text-align: center;
        background-color: #e5eecc;
        border: solid 1px #c3c3c3;
    }

    #panel {
        padding: 50px;
        display: none;
    }
</style>

</html>
