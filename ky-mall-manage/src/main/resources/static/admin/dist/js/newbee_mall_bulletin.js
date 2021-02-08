$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/bulletin/list',
        datatype: "json",
        colModel: [
            {label: '公告编号', name: 'id', index: 'id', width: 60, key: true},
            {label: '公告内容', name: 'announce', index: 'announce', width: 120},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 60},
            {label: '修改时间', name: 'updateTime', index: 'updateTime', width: 60},
        ],
        height: 760,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

});

/**
 * jqGrid重新加载
 */
function reload() {
    initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 添加公告栏
 */
function addBulletin() {
    window.location.href = "/admin/bulletin/edit";
}

/**
 * 修改公告栏
 */
function editBulletin() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/bulletin/edit/" + id;
}