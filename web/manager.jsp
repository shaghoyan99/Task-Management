<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="javax.jws.soap.SOAPBinding" %>
<%@ page import="model.Task" %>
<%@ page import="model.Comment" %><%--
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
<%
    List<User> users = (List<User>) request.getAttribute("user");
    List<Task> tasks = (List<Task>) request.getAttribute("task");
%>

<a href="/logout"> logout </a>

<div style="width: 800px;">
    <div style="width: 50%; float: left">
        Add User:<br>
        <form action="/userRegister" method="post" enctype="multipart/form-data">
            <input type="text" name="name" placeholder="name"><br>
            <input type="text" name="surname" placeholder="surname"><br>
            <input type="text" name="email" placeholder="email"><br>
            <input type="password" name="password" placeholder="password"><br>
            <select name="type">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>
            </select><br>
            <input type="file" name="image"> <br>

            <input type="submit" value="Register">
        </form>
    </div>
    <div style="width: 50%; float: left">
        Add Task:<br>
        <form action="/addTask" method="post">
            <input type="text" name="name" placeholder="name"><br>
            <textarea name="description" placeholder="description"> </textarea><br>
            <input type="date" name="date"><br>
            <select name="status">
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>
            </select><br>
            <select name="user_id">

                <%
                    if (users != null) {
                        for (User user : users) {
                %>
                <option value="<%=user.getId()%>"><%=user.getName()%> <%=user.getSurname()%></option>

                <%
                        }
                    }
                %>

            </select><br><br>
            <input type="submit" value="Add">
        </form>
    </div>
<div>
    All Users:<br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>surname</th>
            <th>email</th>
            <th>type</th>
            <th>picture</th>
        </tr>
        <%
            if (users != null) {
                for (User user : users) {%>
        <tr>
            <td><%=user.getName()%>
            </td>
            <td><%=user.getSurname()%>
            </td>
            <td><%=user.getEmail()%>
            </td>
            <td><%=user.getUserType().name()%>
            </td>
            <td> <% if (user.getPictureUrl() != null) { %>
                <img src="/image?path=<%=user.getPictureUrl()%>" width="30"/>
                <%}%>
            </td>
            <%
                }
                }
            %>
        </tr>
    </table>
</div>
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
            if (tasks != null) { %>
               <% for (Task task : tasks) { %>
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

            <%
                    } %>
               <% }
            %>
        </tr>
    </table>
</div>
</div>

</body>
</html>