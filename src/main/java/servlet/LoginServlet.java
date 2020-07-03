package servlet;

import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet  extends HttpServlet {

    private  UserManager userManager = new UserManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        StringBuilder msg = new StringBuilder();

        if (email == null || email.length() == 0) {
            msg.append("Email field is required <br>");
        }
        if (password == null || password.length() == 0) {
            msg.append("Password field is required <br>");
        }


        if(msg.toString().equals("")) {

            User user = userManager.getUserByEmailAndPassword(email, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                if (user.getUserType() == UserType.MANAGER) {
                    resp.sendRedirect("/managerHome");
                    return;
                } else {
                    resp.sendRedirect("/userHome");
                    return;
                }
            } else {
                msg.append("User does not exists");
            }
        }
        req.getSession().setAttribute("msg", msg.toString());
        resp.sendRedirect("/");
    }
}
