package servlet;

import manager.CommentManager;
import manager.TaskManager;
import model.Comment;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/taskPage")
public class TaskPageServlet extends HttpServlet {

    TaskManager taskManager = new TaskManager();
    CommentManager commentManager = new CommentManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Task taskById = taskManager.getTaskById(id);
        List<Comment> allCommentsByTaskId = commentManager.getAllCommentsByTaskId(taskById.getId());
        HttpSession session = req.getSession();
        session.setAttribute("task",taskById);
        req.setAttribute("allComments",allCommentsByTaskId);
        req.getRequestDispatcher("/WEB-INF/taskPage.jsp").forward(req,resp);
    }
}
