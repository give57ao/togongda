			const grid1 = new tui.Grid({
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

					grid1.resetData(list);
				}// suc
			}); //ajax 
			

			
			var today = new Date();
			var yesterday = new Date(today.setDate(today.getDate()-1));
			
			 var datepicker = new tui.DatePicker('#wrapper', {
		            date: yesterday,
		            input: {
		                element: '#datepicker-input',
		                format: 'yyyy-MM-dd'  
		            }
		        });
			
			
		}; //window onload
		
		function search(){
			var date = document.getElementById('datepicker-input').value;
			$.ajax({
				url : "/togongda/searchCovidList?date=" + date,
				method : "POST",
				dataType : "JSON",
				data : date,
				contentType : "application/json; charset=UTF-8",
				success : function(result) {
					console.log(date);
					console.dir(result);
					
					grid1.resetData(result);
				}// suc
			}); //ajax 

			
			
		}
