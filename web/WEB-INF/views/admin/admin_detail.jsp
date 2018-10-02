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
    var boardInfo;

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
                renderDetail(data["notice"]);
            },
            error : function(xhr) {
                alert("게시판 상세 정보를 불러오는데 실패 하였습니다.");
            }
        });
    }

    function renderDetail(notice) {
        $("#noticeDetail").empty();

        var html = '<tr>' +
                        '<td>타이틀 : ' + notice.title + '</td>' +
                        '<td>작성자 : ' + notice.writer.id + '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td colspan="2">' +
                            '<input type="image" src=""/>' +
                        '</td>' +
                    '</tr>' +
                    '<tr align="center">' +
                        '<td colspan="2">' +
                           '<textarea style="height: 100px; width: 100%;">' + notice.content + '</textarea>' +
                        '</td>' +
                    '</tr>';

        $("#noticeDetail").append(html);
    }

    getDetail();
</script>
    <table border="1" width="500" align="center">
        <thead>
            <tr>
                <th colspan="2" height="50">공지사항 상세 보기</th>
            </tr>
        </thead>
        <tbody id="noticeDetail"></tbody>
    </table>

    </br>

    <c:set var="authority" value="${sessionScope.user.authority}" />
    <c:choose>
        <c:when test="${authority eq 'ROLE_ADMIN'}">
            <form action="${pageContext.request.contextPath}/admin/main" method="GET" align="center">
                <button type="submit">돌아가기</button>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/board/main" method="GET" align="center">
                <button type="submit">돌아가기</button>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>


<html>