var loadDirDataPath;
var swxtitle;
var updateAction;
var addAction;
var list;

// 加载目录信息
function loadDirData() {
    var depid = $("#depid").val();
    if(depid==1){
        loadDirDataPath = "/firm/";
        swxtitle =
            '     <span style="font-size: 25px;">公司公告\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Company Announcements</span>\n' +
            '     </span>'

    }else if(depid==2){
        loadDirDataPath = "/operation/";
        swxtitle =
            '     <span style="font-size: 25px;">运营中心\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Operation department</span>\n' +
            '     </span>'
    }else if(depid==3){
        loadDirDataPath = "/human/";
        swxtitle =
            '     <span style="font-size: 25px;">人力资源部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Human resources department</span>\n' +
            '     </span>'
    }else if(depid==4){
        loadDirDataPath = "/finance/";
        swxtitle =
            '     <span style="font-size: 25px;">财务部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Finance department</span>\n' +
            '     </span>'
    }else if(depid==5){
        loadDirDataPath = "/confidentiality/";
        swxtitle =
            '     <span style="font-size: 25px;">质量管理部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Quality management department</span>\n' +
            '     </span>'
    }else if(depid==6){
        loadDirDataPath = "/quality/";
        swxtitle =
            '     <span style="font-size: 25px;">保密管理办公室\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Confidentiality management office</span>\n' +
            '     </span>'
    }else if(depid==7){
        loadDirDataPath = "/office/";
        swxtitle =
            '     <span style="font-size: 25px;">办公室\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">The office</span>\n' +
            '     </span>'
        updateAction = getContextPath() +'/office/updateTDirName';
        addAction = getContextPath() +'/office/addTDir';
    }else if(depid==8){
        loadDirDataPath = "/law/";
        swxtitle =
            '     <span style="font-size: 25px;">政法事业部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Political and legal affairs department</span>\n' +
            '     </span>'
    }else if(depid==9){
        loadDirDataPath = "/military/";
        swxtitle =
            '     <span style="font-size: 25px;">军工事业部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Military department</span>\n' +
            '     </span>'
    }else if(depid==11){
        loadDirDataPath = "/remote/";
        swxtitle =
            '     <span style="font-size: 25px;">遥感事业部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Remote sensing</span>\n' +
            '     </span>'
    }else if(depid==12){
        loadDirDataPath = "/discipline/";
        swxtitle =
            '     <span style="font-size: 25px;">纪检监察事业部\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Discipline inspection and supervision department</span>\n' +
            '     </span>'
    }else if(depid==13){
        loadDirDataPath = "/beijing/";
        swxtitle =
            '     <span style="font-size: 25px;">北京分公司\n' +
            '          <span style="font-size: 16px;color: #668995;margin-top: 40px;">Beijing branch</span>\n' +
            '     </span>'
    }

    var da = JSON.stringify({id: depid})
    $.ajax({
        url: getContextPath() + loadDirDataPath + "searchDirectory",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        type: "post",
        data:da,
        success: function (data) {
            if(data.length==0){
                nodata();
            }
            var content = '';
            //显示数据
            for (var i = 0; i < data.length; i++) {
                list = data;
                var content1 =
                    '        <div class="col-md-6 col-sm-6  ">\n' +
                    '                        <div class="x_panel">\n' +
                    '                            <div class="x_title">\n' +
                    '                                <h2>\n' +
                    '                                    <i class="fa fa-bars"></i>\n' +

                    data[i].dirname +
                    '                                    <small>'+data[i].enname+'</small>\n' +
                    '                                </h2>\n' +
                    '                                <ul class="nav navbar-right panel_toolbox">\n' +
                    '                                    <li style="float: right;margin-left:50px"><a class="collapse-link" href="javascript:sortFront(' + data[i].id + ')" data-toggle="tooltip" data-placement="top" title="向前移动" ><i class="fa fa-caret-up" ></i></a>\n' +
                    '                                    </li>\n' +
                    '                                    <li style="float: right;margin-left:2px"><a class="collapse-link" href="javascript:sortDown(' + data[i].id + ')" data-toggle="tooltip" data-placement="top" title="向后移动"><i class="fa fa-caret-down"></i></a>\n' +
                    '                                    </li>\n' +
                    '                                    <li style="float: right;margin-left:2px"><a class="collapse-link" href="javascript:updateName(' + data[i].id + ')" data-toggle="tooltip" data-placement="top" title="编辑"><i class="fa fa-cog"></i></a>\n' +
                    '                                    </li>\n' +
                    '                                    <li style="float: right;margin-left:2px"><a class="collapse-link" href="javascript:deleteConfirm(' + data[i].id + ')" data-toggle="tooltip" data-placement="top" title="删除"><i class="fa fa-close"></i></a>\n' +
                    '                                    </li>\n' +
                    '                                </ul>\n' +
                    '                                <div class="clearfix"></div>\n' +
                    '                            </div>\n' +
                    '                            <a href="javascript:searchDetails('+data[i].id+')" style="float: right;padding-right: 20px">查看详情>></a>\n' +
                    '                        </div>\n' +
                    '                    </div>';

                content += content1;

            }

            // dirContent += dirContent2;
            // dirContent += dirContent3;
            updateAction = getContextPath()+ loadDirDataPath + 'updateTDirName';
            addAction = getContextPath()+ loadDirDataPath + 'addTDir';

            $("#dirContent").html(content);
            $(".title_left").html(swxtitle);

        }
    })
}



function deleteConfirm(id) {
    layer.confirm('删除该目录将同时删除该目录下的文件，确认删除吗？', {
            skin: 'layui-layer-molv', //样式类名
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            deleteOne(id)
        }, function () {
        }
    );
}

function deleteOne(id) {
    var da = JSON.stringify({id: id})
    var path = getContextPath()+ loadDirDataPath + "deleteTDir"
    $.ajax({
        url: path,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        beforeSend: function () {

        },
        success: function (data) {
            if (data == true) {
                layer.msg('删除分类目录成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
                setTimeout(function () {
                    window.location.href = getContextPath() + "/route"+ loadDirDataPath + "index";
                }, 1000)
            }
        }
    })
}

function searchDetails(id) {
    window.location.href = getContextPath() + '/route'+ loadDirDataPath + 'fileDir/{' + id + '}';
}

function convertToFileVersion(id) {
    window.location.href = getContextPath() + '/route'+ loadDirDataPath + 'fileVersions/{' + id + '}';
}

function updateName(id) {

    $("#dirid").val(id);

    var da = JSON.stringify({id: id});
    $.ajax({
        url: getContextPath()+ loadDirDataPath + "getDir",
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {
            $("#oneName").val(data.dirname);
            $("#twoName").val(data.enname);
        }
    })
    $("#updateTDirAction").attr("action",updateAction);
    $("#disbtn").click();

}

function addTDir() {
    $("#addTDirAction").attr("action",addAction);
    $("#disbtn2").click();
}

function sortFront(id) {
    var lastId;
    var flag = false;
    for(var i=0;i<list.length;i++){
        if(list[i].id==id){
            break;
        }
        lastId = list[i].id;
        flag = true;
    }
    if(flag){
        window.location.href = getContextPath() + loadDirDataPath + 'sortFront/{' + lastId + '}/{'+id+'}';
    }
}

function sortDown(id) {
    var nextid;
    var j;
    for(var i=0;i<list.length;i++){
        if(list[i].id==id){
            j=i;
        }
    }
    for(var i=0;i<list.length;i++){
        nextid = list[i].id;
        if(list[i].sortnum > list[j].sortnum){
            break;
        }
    }
    window.location.href = getContextPath() + loadDirDataPath + 'sortDown/{' + nextid + '}/{'+id+'}';
}

