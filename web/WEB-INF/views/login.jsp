<%--
  Created by IntelliJ IDEA.
  User: cyj0619
  Date: 2018-09-19
  Time: 오전 11:33
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

    function doLogin() {
        var id =$('input[name=id]').val();
        var password =$('input[name=password]').val();

        $.ajax({
            url : getContextPath() + "/login/doLogin",
            contentType: "application/x-www-form-urlencoded; charset=utf-8;",
            method : "POST",
            data : { id : id,
                     password : password },
            dataType : "json",
            success : function(data, status, xhr) {
                window.location.href = xhr.getResponseHeader("redirect");
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;

                switch ( errorCode ) {
                    case 640:
                        alert("가입된 유저정보가 없습니다. 회원가입 해주세요.");
                        window.location.href = data.getResponseHeader("redirect");
                        break;
                    default:
                        alert("알 수 없는 오류 발생");
                        break;
                }

            }
        });
    }
</script>
    <h1 align="center">썬더볼트 로그인</h1>
    <table border="1" width="500" style="margin-left: auto; margin-right: auto;" >
        <tbody>
            <tr>
                <td height="100">
                    ID : <input type="text" name="id"/>
                </td>
            </tr>
            <tr>
                <td height="100">
                    PW : <input type="password" name="password"/>
                </td>
            </tr>
        </tbody>
    </table>

    </br>

    <div align="center">
        <button type="submit" onclick="doLogin()">로그인</button>
    </div>

    </br>

    <div align="center">
        <form action="${pageContext.request.contextPath}/login/joinForm" method="GET" align="center">
            <button type="submit" onclick="">회원가입</button>
        </form>
    </div>

</body>
</html>
