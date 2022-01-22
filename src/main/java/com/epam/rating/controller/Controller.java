package com.epam.rating.controller;

import com.epam.rating.dao.factory.DaoFactory;
import com.epam.rating.dao.factory.impl.DaoFactoryImpl;
import com.epam.rating.pool.ConnectionPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private final CommandProvider provider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandValue = request.getParameter("command");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Command command = provider.getCommand(commandValue);
        command.execute(request, response);
    }
}