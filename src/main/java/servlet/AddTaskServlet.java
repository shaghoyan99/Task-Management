package servlet;

import manager.TaskManager;
import model.Task;
import model.TaskStatus;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/addTask")
public class AddTaskServlet extends HttpServlet {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private TaskManager taskManager = new TaskManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String date = req.getParameter("date");
        String status = req.getParameter("status");
        int userId = Integer.parseInt(req.getParameter("user_id"));

        StringBuilder msg = new StringBuilder();

        if (name == null || name.length() == 0) {
            msg.append("Name field is required <br>");
        }
        if (description == null || description.length() == 0) {
            msg.append("Description field is required <br>");
        }
        if (date == null || date.length() == 0) {
            msg.append("Date field is required <br>");
        }

        if (msg.toString().equals("")) {

            try {
                taskManager.addTask(Task.builder()
                        .name(name)
                        .description(description)
                        .deadline(sdf.parse(date))
                        .taskStatus(TaskStatus.valueOf(status))
                        .userId(userId)
                        .build());
                msg.append("Task was successfully added <br>");

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        req.getSession().setAttribute("msg", msg.toString());
        resp.sendRedirect("/managerHome");
    }
}
