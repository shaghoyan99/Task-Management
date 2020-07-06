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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        int taskId = Integer.parseInt(req.getParameter("taskId"));

        StringBuilder msg = new StringBuilder();

        if (msg.toString().equals("")) {
            commentManager.deleteCommentById(commentId);
            msg.append("Comment was deleted <br>");
        }
        req.getSession().setAttribute("msg", msg.toString());
        resp.sendRedirect("/taskPage?taskId=" + taskId);
    }
}
