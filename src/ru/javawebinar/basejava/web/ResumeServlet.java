package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.basejava.model.SectionType.EDUCATION;
import static ru.javawebinar.basejava.model.SectionType.EXPERIENCE;

@WebServlet(name = "resumeServlet", urlPatterns = "/resume")
public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;

        switch (action) {
            case "add":
                r = new Resume("newResume", null);
                addEmptyOrganization(r, EXPERIENCE);
                addEmptyOrganization(r, EDUCATION);
                request.setAttribute("resume", r);
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                addEmptyOrganization(r, EXPERIENCE);
                addEmptyOrganization(r, EDUCATION);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void addEmptyOrganization(Resume r, SectionType type) {

        Organization emptyOrganization = new Organization("","",
                new Organization.Position());
        OrganizationSection section = (OrganizationSection) r.getSection(type);
        if (section != null) {
            section.getOrganizations().add(0, emptyOrganization);
        } else {
            r.addSection(type, new OrganizationSection(emptyOrganization));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        Resume r;
        if (uuid.equals("newResume")) {
            r = new Resume("");
            fillResume(request, r);
            storage.save(r);
        } else {
            r = storage.get(uuid);
            fillResume(request, r);
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    private void fillResume(HttpServletRequest request, Resume r) {
        String fullName = request.getParameter("fullName");
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (valueIsEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                Section section;
                switch (type) {
                    case PERSONAL, OBJECTIVE -> section = new TextSection(value);
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = Arrays.asList(value.split("\n"));
                        items = items.stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
                        section = new ListSection(items);
                    }
                    case EXPERIENCE, EDUCATION -> {

                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");

                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!valueIsEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String index = type.name() + i;
                                String[] startDates = request.getParameterValues(index + "startDate");
                                String[] endDates = request.getParameterValues(index + "endDate");
                                String[] titles = request.getParameterValues(index + "title");
                                String[] descriptions = request.getParameterValues(index + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!valueIsEmpty(titles[j])) {
                                        positions.add(new Organization.Position(DateUtil.parse(startDates[j]),
                                                DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        section = new OrganizationSection(organizations);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                }
                r.addSection(type, section);
            }
        }
    }

    private boolean valueIsEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}

//                        String valueJson = JsonParser.write(value, Section.class) ;
//
//                        section = JsonParser.read(value, Section.class);