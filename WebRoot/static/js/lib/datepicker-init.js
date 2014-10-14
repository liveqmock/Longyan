/**
 * 初始化日期控件，该文件必须在jquery-ui系列JS文件引用之后引用
 */
require.config({
    paths:{
        'jquery':"jquery-1.9.1",
        'jquery-ui':  'lib/jquery-ui.min'
    },
    shim: {
        'jquery':{ exports: "$"},
        'jquery-ui': {
            'deps': ['jquery'],
            'exports': 'jQuery.fn.jquery_ui'
        }
    }
});
define(['jquery','jquery-ui'],function($,jquery_ui){
    /**
     * 这个样式，初始化会带上设置年和月的下拉框
     * @type {Boolean}
     */
    $(".date-picker").datepicker();
    console.log($(".date-picker"))
    $.datepicker.setDefaults({
        dateFormat: "yy-mm-dd",
        dayNames : ["日","一","二","三","四","五","六"],
        dayNamesMin : ["日","一","二","三","四","五","六"],
        monthNames: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
        monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
    });
  // $(".dp").datepicker({ changeMonth: true, changeYear: true, showMonthAfterYear: true, yearRange: "2010:2014" });
});

