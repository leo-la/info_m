var dirid = $("#dirid").val();
var loadDirDataPath;
var swxtitle;
// 加载页面数据
function loadPageData(currentPage) {
    var depid = $("#depid").val();
    var pageSize = $("#perPage").val();
    var dirid = $("#dirid").val();
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

    var da = JSON.stringify({pageSize: pageSize, currentPage: currentPage, id: dirid});
    $.ajax({
        url: getContextPath() + loadDirDataPath + "searchDirectoryDetails",
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {
            //显示数据
            var tbody = "";
            for (var i = 0; i < data.pageData.length; i++) {
                var tr = '       <tr style="font-size: 14px;">\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].name + '</td>\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].createtime + '</td>\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].versionnum + '</td>\n' +
                    '                <td style="display: table-cell;vertical-align: middle;"> ' +
                    '<a href="javascript:fileVersions(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">查看</a>' +
                    '<a href="javascript:updateName(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px" >编辑</a>' +
                    '<a href="javascript:deleteConfirm(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">删除</a>' +
                    '</td>\n' +
                    '            </tr>'
                tbody = tbody + tr ;
            }
            $("#pageTbody").html(tbody);
            //显示左侧页面信息
            var page1 = '共<span style="color: #017ebc;"> ' + data.totalPage + ' </span> 页 <span style="color: #017ebc;"> ' + data.totalCount + ' </span>条'
            $("#pageLeft").html(page1)

            //显示右侧分页信息
            var pager = ' <li><a href="javascript:loadPageData(' + (data.currentPage - 1) + ')">Previous</a></li>';
            if (data.currentPage - 1 <= 0) {
                pager = ' <li><a href="javascript:void(0)">Previous</a></li>';
            }

            for (var i = 0; i < data.totalPage; i++) {
                if (data.currentPage == (i + 1)) {
                    var pagerli = '<li class="active"><a href="javascript:loadPageData(' + (i + 1) + ')">' + (i + 1) + '</a></li>';
                } else {
                    var pagerli = '<li><a href="javascript:loadPageData(' + (i + 1) + ')">' + (i + 1) + '</a></li>';

                }
                pager += pagerli;
            }
            var pager2 = '<li><a href="javascript:loadPageData(' + (data.currentPage + 1) + ')">Next</a></li>';
            if (data.currentPage + 1 > data.totalPage) {
                pager2 = '<li><a href="javascript:void(0))">Next</a></li>';
            }
            pager += pager2;

            updateAction = getContextPath()+ loadDirDataPath + 'updateDirName';
            addAction = getContextPath()+ loadDirDataPath + 'addFileDir';
            $("#pageRight").html(pager);
            $(".title_left").html(swxtitle);
            $("#updateDirName").attr("action",updateAction);
            $("#addDir").attr("action",addAction);
        }
    });

}

function fileVersions(id) {
    window.location.href = getContextPath() + '/route' + loadDirDataPath + 'fileVersions/{' + id + '}'
}

function deleteConfirm(id) {
    layer.confirm('删除该目录将同时删除目录下的文件，确认删除吗？', {
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
    var path = getContextPath() + loadDirDataPath + "deleteFileDir"
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
                    window.location.href = getContextPath() + "/route" + loadDirDataPath + "fileDir/{"+ dirid +"}";
                }, 1000)
            }
        }
    })
}

function preview(id) {
    window.location.href = getContextPath() + loadDirDataPath + 'searchFilePreview/{' + id + '}';
}

function updateVersionFile() {
    var id = $("#versionid").val();
    window.location.href = getContextPath() + '/route' + loadDirDataPath + 'updateFiles/{' + id + '}';
}

function downloadFile(id) {
    window.location.href = getContextPath() + loadDirDataPath + 'downloadFile/{' + id + '}';
}

function updateName(id) {
    $("#fileDirid").val(id);
    $("#disbtn").click();

}
