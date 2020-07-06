<%@ page import="model.Task" %>
<%@ page import="model.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.UserType" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 26.06.2020
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TaskComment</title>
</head>
<body>

<%
    String msg = "";
    if (session.getAttribute("msg") != null) {
        msg = (String) session.getAttribute("msg");
        session.removeAttribute("msg");
    }
%>

<p style="color: red">
    <%=msg%>
</p>

<%User user = (User) session.getAttribute("user");%>

<%List<Comment> comments = (List<Comment>) request.getAttribute("allComments");%>
<%Task task = (Task) request.getAttribute("tasks");%>


Name : <%=task.getName()%><br>
Status : <%=task.getTaskStatus()%><br>
Description : <%=task.getDescription()%><br>
Deadline : <%=task.getDeadline()%><br><br>
add Comment<br><br>
<div>
    <form action="/addComment" method="post">

        <input type="hidden" name="taskId" value="<%=task.getId()%>">
        <input type="hidden" name="userId" value="<%=user.getId()%>">
        <input type=hidden name="userName" value="<%=user.getName()%>">

        <textarea style="width: 250px;height: 150px;" placeholder="Comment" name="comment"></textarea><br><br>
        <input type="submit" class="button" name="comment">
    </form>

</div>
<br>
<%for (Comment comment : comments) {%>
<div class="user"><%=comment.getUser().getName()%> by :</div>
<div class="comment"><%=comment.getComment()%>
</div>
<div class="date"><%=comment.getDate()%>
</div>
<%if ((user.getUserType() == UserType.USER && user.getName().equals(comment.getUser().getName())) || (user.getUserType() == UserType.MANAGER)) {%>
<a href="/removeComment?commentId=<%=comment.getId()%>&taskId=<%=comment.getTaskId()%>">
    <button class="button">Remove</button>
</a>
<%}%><br><br>
<%}%>


</body>
</html>
