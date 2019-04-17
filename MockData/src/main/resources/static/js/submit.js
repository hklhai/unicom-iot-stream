$(function(){

    function submit(){
        this.pageNumber = 1;
        this.totalPage = 1;
    }
    submit.prototype = {
        constructor: submit,
        init: function(){
            this.initTask();
            this.eventBind();
            this.tbodyEvent();
        },
        initPageDom: function(){
            $('.pageNo').text('');
            $('.totalPage').text('');
            $('#curPage').text('');
            $('.pageNo').text(this.pageNumber);
            $('.totalPage').text(this.totalPage);
        },
        getRootPath_web:function(){
            var curWwwPath = window.document.location.href;
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            var localhostPaht = curWwwPath.substring(0, pos);
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return (localhostPaht + projectName);
        },
        initTask: function(){
            var self = this;
            var basic_url = self.getRootPath_web();
            $.ajax({
                type: 'post',
                url: basic_url+'/system/taskData',
                data: {
                    page: self.pageNumber-1,
                    size:10
                },
                dataType: "json",
                success: function(data){
                    //String UNSUBMIT = "unsubmit";  // 未提交
                    // String UNDO = "undo";          // 未执行
                    // String RUNNING = "running";    // 运行中
                    // String FINISH = "finish";      // 已完成
                    // String TASKFAIL = "taskfail";  // 任务失败
                    // String NODATA = "nodata";      // 无数据
                    self.clearInput();
                    var lists = data.users;
                    var str = "";
                    self.totalPage = data.totalPages;
                    for(var i=0;i<lists.length;i++){
                        str += '<tr><td>'+lists[i].taskName
                            +'</td><td>'+lists[i].createTime;
                        switch(lists[i].taskStatus){
                            case 'unsubmit':
                                str+='</td><td>未提交';
                                str+='</td><td style="display: none;">'+lists[i].taskid;
                                str+='</td><td style="display: none;">'+lists[i].taskParam.split(",")[2].split(":")[1].replace(/\"/g," ");
                                str+='</td><td>提交</td></tr>';
                                break;
                            case 'undo':
                                str+='</td><td>未执行';
                                str+='</td><td style="display: none;">'+lists[i].taskid;
                                str+='</td><td style="display: none;">'+lists[i].taskParam.split(",")[2].split(":")[1].replace(/\"/g," ");
                                str+='</td><td></td></tr>';
                                break;
                            case 'running':
                                str+='</td><td>运行中';
                                str+='</td><td style="display: none;">'+lists[i].taskid;
                                str+='</td><td style="display: none;">'+lists[i].taskParam.split(",")[2].split(":")[1].replace(/\"/g," ");
                                str+='</td><td></td></tr>';
                                break;
                            case 'finish':
                                str+='</td><td>已完成';
                                str+='</td><td style="display: none;">'+lists[i].taskid;
                                str+='</td><td style="display: none;">'+lists[i].taskParam.split(",")[2].split(":")[1].replace(/\"/g," ");
                                str+='</td><td>生成报告</td></tr>';
                                break;
                            case 'taskfail':
                                str+='</td><td>任务失败';
                                str+='</td><td style="display: none;">'+lists[i].taskid;
                                str+='</td><td style="display: none;">'+lists[i].taskParam.split(",")[2].split(":")[1].replace(/\"/g," ");
                                str+='</td><td></td></tr>';
                                break;
                            default:
                                str+='</td><td>无数据';
                                str+='</td><td style="display: none;">'+lists[i].taskid;
                                str+='</td><td style="display: none;">'+lists[i].taskParam.split(",")[2].split(":")[1].replace(/\"/g," ");
                                str+='</td><td></td></tr>';
                                break;
                        }
                    }
                    $('.panel table tbody').html('')
                    $('.panel table tbody').html(str);
                    self.initPageDom();
                }
            });
        },
        clearInput: function(){
            $('.item input[type="text"]').text('');
            $('#film').prop('checked',true);
        },
        tbodyEvent: function(){
            var self = this;
            var basic_url = self.getRootPath_web();
            $('table.table tbody').on('click','tr>td:last-child',function(event){
                var $lastTd = $(this);
                var trIndex = $lastTd.parents("tr").index();
                var $status = $(this).siblings().eq(-3);
                var taskId = $(this).siblings().eq(-2).text();
                var category = $(this).siblings().eq(-1).text();
                var time = $(this).siblings().eq(1).text();
                localStorage.setItem("market_category", category);
                if($lastTd.text()=='提交'){
                    $.ajax({
                        type: 'post',
                        url: basic_url+'/system/submitTask',
                        data: {
                            taskid: taskId,
                        },
                        dataType: 'json',
                        success: function(data){
                            if(data.code==1){
                                $lastTd.text('已提交');
                                $status.text('未执行');
                                clearInterval(timer);
                                var timer = setInterval(function(){
                                    self.initTask();
                                    var txt = $("table.table tbody tr").eq(trIndex).find("td").eq(2).text();
                                    if(txt=="已完成"||txt=="任务失败"||txt=="无数据"){
                                        clearInterval(timer);
                                    }
                                },5000);
                            }
                            return false;
                        }
                    });

                }else if($lastTd.text()=='生成报告'){
                    window.location = basic_url+'/report?taskid='+taskId+'&time='+time;
                }else{
                    return false;
                }
            });
        },
        eventBind: function(){
            var self = this;
            var basic_url = self.getRootPath_web();
            $('.submit').on('click',function(){
                var startTime = $('#datetimepicker7').val();
                var endTime = $('#datetimepicker8').val();
                var category = $('input[type="radio"]:checked').val();
                var names = $('.names').val();
                if(category&&category!==''&&startTime&&startTime!==''&&endTime&&endTime!==''&&names&&names!==''){
                    var str = {
                        "startTime":startTime.replace(/\//g,'-')+':00',
                        "endTime":endTime.replace(/\//g,'-')+':00',
                        "category":category,
                        "title":names
                    };
                    $.ajax({
                        type: 'post',
                        url: basic_url+'/system/addTask',
                        data: {
                            taskParam:JSON.stringify(str)
                        },
                        dataType: 'json',
                        success: function(data){

                            if(data.code==1){
                                alert('添加成功！');

                                //清空查询数据
                                $(".queryBox .category #film").prop("checked", true);
                                $(".queryBox .description .names").val("");
                                $("#datetimepicker7").val("");
                                $("#datetimepicker8").val("");
                                self.initTask();
                                self.tbodyEvent();
                            }else{
                                alert('添加失败！');
                            }
                        }
                    });
                }else if(!category||category==''){
                    alert('类别不能为空！');
                }else if(!startTime||startTime==''||!endTime||endTime==''){
                    alert('查询时间不能为空！');
                }else{
                    alert('名称不能为空！');
                }
            });

            //page按钮
            $(".prePage").click(function(){
                var prepage = self.pageNumber - 1;
                if (prepage < 1) {
                    alert("已是第一页！");
                } else {
                    self.pageNumber = prepage;
                    self.initTask();
                }
            });
            $(".nextPage").click(function(){
                var nextpage = self.pageNumber + 1;
                var totalPage = self.totalPage;
                if (nextpage > totalPage) {
                    alert("已是最后一页！");
                } else {
                    self.pageNumber = nextpage;
                    self.initTask();
                }
            });
            $(".gotoPage").click(function(){
                var totalPage = self.totalPage;
                var gotoPage = $("#curPage").val();
                if (gotoPage < 1 || gotoPage > totalPage) {
                   alert("请输入正确的页数！")
                } else {
                    self.pageNumber = gotoPage;
                    self.initTask();
                }
            });
        }
    }

    new submit().init();
});