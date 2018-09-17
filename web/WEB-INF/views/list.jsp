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
    <script src="https://code.jquery.com/jquery-3.3.1.js" ></script>
</head>
<script type="text/javascript">
    function showPasswordBox(obj, boardNo) {
        var passwordBox = '<input type="text" name="password-' + boardNo + '" value="">' +
            '<button class="deleteButton" onclick="deleteBoard(' + boardNo + ')">삭제 확인</button>';

        obj.parentNode.innerHTML = passwordBox;
        obj.remove();
    }

    function deleteBoard(boardNo) {
        var password = $('input[name=password-' + boardNo + ']').val();

        $.ajax({
            url : "/Toy/board/delete",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            method : "POST",
            data : { boardNo : boardNo, password : password },
            dataType : "json",
            success : function(data, status, xhr) {
                getList();
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;

                switch ( errorCode ) {
                    case 610:
                        alert("삭제 실패 : 게시판 작성자가 일치하지 않습니다.");
                        break;
                    case 620:
                        alert("삭제 실패 : 게시판 비밀번호가 일치하지 않습니다.");
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
    }

    function getList() {
        $.ajax({
            url : "/Toy/board/list",
            contentType: 'application/json; charset=utf-8;',
            method : "GET",
            dataType : "json",
            success : function(data, status, xhr) {
                renderBoards(data["boards"]);
            },
            error : function(xhr) {
                alert("게시판 정보를 불러오는데 실패 하였습니다.");
            }
        });
    }

    function renderBoards(boards) {
        $("#boards").empty();

        for ( var i in boards ) {
            var indexNo = Number(i) + 1;
            var html =
                '<tr height="35" align="center">' +
                '<td>' + indexNo + '</td>' +
                '<td>' +
                '<a href="/Toy/board/detailForm/' + boards[i].no + '" >' + boards[i].title + '</a>' +
                '</td>' +
                '<td>' + boards[i].writer.id + '</td>' +
                '<td>' +
                '<form action="/Toy/board/modifyForm?boardNo=' + boards[i].no + '" method="POST">' +
                '<button type="submit">수정</button>' +
                '</form>' +
                '</td>' +
                '<td class="deleteBox" width="300">' +
                '<input type="button" value="삭제" onclick="showPasswordBox(this, ' + boards[i].no + ')" />' +
                '</td>' +
                '</tr>';

            $("#boards").append(html);
        }
    }

    getList();
</script>
<body>
    <table border="1" width="60%" align="center">
        <thead>
        <tr>
            <th colspan="5" height="50">썬더볼트</th>
        </tr>
        <tr height="40" align="center" >
            <th>No</th>
            <th>Title</th>
            <th>작성자</th>
            <th>수정</th>
            <th>삭제</th>
        </tr>
        </thead>

        <tbody id="boards"></tbody>
    </table>

    <br/>

    <div align="center">
        <form action="/Toy/board/createForm" method="get" style="height:40px; width:150px">
            <button type="submit">새 번개 등록</button>
        </form>
    </div>
</body>
</html>
