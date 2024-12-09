package AcademicExchangePlatform.servlet;

import AcademicExchangePlatform.model.Notification;
import AcademicExchangePlatform.service.NotificationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class NotificationServlet extends HttpServlet {
    private final NotificationService notificationService = NotificationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        int userId = (int) request.getSession().getAttribute("userId");

        if ("/count".equals(pathInfo)) {
            // AJAX endpoint for getting unread count
            response.setContentType("application/json");
            response.getWriter().write("{\"count\":" + notificationService.getUnreadCount(userId) + "}");
            return;
        }

        // Get notifications for display
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        request.setAttribute("notifications", notifications);
        request.setAttribute("unreadCount", notificationService.getUnreadCount(userId));
        
        request.getRequestDispatcher("/WEB-INF/views/notification/notifications.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        int userId = (int) request.getSession().getAttribute("userId");

        switch (action) {
            case "markRead":
                int notificationId = Integer.parseInt(request.getParameter("notificationId"));
                notificationService.markAsRead(notificationId);
                break;

            case "markAllRead":
                notificationService.markAllAsRead(userId);
                break;

            case "delete":
                notificationId = Integer.parseInt(request.getParameter("notificationId"));
                notificationService.deleteNotification(notificationId);
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        }

        // If it's an AJAX request, send JSON response
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":true}");
        } else {
            // Otherwise redirect back to notifications page
            response.sendRedirect(request.getContextPath() + "/notifications");
        }
    }
}
