<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<link rel="stylesheet"
	href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<link rel="stylesheet"
	href="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.css" />
</head>
<body>



	<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
	<script
		src="https://uicdn.toast.com/tui.pagination/latest/tui-pagination.js"></script>
	<!-- <script type="text/javascript" src="/togongda/resources/js/tui-grid.js"></script>
 -->
	<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>


	<script>
		window.onload = function() {

			$.ajax({
				url : "/togongda/getBoardList",
				method : "POST",
				dataType : "JSON",
				contentType : "application/json; charset=UTF-8",
				success : function(result) {
					console.dir(result);

					var grid = new tui.Grid({
						el : document.getElementById('grid'),
						scrollX : false,
						scrollY : false,
					
						columns : [ {
							header : 'board_title',
							name : 'board_title'
						}, {
							header : 'board_date',
							name : 'board_date'
						}, {
							header : 'board_no',
							name : 'board_no'
						}, {
							header : 'board_file_check',
							name : 'board_file_check'
						} ]

					}); //grid 

					grid.resetData(result);
				}// suc
			}); //ajax 

			var pagination2 = new tui.Pagination(document
					.getElementById('pagination2'), {
				totalItems : 500,
				itemsPerPage : 10,
				visiblePages : 5,
				centerAlign : true
			});

		}; //window onload
	</script>

	<!-- 토스트 그리드 -->
	<div id="grid"></div>
	
	<!-- 페이지네이션 -->
	<div id="pagination2" class="tui-pagination"></div>
</body>
</html>