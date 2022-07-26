<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
</head>
<body>
<!-- 토스트 그리드 -->
<div id="grid"></div>
el : 그리드가 생성되는 컨테이너 엘리먼트 지정
columns : 그리드 컬럼 정보 설정
data : 초기 생성 데이터 설정


<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>

<script>
window.onload = function(){
	
/*     $.ajax({
        url : "/togongda/getBoardList",
        method :"GET",
        dataType : "JSON",
        success : function(result){
            grid.resetData(result);
        } 
    }); */
    
    
    const grid = new tui.Grid({
      el: document.getElementById('grid'),
      scrollX: false,
      scrollY: false,
      columns: [
    	    {
    	      header: 'Name',
    	      name: 'name'
    	    },
    	    {
    	      header: 'Artist',
    	      name: 'artist'
    	    },
    	    {
    	      header: 'Price',
    	      name: 'price'
    	    },
    	    {
    	      header: 'Genre',
    	      name: 'genre'
    	    }
    	  ],
    	  data: [
    		    {
    		      name: 'Beautiful Lies',
    		      artist: 'Birdy',
    		      price: 10000,
    		      genre: 'Pop'
    		    }
    		  ]
    });
    /* 데이터를 추가 시 rowData를 선언하고 */
    const rowData = [
    	  {
    	    name: 'X',
    	    artist: 'Ed Sheeran',
    	    price: 20000,
    	    genre: 'Pop'
    	  },
    	  {
    	    name: 'A Head Full Of Dreams',
    	    artist: 'Coldplay',
    	    price: 25000,
    	    genre: 'Rock'
    	  }
    	];
/* rowData를 그리드에 추가시킬 수 있음  */
    	rowData.forEach(row => {
    	  grid.appendRow(row);
    	});
    	
    	};
</script>
</body>
</html>