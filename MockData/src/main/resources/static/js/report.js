$(function(){
    var taskId = getQueryString("taskid");
    var time = getQueryString("time");
    var dataYear = parseInt(time.split("-")[0]);
    var thisYear =  new Date().getFullYear();
    var urlTime = "";
    if(dataYear - thisYear == 0){
        urlTime = "(from:now%2Fy,mode:quick,to:now%2Fy))";
    }else{
        urlTime = "(from:now-5y,mode:quick,to:now))";
    }
    var url = "";

    var category = trim(localStorage.getItem("market_category"));
    switch(category){
        //网络文学
        case "literature":
            url = "http://210.14.147.105:5601/app/kibana#/dashboard/b1721990-63b7-11e8-91a6-8dc4e1d30ce2?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:"+urlTime+"&_a=(description:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2-----%E7%BD%91%E7%BB%9C%E6%96%87%E5%AD%A6,filters:!(),fullScreenMode:!f,options:(darkTheme:!f,hidePanelTitles:!f,useMargins:!t),panels:!((gridData:(h:3,i:'1',w:12,x:0,y:0),id:eaca0a60-63b5-11e8-91a6-8dc4e1d30ce2,panelIndex:'1',type:visualization,version:'6.1.2'),(gridData:(h:3,i:'2',w:12,x:0,y:3),id:e70e97c0-63af-11e8-91a6-8dc4e1d30ce2,panelIndex:'2',type:visualization,version:'6.1.2')),query:(language:lucene,query:'taskid:"+taskId+"'),timeRestore:!f,title:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2-----%E7%BD%91%E7%BB%9C%E6%96%87%E5%AD%A6,uiState:(),viewMode:view)";
            break;
        //上映电影
        case "maoyan":
            url = "http://210.14.147.105:5601/app/kibana#/dashboard/266bbcc0-63b7-11e8-91a6-8dc4e1d30ce2?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:"+urlTime+"&_a=(description:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2------%E4%B8%8A%E6%98%A0%E7%94%B5%E5%BD%B1dashboard%E5%B1%95%E7%A4%BA,filters:!(),fullScreenMode:!f,options:(darkTheme:!f,hidePanelTitles:!f,useMargins:!t),panels:!((gridData:(h:3,i:'1',w:12,x:0,y:0),id:'97391b60-63b6-11e8-91a6-8dc4e1d30ce2',panelIndex:'1',type:visualization,version:'6.1.2'),(gridData:(h:3,i:'2',w:12,x:0,y:3),id:ec730a00-63b6-11e8-91a6-8dc4e1d30ce2,panelIndex:'2',type:visualization,version:'6.1.2')),query:(language:lucene,query:'taskid:"+taskId+"'),timeRestore:!f,title:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2------%E4%B8%8A%E6%98%A0%E7%94%B5%E5%BD%B1,uiState:(),viewMode:view)";
            break;
        //图书
        case "book":
            url = "http://210.14.147.105:5601/app/kibana#/dashboard/0463ecf0-63b8-11e8-91a6-8dc4e1d30ce2?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:"+urlTime+"&_a=(description:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2-----%E5%9B%BE%E4%B9%A6,filters:!(),fullScreenMode:!f,options:(darkTheme:!f,hidePanelTitles:!f,useMargins:!t),panels:!((gridData:(h:3,i:'1',w:12,x:0,y:0),id:'32e53970-63af-11e8-91a6-8dc4e1d30ce2',panelIndex:'1',type:visualization,version:'6.1.2'),(gridData:(h:3,i:'2',w:12,x:0,y:3),id:e70e97c0-63af-11e8-91a6-8dc4e1d30ce2,panelIndex:'2',type:visualization,version:'6.1.2')),query:(language:lucene,query:'taskid:"+taskId+"'),timeRestore:!f,title:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2-----%E5%9B%BE%E4%B9%A6,uiState:(),viewMode:view)";
            break;
        //电影，综艺，电视剧
        default:
            url = "http://210.14.147.105:5601/app/kibana#/dashboard/772bbc00-63b7-11e8-91a6-8dc4e1d30ce2?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:"+urlTime+"&_a=(description:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2-------%E7%94%B5%E5%BD%B1%EF%BC%8C%E7%BB%BC%E8%89%BA%EF%BC%8C%E7%94%B5%E8%A7%86%E5%89%A7dashboard%E5%B1%95%E7%A4%BA,filters:!(),fullScreenMode:!f,options:(darkTheme:!f,hidePanelTitles:!f,useMargins:!t),panels:!((gridData:(h:3,i:'1',w:12,x:0,y:0),id:'72e0e860-63b6-11e8-91a6-8dc4e1d30ce2',panelIndex:'1',type:visualization,version:'6.1.2'),(gridData:(h:3,i:'2',w:12,x:0,y:3),id:e70e97c0-63af-11e8-91a6-8dc4e1d30ce2,panelIndex:'2',type:visualization,version:'6.1.2')),query:(language:lucene,query:'taskid:"+taskId+"'),timeRestore:!f,title:%E4%BA%A4%E4%BA%92%E5%BC%8F%E6%9F%A5%E8%AF%A2-------%E7%94%B5%E5%BD%B1%EF%BC%8C%E7%BB%BC%E8%89%BA%EF%BC%8C%E7%94%B5%E8%A7%86%E5%89%A7,uiState:(),viewMode:view)";
            break;
    }
    $("#dataShow").attr("src",url);
    var basic_url = getRootPath_web();
    $.ajax({
        type: "post",
        url: basic_url+"/system/showData",
        data: {
            taskid: taskId
        },
        dataType: "json",
        success: function(data){
            if(data.baiduInfo&&data.baiduInfo.otherInfo){
                var info = data.baiduInfo.otherInfo.replace(/\"/g,"").replace("{","").replace("}","");
                var infoArray = info.split(",");
                var str = "";
                var tdInfo1 = [];
                var tdInfo2 = [];
                $(".contener h4").text(infoArray[0].split(":")[1]+"----分析报告");
                for(var i=0,len=parseInt(infoArray.length/2);i<len;i=i+2){
                    console.log(i);
                    tdInfo1 = infoArray[i].split(":");
                    tdInfo2 = infoArray[i+1].split(":");
                    str+="<tr><td>"+tdInfo1[0]+"</td><td>"+tdInfo1[1]+"</td><"
                        +"<td>"+tdInfo2[0]+"</td><td>"+tdInfo2[1]+"</td></tr>";
                }
                $("table tbody").html(str);
                $(".info").css("top","30px");
                var iframeTop = $(".table").height()+90+"px";
                $(".iframe-div").css("top",iframeTop);
                var tmpTop = "-100px";
                $("#dataShow").css("top",tmpTop);
            }else{
                $(".contener h4").text(data.title+"----分析报告");
            }

        }
    });

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    function getRootPath_web(){
        var curWwwPath = window.document.location.href;
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        var localhostPaht = curWwwPath.substring(0, pos);
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht + projectName);
    }
    function trim(str)
    {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
});