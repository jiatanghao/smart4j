package org.smart4j.chapter2.controller;

import lombok.extern.slf4j.Slf4j;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customer")
@Slf4j
public class CustomerServlet extends HttpServlet {
    private final CustomerService customerService;
    public CustomerServlet() {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Customer> customers = customerService.getCustomerList();
        request.setAttribute("customers", customers);
        try {
            request.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            log.info("跳转失败", e);
        }
    }
}
