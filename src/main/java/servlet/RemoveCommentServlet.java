package servlet;

import lombok.SneakyThrows;
import manager.CommentManager;
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

@WebServlet(urlPatterns = "/removeComment")
public class RemoveCommentServlet extends HttpServlet {

    CommentManager commentManager = new CommentManager();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        Task task = (Task) session.getAttribute("task");

        StringBuilder msg = new StringBuilder();

        if (msg.toString().equals("")) {
            commentManager.deleteCommentById(id);
            msg.append("Comment was deleted <br>");
        }
        req.getSession().setAttribute("msg", msg.toString());
        req.setAttribute("id", id);
        resp.sendRedirect("/taskPage?id=" + task.getId());
    }
}
