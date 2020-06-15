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
                    '                                </ul>\n' +
                    '                                <div class="clearfix"></div>\n' +
                    '                            </div>\n' +
                    '                            <a href="javascript:searchNo_2Details('+data[i].id+')" style="float: right;padding-right: 20px">查看详情>></a>\n' +
                    '                        </div>\n' +
                    '                    </div>';

                content += content1;

            }

            $("#dirContent").html(content);
            $(".title_left").html(swxtitle);

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
            //显示数据
            var tbody = "";
            for (var i = 0; i < data.pageData.length; i++) {
                var tr = '       <tr style="font-size: 14px;">\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].name + '</td>\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].createtime + '</td>\n' +
                    '                <td style="display: table-cell;vertical-align: middle;">' + data.pageData[i].versionnum + '</td>\n' +
                    '                <td style="display: table-cell;vertical-align: middle;"> ' +
                    '<a href="javascript:searchNo_3Details(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">查看</a>' +
                    '</td>\n' +
                    '            </tr>'
                tbody = tbody + tr ;
            }
            $("#pageTbody").html(tbody);
            //显示左侧页面信息
            var page1 = '共<span style="color: #017ebc;"> ' + data.totalPage + ' </span> 页 <span style="color: #017ebc;"> ' + data.totalCount + ' </span>条'
            $("#pageLeft").html(page1)

            //显示右侧分页信息
            var pager = ' <li><a href="javascript:loadNo_2DirPage(' + (data.currentPage - 1) + ')">Previous</a></li>';
            if (data.currentPage - 1 <= 0) {
                pager = ' <li><a href="javascript:void(0)">Previous</a></li>';
            }

            for (var i = 0; i < data.totalPage; i++) {
                if (data.currentPage == (i + 1)) {
                    var pagerli = '<li class="active"><a href="javascript:loadNo_2DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';
                } else {
                    var pagerli = '<li><a href="javascript:loadNo_2DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';

                }
                pager += pagerli;
            }
            var pager2 = '<li><a href="javascript:loadNo_2DirPage(' + (data.currentPage + 1) + ')">Next</a></li>';
            if (data.currentPage + 1 > data.totalPage) {
                pager2 = '<li><a href="javascript:void(0))">Next</a></li>';
            }
            pager += pager2;

            $("#pageRight").html(pager);
            $(".title_left").html(swxtitle);
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
                        '<a href="javascript:searchNo_4Details(' + data.pageData[i].id + ')" class="btn btn-info btn-sm" role="button" style="width: 45px;height: 28px;font-size: 12px">打开</a>';
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
            var pager = ' <li><a href="javascript:loadNo_3DirPage(' + (data.currentPage - 1) + ')">Previous</a></li>';
            if (data.currentPage - 1 <= 0) {
                pager = ' <li><a href="javascript:void(0)">Previous</a></li>';
            }

            for (var i = 0; i < data.totalPage; i++) {
                if (data.currentPage == (i + 1)) {
                    var pagerli = '<li class="active"><a href="javascript:loadNo_3DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';
                } else {
                    var pagerli = '<li><a href="javascript:loadNo_3DirPage(' + (i + 1) + ')">' + (i + 1) + '</a></li>';

                }
                pager += pagerli;
            }
            var pager2 = '<li><a href="javascript:loadNo_3DirPage(' + (data.currentPage + 1) + ')">Next</a></li>';
            if (data.currentPage + 1 > data.totalPage) {
                pager2 = '<li><a href="javascript:void(0))">Next</a></li>';
            }
            pager += pager2;

            $("#pageRight").html(pager);

            if($("#dirLevel").val()==3){
                $("#accordion2").css("display","none");
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


//删除目录确认

//删除

//更新确认

//更新

//排序

//添加

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
        '<script src="'+getContextPath()+'/js/frontCommons.js"></script>'
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
        '                            <!--上传文件-->\n' +
        '                            <!--添加文件夹-->\n' +
        '                            <!--更新文件弹窗-->\n' +
        '                            <!--更新文件夹弹窗-->\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';



    $("#right_col").html(content2);

    var js =
        '<script src="'+getContextPath()+'/js/frontCommons.js"></script>';
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
        '                    <div class="x_panel">\n' +
        '                        <div class="x_title">\n' +
        '                            <span style="font-size: 18px;color: #668995;padding-bottom: 0;width: 100px">版本分类\n' +
        '                                <span style="font-size: 10px;color: #668995;margin-top: 40px;width: 100px">Document classification</span>\n' +
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
        '                            <!--上传文件-->\n' +
        '                            <!--添加文件夹-->\n' +
        '                            <!--更新文件夹弹窗-->\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';


    $("#right_col").html(content2);

    var js =
        '<script src="'+getContextPath()+'/js/frontCommons.js"></script>'
    $("#reloadJs").html(js);

    bsCustomFileInput.init();

    loadNo_3DirPage();


}

//预览
function preview(id) {
    window.open(getContextPath() + '/searchFilePreview/{' + id + '}/pdf');
}

//下载
function downloadFile(id) {
    window.location.href = getContextPath() + '/downloadFile/{' + id + '}';
}