var loadDirDataPath;
var swxtitle;
var versionid =  $("#versionid").val();

// 加载页面数据-fileid
function loadPageData2(currentPage) {
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

    var pageSize = $("#perPage").val();

    var da = JSON.stringify({pageSize: pageSize, currentPage: currentPage, id: versionid});

    var path = '';
    var dirLevel = $("#dirLevel").val();
    if(dirLevel==3){
        path = getContextPath() + loadDirDataPath + "searchThreeDirPage";
    }else{
        path = getContextPath() + loadDirDataPath + "searchHistoryVersion";
    }

    $.ajax({
        url: path,
        contentType: "application/json;charset=UTF-8",
        data: da,
        dataType: "json",
        type: "post",
        success: function (data) {

            //显示数据
            var tbody = "";
            for (var i = 0; i < data.pageData.length; i++) {
                var tr = '       <tr style="font-size: 14px;">\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].name + '</td>\n';
                var tr2 = '';
                if(data.pageData[i].description==null){
                    tr2 = '                <td style="display: table-cell;vertical-align: middle;">无</td>'+
                        '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].createtime + '</td>\n' +
                        '                <td style="display: table-cell;vertical-align: middle;"> ';
                }else{
                    tr2 =  '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].description + '</td>'+
                        '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].createtime + '</td>\n' +
                        '                <td style="display: table-cell;vertical-align: middle;"> ';
                }
                tr += tr2;

                var tr3 = '';
                if(data.pageData[i].type==1){
                    tr3 =
                        '<a href="javascript:openDir(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">打开</a>';
                }else {
                    tr3 =
                        '<a href="javascript:preview(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">预览</a>';
                }
                tr += tr3;
                var downloead = '';
                if (data.pageData[i].download == 1) {
                    downloead =
                        '<a href="javascript:downloadFile(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">下载</a>'
                }
                var tr4 =
                    '</td>\n' +
                    '            </tr>'
                tbody = tbody + tr + downloead + tr4;
            }
            $("#pageTbody").html(tbody);
            //显示左侧页面信息
            var page1 = '共<span style="color: #017ebc;"> ' + data.totalPage + ' </span> 页 <span style="color: #017ebc;"> ' + data.totalCount + ' </span>条'
            $("#pageLeft").html(page1)

            //显示右侧分页信息
            var pager = ' <li><a href="javascript:loadPageData2(' + (data.currentPage - 1) + ')">Previous</a></li>';
            if (data.currentPage - 1 <= 0) {
                pager = ' <li><a href="javascript:void(0)">Previous</a></li>';
            }

            for (var i = 0; i < data.totalPage; i++) {
                if (data.currentPage == (i + 1)) {
                    var pagerli = '<li class="active"><a href="javascript:loadPageData2(' + (i + 1) + ')">' + (i + 1) + '</a></li>';
                } else {
                    var pagerli = '<li><a href="javascript:loadPageData2(' + (i + 1) + ')">' + (i + 1) + '</a></li>';

                }
                pager += pagerli;
            }
            var pager2 = '<li><a href="javascript:loadPageData2(' + (data.currentPage + 1) + ')">Next</a></li>';
            if (data.currentPage + 1 > data.totalPage) {
                pager2 = '<li><a href="javascript:void(0))">Next</a></li>';
            }
            pager += pager2;

            $("#pageRight").html(pager);

        }
    });

}
function preview(id) {
    window.location.href = getContextPath() + loadDirDataPath + 'searchFilePreview/{' + id + '}/pdf预览';
}
function downloadFile(id) {
    window.location.href = getContextPath() + loadDirDataPath + 'downloadFile/{' + id + '}';
}

function openDir(id) {
    window.location.href = getContextPath() + '/route/front' + loadDirDataPath + 'threeDirFiles/{' + id + '}'
}