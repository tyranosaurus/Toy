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
    var boardInfo;

    function getContextPath() {
        var hostIndex = location.href.indexOf( location.host ) + location.host.length;
        return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
    }

    function getDetail() {
        $.ajax({
            url : getContextPath() + "/board/detail",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            data : { boardNo : ${boardNo} },
            method : "GET",
            dataType : "json",
            success : function(data, status, xhr) {
                boardInfo = data["boardDetail"].board;
                renderDetail(data["boardDetail"]);
            },
            error : function(xhr) {
                alert("게시판 상세 정보를 불러오는데 실패 하였습니다.");
            }
        });
    }

    function renderDetail(boardDetail) {
        $("#boardDetail").empty();

        var totalParticipants = '';

        for ( var i in boardDetail.participants ) {
            totalParticipants += boardDetail.participants[i].id + ", ";
        }

        var html = '<tr>' +
                        '<td>타이틀 : ' + boardDetail.board.title + '</td>' +
                        '<td>작성자 : ' + boardDetail.board.writer.id + '</td>' +
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
                    '</tr>' +
                        '<td>참가/취소</td>' +
                        '<td>' +
                            '<button id="participation" onclick="participate()" >번개 참가</button>' +
                            '<button id="cancel" onclick="cancelParticipate()" >번개 취소</button>' +
                        '</td>' +
                    '</tr>' +
                    '<tr align="center">' +
                        '<td colspan="2">' +
                           '<textarea style="height: 100px; width: 100%;">' + boardDetail.board.content + '</textarea>' +
                        '</td>' +
                    '</tr>';

        $("#boardDetail").append(html);
        $("#participants").text(totalParticipants);

        for ( var i in boardDetail.participants ) {
            if ( boardDetail.participants[i].id == '${sessionScope.user.id}') {
                $("#participation").hide();
                $("#cancel").show();

                break;
            } else {
                $("#participation").show();
                $("#cancel").hide();
            }
        }
    }

    function participate() {
        var boardNo = boardInfo.no;
        var userNo = ${sessionScope.user.no};

        $.ajax({
            url : getContextPath() + "/board/participate",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            method : "POST",
            data : { boardNo : boardNo,
                     userNo : userNo },
            dataType : "json",
            success : function(data, status, xhr) {
                getDetail();
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;

                switch ( errorCode ) {
                    case 670:
                        alert("참가 실패 : 이미 번개모임에 참여하였습니다.");
                        break;
                    case 680:
                        alert("참가 실패 : 최대 참가자 수를 초과하였습니다.");
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
    }

    function cancelParticipate() {
        var boardNo = boardInfo.no;
        var userNo = ${sessionScope.user.no};

        $.ajax({
            url : getContextPath() + "/board/cancelParticipate",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            method : "POST",
            data : { boardNo : boardNo,
                     userNo : userNo },
            dataType : "json",
            success : function(data, status, xhr) {
                getDetail();
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;

                switch ( errorCode ) {
                    case 690:
                        alert("참가 취소 실패 : 번개모임에 참여하지 않았습니다.");
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
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

    <form action="${pageContext.request.contextPath}/board/main" method="GET" align="center">
        <button type="submit">돌아가기</button>
    </form>
</body>
</html>


<html>