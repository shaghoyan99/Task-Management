package servlet;

import manager.TaskManager;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/userHome")

public class UserHomeServlet extends HttpServlet {

    private TaskManager taskManager = new TaskManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            List<Task> allTasksByUserId = taskManager.getAllTasksByUserId(user.getId());
            req.setAttribute("tasks", allTasksByUserId);
            req.getRequestDispatcher("/WEB-INF/user.jsp").forward(req, resp);
        }else {
            resp.sendRedirect("/");
        }




    }

}
