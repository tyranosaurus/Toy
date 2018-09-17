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
    <script src="https://code.jquery.com/jquery-3.3.1.js" ></script>
</head>
<body>
<script type="text/javascript">
    function getDetail() {
        $.ajax({
            url : "/Toy/board/detail",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            data : { boardNo : ${boardNo} },
            method : "GET",
            dataType : "json",
            success : function(data, status, xhr) {
                renderDetail(data["boardDetail"]);
                addParticipants(data["boardDetail"].participants);
            },
            error : function(xhr) {
                alert("게시판 상세 정보를 불러오는데 실패 하였습니다.");
            }
        });
    }

    /* JSP -> HTML 과정 공부할 것. 정리 -> Confluence 정리 */
    function renderDetail(boardDetail) {
        $("#boardDetail").empty();

        var html =``;

        var html = '<tr>' +
                        '<td>' + boardDetail.board.title + '</td>' +
                        '<td>' + boardDetail.board.writer.id + '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td colspan="2">' +
                            '<input type="image" src=""/>' +
                        '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td>참가자</td>' +
                        '<td id="participants">' +
                        '</td>' +
                    '</tr>' +
                    '<tr align="center">' +
                        '<td colspan="2">' +
                           '<textarea style="height: 100px; width: 100%;">' + boardDetail.board.content + '</textarea>' +
                        '</td>' +
                    '</tr>';

        $("#boardDetail").append(html);
    }

    function addParticipants(participants) {
        var totalParticipants = '';

        for ( var i in participants ) {
            totalParticipants += participants[i].id + ", ";
        }


        $("#participants").text(totalParticipants);
    }

    getDetail();
</script>
    <table border="1" width="500" align="center">
        <thead>
            <tr>
                <th colspan="2" height="50">번개 상세 보기</th>
            </tr>
        </thead>
        <tbody id="boardDetail"></tbody>
    </table>

    </br>

    <form action="/Toy/board/main" method="GET" align="center">
        <button type="submit">돌아가기</button>
    </form>
</body>
</html>


<html>