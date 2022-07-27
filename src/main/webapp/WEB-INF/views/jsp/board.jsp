<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
</head>
<body>



<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>

<script>
window.onload = function(){
	
    $.ajax({
        url : "/togongda/getBoardList",
        method :"POST",
        dataType : "JSON",
        success : function(result){
        	console.dir(result);
            grid.resetData(result);
        } 
    }); 
    
    const grid = new tui.Grid({
      el: document.getElementById('grid'),
      scrollX: false,
      scrollY: false,
      height:500,
      width:500,
      columns: [
    	    {
    	      header: 'board_title',
    	      name: 'board_title'
    	    },
    	    {
    	      header: 'board_date',
    	      name: 'board_date'
    	    },
    	    {
    	      header: 'board_no',
    	      name: 'board_no'
    	    },
    	    {
    	      header: 'board_file_check',
    	      name: 'board_file_check'
    	    }
    	  ]
   
    
    
    });
    

 
    
}

 </script>
 
 <!-- 토스트 그리드 -->
<div id="grid"></div>
el : 그리드가 생성되는 컨테이너 엘리먼트 지정
columns : 그리드 컬럼 정보 설정
data : 초기 생성 데이터 설정
</body>
</html>