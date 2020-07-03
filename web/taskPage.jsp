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

<% Task task = (Task) session.getAttribute("task");
    List<Comment> allComments = (List<Comment>) request.getAttribute("allComments");
    User user = (User) session.getAttribute("user");
%>
<% if (user.getUserType() == UserType.MANAGER) {%>
<a href="/managerHome">Back</a>
<% } else {%>
<a href="/userHome"> Back </a>
<%}%>

<div>
    All Tasks: <br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user</th>
        </tr>
        <%
            if (task != null) {
        %>
        <tr>
            <td><%=task.getName()%>
            </td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getTaskStatus().name()%>
            </td>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname()%>
            </td>
            <%
                }
            %>
        </tr>
    </table>
</div>
<div>
    All Comments: <br>
    <table border="1">
        <tr>
            <th>comment</th>
            <th>date</th>
            <th>user</th>
            <th>remove</th>
        </tr>
        <%
            if (allComments != null) {
                for (Comment comment : allComments) { %>
        <tr>
            <td><%=comment.getComment()%>
            </td>
            <td><%=comment.getDate()%>
            </td>
            <td><%=comment.getUser().getName() + " " + comment.getUser().getSurname()%>
            </td>
            <td><%
                if (user.getUserType() == UserType.MANAGER) {%>
                <a href="/removeComment?id=<%=comment.getId()%>">X</a>
                <% } else if (comment.getUser_id() == user.getId()) {%>
                <a href="/removeComment?id=<%=comment.getId()%>">X</a>
                <% } %>
            </td>


            <%
                    }
                }
            %>
        </tr>
    </table>
</div>
<div>
    <form action="/addComment" method="post">
        <label>
            <textarea name="comment" placeholder="comment"></textarea>
        </label>
        <input type="submit" name="addComment">
    </form>

</div>


</body>
</html>
