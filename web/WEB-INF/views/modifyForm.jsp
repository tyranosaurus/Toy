<%--
  Created by IntelliJ IDEA.
  User: cyj0619
  Date: 2018-09-04
  Time: 오후 4:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>썬더볼트</title>
</head>
<body>
    <table border="1" width="500" align="center">
        <thead>
            <tr>
                <th colspan="2" height="50">번개 수정</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    타이틀 : <input type="text" />
                </td>
                <td>
                    작성자 : <input type="text" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="image" src=""/>
                </td>
            </tr>
            <tr>
                <td colspan="2">내용</td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <textarea style="height: 100px; width: 100%;"></textarea>
                </td>
            </tr>
        </tbody>
    </table>

    <br/>

    <div align="center">
        <form action="/Toy/board/modify" method="get" style="height:40px; width:150px">
            <button type="submit">완료</button>
        </form>
    </div>
</body>
</html>
