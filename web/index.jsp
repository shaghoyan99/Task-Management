
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
    <input type="text" name="email" placeholder="Input your email" required><br>
    <input type="password" name="password" placeholder="Input your password" required ><br>
    <input type="submit" value="Login">
  </form>

  </body>
</html>
