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
    <meta name="_csrf_parameter" content="${_csrf.parameterName}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta name="_csrf" content="${_csrf.token}" />
</head>
<body>
<script type="text/javascript">
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            if(token && header) {
                xhr.setRequestHeader(header, token);
            }
        });
    });

    function getContextPath() {
        var hostIndex = location.href.indexOf( location.host ) + location.host.length;
        return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
    }

    function showNoticePasswordBox(obj, noticeNo){
        var passwordBox = '<input type="password" name="password-' + noticeNo + '" value="">' +
                          '<button class="deleteButton" onclick="deleteNotice(' + noticeNo + ')">삭제 확인</button>';

        obj.parentNode.innerHTML = passwordBox;
        obj.remove();
    }

    function deleteBoard(boardNo) {
        if( !confirm('게시글을 삭제 하시겠습니까?') ) {
            return;
        }

        var authority = "${sessionScope.user.authority}";

        $.ajax({
            url : getContextPath() + "/admin/deleteBoard",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            method : "POST",
            data : { boardNo : boardNo,
                     authority : authority },
            dataType : "json",
            success : function(data, status, xhr) {
                getNotices();
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;

                switch ( errorCode ) {
                    case 700:
                        alert("관리자 계정이 아닙니다. 관리자로 로그인 해주세요.");
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
    }

    function deleteNotice(noticeNo) {
        var password = $('input[name=password-' + noticeNo + ']').val();

        $.ajax({
            url : getContextPath() + "/admin/delete",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            method : "POST",
            data : { noticeNo : noticeNo,
                     password : password },
            dataType : "json",
            success : function(data, status, xhr) {
                getNotices();
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;

                switch ( errorCode ) {
                    case 610:
                        alert("삭제 실패 : 공지사항 작성자가 일치하지 않습니다.");
                        break;
                    case 620:
                        alert("삭제 실패 : 공지사항항 비밀번호가 일하지 않습니다.");
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
    }

    function getNotices() {
        $.ajax({
            url : getContextPath() + "/admin/notices",
            contentType: 'application/json; charset=utf-8;',
            method : "GET",
            dataType : "json",
            success : function(data, status, xhr) {
                renderNotices(data["notices"]);
                getList();
            },
            error : function(xhr) {
                alert("공지사항을 불러오는데 실패 하였습니다.");
            }
        });
    }

    function renderNotices(notices) {
        $("#boards").empty();

        for ( var i in notices ) {
            var html =
                '<tr height="35" align="center">' +
                '<td>공지사항</td>' +
                '<td>' +
                '<a href="' + getContextPath() + '/admin/detailForm/' + notices[i].no + '" >' + notices[i].title + '</a>' +
                '</td>' +
                '<td>' + notices[i].writer.id + '</td>' +
                '<td>' +
                '<form action="' + getContextPath() + '/admin/modifyForm/' + notices[i].no + '" method="GET">' +
                '<button type="submit">수정</button>' +
                '</form>' +
                '</td>' +
                '<td class="deleteBox" width="300">' +
                '<input type="button" value="삭제" onclick="showNoticePasswordBox(this, ' + notices[i].no + ')" />' +
                '</td>' +
                '</tr>';

            $("#boards").append(html);
        }
    }

    function getList() {
        $.ajax({
            url : getContextPath() + "/board/list",
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
        for ( var i in boards ) {
            var indexNo = Number(i) + 1;
            var html =
                '<tr height="35" align="center">' +
                '<td>' + indexNo + '</td>' +
                '<td>' +
                '<a href="' + getContextPath() + '/board/detailForm/' + boards[i].no + '" >' + boards[i].title + '</a>' +
                '</td>' +
                '<td>' + boards[i].writer.id + '</td>' +
                '<td>' +
                '<form action="' + getContextPath() + '/board/modifyForm/' + boards[i].no + '" method="GET">' +
                '<button type="submit">수정</button>' +
                '</form>' +
                '</td>' +
                '<td class="deleteBox" width="300">' +
                '<input type="button" value="삭제" onclick="deleteBoard(' + boards[i].no + ')" />' +
                '</td>' +
                '</tr>';

            $("#boards").append(html);
        }
    }

    getNotices();
</script>
    <h1 align="center">관리자 전용 페이지</h1>

    </br>

    <div align="center">
        <form action="${pageContext.request.contextPath}/login/logout" method="get" style="height:40px; width:150px">
            <button type="submit">로그아웃</button>
        </form>
    </div>

    <br>

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
        <form action="${pageContext.request.contextPath}/admin/createNoticeForm" method="get" style="height:40px; width:150px">
            <button type="submit">공지사항 등록</button>
        </form>
    </div>
</body>
</html>
