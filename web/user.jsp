<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 20.06.2020
  Time: 1:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% List<Task> tasks = (List<Task>) request.getAttribute("tasks");

    User user = (User) session.getAttribute("user");
%>

Welcome <%=user.getName()%> <% if (user.getPictureUrl() != null) { %>
<img src="/image?path=<%=user.getPictureUrl()%>" width="50"/> <%}%>

<a href="/logout"> logout </a>

<div>
    All Tasks: <br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user</th>
            <th>Update Status</th>
        </tr>
        <%
            if (tasks != null){
            for (Task task : tasks){ %>
        <tr>
            <td>  <a href="/taskPage?id=<%=task.getId()%>"> <%=task.getName()%> </a>
            </td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getTaskStatus().name()%>
            </td>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname()%>
            </td>
            <td>
                <form action="/changeTaskStatus" method="post">
                    <input type="hidden" name="taskId" value="<%=task.getId()%>">
                    <select name="status">
                        <option value="NEW">NEW</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                        <option value="FINISHED">FINISHED</option>
                    </select> <input type="submit" value="OK">
                </form>
            </td>

        <%
            }
            }
        %>
        </tr>
    </table>
</div>
</body>
</html>
