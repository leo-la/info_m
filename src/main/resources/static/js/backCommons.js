
// 加载目录信息
function loadNo_1DirPage() {
    var da = JSON.stringify({id: $("#parentDirId").val()})
    $.ajax({
        url: getContextPath() + "/searchNo_1DirPage",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        type: "post",
        data:da,
        success: function (data) {
            if (data.code == "200") {
                if(data.result.length==0){
                    nodata();
                }
                var content = '';
                //显示数据
                for (var i = 0; i < data.result.length; i++) {
                    list = data.result;
                    var content1 =
                        '        <div class="col-md-6 col-sm-6  ">\n' +
                        '                        <div class="x_panel">\n' +
                        '                            <div class="x_title">\n' +
                        '                                <h2>\n' +
                        '                                    <i class="fa fa-bars"></i>\n' +

                        data.result[i].dirname +
                        '                                    <small>'+data.result[i].enname+'</small>\n' +
                        '                                </h2>\n' +
                        '                                <ul class="nav navbar-right panel_toolbox">\n' +
                        '                                    <li style="float: right;margin-left:50px"><a class="collapse-link" href="javascript:sortFront(' + data.result[i].id + ')" data-toggle="tooltip" data-placement="top" title="向前移动" ><i class="fa fa-caret-up" ></i></a>\n' +
                        '                                    </li>\n' +
                        '                                    <li style="float: right;margin-left:2px"><a class="collapse-link" href="javascript:sortDown(' + data.result[i].id + ')" data-toggle="tooltip" data-placement="top" title="向后移动"><i class="fa fa-caret-down"></i></a>\n' +
                        '                                    </li>\n' +
                        '                                    <li style="float: right;margin-left:2px"><a class="collapse-link" href="javascript:updateNo_1DirInit(' + data.result[i].id + ')" data-toggle="tooltip" data-placement="top" title="编辑"><i class="fa fa-cog"></i></a>\n' +
                        '                                    </li>\n' +
                        '                                    <li style="float: right;margin-left:2px"><a class="collapse-link" href="javascript:deleteNo_1DirConfirm(' + data.result[i].id + ')" data-toggle="tooltip" data-placement="top" title="删除"><i class="fa fa-close"></i></a>\n' +
                        '                                    </li>\n' +
                        '                                </ul>\n' +
                        '                                <div class="clearfix"></div>\n' +
                        '                            </div>\n' +
                        '                            <a href="javascript:searchNo_2Details('+data.result[i].id+')" style="float: right;padding-right: 20px">查看详情>></a>\n' +
                        '                        </div>\n' +
                        '                    </div>';

                    content += content1;
                }
                $("#dirContent").html(content);
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }

        }
    })
}

function loadNo_2DirPage(currentPage){
    var da = JSON.stringify({pageSize: $("#No_2PerPage").val(), currentPage: currentPage, id: $("#parentDirId").val()});
    $.ajax({
        url: getContextPath() + "/searchNo_2DirPage",
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data.code == "200") {
                //显示数据
                var tbody = "";
                for (var i = 0; i < data.result.pageData.length; i++) {
                    var tr = '       <tr style="font-size: 14px;">\n' +
                        '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].name + '</td>\n' +
                        '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].createtime + '</td>\n' +
                        '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].versionnum + '</td>\n' +
                        '                <td style="display: table-cell;vertical-align: middle;"> ' +
                        '<a href="javascript:searchNo_3Details(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">查看</a>' +
                        '<a href="javascript:updateNo_2DirInit(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px" >编辑</a>' +
                        '<a href="javascript:deleteNo_2DirConfirm(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">删除</a>' +
                        '</td>\n' +
                        '            </tr>'
                    tbody = tbody + tr ;
                }
                $("#pageTbody").html(tbody);
                //显示左侧页面信息
                var page1 = '共<span style="color: #017ebc;"> ' + data.result.totalPage + ' </span> 页 <span style="color: #017ebc;"> ' + data.result.totalCount + ' </span>条'
                $("#pageLeft").html(page1)

                //显示右侧分页信息
                var pager = ' <li><a href="javascript:loadNo_2DirPage(' + (data.result.currentPage - 1) + ')">Previous</a></li>';
                if (data.result.currentPage - 1 <= 0) {
                    pager = ' <li><a href="javascript:void(0)">Previous</a></li>';
                }

                for (var i = 0; i < data.result.totalPage; i++) {
                    if (data.result.currentPage == (i + 1)) {
                        var pagerli = '<li class="active"><a href="javascript:loadNo_2DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';
                    } else {
                        var pagerli = '<li><a href="javascript:loadNo_2DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';

                    }
                    pager += pagerli;
                }
                var pager2 = '<li><a href="javascript:loadNo_2DirPage(' + (data.result.currentPage + 1) + ')">Next</a></li>';
                if (data.result.currentPage + 1 > data.result.totalPage) {
                    pager2 = '<li><a href="javascript:void(0))">Next</a></li>';
                }
                pager += pager2;

                $("#pageRight").html(pager);
                $(".title_left").html(swxtitle);
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }

        }
    });

}

function loadNo_3DirPage(currentPage) {
    var da = JSON.stringify({pageSize: $("#No_3PerPage").val(), currentPage: currentPage, id: $("#parentDirId").val()});
    var path = '';
    if($("#fileLevel").val()==2){
        path = getContextPath() + "/searchNo_4DirPage";
    }else{
        path = getContextPath() + "/searchNo_3DirPage";
    }

    $.ajax({
        url: path,
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data.code == "200") {
                //显示数据
                var tbody = "";
                for (var i = 0; i < data.result.pageData.length; i++) {
                    var tr = '       <tr style="font-size: 14px;">\n' +
                        '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].name + '</td>\n';
                    var tr2 = '';
                    if(data.result.pageData[i].description==null){
                        tr2 = '                <td style="display: table-cell;vertical-align: middle;">无</td>'+
                            '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].createtime + '</td>\n' +
                            '                <td style="display: table-cell;vertical-align: middle;"> ';
                    }else{
                        tr2 =  '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].description + '</td>'+
                            '                <td style="display: table-cell;vertical-align: middle;">' + data.result.pageData[i].createtime + '</td>\n' +
                            '                <td style="display: table-cell;vertical-align: middle;"> ';
                    }
                    tr += tr2;

                    var tr3 = '';
                    if(data.result.pageData[i].type==1){
                        tr3 =
                            '<a href="javascript:searchNo_4Details(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">打开</a>' +
                            '<a href="javascript:updateNo_3DirConfirm(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">编辑</a>' +
                            '<a href="javascript:deleteNo_3DirConfirm(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">删除</a>';
                    }else {
                        tr3 =
                            '<a href="javascript:preview(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">预览</a>' +
                            '<a href="javascript:updateNo_3fileConfirm(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">更新</a>' +
                            '<a href="javascript:deleteNo_3DirConfirm(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">删除</a>';
                    }
                    tr += tr3;
                    var downloead = '';
                    if (data.result.pageData[i].download == 1) {
                        downloead =
                            '<a href="javascript:downloadFile(' + data.result.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">下载</a>'
                    }
                    var tr4 =
                        '</td>\n' +
                        '            </tr>'
                    tbody = tbody + tr + downloead + tr4;
                }
                $("#pageTbody").html(tbody);
                //显示左侧页面信息
                var page1 = '共<span style="color: #017ebc;"> ' + data.result.totalPage + ' </span> 页 <span style="color: #017ebc;"> ' + data.result.totalCount + ' </span>条'
                $("#pageLeft").html(page1)

                //显示右侧分页信息
                var pager = ' <li><a href="javascript:loadNo_3DirPage(' + (data.result.currentPage - 1) + ')">Previous</a></li>';
                if (data.result.currentPage - 1 <= 0) {
                    pager = ' <li><a href="javascript:void(0)">Previous</a></li>';
                }

                for (var i = 0; i < data.result.totalPage; i++) {
                    if (data.result.currentPage == (i + 1)) {
                        var pagerli = '<li class="active"><a href="javascript:loadNo_3DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';
                    } else {
                        var pagerli = '<li><a href="javascript:loadNo_3DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';

                    }
                    pager += pagerli;
                }
                var pager2 = '<li><a href="javascript:loadNo_3DirPage(' + (data.result.currentPage + 1) + ')">Next</a></li>';
                if (data.result.currentPage + 1 > data.result.totalPage) {
                    pager2 = '<li><a href="javascript:void(0))">Next</a></li>';
                }
                pager += pager2;

                $("#pageRight").html(pager);
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }

        }
    });

}

$("#No_2PerPage").bind("change",function () {
    loadNo_2DirPage();
})

$("#No_3PerPage").bind("change",function () {
    loadNo_3DirPage();
})

$("#No_4PerPage").bind("change",function () {
    loadNo_3DirPage();
})


//删除目录确认
function deleteNo_1DirConfirm(id) {
    layer.confirm('删除该目录将同时删除该目录下的文件，确认删除吗？', {
            skin: 'layui-layer-molv', //样式类名
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            deleteNo_1Dir(id);
        }, function () {
        }
    );
}

function deleteNo_2DirConfirm(id) {
    layer.confirm('删除该目录将同时删除目录下的文件，确认删除吗？', {
            skin: 'layui-layer-molv', //样式类名
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            deleteNo_2Dir(id)
        }, function () {
        }
    );
}

function deleteNo_3DirConfirm(id) {
    layer.confirm('确定删除该文件吗？', {
            skin: 'layui-layer-molv', //样式类名
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            deleteNo_3Dir(id)
        }, function () {
        }
    );
}

//删除
function deleteNo_1Dir(id) {
    var da = JSON.stringify({id: id})
    var path = getContextPath() + "/deleteNo_1Dir"
    $.ajax({
        url: path,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('删除成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                });
                setTimeout(function () {
                    loadNo_1DirPage();
                },1000)
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
}

function deleteNo_2Dir(id) {
    var da = JSON.stringify({id: id})
    var path = getContextPath() + "/deleteNo_2Dir"
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
                layer.msg('删除文件成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
                setTimeout(function () {
                    loadNo_2DirPage();
                }, 1000)
            }
        }
    })
}

function deleteNo_3Dir(id) {
    var da = JSON.stringify({id: id})
    var path = getContextPath()  + "/deleteNo_3FileDir"
    $.ajax({
        url: path,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('删除成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                });
                setTimeout(function () {
                    loadNo_3DirPage();
                },1000)
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
}

//更新确认
function updateNo_1DirInit(id) {
    $("#dirid").val(id);
    var da = JSON.stringify({id: id});
    $.ajax({
        url: getContextPath() + "/getNo_1DirInfo",
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {
            if(data.code == "200"){
                $("#oneName").val(data.result.dirname);
                $("#twoName").val(data.result.enname);
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
    $("#updateNo_1Btn").click();
}

function updateNo_2DirInit(id) {
    $("#dirid").val(id);
    $("#updateNo_2DirBtn").click();
}

function updateNo_3fileConfirm(id) {
    layer.confirm('重新上传文件将覆盖原文件，确定更新吗?', {
            skin: 'layui-layer-molv', //样式类名
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            $("#updateNo_3Fileid").val(id);
            $("#updateNo_3FileBtn").click();
        }, function () {
        }
    );
}

function updateNo_3DirConfirm(id) {
    layer.confirm('重新上传文件将覆盖原文件，确定更新吗?', {
            skin: 'layui-layer-molv', //样式类名
            btn: ['确定', '取消']
        }, function (index) {
            layer.close(index);
            $("#updateNo_3Dirid").val(id);
            $("#updateNo_3DirBtn").click();
        }, function () {
        }
    );
}

//更新
$("#updateNO_1Dir").bind("click",function () {
    var da = JSON.stringify({id: $("#dirid").val(),dirname: $("#oneName").val(),enname: $("#twoName").val()});
    $.ajax({
        url: getContextPath() + "/updateNO_1Dir",
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('更新成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                });
                $("#cancelUpdateNO_1Dir").click();
                setTimeout(function () {
                    loadNo_1DirPage();
                },1000)
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
})

$("#updateNo_2Dir").bind("click",function () {
    var da = JSON.stringify({id: $("#dirid").val(),name:$("#No_2DirName").val()});
    var path = getContextPath() + "/updateNo_2Dir"
    $.ajax({
        url: path,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('更新成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
                setTimeout(function () {
                    loadNo_2DirPage();
                    $("#cancelUpdateNo_2Dir").click();
                }, 1000)
            }else {
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
})

$("#updateNo_3Dir").bind("click",function () {
    var da = JSON.stringify({
        id: $("#updateNo_3Dirid").val(),
        name:$("#name").val(),
        description:$("#updateNo_3DirDescription").val()
    });
    $.ajax({
        url: getContextPath() + "/updateNo_3Dir",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('更新成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
                setTimeout(function () {
                    loadNo_3DirPage();
                }, 1000)
            }else {
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
})

$("#reuploadFile").bind("click",function () {
    if (document.getElementById("updatefile").files.length > 0 && $("#updatedescription").val().length > 0) {
        var updatefile = document.getElementById("updatefile").files[0];
        var formData = new FormData();
        var download = 0;
        if ($("#updatecheckb")[0].checked) {
            download = 1;
        }
        formData.append("updatefile", updatefile);
        formData.append("parentid", $("#parentDirId").val());
        formData.append("description", $("#updatedescription").val());
        formData.append("download", download);
        formData.append("id", $("#updateNo_3Fileid").val());
        layer.msg('正在上传', {
            skin: 'layui-layer-molv', //样式类名
            closeBtn: 0,
            icon: 1,
            time: 10000 //2秒关闭（如果不配置，默认是3秒）
        });
        var path = getContextPath() + "/reuploadFile";
        $.ajax({
            url: path,
            type: "POST",
            data: formData,
            /**
             *必须false才会自动加上正确的Content-Type
             */
            contentType: false,
            /**
             * 必须false才会避开jQuery对 formdata 的默认处理
             * XMLHttpRequest会对 formdata 进行正确的处理
             */
            processData: false,
            cache: false,
            async: false,
            success: function (data) {
                if (data == true) {
                    layer.msg('添加成功', {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        icon: 1,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    $("#cancelReuploadFile").click();
                    setTimeout(function () {
                        loadNo_3DirPage();
                    }, 1000)
                } else {
                    layer.msg(data.message, {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        icon: 2,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    })
                }
            }
        })
    }
})


//排序
function sortFront(id) {
    var opid;
    var flag = false;
    for(var i=0;i<list.length;i++){
        if(list[i].id==id){
            break;
        }
        opid = list[i].id;
        flag = true;
    }
    if(flag){
        sortDir(opid,id)
    }
}

function sortDown(id) {
    var opid;
    var j;
    for(var i=0;i<list.length;i++){
        if(list[i].id==id){
            j=i;
        }
    }
    for(var i=0;i<list.length;i++){
        opid = list[i].id;
        if(list[i].sortnum > list[j].sortnum){
            break;
        }
    }
    sortDir(opid,id)
}

function sortDir(opid,id) {
    var path = getContextPath() + '/sortDir';
    var da = JSON.stringify({opid: opid,id:id})
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
                loadNo_1DirPage();
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
}

//添加
$("#addNo_1DirWindow").bind("click",function () {
    $("#addNo_1Btn").click();
})

$("#addNo_1dir").bind("click",function () {
    var path = getContextPath() + '/addNO_1Dir';
    var da = JSON.stringify({depid: $("#depid").val(),dirname:$("#dirname").val(),enname:$("#enname").val()})
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
                layer.msg('创建成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                });
                $("#cancelAdd1LevelDir").click();
                setTimeout(function () {
                    loadNo_1DirPage();
                    },1000)
            }else{
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
})

$("#addNo_2Dir").bind("click",function () {
    var da = JSON.stringify({dirid:$("#parentDirId").val(),name:$("#name").val()});
    var path = getContextPath() + "/addNo_2Dir"
    $.ajax({
        url: path,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('创建成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
                setTimeout(function () {
                    loadNo_2DirPage();
                    $("#headingThree1").click();
                }, 1000)
            }else {
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
})

$("#addNo_3Dir").bind("click",function () {
    var da = JSON.stringify({
        parentid: $("#parentDirId").val(),
        name: $("#name").val(),
        description: $("#dirDescription").val(),
        level: $("#fileLevel").val()
    });
    $.ajax({
        url: getContextPath() + "/addNo_3Dir",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: da,
        type: "post",
        success: function (data) {
            if (data == true) {
                layer.msg('创建成功', {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 1,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
                setTimeout(function () {
                    loadNo_3DirPage();
                    $("#addNo_3WindowDir").click();
                }, 1000)
            }else {
                layer.msg(data.message, {
                    skin: 'layui-layer-molv', //样式类名
                    closeBtn: 0,
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                })
            }
        }
    })
})

$("#uploadfile").bind("click",function uploadFile() {
    if(document.getElementById("file").files.length>0&&$("#description").val().length>0){
        var formData = new FormData();
        var download = 0;
        if($("#checkb")[0].checked){
            download = 1;
        }
        formData.append("file",document.getElementById("file").files[0]);
        formData.append("parentid",$("#parentDirId").val());
        formData.append("description", $("#description").val());
        formData.append("download", download);
        formData.append("level", $("#fileLevel").val());
        layer.msg('正在上传', {
            skin: 'layui-layer-molv', //样式类名
            closeBtn: 0,
            icon: 1,
            time: 10000 //2秒关闭（如果不配置，默认是3秒）
        });
        var path = getContextPath() + "/addNo_3File";
        $.ajax({
            url: path,
            type: "POST",
            data: formData,
            /**
             *必须false才会自动加上正确的Content-Type
             */
            contentType: false,
            /**
             * 必须false才会避开jQuery对 formdata 的默认处理
             * XMLHttpRequest会对 formdata 进行正确的处理
             */
            processData: false,
            cache: false,
            async:false,
            success: function (data) {
                if (data == true) {
                    layer.msg('上传成功', {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        icon: 1,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    $("#uploadFileWindowBtn").click();
                    setTimeout(function () {
                        loadNo_3DirPage();
                    }, 1000)
                } else {
                    layer.msg(data.message, {
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        icon: 2,
                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                    })
                }
            }
        })
    }
})


//页面替换
function searchNo_2Details(id) {
    $("#parentDirId").val(id);
    $("#No_2Id").val(id);
    $("#totalLevel").val(2);
    var content =
        '<div class="">\n' +
        '                <div class="page-title">\n' +
        '                    <div class="title_left" style="padding-bottom: 8px;padding-left: 12px">\n' +
        '                       <ol class="navigationPath" style="font-size: 14px">\n' +
        '                            <li id="No_1Page"><a href="#" > 首页 </a></li>\n' +
        '                            <li id="No_2Page"><a href="#" > 目录 </a></li>\n' +
        '                        </ol>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="clearfix"></div>\n' +
        '\n' +
        '                <div class="col-md-12 col-sm-12  ">\n' +
        '                    <div class="x_panel">\n' +
        '                        <div class="x_title">\n' +
        '                            <span style="font-size: 18px;color: #668995;padding-bottom: 0;width: 100px">文件分类\n' +
        '                                <span style="font-size: 10px;color: #668995;margin-top: 40px;width: 100px">Document classification</span>\n' +
        '\n' +
        '                            </span>\n' +
        '\n' +
        '                            <ul class="nav navbar-right panel_toolbox">\n' +
        '                                <li style="float: right;margin-left:50px"><a class="collapse-link"><i\n' +
        '                                        class="fa fa-chevron-up"></i></a>\n' +
        '                                </li>\n' +
        '                                <li style="float: right;margin-left:10px"><a id="return"><i\n' +
        '                                        class="fa fa-reply"></i></a>\n' +
        '                                </li>\n' +
        '\n' +
        '                            </ul>\n' +
        '\n' +
        '                            <div class="clearfix"></div>\n' +
        '                        </div>\n' +
        '\n' +
        '                        <div class="x_content">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-6">\n' +
        '                                    <div class="dataTables_length" id="datatable_length">\n' +
        '                                        <div class="col-md-1" style="padding-top: 5px">\n' +
        '                                            显示\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-2" style="padding-left: 0;padding-bottom: 20px;width:80px">\n' +
        '                                            <select id="No_2PerPage"\n' +
        '                                                    name="datatable_length" aria-controls="datatable"\n' +
        '                                                    class="form-control input-sm"\n' +
        '                                                    style="width: 70px;height: 30px">\n' +
        '                                                <option value="5">5</option>\n' +
        '                                                <option value="10">10</option>\n' +
        '                                            </select>\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-2" style="padding-left: 0;padding-top: 5px">\n' +
        '                                            条数据\n' +
        '                                        </div>\n' +
        '\n' +
        '\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3"></div>\n' +
        '                                <div class="col-md-3" style="float: right;">\n' +
        '                                    <div class="col-md-12" style="float: right;">\n' +
        '                                        <div class="form-group pull-right">\n' +
        '                                            <form class="form-inline">\n' +
        '                                                <div class="form-group">\n' +
        '                                                    <!--<div class="input-group">-->\n' +
        '                                                        <!--<input type="text" class="form-control" id="exampleInputAmount"-->\n' +
        '                                                               <!--style="height: 30px;font-size: 12px;width:240px"-->\n' +
        '                                                               <!--placeholder="搜索关键词">-->\n' +
        '                                                        <!--<button type="submit" class="btn btn-info btn-sm "-->\n' +
        '                                                                <!--style="height: 29px;">查找-->\n' +
        '                                                        <!--</button>-->\n' +
        '                                                    <!--</div>-->\n' +
        '                                                </div>\n' +
        '\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '\n' +
        '                            <div style="">\n' +
        '                                <table class="table table-striped jambo_table bulk_action">\n' +
        '                                    <thead>\n' +
        '                                    <tr class="headings">\n' +
        '                                        <th class="column-title" style="width: 15%">文件名称</th>\n' +
        '                                        <th class="column-title" style="width: 15%">创建时间</th>\n' +
        '                                        <th class="column-title" style="width: 10%">版本数量</th>\n' +
        '                                        <th class="column-title no-link last" style="width: 12%"><span\n' +
        '                                                class="nobr">操作</span>\n' +
        '                                        </th>\n' +
        '                                    </tr>\n' +
        '                                    </thead>\n' +
        '\n' +
        '                                    <tbody id="pageTbody">\n' +
        '\n' +
        '                                    </tbody>\n' +
        '                                </table>\n' +
        '\n' +
        '                                <div class="accordion" id="accordion1" role="tablist" aria-multiselectable="true">\n' +
        '                                    <a class="panel-heading collapsed" role="tab" id="headingThree1"\n' +
        '                                       data-toggle="collapse" data-parent="#accordion1" href="#collapseThree1"\n' +
        '                                       aria-expanded="false" aria-controls="collapseThree">\n' +
        '                                        <h4 class="panel-title">新建文件目录</h4>\n' +
        '                                    </a>\n' +
        '                                    <div id="collapseThree1" class="panel-collapse collapse" role="tabpanel"\n' +
        '                                         aria-labelledby="headingThree">\n' +
        '                                        <br>\n' +
        '                                        <form id="addDir" method="post">\n' +
        '                                            <div class="panel-body">\n' +
        '                                                <p><strong>目录名称</strong>\n' +
        '                                                </p>\n' +
        '                                                <div class="form-group">\n' +
        '                                                    <div class="input-group">\n' +
        '                                                        <div class="custom-file">\n' +
        '                                                            <input type="text" class="form-control" id="name">\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                            </div>\n' +
        '                                            <br>\n' +
        '                                            <div class="col-md-12" style="text-align: center">\n' +
        '                                                <a id="addNo_2Dir" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                            </div>\n' +
        '                                        </form>\n' +
        '\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '\n' +
        '                            </div>\n' +
        '\n' +
        '                            <div class="col-md-12">\n' +
        '                                <div class="col-md-2" style="padding-top: 18px">\n' +
        '                                         <span id="pageLeft" style="font-size: 18px;">\n' +
        '                                         </span>\n' +
        '                                </div>\n' +
        '\n' +
        '                                <div class="col-md-10">\n' +
        '                                    <nav aria-label="..." style="float: right">\n' +
        '                                        <ul id="pageRight" class="pagination">\n' +
        '                                        </ul>\n' +
        '                                    </nav>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '\n' +
        '            <!-- Small modal -->\n' +
        '            <button data-toggle="modal" data-target=".bs-example-modal-sm" style="display: none" id="updateNo_2DirBtn"></button>\n' +
        '            <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">\n' +
        '                <div class="modal-dialog modal-sm">\n' +
        '                    <div class="modal-content">\n' +
        '\n' +
        '                        <div class="modal-header">\n' +
        '                            <h4 class="modal-title" id="myModalLabel2">编辑目录信息</h4>\n' +
        '                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
        '                                <span aria-hidden="true">×</span>\n' +
        '                            </button>\n' +
        '                        </div>\n' +
        '                        <div class="modal-body">\n' +
        '                            <h2>修改名称</h2>\n' +
        '                            <form method="post">\n' +
        '                                <div class="panel-body">\n' +
        '                                    <div class="form-group">\n' +
        '                                        <div class="input-group">\n' +
        '                                            <div class="custom-file">\n' +
        '                                                <input type="text" class="form-control" id="No_2DirName">\n' +
        '                                            </div>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                                <div style="text-align: center">\n' +
        '                                    <a id="cancelUpdateNo_2Dir" class="btn btn-secondary btn-sm" data-dismiss="modal">取消\n' +
        '                                    </a>\n' +
        '                                    <a id="updateNo_2Dir" class="btn btn-info btn-sm">保存</a>\n' +
        '                                </div>\n' +
        '                            </form>\n' +
        '                        </div>\n' +
        '\n' +
        '\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <!-- /modals -->'

    $("#right_col").html(content);
    var js =
        '<script src="'+getContextPath()+'/js/backCommons.js"></script>'
    $("#reloadJs").html(js);

    loadNo_2DirPage();
}

function searchNo_3Details(id){
    $("#parentDirId").val(id);
    $("#fileLevel").val(1);
    $("#No_3Id").val(id);
    $("#totalLevel").val(3);
    var content2 =
        '  <div class="">\n' +
        '                <div class="page-title">\n' +
        '                    <div class="title_left" style="padding-bottom: 8px;padding-left: 12px">\n' +
        '                       <ol class="navigationPath" style="font-size: 14px">\n' +
        '                            <li><a href="#" id="No_1Page"> 首页 </a></li>\n' +
        '                            <li><a href="#" id="No_2Page"> 目录 </a></li>\n' +
        '                            <li><a href="#" id="No_3Page"> 文件 </a></li>\n' +
        '                        </ol>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="clearfix"></div>\n' +
        '                <div class="" id="imgContent"></div>\n' +
        '                <div class="col-md-12 col-sm-12  ">\n' +
        '\n' +
        '                    <div class="x_panel">\n' +
        '                        <div class="x_title">\n' +
        '                            <span style="font-size: 18px;color: #668995;padding-bottom: 0;width: 100px">版本分类\n' +
        '                                <span style="font-size: 10px;color: #668995;margin-top: 40px;width: 100px">Document classification</span>\n' +
        '\n' +
        '                            </span>\n' +
        '\n' +
        '                            <ul class="nav navbar-right panel_toolbox">\n' +
        '                                <li style="float: right;margin-left:50px"><a class="collapse-link"><i\n' +
        '                                        class="fa fa-chevron-up"></i></a>\n' +
        '                                </li>\n' +
        '                                <li style="float: right;margin-left:10px"><a id="return"><i\n' +
        '                                        class="fa fa-reply"></i></a>\n' +
        '                                </li>\n' +
        '\n' +
        '                            </ul>\n' +
        '\n' +
        '                            <div class="clearfix"></div>\n' +
        '                        </div>\n' +
        '\n' +
        '                        <div class="x_content">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-6">\n' +
        '                                    <div class="dataTables_length" id="datatable_length">\n' +
        '                                        <div class="col-md-1" style="padding-top: 5px">\n' +
        '                                            显示\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-2" style="padding-left: 0;padding-bottom: 20px;width:80px">\n' +
        '                                            <select id="No_3PerPage"\n' +
        '                                                    name="datatable_length" aria-controls="datatable"\n' +
        '                                                    class="form-control input-sm"\n' +
        '                                                    style="width: 70px;height: 30px">\n' +
        '                                                <option value="5">5</option>\n' +
        '                                                <option value="10">10</option>\n' +
        '                                            </select>\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-2" style="padding-left: 0;padding-top: 5px">\n' +
        '                                            条数据\n' +
        '                                        </div>\n' +
        '\n' +
        '\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3"></div>\n' +
        '                                <div class="col-md-3" style="float: right;">\n' +
        '                                    <div class="col-md-12" style="float: right;">\n' +
        '                                        <div class="form-group pull-right">\n' +
        '                                            <form class="form-inline">\n' +
        '                                                <div class="form-group">\n' +
        '                                                    <!--<div class="input-group">-->\n' +
        '                                                        <!--<input type="text" class="form-control" id="exampleInputAmount"-->\n' +
        '                                                               <!--style="height: 30px;font-size: 12px;width:240px"-->\n' +
        '                                                               <!--placeholder="搜索关键词">-->\n' +
        '                                                        <!--<button type="submit" class="btn btn-info btn-sm "-->\n' +
        '                                                                <!--style="height: 29px;">查找-->\n' +
        '                                                        <!--</button>-->\n' +
        '                                                    <!--</div>-->\n' +
        '                                                </div>\n' +
        '\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <!--表格内容-->\n' +
        '                            <div style="">\n' +
        '                                <table class="table table-striped jambo_table bulk_action">\n' +
        '                                    <thead>\n' +
        '                                    <tr class="headings">\n' +
        '                                        <th class="column-title" style="width: 15%">历史版本</th>\n' +
        '                                        <th class="column-title" style="width: 15%">描述</th>\n' +
        '                                        <th class="column-title" style="width: 10%">创建时间</th>\n' +
        '                                        <th class="column-title no-link last" style="width: 12%"><span\n' +
        '                                                class="nobr">操作</span>\n' +
        '                                        </th>\n' +
        '                                    </tr>\n' +
        '                                    </thead>\n' +
        '\n' +
        '                                    <tbody id="pageTbody">\n' +
        '\n' +
        '                                    </tbody>\n' +
        '                                </table>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <div class="col-md-12">\n' +
        '                                <div class="col-md-2" style="padding-top: 18px">\n' +
        '                                         <span id="pageLeft" style="font-size: 18px;">\n' +
        '                                         </span>\n' +
        '                                </div>\n' +
        '\n' +
        '                                <div class="col-md-10">\n' +
        '                                    <nav aria-label="..." style="float: right">\n' +
        '                                        <ul id="pageRight" class="pagination">\n' +
        '                                        </ul>\n' +
        '                                    </nav>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '\n' +
        '                            <!--上传文件-->\n' +
        '                            <div class="accordion" id="accordion1" role="tablist" aria-multiselectable="true">\n' +
        '                                <a class="panel-heading collapsed" role="tab" id="uploadFileWindowBtn"\n' +
        '                                   data-toggle="collapse" data-parent="#accordion1" href="#collapseThree1"\n' +
        '                                   aria-expanded="false" aria-controls="collapseThree">\n' +
        '                                    <h4 class="panel-title">添加版本</h4>\n' +
        '                                </a>\n' +
        '                                <div id="collapseThree1" class="panel-collapse collapse" role="tabpanel"\n' +
        '                                     aria-labelledby="headingThree">\n' +
        '                                    <br>\n' +
        '                                    <form method="post" enctype ="multipart/form-data">\n' +
        '                                        <div class="panel-body">\n' +
        '                                            <p><strong>上传文件</strong>\n' +
        '                                            </p>\n' +
        '                                            <div class="form-group">\n' +
        '                                                <div class="input-group">\n' +
        '                                                    <div class="custom-file">\n' +
        '                                                        <input type="file" class="custom-file-input"\n' +
        '                                                               id="file" name="file" required="required">\n' +
        '                                                        <label class="custom-file-label"\n' +
        '                                                               for="file">选择文件</label>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                            </div>\n' +
        '                                        </div>\n' +
        '                                        <br>\n' +
        '                                        <p><strong>版本描述</strong>\n' +
        '                                        </p>\n' +
        '                                        <textarea id="description" required="required" class="form-control" name="description"\n' +
        '                                                  data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                  data-parsley-maxlength="100"\n' +
        '                                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                  data-parsley-validation-threshold="10"></textarea>\n' +
        '                                        <br>\n' +
        '                                        <div class="">\n' +
        '                                            <label>\n' +
        '                                                <input type="checkbox" class="js-switch" id="checkb" name="download"/> 是否可下载\n' +
        '                                            </label>\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-12" style="text-align: center">\n' +
        '                                            <a id="uploadfile" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                        </div>\n' +
        '                                    </form>\n' +
        '\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <!--添加文件夹-->\n' +
        '                            <div class="accordion" id="accordion2" role="tablist" aria-multiselectable="true" style="display: block">\n' +
        '                                <a class="panel-heading collapsed" role="tab" id="addNo_3WindowDir"\n' +
        '                                   data-toggle="collapse" data-parent="#accordion1" href="#collapseThree2"\n' +
        '                                   aria-expanded="false" aria-controls="collapseThree2">\n' +
        '                                    <h4 class="panel-title">添加文件夹</h4>\n' +
        '                                </a>\n' +
        '                                <div id="collapseThree2" class="panel-collapse collapse" role="tabpanel"\n' +
        '                                     aria-labelledby="headingThree">\n' +
        '                                    <br>\n' +
        '                                    <div class="modal-body">\n' +
        '\n' +
        '                                        <form id="addFourDir" method="post">\n' +
        '                                            <div class="panel-body">\n' +
        '                                                <p><strong>名称</strong>\n' +
        '                                                </p>\n' +
        '                                                <div class="form-group">\n' +
        '                                                    <div class="input-group">\n' +
        '                                                        <div class="custom-file">\n' +
        '                                                            <input type="text" class="form-control" id="name" required="required">\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                            </div>\n' +
        '                                            <br>\n' +
        '                                            <p><strong>描述</strong>\n' +
        '                                            </p>\n' +
        '                                            <textarea class="form-control" id="dirDescription"\n' +
        '                                                      data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                      data-parsley-maxlength="100"\n' +
        '                                                      data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                      data-parsley-validation-threshold="10"></textarea>\n' +
        '                                            <br>\n' +
        '\n' +
        '                                            <div style="text-align: center">\n' +
        '                                                <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">取消</button>\n' +
        '                                                <a id="addNo_3Dir" class="btn btn-info btn-sm">保存</a>\n' +
        '                                            </div>\n' +
        '                                        </form>\n' +
        '                                    </div>\n' +
        '\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                            <!--更新文件弹窗-->\n' +
        '                            <button data-toggle="modal" data-target=".bs-example-modal-sm" style="display: none" id="updateNo_3FileBtn"></button>\n' +
        '                            <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">\n' +
        '                                <div class="modal-dialog modal-sm">\n' +
        '                                    <div class="modal-content" style="width: 500px">\n' +
        '\n' +
        '                                        <div class="modal-header">\n' +
        '                                            <h4 class="modal-title" id="myModalLabel">更新文件</h4>\n' +
        '                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
        '                                                <span aria-hidden="true">×</span>\n' +
        '                                            </button>\n' +
        '                                        </div>\n' +
        '                                        <div class="modal-body">\n' +
        '\n' +
        '                                            <form id="updateFileForm" method="post">\n' +
        '                                                <input type="hidden" id="updateNo_3Fileid">\n' +
        '                                                <div class="panel-body">\n' +
        '                                                    <p><strong>上传文件</strong>\n' +
        '                                                    </p>\n' +
        '                                                    <div class="form-group">\n' +
        '                                                        <div class="input-group">\n' +
        '                                                            <div class="custom-file">\n' +
        '                                                                <input type="file" class="custom-file-input"\n' +
        '                                                                       id="updatefile" name="file" required="required">\n' +
        '                                                                <label class="custom-file-label"\n' +
        '                                                                       for="file">选择文件</label>\n' +
        '                                                            </div>\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                                <br>\n' +
        '                                                <p><strong>版本描述</strong>\n' +
        '                                                </p>\n' +
        '                                                <textarea id="updatedescription" required="required" class="form-control" name="description"\n' +
        '                                                          data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                          data-parsley-maxlength="100"\n' +
        '                                                          data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                          data-parsley-validation-threshold="10"></textarea>\n' +
        '                                                <br>\n' +
        '                                                <div class="">\n' +
        '                                                    <label>\n' +
        '                                                        <input type="checkbox" class="js-switch" id="updatecheckb" name="download"/> 是否可下载\n' +
        '                                                    </label>\n' +
        '                                                </div>\n' +
        '                                                <div class="col-md-12" style="text-align: center">\n' +
        '                                                    <a id="reuploadFile" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                                    <a id="cancelReuploadFile" class="btn btn-secondary btn-sm" data-dismiss="modal">取消\n' +
        '                                                    </a>\n' +
        '                                                </div>\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '\n' +
        '\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <!--更新文件夹弹窗-->\n' +
        '                            <button data-toggle="modal" data-target=".bs-example-modal-sm2" style="display: none" id="updateNo_3DirBtn"></button>\n' +
        '                            <div class="modal fade bs-example-modal-sm2" tabindex="-1" role="dialog" aria-hidden="true" >\n' +
        '                                <div class="modal-dialog modal-sm">\n' +
        '                                    <div class="modal-content" style="width: 500px">\n' +
        '\n' +
        '                                        <div class="modal-header">\n' +
        '                                            <h4 class="modal-title" id="myModalLabe2">更新文件夹</h4>\n' +
        '                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
        '                                                <span aria-hidden="true">×</span>\n' +
        '                                            </button>\n' +
        '                                        </div>\n' +
        '                                        <div class="modal-body">\n' +
        '                                            <form id="updateFourDirForm" method="post">\n' +
        '                                                <input type="hidden" id="updateNo_3Dirid" id="id">\n' +
        '                                                <div class="panel-body">\n' +
        '                                                    <p><strong>名称</strong>\n' +
        '                                                    </p>\n' +
        '                                                    <div class="form-group">\n' +
        '                                                        <div class="input-group">\n' +
        '                                                            <div class="custom-file">\n' +
        '                                                                <input type="text" class="form-control"\n' +
        '                                                                       id="name" required="required">\n' +
        '                                                            </div>\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                                <br>\n' +
        '                                                <p><strong>描述</strong>\n' +
        '                                                </p>\n' +
        '                                                <textarea id="updateNo_3DirDescription" required="required" class="form-control" name="description"\n' +
        '                                                          data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                          data-parsley-maxlength="100"\n' +
        '                                                          data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                          data-parsley-validation-threshold="10"></textarea>\n' +
        '                                                <br>\n' +
        '                                                <div class="col-md-12" style="text-align: center">\n' +
        '                                                    <a id="updateNo_3Dir" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">取消\n' +
        '                                                    </button>\n' +
        '                                                </div>\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '\n' +
        '\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';



    $("#right_col").html(content2);

    var js =
        '<script src="'+getContextPath()+'/js/backCommons.js"></script>';
    $("#reloadJs").html(js);

    bsCustomFileInput.init();

    loadNo_3DirPage();
}

function searchNo_4Details(id){
    $("#parentDirId").val(id);
    $("#fileLevel").val(2);
    $("#No_4Id").val(id);
    $("#totalLevel").val(4);
    var content2 =
        '  <div class="">\n' +
        '                <div class="page-title">\n' +
        '                    <div class="title_left" style="padding-bottom: 8px;padding-left: 12px">\n' +
        '                       <ol class="navigationPath" style="font-size: 14px">\n' +
        '                            <li><a href="#" id="No_1Page"> 首页 </a></li>\n' +
        '                            <li><a href="#" id="No_2Page"> 目录 </a></li>\n' +
        '                            <li><a href="#" id="No_3Page"> 文件 </a></li>\n' +
        '                            <li><a href="#" id="No_4Page"> 子文件 </a></li>\n' +
        '                        </ol>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="clearfix"></div>\n' +
        '                <div class="" id="imgContent"></div>\n' +
        '                <div class="col-md-12 col-sm-12  ">\n' +
        '\n' +
        '                    <div class="x_panel">\n' +
        '                        <div class="x_title">\n' +
        '                            <span style="font-size: 18px;color: #668995;padding-bottom: 0;width: 100px">版本分类\n' +
        '                                <span style="font-size: 10px;color: #668995;margin-top: 40px;width: 100px">Document classification</span>\n' +
        '\n' +
        '                            </span>\n' +
        '\n' +
        '                            <ul class="nav navbar-right panel_toolbox">\n' +
        '                                <li style="float: right;margin-left:50px"><a class="collapse-link"><i\n' +
        '                                        class="fa fa-chevron-up"></i></a>\n' +
        '                                </li>\n' +
        '                                <li style="float: right;margin-left:10px"><a id="return"><i\n' +
        '                                        class="fa fa-reply"></i></a>\n' +
        '                                </li>\n' +
        '\n' +
        '                            </ul>\n' +
        '\n' +
        '                            <div class="clearfix"></div>\n' +
        '                        </div>\n' +
        '\n' +
        '                        <div class="x_content">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-6">\n' +
        '                                    <div class="dataTables_length" id="datatable_length">\n' +
        '                                        <div class="col-md-1" style="padding-top: 5px">\n' +
        '                                            显示\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-2" style="padding-left: 0;padding-bottom: 20px;width:80px">\n' +
        '                                            <select id="No_3PerPage"\n' +
        '                                                    name="datatable_length" aria-controls="datatable"\n' +
        '                                                    class="form-control input-sm"\n' +
        '                                                    style="width: 70px;height: 30px">\n' +
        '                                                <option value="5">5</option>\n' +
        '                                                <option value="10">10</option>\n' +
        '                                            </select>\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-2" style="padding-left: 0;padding-top: 5px">\n' +
        '                                            条数据\n' +
        '                                        </div>\n' +
        '\n' +
        '\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3"></div>\n' +
        '                                <div class="col-md-3" style="float: right;">\n' +
        '                                    <div class="col-md-12" style="float: right;">\n' +
        '                                        <div class="form-group pull-right">\n' +
        '                                            <form class="form-inline">\n' +
        '                                                <div class="form-group">\n' +
        '                                                    <!--<div class="input-group">-->\n' +
        '                                                        <!--<input type="text" class="form-control" id="exampleInputAmount"-->\n' +
        '                                                               <!--style="height: 30px;font-size: 12px;width:240px"-->\n' +
        '                                                               <!--placeholder="搜索关键词">-->\n' +
        '                                                        <!--<button type="submit" class="btn btn-info btn-sm "-->\n' +
        '                                                                <!--style="height: 29px;">查找-->\n' +
        '                                                        <!--</button>-->\n' +
        '                                                    <!--</div>-->\n' +
        '                                                </div>\n' +
        '\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <!--表格内容-->\n' +
        '                            <div style="">\n' +
        '                                <table class="table table-striped jambo_table bulk_action">\n' +
        '                                    <thead>\n' +
        '                                    <tr class="headings">\n' +
        '                                        <th class="column-title" style="width: 15%">历史版本</th>\n' +
        '                                        <th class="column-title" style="width: 15%">描述</th>\n' +
        '                                        <th class="column-title" style="width: 10%">创建时间</th>\n' +
        '                                        <th class="column-title no-link last" style="width: 12%"><span\n' +
        '                                                class="nobr">操作</span>\n' +
        '                                        </th>\n' +
        '                                    </tr>\n' +
        '                                    </thead>\n' +
        '\n' +
        '                                    <tbody id="pageTbody">\n' +
        '\n' +
        '                                    </tbody>\n' +
        '                                </table>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <div class="col-md-12">\n' +
        '                                <div class="col-md-2" style="padding-top: 18px">\n' +
        '                                         <span id="pageLeft" style="font-size: 18px;">\n' +
        '                                         </span>\n' +
        '                                </div>\n' +
        '\n' +
        '                                <div class="col-md-10">\n' +
        '                                    <nav aria-label="..." style="float: right">\n' +
        '                                        <ul id="pageRight" class="pagination">\n' +
        '                                        </ul>\n' +
        '                                    </nav>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '\n' +
        '                            <!--上传文件-->\n' +
        '                            <div class="accordion" id="accordion1" role="tablist" aria-multiselectable="true">\n' +
        '                                <a class="panel-heading collapsed" role="tab" id="uploadFileWindowBtn"\n' +
        '                                   data-toggle="collapse" data-parent="#accordion1" href="#collapseThree1"\n' +
        '                                   aria-expanded="false" aria-controls="collapseThree">\n' +
        '                                    <h4 class="panel-title">添加版本</h4>\n' +
        '                                </a>\n' +
        '                                <div id="collapseThree1" class="panel-collapse collapse" role="tabpanel"\n' +
        '                                     aria-labelledby="headingThree">\n' +
        '                                    <br>\n' +
        '                                    <form method="post" enctype ="multipart/form-data">\n' +
        '                                        <div class="panel-body">\n' +
        '                                            <p><strong>上传文件</strong>\n' +
        '                                            </p>\n' +
        '                                            <div class="form-group">\n' +
        '                                                <div class="input-group">\n' +
        '                                                    <div class="custom-file">\n' +
        '                                                        <input type="file" class="custom-file-input"\n' +
        '                                                               id="file" name="file" required="required">\n' +
        '                                                                <label class="custom-file-label"\n' +
        '                                                                       for="file">选择文件</label>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                            </div>\n' +
        '                                        </div>\n' +
        '                                        <br>\n' +
        '                                        <p><strong>版本描述</strong>\n' +
        '                                        </p>\n' +
        '                                        <textarea id="description" required="required" class="form-control" name="description"\n' +
        '                                                  data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                  data-parsley-maxlength="100"\n' +
        '                                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                  data-parsley-validation-threshold="10"></textarea>\n' +
        '                                        <br>\n' +
        '                                        <div class="">\n' +
        '                                            <label>\n' +
        '                                                <input type="checkbox" class="js-switch" id="checkb" name="download"/> 是否可下载\n' +
        '                                            </label>\n' +
        '                                        </div>\n' +
        '                                        <div class="col-md-12" style="text-align: center">\n' +
        '                                            <a id="uploadfile" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                        </div>\n' +
        '                                    </form>\n' +
        '\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <!--添加文件夹-->\n' +
        '                            <div class="accordion" id="accordion2" role="tablist" aria-multiselectable="true" style="display: none">\n' +
        '                                <a class="panel-heading collapsed" role="tab" id="headingThree2"\n' +
        '                                   data-toggle="collapse" data-parent="#accordion1" href="#collapseThree2"\n' +
        '                                   aria-expanded="false" aria-controls="collapseThree2">\n' +
        '                                    <h4 class="panel-title">添加文件夹</h4>\n' +
        '                                </a>\n' +
        '                                <div id="collapseThree2" class="panel-collapse collapse" role="tabpanel"\n' +
        '                                     aria-labelledby="headingThree">\n' +
        '                                    <br>\n' +
        '                                    <div class="modal-body">\n' +
        '\n' +
        '                                        <form id="addFourDir" method="post">\n' +
        '                                            <div class="panel-body">\n' +
        '                                                <p><strong>名称</strong>\n' +
        '                                                </p>\n' +
        '                                                <div class="form-group">\n' +
        '                                                    <div class="input-group">\n' +
        '                                                        <div class="custom-file">\n' +
        '                                                            <input type="text" class="form-control" id="name" required="required">\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                            </div>\n' +
        '                                            <br>\n' +
        '                                            <p><strong>描述</strong>\n' +
        '                                            </p>\n' +
        '                                            <textarea class="form-control" id="description"\n' +
        '                                                      data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                      data-parsley-maxlength="100"\n' +
        '                                                      data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                      data-parsley-validation-threshold="10"></textarea>\n' +
        '                                            <br>\n' +
        '\n' +
        '                                            <div style="text-align: center">\n' +
        '                                                <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">取消</button>\n' +
        '                                                <a id="addNo_3Dir" class="btn btn-info btn-sm">保存</a>\n' +
        '                                            </div>\n' +
        '                                        </form>\n' +
        '                                    </div>\n' +
        '\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <br>\n' +
        '                            <br>\n' +
        '                            <!--更新文件弹窗-->\n' +
        '                            <button data-toggle="modal" data-target=".bs-example-modal-sm" style="display: none" id="updateNo_3FileBtn"></button>\n' +
        '                            <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">\n' +
        '                                <div class="modal-dialog modal-sm">\n' +
        '                                    <div class="modal-content" style="width: 500px">\n' +
        '\n' +
        '                                        <div class="modal-header">\n' +
        '                                            <h4 class="modal-title" id="myModalLabel">更新文件</h4>\n' +
        '                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
        '                                                <span aria-hidden="true">×</span>\n' +
        '                                            </button>\n' +
        '                                        </div>\n' +
        '                                        <div class="modal-body">\n' +
        '\n' +
        '                                            <form id="updateFileForm" method="post">\n' +
        '                                                <input type="hidden" id="updateNo_3Fileid">\n' +
        '                                                <div class="panel-body">\n' +
        '                                                    <p><strong>上传文件</strong>\n' +
        '                                                    </p>\n' +
        '                                                    <div class="form-group">\n' +
        '                                                        <div class="input-group">\n' +
        '                                                            <div class="custom-file">\n' +
        '                                                                <input type="file" class="custom-file-input"\n' +
        '                                                                       id="updatefile" name="file" required="required">\n' +
        '                                                                <label class="custom-file-label"\n' +
        '                                                                       for="file">选择文件</label>\n' +
        '                                                            </div>\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                                <br>\n' +
        '                                                <p><strong>版本描述</strong>\n' +
        '                                                </p>\n' +
        '                                                <textarea id="updatedescription" required="required" class="form-control" name="description"\n' +
        '                                                          data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                          data-parsley-maxlength="100"\n' +
        '                                                          data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                          data-parsley-validation-threshold="10"></textarea>\n' +
        '                                                <br>\n' +
        '                                                <div class="">\n' +
        '                                                    <label>\n' +
        '                                                        <input type="checkbox" class="js-switch" id="updatecheckb" name="download"/> 是否可下载\n' +
        '                                                    </label>\n' +
        '                                                </div>\n' +
        '                                                <div class="col-md-12" style="text-align: center">\n' +
        '                                                    <a id="reuploadFile" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                                    <a id="cancelReuploadFile" class="btn btn-secondary btn-sm" data-dismiss="modal">取消\n' +
        '                                                    </a>\n' +
        '                                                </div>\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '\n' +
        '\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '\n' +
        '                            <!--更新文件夹弹窗-->\n' +
        '                            <button data-toggle="modal" data-target=".bs-example-modal-sm2" style="display: none" id="updateNo_3DirBtn"></button>\n' +
        '                            <div class="modal fade bs-example-modal-sm2" tabindex="-1" role="dialog" aria-hidden="true" >\n' +
        '                                <div class="modal-dialog modal-sm">\n' +
        '                                    <div class="modal-content" style="width: 500px">\n' +
        '\n' +
        '                                        <div class="modal-header">\n' +
        '                                            <h4 class="modal-title" id="myModalLabe2">更新文件夹</h4>\n' +
        '                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
        '                                                <span aria-hidden="true">×</span>\n' +
        '                                            </button>\n' +
        '                                        </div>\n' +
        '                                        <div class="modal-body">\n' +
        '                                            <form id="updateFourDirForm" method="post">\n' +
        '                                                <input type="hidden" id="updateNo_3Dirid" id="id">\n' +
        '                                                <div class="panel-body">\n' +
        '                                                    <p><strong>名称</strong>\n' +
        '                                                    </p>\n' +
        '                                                    <div class="form-group">\n' +
        '                                                        <div class="input-group">\n' +
        '                                                            <div class="custom-file">\n' +
        '                                                                <input type="text" class="form-control"\n' +
        '                                                                       id="name" required="required">\n' +
        '                                                            </div>\n' +
        '                                                        </div>\n' +
        '                                                    </div>\n' +
        '                                                </div>\n' +
        '                                                <br>\n' +
        '                                                <p><strong>描述</strong>\n' +
        '                                                </p>\n' +
        '                                                <textarea id="updateNo_3DirDescription" required="required" class="form-control" name="description"\n' +
        '                                                          data-parsley-trigger="keyup" data-parsley-minlength="20"\n' +
        '                                                          data-parsley-maxlength="100"\n' +
        '                                                          data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment.."\n' +
        '                                                          data-parsley-validation-threshold="10"></textarea>\n' +
        '                                                <br>\n' +
        '                                                <div class="col-md-12" style="text-align: center">\n' +
        '                                                    <a id="updateNo_3Dir" class="btn btn-info btn-sm" style="color: white" >确认</a>\n' +
        '                                                    <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">取消\n' +
        '                                                    </button>\n' +
        '                                                </div>\n' +
        '                                            </form>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '\n' +
        '\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';


    $("#right_col").html(content2);

    var js =
        '<script src="'+getContextPath()+'/js/backCommons.js"></script>'
    $("#reloadJs").html(js);

    bsCustomFileInput.init();

    loadNo_3DirPage();
}


//导航
$("#No_1Page").bind("click",function () {
    window.location.reload();
})

$("#No_2Page").bind("click",function () {
    searchNo_2Details($("#No_2Id").val());
})

$("#No_3Page").bind("click",function () {
    searchNo_3Details($("#No_3Id").val())
})

$("#No_4Page").bind("click",function () {
    searchNo_4Details($("#No_4Id").val())
})


//返回
$("#return").bind("click",function () {
    var level = $("#totalLevel").val();
    switch (level){
        case '2':
            window.location.reload();
            break;
        case '3':
            searchNo_2Details($("#No_2Id").val());
            break;
        case '4':
            searchNo_3Details($("#No_3Id").val());
            break;
        default:
            window.location.reload();
    }
})


//预览
function preview(id) {
    window.open(getContextPath() + '/searchFilePreview/{' + id + '}/pdf');
}


//下载
function downloadFile(id) {
    window.location.href = getContextPath() + '/downloadFile/{' + id + '}';
}