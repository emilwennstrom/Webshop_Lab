package com.example.Dist_sys_lab1_webshop.UI;

import java.io.*;
import com.example.Dist_sys_lab1_webshop.Database.DBManager;
import com.example.Dist_sys_lab1_webshop.Model.Item.ItemHandler;
import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;
import com.example.Dist_sys_lab1_webshop.Model.User.Shoppingcart;
import com.example.Dist_sys_lab1_webshop.Model.User.User;
import com.example.Dist_sys_lab1_webshop.Model.User.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


/**
 * All interaktion med JSP-sidorna ska gå via denna
 *
 */




@WebServlet(name = "ControllerServlet", value = {
        "/shoppingBasket",
        "/wares",
        "/hatPage",
        "/login",
        "/userAdmin"})
public class ControllerServlet extends HttpServlet {
    private final static String READONLYUSER = "distlab1user";
    private String message;
    private String message_buy_item;

    public void init() {
        message = "Hello World!";
        message_buy_item="Successfully bought item";
        DBManager.setInitUser(); //initerar användaren till read-only
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doPost(request, response);
        response.setContentType("text/html");

        String path = request.getServletPath(); // Hämta sökvägen för den aktuella begäran

        switch (path){
            case "/shoppingBasket":
                response.sendRedirect("shoppingBasket.jsp");
                break;
            case "/wares":
                request.setAttribute("items", ItemHandler.getAllItems());
                request.getRequestDispatcher("itemPage.jsp").forward(request, response);
                break;
            case "/hatPage":
                response.sendRedirect("hatPage.jsp");
                break;
            case "/login":
                handleLoginServlet(request, response);
                break;
            case "/userAdmin":
                handleAdminServlet(request, response);
            default: break;

        }

    }


    private void handleAdminServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = getUserSession(request);
        if (user != null) {
            if (user.getPrivilege() == Privilege.ADMIN){
                request.setAttribute("users", UserHandler.getAllUsers());
                request.getRequestDispatcher("userAdminPage.jsp").forward(request, response);
            }
        }

    }

    private void handleLoginServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = UserHandler.authenticateUser(name, password);
        Shoppingcart shoppingcart = new Shoppingcart();
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("shopingcart", shoppingcart);
        }
        response.sendRedirect("index.jsp");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       doPost(request, response);
    }

    public void destroy() {
    }

    private User getUserSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");

    }

}

