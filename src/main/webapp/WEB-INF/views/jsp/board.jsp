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

	<script>
		window.onload = function() {

			/* $.ajax({
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
 */			
			//공공 데이터
			$.ajax({
				url : "/togongda/getCovidList",
				method : "POST",
				dataType : "JSON",
				contentType : "application/json; charset=UTF-8",
				success : function(list) {
					console.dir(list);

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

					grid1.resetData(list);
				}// suc
			}); //ajax 
			

			
			
			

			/* var pagination2 = new tui.Pagination(document
					.getElementById('pagination2'), {
				totalItems : 500,
				itemsPerPage : 10,
				visiblePages : 5,
				centerAlign : true
			}); */
			
			 var datepicker = new tui.DatePicker('#wrapper', {
		            date: new Date(),
		            input: {
		                element: '#datepicker-input',
		                format: 'yyyy-MM-dd'  
		            }
		        });
			
			
		}; //window onload
		
		function searchBoard(){
			
		}
	</script>

	<!-- 토스트 그리드 -->
	<div id="grid"></div>
	
	<div id="grid1"></div>
	
	<!--  데이트피커 -->
	<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
		<input type="text" id="datepicker-input" aria-label="Date-Time">
		<span class="tui-ico-date"></span>
	</div>
	<div id="wrapper" style="margin-top: -1px;"></div>
	
	<div id="search">
						<select class="dropdown" id="selectBoxTest">
                        	<option value="경기" id="gu" selected>경기</option>
                            <option value="서울" id="so">서울</option>
                            <option value="부산" id="bu">부산</option>
                            <option value="대구" id="gu">대구</option>
                            <option value="대전" id="da">대전</option>
                            <option value="제주" id="ja">제주</option>
                        </select>
	</div>
	
	 <div class="search">
     <button class="btn_search" onClick="searchBoard()">검색</button>
     </div>
	<!-- 페이지네이션 -->
	<!-- <div id="pagination2" class="tui-pagination"></div> -->


</body>
</html>