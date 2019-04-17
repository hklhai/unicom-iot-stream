$(function(){

    function bigData(){
        this.pageNumber = 1;
        this.totalPage = 1;
    }
    bigData.prototype = {
        constructor: bigData,
        init: function(){
            this.requestData();
            this.eventBind();
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
        requestData: function(){
            var self = this;
            var basic_url = self.getRootPath_web();
            $.ajax({
                type: 'post',
                url: basic_url+'/show/statusData',
                data: {
                    page: self.pageNumber-1,
                    size:10
                },
                dataType: "json",
                success: function(data){

                    self.totalPage = data.totalPages;

                    var tableData = data.users;
                    var htmlStr = '';
                    for(var i=0;i<tableData.length;i++){
                        htmlStr+='<tr><td>'+parseInt(i+1)
                                 +'</td><td>'+tableData[i].maoyan
                                 +'</td><td>'+tableData[i].literature
                                 +'</td><td>'+tableData[i].iqiyi
                                 +'</td><td>'+tableData[i].book
                                 +'</td><td>'+tableData[i].adddate
                                 +'</td></tr>';
                    }

                    $('.panel table tbody').html('')
                    $('.panel table tbody').html(htmlStr);
                    self.initPageDom();
                }
            });

        },
        eventBind: function(){
            var self = this;
            var basic_url = self.getRootPath_web();

            //page按钮
            $(".prePage").click(function(){
                var prepage = self.pageNumber - 1;
                if (prepage < 1) {
                    alert("已是第一页！");
                } else {
                    self.pageNumber = prepage;
                    self.requestData();
                }
            });
            $(".nextPage").click(function(){
                var nextpage = self.pageNumber + 1;
                var totalPage = self.totalPage;
                if (nextpage > totalPage) {
                    alert("已是最后一页！");
                } else {
                    self.pageNumber = nextpage;
                    self.requestData();
                }
            });
            $(".gotoPage").click(function(){
                var totalPage = self.totalPage;
                var gotoPage = $("#curPage").val();
                if (gotoPage < 1 || gotoPage > totalPage) {
                   alert("请输入正确的页数！")
                } else {
                    self.pageNumber = gotoPage;
                    self.requestData();
                }
            });
        }
    }

    new bigData().init();
});