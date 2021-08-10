<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>测震</title>
    <script src="js/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
    <div align="center" calss="logo-content">
        <img src="images/earth_banner.png" width="420px" height="100px">
    </div>
    <div id="content">
        <div class="login-header">
            用户登录
        </div>
        <form action="/login" method="post" onsubmit="return check();">
            <div class="login-input-box">
                <span class="icon icon-user"></span>
                <input type="text" name="userName" id="userName" placeholder="请输入用户名">
            </div>
            <div class="login-input-box">
                <span class="icon icon-password"></span>
                <input type="password" name="password" id="password"placeholder="请输入密码">
            </div>

            <div class="remember-box">
                <label>
                    <input type="checkbox">记住密码
                </label>
            </div>
            <div class="login-button-box">
                <button type="submit">登录</button>
            </div>
            <div class="logon-box">
                <span><#if info??>用户名或密码错误</#if></span>
            </div>
        </form>
    </div>
</body>
<script>
    function check(){
        if($("#userName").val()==""){
            alert("用户名不能为空");
            return false;
        }
        if($("#password").val()==""){
            alert("密码不能为空");
            return false;
        }
        return true;
    }
</script>
</html>