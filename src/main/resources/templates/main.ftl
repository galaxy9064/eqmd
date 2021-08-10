<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>测震</title>
    <script  type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
<div width="50%" align="center"><img src="/images/earth_banner.png" width="800px" height="190px"></div>

    <fieldset style="border-radius: 13px; width: 600px; text-align: center; margin: auto auto">
        <legend style="font-size: 18px;">波形数据设置</legend>
        <form action="/transfer" method="post" id="transfer" onsubmit="return validate();" style="text-align: center;margin: auto auto">

            <div class="data-type-check-box">
                <span style="color:#F00">*</span>数据格式:<input type="checkbox" name="dataType" value="seed">SEED&nbsp;<input type="checkbox" name="dataType" value="sac">SAC
            </div>
            <div class="time-input-box">
                <span style="color:#F00">*</span>开始时间:<input name="startTime" type="text" id="startTime" class="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </div>
            <div class="time-input-box">
                <span style="color:#F00">*</span>结束时间:<input name="endTime" type="text" id="endTime" class="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </div>
            <div class="submit-button-box">
                <button type="submit">执行导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" onclick="logout()">退出</button>
            </div>
        </form>
    </fieldset>

</body>
<script>
    function validate(){
        var dataType = $("input[name='dataType']:checked").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();

        if(dataType==undefined || dataType==""){
            alert("文件格式不能为空");
            return false;
        }
        if(startTime==""){
            alert("开始时间不能为空");
            return false;
        }
        if(endTime==""){
            alert("结束时间不能为空");
            return false;
        }
        if(startTime > endTime){
            alert("结束时间应该大于开始时间");
            return false;
        }

        return true;

    }
    function logout(){
        var form = document.getElementById("transfer");
        form.action="/logout";
        form.submit();
    }
</script>
</html>