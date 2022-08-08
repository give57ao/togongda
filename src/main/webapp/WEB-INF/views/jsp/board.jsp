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
							header : '게시글 제목',
							name : 'board_title'
						}, {
							header : '작성 날짜',
							name : 'board_date'
						}, {
							header : '글 번호',
							name : 'board_no'
						}, {
							header : '첨부파일 유무',
							name : 'board_file_check'
						} ]

					}); //grid 

					grid.resetData(result);
				}// suc
			}); //ajax 
			
			//공공 데이터
			$.ajax({
				url : "/togongda/getCovidList",
				method : "POST",
				dataType : "JSON",
				contentType : "application/json; charset=UTF-8",
				success : function(result) {
					console.dir(result);

					var grid1 = new tui.Grid({
						el : document.getElementById('grid1'),
						scrollX : false,
						scrollY : false,
					
						columns : [ {
							header : '기준일자',
							name : 'stdDay'
						}, {
							header : '누적 사망자 수',
							name : 'deathCnt'
						}, {
							header : '누적 확진자 수',
							name : 'defCnt'
						}, {
							header : '누적 격리해제 수',
							name : 'isolClearCnt'
						}, {
							header : '지역발생수',
							name : 'localOccCnt'
						}, {
							header : '만명당 발생 확률',
							name : 'qurRate'
						}, {
							header : '해외 유입 수',
							name : 'overFlowCnt'
						}, {
							header : '전일 대비 확진자 증감 수',
							name : 'incDec'
						}, {
							header : '누적 격리 해제 수',
							name : 'isolIngCnt'
						}, {
							header : '시도명',
							name : 'gubun'
						}
						
						]

					}); //grid 

					grid1.resetData(result);
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
	
	<div id="grid1"></div>
	
	<!-- 페이지네이션 -->
	<div id="pagination2" class="tui-pagination"></div>
</body>
</html>