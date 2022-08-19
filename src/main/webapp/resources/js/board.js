			const grid1 = new tui.Grid({
				el : document.getElementById('grid1'),
				scrollX : false,
				scrollY : true,
			
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
			

			
			/*var today = new Date();
			var yesterday = new Date(today.setDate(today.getDate()-1));
			
			 var datepicker = new tui.DatePicker('#wrapper', {
		            date: yesterday,
		            input: {
		                element: '#datepicker-input',
		                format: 'yyyy-MM-dd'  
		            }
		        });*/
	var today = new Date();	
	var yesterDay = new Date(today.setDate(today.getDate() - 1));
	var picker = tui.DatePicker.createRangePicker({
		startpicker: {
			date: today,
			input: '#startpicker-input',
			container: '#startpicker-container'
		},
		endpicker: {
			date: today,
			input: '#endpicker-input',
			container: '#endpicker-container'
		},
		selectableRanges: [
			[new Date('2020-05-01'), yesterDay]
		]
	});
			/*today.getFullYear() + 1, today.getMonth(), today.getDate()*/
		}; //window onload
		
		
		function getDateRangeData(param1, param2){  //param1은 시작일, param2는 종료일이다.
	var res_day = [];
 	var ss_day = new Date(param1);
   	var ee_day = new Date(param2);    	
  		while(ss_day.getTime() <= ee_day.getTime()){
  			var _mon_ = (ss_day.getMonth()+1);
  			_mon_ = _mon_ < 10 ? '0'+_mon_ : _mon_;
  			var _day_ = ss_day.getDate();
  			_day_ = _day_ < 10 ? '0'+_day_ : _day_;
   			res_day.push(ss_day.getFullYear() + '-' + _mon_ + '-' +  _day_);
   			ss_day.setDate(ss_day.getDate() + 1);
   	}
   	return res_day;
}

function getCheckboxValue()  {
  // 선택된 목록 가져오기
  const query = 'input[name="gubun"]:checked';
  const selectedEls = document.querySelectorAll(query);
  
  // 선택된 목록에서 value 찾기
  let result = '';
  selectedEls.forEach((el) => {
    result += el.value + ' ';
  });
  resultArray = result.split(' ');
  console.log(resultArray);
}
		
		
		function search(){
			
			/*
			선택한 구분값 구하기 resultArray
			*/
			const query = 'input[name="gubun"]:checked'; //gubun name을 가진 input값의 checked값들만 가져온다
			const selectedEls = document.querySelectorAll(query);

			let result = '';
			selectedEls.forEach((el) => {
				result += el.value + ' '; //스페이스바를 줌으로서 기준점을 주는데
			});
			trimResult = result.trim();
			resultArray = trimResult.split(' '); //스페이스바를 기준으로 배열이 생성되면서 마지막 result값에 스페이스바도 나뉘어짐
			console.log(resultArray);
			
			
			/*
			선택한 기간 구하기 dateArray
			*/
			var startDate = document.getElementById('startpicker-input').value;
			var endDate = document.getElementById('endpicker-input').value;
			var dateArray = getDateRangeData(startDate, endDate);
			console.log(dateArray);
			
			  const objParams = {
                        "resultArray" : JSON.stringify(resultArray), 
                        "dateArray" : JSON.stringify(dateArray)       
                    };
                    
                    console.log(objParams);
			
			
			$.ajax({
				url : "/togongda/searchCovidList",
				method : "POST",
				dataType : "JSON",
				data : objParams,
				contentType : "application/json; charset=UTF-8",
				success : function(result) {
					console.log(objParams);
					grid1.resetData(result);
				}// suc
				 ,error:function(){        consle.log("objParams = "+ objParams); // 실패 시 처리       
				}
			}); //ajax 

			
			
		}
		
		
