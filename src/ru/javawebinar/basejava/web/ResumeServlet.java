package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        Storage storage = Config.get().getSqlStorage();
        List<Resume> resumes = storage.getAllSorted();
        response.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border:2px solid green;\n" +
                "}\n" +
                "H2 { text-align: center }" +
                "</style>\n" +
                "<body>" +
                "<h2>БАЗА РЕЗЮМЕ</h2>" +
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>FULL NAME</th>\n" +
                "  </tr>");
        for (Resume resume : resumes) {
            response.getWriter().write("<tr>\n" +
                    "    <td>" + resume.getUuid() + "</td>\n" +
                    "    <td>" + resume.getFullName() + "</td>\n" +
                    "  </tr>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
