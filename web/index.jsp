<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19.06.2020
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login Page </title>
  </head>
  <body>

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

  <form action="/login" method="post">
    <input type="text" name="email" placeholder="Input your email"><br>
    <input type="password" name="password" placeholder="Input your password"><br>
    <input type="submit" value="Login">
  </form>

  </body>
</html>
