<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>
<%
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    User currentUser = (User) session.getAttribute("user");
%>
Welcome to <%=currentUser.getName()%> <a href="/logout">logout </a> <br>

<%--Add Task:--%>
<%--<form action="/addTask" method="post">--%>

<%--    <input name="title" type="text"/> <br>--%>
<%--    <input name="deadline" type="date"/> <br>--%>
<%--    <input type="submit" value="create">--%>

<%--</form>--%>
<%--<ul>--%>
<%--    <% for (Task task : tasks) {%>--%>
<%--    <%= task.getName()%>--%>
<%--    <li><%= task.getTaskStatus()%>--%>
<%--    </li>--%>
<%--    <% }%>--%>
<%--</ul>--%>
<div>
    All Tasks:<br>
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
            for (Task task : tasks) { %>
        <tr>
            <td><%=task.getName() %>
            </td>
            <td><%=task.getDescription() %>
            </td>
            <td><%=task.getDeadline() %>
            </td>
            <td><%=task.getTaskStatus().name() %>
            </td>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname() %>
            </td>
            <td>
                <form action="/changeTaskStatus" method="post">
                <input type="hidden" name="taskId" value="<%=task.getId()%>">
                <select name="status">
                    <option value="NEW">NEW</option>
                    <option value="IN_PROGRESS">IN_PROGRESS</option>
                    <option value="FINISHED">FINISHED</option>
                </select><input type="submit" value="OK">
                </form>
            </td>
        </tr>

        <%
            }
        %>
    </table>
</div>
</body>
</html>
