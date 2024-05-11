<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"
                       pattern="[^\s]+( [^\s]+)*"
                       title="Не допускается ввод начальных, конечных и повторяющихся пробелов" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=40 value="${resume.getContact(type)}"
                           pattern="[^\s]+( [^\s]+)*"
                           title="Не допускается ввод начальных, конечных и повторяющихся пробелов"></dd>
            </dl>
        </c:forEach>
        <hr>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type eq 'OBJECTIVE' || type eq 'PERSONAL' || type eq 'ACHIEVEMENT' || type eq 'QUALIFICATIONS'}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><textarea name="${type.name()}" cols="60" rows="2">${resume.getSection(type)}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${type eq 'EXPERIENCE' || type eq 'EDUCATION'}">
                    <%--                        TO DO доделать вывод этих секций--%>
                    <h2>${type.title}</h2>
                    <c:forEach var="organization" items="${(resume.getSection(type)).getOrganizations()}">
                        <dl>
                            <dt>Название</dt>
                            <dd><input type="text" name="homePageName_${type}" size=40 value="${organization.homePage.name}"
                                       pattern="[^\s]+( [^\s]+)*"
                                       title="Не допускается ввод начальных, конечных и повторяющихся пробелов"></dd>
                        </dl>
                        <dl>
                            <dt>Ссылка</dt>
                            <dd><input type="text" name="homePageUrl_${type}" size=40 value="${organization.homePage.url}"
                                       pattern="[^\s]+( [^\s]+)*"
                                       title="Не допускается ввод начальных, конечных и повторяющихся пробелов"></dd>
                        </dl>


                        <%--                            <h2><a href="${organization.getHomePage().url}">${organization.getHomePage().name}</a></h2>--%>
                        <%--                            <p><strong>Период работы:</strong> ${organization.period}</p>--%>
                        <%--                            <p><strong>Должность:</strong> ${organization.position}</p>--%>
                        <%--                            <p><strong>Описание:</strong> ${organization.description}</p>--%>
                        <hr>
                    </c:forEach>

                    <%--                    <dl>--%>
                    <%--                        <dt>${Название}</dt>--%>
                    <%--                        <dd><input type="text" name="${Название1}" size=40 value="${resume.getSection(type)}"--%>
                    <%--                                   pattern="[^\s]+( [^\s]+)*"--%>
                    <%--                                   title="Не допускается ввод начальных, конечных и повторяющихся пробелов"></dd>--%>


                    <%--                        <dt>${type.title}</dt>--%>
                    <%--                        <dd><textarea name="${type.name()}" cols="60" rows="2">${resume.getSection(type)}</textarea>--%>
                    <%--                        </dd>--%>

                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
