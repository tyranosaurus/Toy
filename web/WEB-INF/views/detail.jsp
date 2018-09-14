<%--
  Created by IntelliJ IDEA.
  User: cyj0619
  Date: 2018-09-04
  Time: 오후 4:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>썬더볼트</title>
</head>
<br>
    <table border="1" width="500" align="center">
        <thead>
            <tr>
                <th colspan="2" height="50">번개 상세 보기</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${boardDetail.board.title}</td>
                <td>${boardDetail.board.writer.id}</td>
            </tr>
            <tr>
                <%-- 나중에 사진 넣을 것! --%>
                <td colspan="2">
                    <input type="image" src=""/>
                </td>
            </tr>
            <tr>
                <td>참가자</td>
                <td>
                    <c:forEach var="participant" items="${boardDetail.participants}" begin="0" end="${boardDetail.participants.size() - 1}" step="1" varStatus="status">
                        <c:set var="index" value="${status.index}" />
                        <c:choose>
                            <c:when test="${index eq boardDetail.participants.size() - 1}">
                                ${participant.id}
                            </c:when>

                            <c:otherwise>
                                ${participant.id}
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <textarea style="height: 100px; width: 100%;">${boardDetail.board.content}</textarea>
                </td>
            </tr>
        </tbody>
    </table>

    </br>

    <form action="/Toy/board/list" method="GET" align="center">
        <button type="submit">돌아가기</button>
    </form>

</body>
</html>
