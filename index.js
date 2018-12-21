/**
 * 用户模块Index
 *
 * @author linguozhi@52tt.com
 * @since 2016-06-01
 * @version 1.0.0
 */

/**
 * datatable表格渲染控制
 */
var datatable = $('#datatable').DataTable(
    {
        "draw": 1,
        "serverSide": true,
        "processing": false,
        "order": [1, 'desc'],
        "lengthMenu": [[15, 30, 50, 100], [15, 30, 50, 100]],
        "ajax": {
            "url": WEBROOT + "/dailyHeap/getList.html",
            "type": 'post',
            "data": function (d) {
                return $.extend({}, d,
                    {
                        "dayTimeStart": $('#sch_day_time_start').val(),
                        "dayTimeEnd": $('#sch_day_time_end').val(),
                        "gameId": $('#gameid').val(),
                        "gameName":$("#sch_game").val,
                        "cpId": $('#sch_cp_id').val(),
                        "type": $('#sch_type').val(),

                        "ttAccount": $('#sch_tt_account').val(),
                        "userId": $('#userId').val(),
                        "registerAccountName": $('#sch_registerAccountName').val(),
                        "dayOfNotLogin": $('#sch_dayOfNotLogin').find("option:selected").val(),
                        "dayOfNotCharge": $('#sch_dayOfNotCharge').find("option:selected").val(),
                        "minFee": $('#sch_minFee').val(),
                        "maxFee": $('#sch_maxFee').val(),
                        "colName": $('tr[role="row"]').find('th').eq($('#datatable').DataTable().order()[0][0]).attr('id'),
                        "direction": $('#datatable').DataTable().order()[0][1]
                    });
            }
        },
        "bLengthChange": false,
        "columns": [
            {"data": "id", className: "center", "orderable": false, "width": "10px", class: "text-center"},
            {"data": "id", "defaultContent": "", "width": "80px", class: "text-center"},
            {"data": "dayTime", "defaultContent": ""},
            {
                "data": "lastChargeDate", "defaultContent": "", "render": function (data, type) {
                    return moment(data).format("YYYY-MM-DD HH:mm:ss");
                }
            },
            {"data": "gameName", "defaultContent": ""},
            {"data": "cpId", "defaultContent": ""},
            {"data": "bidingPhone", "defaultContent": ""},
            {"data": "registerPhone", "defaultContent": ""},
            {"data": "qq", "defaultContent": ""},
            {"data": "ttAccount", "defaultContent": ""},
            {"data": "ttUserId", "defaultContent": ""},
            {
                "data": "lastLoginTime", "defaultContent": "", "render": function (data, type) {
                    return moment(data).format("YYYY-MM-DD HH:mm:ss");
                }
            },
            {
                "data": "type", render: function (data) {
                    switch (data) {
                        case 1:
                            return '普通';
                        case 2:
                            return 'VIP';
                        case 3:
                            return 'SVIP';
                        default:
                            return 'weizhi';
                    }
                }, "defaultContent": ""
            },
            {"data": "feeTotal", "defaultContent": ""},
            {"data": "tagName", "defaultContent": ""},
            {"data": "registerAccountName", "defaultContent": ""}
            //{"data" : "id", className: "center", "orderable": false, "width": "80px", class:"text-center"},
        ],
        "preDrawCallback": function (settings) {
            popup.loading().show();
        },
        "fnCreatedRow": function (nRow, aData, iDataIndex) {
            $('td:eq(0)', nRow).html('<input role="ck" type="checkbox" name="checkbox" class="center" value="' + aData.id + '">');
            /*	            var html = '<a href="javascript:doDetail('+aData.id+ ')">查看</a>|';
                            html += '<a href="javascript:doMod('+aData.id+ ')">修改</a>|';
                            html += '<a href="javascript:doDel('+aData.id+ ')">删除</a>|';
                            $('td:eq(12)', nRow).html(html.substr(0, html.length - 1));*/
        },
        "drawCallback": function (settings) {
            popup.loading().hide();
            myDataTable.checkboxAll(); // 添加复选框事件
        },
        "initComplete": function (settings, json) {
        }
    });

/**
 * 查询
 */
doQuery = function () {
    datatable.ajax.reload();
};


/**
 * 导出Excel
 */
doExport = function () {
    var props = myDataTable.getProps();
    // 去掉第一个id
    props = props.replace(/^id,/, "");
    $("#props").val(props);

    jQuery("#converageForm").attr("action", WEBROOT + "/dailyHeap/export.html");
    jQuery("#converageForm").submit();
};

onLoadInit = function () {

    $("#sch_game").typeahead({
        source: function (query, process) {
            var parameter = {
                name: query,
                start: 0,
                length: 10
            };
            url = document.location.origin + '/game/rest/games/getList.shtml';
            $.post(WEBROOT + '/game/getListByName.html', parameter, function (result) {
                $("#gameid").val(""); // 清空
                names = [];
                map = {};
                var data = result;

                $.each(data, function (i, game) {
                    var item = '[' + game.id + ']' + game.name;
                    map[item] = game;
                    names.push(item);
                });
                process(names);
            }, 'json');
        },
        updater: function (item) {
            selectedId = map[item].id;
            $("#gameid").val(selectedId);
            return item;
        },
        matcher: function (item) {
            if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
                return true;
            }
        },
        sorter: function (items) {
            return items.sort();
        },
        highlighter: function (item) {
            var regex = new RegExp('(' + this.query + ')', 'gi');
            return item.replace(regex, "<strong>$1</strong>");
        },
    })


};

/**
 * 创建未登录天数下拉框
 */
function create_DayOfNotLogin() {
    var select = $("#sch_dayOfNotLogin");
    var i = 0;
    select.append("<option value="
        + 32 +
        ">超过31天</option>");

    for (i = 1; i < 32; i++) {
        select.append("<option value="
            + i +
            ">" +
            i + "</option>");
    }
}

/**
 * 创建未登录天数下拉框
 */
function create_DayOfNotCharge() {
    var select = $("#sch_dayOfNotCharge");
    var i = 0;
    select.append("<option value="
        + 32 +
        ">超过31天</option>");

    for (i = 1; i < 32; i++) {
        select.append("<option value="
            + i +
            ">" +
            i + "</option>");
    }
}

/**
 * 自动补全显示筛选器登记人姓名
 */
function show_registerUserName() {
    //先获取数据源
    var source = [];
    var map = {};
    $.ajax(
        {
            url: WEBROOT + "/dailyHeap/getUserList.html",
            type: "post",
            data: "",
            dataType: "json",
            success: function (data) {
                //把下属的名字push进数组
                data.forEach(function (item) {
                    source.push(item.userName);
                    map[item.userName] = item.id;
                });
                console.log(source);
            }
        }
    );
    $("#sch_registerAccountName").typeahead(
        {
            source: source,
            updater: function (item) {
                $("#userId").val(map[item]);
                return item;
            }
        }
    )
}

/**
 * 新增
 */
doAdd = function () {
    windows.go(WEBROOT + "/dailyHeap/edit.html");
};

/**
 * 修改
 */
doMod = function (id) {
    // 校验
    if (utils.empty(id)) {
        popup.tip("请选择修改对象");
        return;
    }
    if (utils.length(utils.split(id, ",")) > 1) {
        popup.tip("每次只允许操作1条数据");
        return;
    }
    // 操作
    windows.go(WEBROOT + '/dailyHeap/edit.html?id=' + id);
};

/**
 * 删除
 */
doDel = function (ids) {

    // 校验
    if (utils.emptys(ids)) {
        popup.tip("请选择删除数据");
        return;
    }

    // 操作
    popup.confirm('删除', '是否确认删除', function () {
        popup.loading('正在删除，请稍候……').show();
        $.post(WEBROOT + "/dailyHeap/del.html", {
            id: ids
        }, function (result) {
            popup.loading().hide();
            if (protocols.isSuccess(result)) {
                myDataTable.reloads();
            } else {
                popup.tip(protocols.getMessage(result));
                console.log(protocols.getMessage(result));
            }
        }, "json");
    });
};


/**
 * 详情页
 * @param id    用户ID
 */
doDetail = function (id) {

    // 校验
    if (utils.empty(id)) {
        popup.tip("请选择查看对象");
        return;
    }

    // 构建弹出层
    var userDetailModel = dialog({
        title: '用户详情',
        width: '730px',
        content: "努力加载中...",
        cancelValue: '关 闭',
        cancel: function () {
        }
    });

    // 异步获取页面内容
    $.ajax({
        type: "post",
        url: WEBROOT + '/dailyHeap/detail.html?id=' + id,
        success: function (data) {
            userDetailModel.content(data);
            userDetailModel.showModal();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            popup.tip(errorThrown);
        },
        cache: false
    });
};


/**
 * 修改
 */
doMods = function () {
    doMod(myDataTable.getCheckedIds());
};

/**
 * 删除
 */
doDels = function () {
    doDel(myDataTable.getCheckedIds());
};
/**
 * 绑定日期控件
 */
bindDateTimePicker = function () {
    $('.b-time').datetimepicker({
        lang: 'ch',
        timepicker: false,
        format: 'Ymd',
        formatDate: 'Ymd',
        allowBlank: true
    });
};

/**
 * 系统
 */
$(document).ready(function () {
    onLoadInit();
    $("#btnSearch").click(doQuery);
    $("#btnExport").click(doExport);
    $("#btnAdd").click(doAdd);
    $("#btnMod").click(doMods);
    $("#btnDel").click(doDels);
    bindDateTimePicker();
    show_registerUserName();//自动补全登记人
    create_DayOfNotLogin();//创建未登录天数下拉框
    create_DayOfNotCharge();//创建未充值天数下拉框
});

