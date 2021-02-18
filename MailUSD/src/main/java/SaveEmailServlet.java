import db.entity.Email;
import db.dao.DAO;
import db.dao.EmailDAO;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveEmailServlet", urlPatterns = "/saveEmail")
public class SaveEmailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = ContextListener.getEntityManager();
        String email = req.getParameter("email");
        if (!isValidEmailAddress(email)) {
            resp.getWriter().print("<html>\n" +
                    "<body>\n" +
                    "<h2>Email is not valid!</h2>\n" +
                    "<h2><a href =\"index.jsp\">Try again</a></h2>\n" +
                    "</body>\n" +
                    "</html>");
            return;
        }

        DAO<Integer, Email> emailDAO = new EmailDAO(em);
        emailDAO.insert(new Email(email));

        resp.sendRedirect("index.jsp");
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
