package com.epam.rating.controller;

import com.epam.rating.command.*;
import com.epam.rating.command.factory.CommandFactory;
import com.epam.rating.context.Application;
import com.epam.rating.exception.ConnectionPoolException;
import com.epam.rating.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "Controller" , value = "/Controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestData requestData = new RequestData(request);
        CommandFactory commandFactory = new CommandFactory();
        Optional<CommandRequest> command = commandFactory.defineCommand(requestData);
        if (command.isPresent()) {
            CommandExecute commandExecute = command.get().executeCommand(requestData);
            if (commandExecute != null) {
                requestData.insertSessionAndRequestAttributes(request);
                if (commandExecute.getRouteType().equals(RouteType.REDIRECT)) {
                    response.sendRedirect(request.getContextPath() + commandExecute.getPagePath());
                } else {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(commandExecute.getPagePath());
                    requestDispatcher.forward(request, response);
                }
            } else {
                request.getSession().setAttribute(AttributeName.ERROR, "error.message.404");
                response.sendRedirect(request.getContextPath() + Destination.ERROR.getPath());
            }
        }
    }
        @Override
        public void init() throws ServletException {
            ConnectionPool.getInstance();
            Application.start();
            super.init();
        }

        @Override
        public void destroy() {
            try {
                ConnectionPool.getInstance().close();
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
            }
            LOGGER.debug("Connection pool closed");
            super.destroy();
        }
}