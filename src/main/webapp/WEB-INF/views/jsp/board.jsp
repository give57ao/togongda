<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css" />
</head>

<body>
<script src="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.js"></script>
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<script src="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.js"></script>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>

	<div id="grid"></div>
	
	<div id="grid1"></div>
	
	<!--  데이트피커 -->
	<!-- <div class="tui-datepicker-input tui-datetime-input tui-has-focus">
		<input type="text" id="datepicker-input" aria-label="Date-Time">
		<span class="tui-ico-date"></span>
	</div>
	<div id="wrapper" style="margin-top: -1px;"></div> -->
	
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
	
	 <div class="search">
     <button class="btn_search" onClick="search()">조회</button>
     </div>

     
<script type="text/javascript" src="/togongda/resources/js/board.js"></script>
</body>
</html>