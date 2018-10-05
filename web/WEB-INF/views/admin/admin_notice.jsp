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

    function create() {
        var title =$('input[name=title]').val();
        var content = $('textarea[name=content]').val();
        var password = $('input[name=password]').val();

        $.ajax({
            url : getContextPath() + "/admin/createNotice",
            contentType: "application/json; charset=utf-8;",
            method : "POST",
            data : JSON.stringify({ title : title,
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
                    case 630:
                        alert("가입 실패 : " + errorMessage);
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
    }
</script>

    <table border="1" width="500" style="margin-left: auto; margin-right: auto;" >
        <thead>
        <tr>
            <th colspan="2" height="50">새 공지사항 등록</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                타이틀 : <input type="text" name="title"/>
            </td>
            <td>
                작성자 : ${sessionScope.user.id}
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
            <td>게시글 비밀번호</td>
            <td><input type="text" name="password"/></td>
        </tr>
        </tbody>
    </table>

    </br>

    <div align="center">
        <button type="submit" onclick="create()">공지사항 등록하기</button>
        <a href="${pageContext.request.contextPath}/admin/main">취소</a>
    </div>

</body>
</html>
