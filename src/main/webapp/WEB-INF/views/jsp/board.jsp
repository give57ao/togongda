<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/chart/latest/toastui-chart.min.css" />
<style>
.search{
margin-bottom:250px;}
</style>
</head>

<body>
<script src="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.js"></script>
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<script src="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.js"></script>
<script src="https://uicdn.toast.com/chart/latest/toastui-chart.min.js"></script>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>

	<div id="grid"></div>
	
	<div id="grid1"></div>

    	 <div class="search">


										<input type="checkbox" name="gubun" value="경북" >
			                            <label for="html">경북</label>
			                            <input type="checkbox" name="gubun" value="충남" >
			                            <label for="충남">충남</label>
			                            <input type="checkbox" name="gubun" value="부산" >
			                            <label for="부산">부산</label>
			                            <input type="checkbox" name="gubun" value="대구" >
			                            <label for="대구">대구</label>
			                            <input type="checkbox" name="gubun" value="충북" >
			                            <label for="충북">충북</label>
			                            <input type="checkbox" name="gubun" value="전남" >
			                            <label for="전남">전남</label>
			                            <input type="checkbox" name="gubun" value="세종" >
			                            <label for="세종">세종</label>
			                            <input type="checkbox" name="gubun" value="인천" >
			                            <label for="인천">인천</label>
			                            <input type="checkbox" name="gubun" value="광주" >
			                            <label for="광주">광주</label>
			                            <input type="checkbox" name="gubun" value="서울" >
			                            <label for="서울">서울</label>
			                            <input type="checkbox" name="gubun" value="경기" >
			                            <label for="경기">경기</label>
			                            <input type="checkbox" name="gubun" value="전남" >
			                            <label for="광주">광주</label>
			                            <input type="checkbox" name="gubun" value="강원" >
			                            <label for="강원">강원</label>
			                            <input type="checkbox" name="gubun" value="검역" >
			                            <label for="검역">검역</label>
			                            <input type="checkbox" name="gubun" value="경남" >
			                            <label for="경남">경남</label>
			                            
			                            	
	<!-- DateRangePicker -->
	  <div class="tui-datepicker-input tui-datetime-input tui-has-focus">
        <input id="startpicker-input" type="text" aria-label="Date">
        <span class="tui-ico-date"></span>
        <div id="startpicker-container" style="margin-left: -1px;"></div>
    </div>
    <span>to</span>
    <div class="tui-datepicker-input tui-datetime-input tui-has-focus">
        <input id="endpicker-input" type="text" aria-label="Date">
        <span class="tui-ico-date"></span>
        <div id="endpicker-container" style="margin-left: -1px;"></div>
    </div>
	<button class="btn_search" onClick="search()">조회</button>
    
    </div>
     
     <div id="chart-area"></div>
      

     
<script type="text/javascript" src="/togongda/resources/js/board.js"></script>
<script type="text/javascript" src="/togongda/resources/js/chart.js"></script>

</body>
</html>