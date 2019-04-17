// var myChart = echarts.init(document.getElementById("main"));
// var option = {
//     xAxis: {
//         type: 'category',
//         data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
//     },
//     yAxis: {
//         type: 'value'
//     },
//     series: [{
//         data: [820, 932, 901, 934, 1290, 1330, 1320],
//         type: 'line'
//     }]
// };
// myChart.setOption(option);
$("#renderPdf").on("click",function(){
    $("#renderPdf").hide();
    html2canvas(document.body, {
        onrendered:function(canvas) {

            var contentWidth = canvas.width;
            var contentHeight = canvas.height;

            //一页pdf显示html页面生成的canvas高度;
            var pageHeight = contentWidth / 595.28 * 841.89;
            //未生成pdf的html页面高度
            var leftHeight = contentHeight;
            //pdf页面偏移
            var position = 0;
            //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
            var imgWidth = 555.28;
            var imgHeight = 555.28/contentWidth * contentHeight;

            var pageData = canvas.toDataURL('image/jpeg', 1.0);

            var pdf = new jsPDF('', 'pt', 'a4');
            //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
            //当内容未超过pdf一页显示的范围，无需分页
            if (leftHeight < pageHeight) {
                pdf.addImage(pageData, 'JPEG', 20, 0, imgWidth, imgHeight );
            } else {
                while(leftHeight > 0) {
                    pdf.addImage(pageData, 'JPEG', 20, position, imgWidth, imgHeight)
                    leftHeight -= pageHeight;
                    position -= 841.89;
                    //避免添加空白页
                    if(leftHeight > 0) {
                        pdf.addPage();
                    }
                }
            }

            pdf.save('content.pdf');
            $("#renderPdf").show();
        }
    });
});
//打印的demo
// $("#renderPdf").on("click",function(){
//     window.frames[0].location = "https://www.runoob.com/";
//     var newstr = document.getElementById("dataShow").innerHTML;
//
//     window.frames[0].location.href="http://spark3:5601/app/kibana#/dashboard/4522b9c0-42ce-11e8-9c4e-33472f948452?_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now%2Fy,mode:quick,to:now%2Fy))&amp;_a=(description:'',filters:!(),fullScreenMode:!t,options:(darkTheme:!f,hidePanelTitles:!f,useMargins:!t),panels:!((gridData:(h:3,i:'1',w:12,x:0,y:0),id:'28d6e300-443f-11e8-9c4e-33472f948452',panelIndex:'1',type:visualization,version:'6.1.2'),(gridData:(h:3,i:'2',w:12,x:0,y:3),id:'1e65cb60-4445-11e8-9c4e-33472f948452',panelIndex:'2',type:visualization,version:'6.1.2')),query:(language:lucene,query:'taskid:47'),timeRestore:!f,title:demo--45,uiState:(),viewMode:view)";
//     window.frames[0].focus();
//     window.frames[0].print();
// })
