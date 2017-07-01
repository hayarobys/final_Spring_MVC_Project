<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <script> document.documentElement.className = 'js'; </script> 
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <title> 게시물 현황 </title>
   </head>
<body>

   <%     
      int bbs = (Integer)request.getAttribute("bbscount");
      int board = (Integer)request.getAttribute("boardcount");
      int gongji = (Integer)request.getAttribute("gongjicount");
      int guest = (Integer)request.getAttribute("guestcount");
    
   %>
  
   <fieldset style="border: 1px solid #1F497D; padding: 40px 0px 0 20px;">
    	<legend style="color: #666; padding: 5px 10px ; font-size: 24px;
    border-radius: 5px; margin-left: 20px;">영역별 게시물 수 현황</legend>
	<table width="60%" style="margin-left:30px;">
	  <tr>
       	  <td>자료실 <b>${bbscount}</b> 개</td>
          <td>게시판 <b>${boardcount}</b> 개</td>
          <td>공지사항 <b>${gongjicount}</b> 개</td>
          <td>방명록 <b>${guestcount}</b> 개</td> 
       </tr>
       
	<tr>
	  <td colspan="4" align="left" style="padding-top: 20px;">
	    <input type="button" value="엑셀 저장하기" 
	              onclick="location.href='calcExcel.do'">
	  </td>
	</tr>
  </table>

	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
   
    <script> // PieChart   
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Task', 'Hours per Day'],
          ['자료실',   <%=bbs%>],
          ['게시판',  <%=board%>],
          ['공지사항',  <%=gongji%>],
          ['방명록 ',     <%=guest%>]
        ]);

        var options = {
                title: '영역별 게시물 업로드 수',
                is3D: true,               
              };

        // 차트 유형 : PieChart(),BarChart(),ColumnChart
        var chart = new google.visualization.PieChart(document.getElementById('chart_3d'));
        chart.draw(data, options);

      }
    </script>
    
    <%-- <script>  // ColumnChart(단일 항목 색상 적용)
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['유형', '게시물 수', { role: 'style' }],
          ['자료실',   <%=bbs%>, 'stroke-color: #76A7FA;stroke-width: 4; file-color : #76A7FA'],
          ['게시판',  <%=board%>, 'stroke-color: #871B47; stroke-width: 4; fill-color: #BC5679'],
          ['공지사항',  <%=gongji%>, 'stroke-color: #703593; stroke-width: 4; fill-color: #C5A5CF'],
          ['방명록 ',     <%=guest%>, 'stroke-color: #FF5E00;stroke-width: 4; fill-color : orange']
        ]);

        var options = {
                title: '월별 게시물 업로드 수 비교',
                is3D: true,               
                hAxis: {title: '게시물 유형', titleTextStyle: {color: 'blue'}}
              };

        // 차트 유형 : PieChart(),BarChart(),ColumnChart
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_3d'));
        chart.draw(data, options);

      }
    </script> --%>
  
    <%-- <script>  // ColumnChart(비교 차트)
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Task', '이전월', '현재월'],
          ['자료실',   <%=bbs%>,<%=2%> ],
          ['게시판',  <%=board%>,<%=3%>],
          ['공지사항',  <%=gongji%>,<%=2%>],
          ['방명록 ',     <%=guest%>,<%=6%>]
        ]);

        var options = {
                title: '월별 게시물 업로드 수 비교',
                is3D: true,               
                hAxis: {title: 'Month', titleTextStyle: {color: 'blue'}}
              };

        // 차트 유형 : PieChart(),BarChart(),ColumnChart
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_3d'));
        chart.draw(data, options);

      }
    </script>
   --%>
     <!-- 차트 크기 지정 -->
     <div id="chart_3d" style="width:900px; height: 600px;"></div>
    
 </fieldset>
	 