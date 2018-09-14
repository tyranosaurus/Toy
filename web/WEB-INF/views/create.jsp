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
<body>

    <form action="/Toy/board/create" method="POST" align="center">

        <table border="1" width="500" style="margin-left: auto; margin-right: auto;" >
            <thead>
            <tr>
                <th colspan="2" height="50">새 번개 등록</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    타이틀 : <input type="text" name="title"/>
                </td>
                <td>
                    <%-- 로그인 구현시 로그인 정보를 세션에서 가져옴 --%>
                    작성자 : <input type="text" name="writer"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <img src="">
                </td>
            </tr>
            <tr>
                <td colspan="2">내용</td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <textarea style="height: 100px; width: 100%;" name="content"></textarea>
                </td>
            </tr>
            <tr>
                <td>최대 참가자 수</td>
                <td><input type="text" name="maxParticipantCount"/></td>
            </tr>
            <tr>
                <td>게시글 비밀번호</td>
                <td><input type="text" name="password"/></td>
            </tr>
            </tbody>
        </table>

        </br>

        <button type="submit">번개 등록하기</button>
        <a href="/Toy/board/list">취소</a>

    </form>

</body>
</html>
