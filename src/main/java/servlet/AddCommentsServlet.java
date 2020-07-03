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

@WebServlet(urlPatterns = "/addComment")
public class AddCommentsServlet extends HttpServlet {

    private CommentManager commentManager = new CommentManager();
    private TaskManager taskManager = new TaskManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Task task = (Task) session.getAttribute("task");
        String comment = req.getParameter("comment");

        StringBuilder msg = new StringBuilder();

        if (comment == null || comment.length() == 0) {
            msg.append("Comment field is required <br>");
        }

        if (msg.toString().equals("")) {
            Comment comment1 = Comment.builder()
                    .task_id(task.getId())
                    .user_id(user.getId())
                    .comment(comment)
                    .build();
            commentManager.addComment(comment1);
            msg.append("Comment was successfully added <br>");
        }
        req.getSession().setAttribute("msg", msg.toString());
        resp.sendRedirect("/taskPage?id=" + task.getId());
    }
}
