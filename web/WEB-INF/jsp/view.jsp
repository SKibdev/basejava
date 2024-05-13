<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <%-- Извлекаем ключ (тип секции) и значение (содержимое секции) из записи в карте --%>
        <c:set var="sectionType" value="${sectionEntry.key}"/>
        <c:set var="sectionContent" value="${sectionEntry.value}"/>

        <%-- Проверяем тип секции и отображаем соответствующим образом --%>
        <c:choose>
            <%-- Секция с текстом --%>
            <c:when test="${sectionEntry.value.getClass().getSimpleName() == 'TextSection'}">
                <%-- Приводим содержимое секции к типу TextSection --%>
                <c:set var="textSection" value="${sectionContent}"/>
                <%-- Отображаем текстовое содержимое секции --%>
                <div>
                    <h2>${sectionType.title}</h2>
                    <p>${textSection.content}</p>
                </div>
            </c:when>

            <%-- Секция со списком --%>
            <c:when test="${sectionEntry.value.getClass().getSimpleName() == 'ListSection'}">
                <%-- Приводим содержимое секции к типу ListSection --%>
                <c:set var="listSection" value="${sectionContent}"/>
                <%-- Отображаем элементы списка секции --%>
                <div>
                    <h2>${sectionType.title}</h2>
                    <ul>
                        <c:forEach var="item" items="${listSection.items}">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>
            <%--            Секции оганизации--%>
            <c:when test="${sectionEntry.value.getClass().getSimpleName() == 'OrganizationSection'}">
                <%-- Приводим содержимое секции к типу OrganizationSection --%>
                <c:set var="organizationSection" value="${sectionContent}"/>
                <div>
                    <h2>${sectionType.title}</h2>
                        <%--                Проходим циклом по List<Organization>--%>
                    <c:forEach var="organization" items="${organizationSection.organizations}">
                        <%--Отображаем объект Link название организации и ссылка--%>
                        <c:choose>
                            <c:when test="${empty organization.homePage.url}">
                                <h3>${org.homePage.name}</h3>
                            </c:when>
                            <c:otherwise>
                                <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a></h3>
                            </c:otherwise>
                        </c:choose>
                        <%--                    Отображаем элементы списка positions--%>
                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                            <tr>
                                <td><%=DateUtil.format(position.getStartDate())%> -
                                    <%=DateUtil.format(position.getEndDate())%>
                                </td>
                                <br>
                                <td><b>${position.title}</b><br>${position.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </div>
            </c:when>
            <%-- Случай по умолчанию, если тип секции не распознан --%>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>