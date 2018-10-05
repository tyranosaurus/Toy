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

    function getDetail() {
        $.ajax({
            url : getContextPath() + "/admin/detail",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            data : { noticeNo : ${noticeNo} },
            method : "GET",
            dataType : "json",
            success : function(data, status, xhr) {
                renderModify(data["notice"]);
            },
            error : function(xhr) {
                alert("게시판 상세 정보를 불러오는데 실패 하였습니다.");
            }
        });
    }

    function renderModify(notice) {
        $("#noticeDetail").empty();

        var html = '<tr>' +
            '<td>타이틀 : <input type="text" name="title" value="' + notice.title + '"/>' +
            '<td>작성자 : ' + notice.writer.id + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="2">' +
            '<input type="image" src=""/>' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="2">내용</td>' +
            '</tr>' +
            '<tr align="center">' +
            '<td colspan="2">' +
            '<textarea style="height: 100px; width: 100%;" name="content">' + notice.content + '</textarea>' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>게시글 비밀번호</td>' +
            '<td><input type="text" name="password"/></td>' +
            '</tr>';

        $("#noticeDetail").append(html);
    }

    function modify() {
        var noticeNo = ${noticeNo};
        var title =$('input[name=title]').val();
        var content = $('textarea[name=content]').val();
        var password = $('input[name=password]').val();

        $.ajax({
            url : getContextPath() + "/admin/modify",
            contentType: "application/json; charset=utf-8;",
            method : "POST",
            data : JSON.stringify({ no : noticeNo,
                                    title : title,
                                    content : content,
                                    password : password }),
            dataType : "json",
            success : function(data, status, xhr) {
                window.location.href = xhr.getResponseHeader("redirect");
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;
                var errorMessage = JSON.parse(data.responseText).ErrorMessage;

                switch ( errorCode ) {
                    case 610:
                        alert("수정 실패 : 공지사항 작성자가 일치하지 않습니다.");
                        break;
                    case 620:
                        alert("수정 실패 : 공지사항 비밀번호가 일치하지 않습니다.");
                        break;
                    case 630:
                        alert("수정 실패 : " + errorMessage);
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

    <input	type="hidden" name="boardNo" value="${noticeNo}">

    <table border="1" width="500" align="center">
        <thead>
        <tr>
            <th colspan="2" height="50">공지사항 수정</th>
        </tr>
        </thead>
        <tbody id="noticeDetail"></tbody>
    </table>

    </br>

    <div align="center">
        <button id="modifyBoard" type="submit" onclick="modify()">수정하기</button>
        <c:set var="authority" value="${sessionScope.user.authority}" />
        <c:choose>
            <c:when test="${authority eq 'ROLE_ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/main">취소</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/board/main">취소</a>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
