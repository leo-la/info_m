function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

function nodata() {
    var content =
        '     <div class="col-md-12">\n' +
        '                    <div class="col-md-4"></div>\n' +
        '                    <div class="col-md-4" style="text-align: center">\n' +
        '                        <img src="'+getContextPath()+'/img/nodata.png" style="padding-top:100px;">\n' +
        '                    </div>\n' +
        '                    <div class="col-md-4"></div>\n' +
        '                </div>'
    $("#imgContent").html(content)
}

function checkPassword() {
    var p1 = $("#newPassword").val();
    var p2 = $("#newPassword2").val();
    if(p1!=p2){
        $("#errorMsg").css("display","block")
        $("#errorMsg").text("密码不一致")
        return false;
    }else {
        return true;
    }
}
function updatePas() {
    $("#disbtn3").click();
}

function updatePassword() {
    var flag = checkPassword();
    if(flag){
        var newPassword = $("#newPassword").val();
        var oldPassword = $("#oldPassword").val();
        var da = JSON.stringify({newPassword: newPassword,oldPassword:oldPassword})
        $.ajax({
            url: getContextPath() + "/user/updatePassword",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            type: "post",
            data: da,
            success: function (data) {
                if(data.name=="yes"){
                    layer.msg('修改密码成功,请重新登录', {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        icon: 1,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    })
                    setTimeout(function () {
                        window.location.href = getContextPath() + "/route/toLogin"
                    }, 1000)
                }else if(data.name=="no"){
                    layer.msg('修改密码失败,请稍后重试', {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        icon: 2,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    })
                }else{
                    $("#errorMsg").css("display","block");
                    $("#errorMsg").text("原始密码错误");
                }
            }
        })
    }

}