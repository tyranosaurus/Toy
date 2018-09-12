<%--
  Created by IntelliJ IDEA.
  User: cyj0619
  Date: 2018-09-04
  Time: 오후 3:35
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
<body>

    <table border="1" width="500" align="center">
        <thead>
            <tr>
                <th colspan="4" height="50">썬더볼트</th>
            </tr>
            <tr height="40" align="center" >
                <th>No</th>
                <th>Title</th>
                <th>작성자</th>
                <th>수정/삭제</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="board" items="${boards}" begin="0" end="${boards.size() - 1}" step="1" varStatus="status">
                <tr height="35" align="center">
                    <td>${status.count}</td>
                    <td>
                        <a href="/Toy/board/detailForm?boardNo=${board.no}">${board.title}</a>
                    </td>
                    <td>${board.writer.id}</td>
                    <td>
                        <div style="float:left; margin-right:-10px">
                            <form action="/Toy/board/modifyForm" method="get">
                                <button type="submit">수정</button>
                            </form>
                        </div>
                        <div style="float:right; margin-left:-10px">
                            <form action="/Toy/board/delete" method="post">
                                <button type="submit">삭제</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>

    <br/>

    <div align="center">
        <form action="/Toy/board/writeForm" method="get" style="height:40px; width:150px">
            <button type="submit">새 번개 등록</button>
        </form>
    </div>
</body>
</html>
