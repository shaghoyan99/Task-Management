<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Բարի Գալուստ</title>
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

Login:
<form action="/login" method="post">
    <input type="text" name="email" placeholder="Please input email"/><br>
    <input type="password" name="password" placeholder="Please input password"/><br>
    <input type="submit" value="Login">
</form>
<br>

</body>
</html>
