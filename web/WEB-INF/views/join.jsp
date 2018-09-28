<%--
  Created by IntelliJ IDEA.
  User: cyj0619
  Date: 2018-09-20
  Time: 오후 3:53
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
    function getContextPath() {
        var hostIndex = location.href.indexOf( location.host ) + location.host.length;
        return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
    }

    function join() {
        var id =$('input[name=id]').val();
        var password = $('input[name=password]').val();
        var password2 = $('input[name=password2]').val();
        var name = $('input[name=name]').val();
        var email = $('input[name=email]').val();
        var gender = $('input[name=gender]:checked').val();

        $.ajax({
            url : getContextPath() + "/login/doJoin",
            contentType: "application/json; charset=utf-8;",
            method : "POST",
            data : JSON.stringify({ id : id,
                                    password : password,
                                    password2 : password2,
                                    name : name,
                                    email : email,
                                    gender : gender}),
            dataType : "json",
            success : function(data, status, xhr) {
                alert("회원가입을 축하합니다! 로그인 페이지로 이동합니다.")
                window.location.href = xhr.getResponseHeader("redirect");
            },
            error : function(data, status, xhr) {
                var errorCode = JSON.parse(data.responseText).ErrorCode;
                var errorMessage = JSON.parse(data.responseText).ErrorMessage;

                switch ( errorCode ) {
                    case 660:
                        alert("가입 실패 : 이미 사용중인 아이디 입니다.");
                        break;
                    case 665:
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
    <h1 align="center">썬더볼트 회원가입</h1>
    <table border="1" width="500" style="margin-left: auto; margin-right: auto;" >
        <tbody>
        <tr>
            <td height="50" width="80" align="center">ID : </td>
            <td height="50" align="center">
                <input type="text" name="id" style="width:300px;"/>
            </td>
        </tr>
        <tr>
            <td height="50" width="80" align="center">PW : </td>
            <td height="50" align="center">
                <input type="text" name="password" style="width:300px;"/>
            </td>
        </tr>
        <tr>
            <td height="50" width="80" align="center">PW 확인 : </td>
            <td height="50" align="center">
                <input type="text" name="password2" style="width:300px;"/>
            </td>
        </tr>
        <tr>
            <td height="50" width="80" align="center">이름 : </td>
            <td height="50" align="center">
                <input type="text" name="name" style="width:300px;"/>
            </td>
        </tr>
        <tr>
            <td height="50" width="80" align="center">Email : </td>
            <td height="50" align="center">
                <input type="text" name="email" style="width:300px;"/>
            </td>
        </tr>
        <tr>
            <td height="50" width="80" align="center">남여 : </td>
            <td height="50" align="center">
                <input type="radio" name="gender" value="0" checked="checked">남자
                <input type="radio" name="gender" value="1">여자
            </td>
        </tr>

        </tbody>
    </table>

    </br>

    <div align="center">
        <button type="submit" onclick="join()">회원가입</button>
        <a href="${pageContext.request.contextPath}/login/loginForm">취소</a>
    </div>

</body>
</html>
