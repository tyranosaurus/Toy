<%--
  Created by IntelliJ IDEA.
  User: cyj0619
  Date: 2018-09-04
  Time: 오후 3:35
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
            <tr height="35" align="center">
                <td>1</td>
                <td>
                    <a href="/Toy/board/detailForm">타이틀1</a>
                </td>
                <td>작성자1</td>
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
            <tr height="35" align="center">
                <td>2</td>
                <td>
                    <a href="/Toy/board/detailForm">타이틀2</a>
                </td>
                <td>작성자2</td>
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
            <tr height="35" align="center">
                <td>3</td>
                <td>
                    <a href="/Toy/board/detailForm">타이틀3</a>
                </td>
                <td>작성자3</td>
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
            <tr height="35" align="center">
                <td>4</td>
                <td>
                    <a href="/Toy/board/detailForm">타이틀4</a>
                </td>
                <td>작성자4</td>
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
            <tr height="35" align="center">
                <td>5</td>
                <td>
                    <a href="/Toy/board/detailForm">타이틀5</a>
                </td>
                <td>작성자5</td>
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
            <tr height="35" align="center">
                <td>6</td>
                <td>
                    <a href="/Toy/board/detailForm">타이틀6</a>
                </td>
                <td>작성자6</td>
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
