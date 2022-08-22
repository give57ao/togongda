var StringBuffer = function() {
    this.buffer = new Array();
};
StringBuffer.prototype.append = function(str) {
    this.buffer[this.buffer.length] = str;
};
StringBuffer.prototype.toString = function() {
    return this.buffer.join("");
};

			
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
			
			const el = document.getElementById('chart-area');
			const options = {
	        chart: { title: '코로나 현황', width: 900, height: 400 },
	        xAxis: { pointOnColumn: false, title: { text: 'day' } },
	        yAxis: { title: '만명당 발생확률' },
	      	};
	      	
	    const data = {
        categories: [
        ],
        series: [
        ],
      };
	const chart = toastui.Chart.areaChart({ el, data, options });

			
			
			
			
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
			
						$.ajax({
				url : "/togongda/getChartCovid",
				method : "POST",
				dataType : "JSON",
				contentType : "application/json; charset=UTF-8",
				success : function(list) {
					console.log(list);
					console.log(Array.isArray(list)); //true
					
					const stdDay = list.map(row=>row.stdDay);
					console.log(stdDay);
					const gubun = list.map(row=>row.gubun);
					console.log(gubun);
					const qurRate = list.map(row=>row.qurRate);
					console.log(qurRate); //array true
					
					

					
				chart.setData({
				  categories: stdDay,
				  series: [
				    {
				      "name": gubun,
				      "data": qurRate,
				    }
				  ]
				});
				}//chart suc
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

const division = (배열, n개씩) => {
  const length = 배열.length;
  const divide = Math.floor(length / n개씩) + (Math.floor( length % n개씩 ) > 0 ? 1 : 0);
  const newArray = [];

  for (let i = 0; i <= divide; i++) {
    // 배열 0부터 n개씩 잘라 새 배열에 넣기
    newArray.push(배열.splice(0, n개씩)); 
  }

  return newArray;
}

// [[0, 1, 2], [3, 4, 5], [6, 7]]
		
		
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
                    
                    console.log(typeof(objParams));//object
                    console.log(typeof(resultArray));//object
                    console.log(typeof(dateArray)); //object
                    
                    console.log(Array.isArray(objParams)); //false
                    console.log(Array.isArray(resultArray)); //true
                    console.log(Array.isArray(dateArray)); //true
                    
                    console.log('=====================');
                    
                    console.log(typeof(JSON.stringify(dateArray))); //String
                    console.log(JSON.stringify({
					"resultArray" : JSON.stringify(resultArray), 
                        "dateArray" : JSON.stringify(dateArray)  
				}));

			$.ajax({
				url : "/togongda/searchCovidList",
				method : "POST",
				dataType : "JSON",
				data : JSON.stringify({
					"resultArray" : JSON.stringify(resultArray), 
                        "dateArray" : JSON.stringify(dateArray)  
				}),
				contentType : "application/json; charset=UTF-8",
				success : function(result) {
					grid1.resetData(result);
				}// suc
				 ,error:function(){   
				console.log('error');     
				}
			}); //ajax 
			
						$.ajax({
				url : "/togongda/searchCovidChart",
				method : "POST",
				dataType : "JSON",
				data : JSON.stringify({
					"resultArray" : JSON.stringify(resultArray), 
                        "dateArray" : JSON.stringify(dateArray)  
				}),
				contentType : "application/json; charset=UTF-8",
				success : function(list) {
					
				//var qurArr = [];
				
				/*
				qurRate 전체값 받아오기
				*/
			  	/*for(var j=0; j<resultArray.length; j++){
					for(var i=0; i<list.length;i++){
						if(resultArray[j] == list[i].gubun){ //list안의 구분값 list안의 map 의 key값이 gubun인 value값
						qurArr.push(list[i].qurRate); //rate 값 전부가 담긴 내용
						}
					}
				}*/
				
				/*
				qurRate 구분 값에 맞게 나누기
				*/
				/*console.log(qurArr);
				const newArray = division(qurArr, resultArray.length);
				console.log(newArray); //[Array[0], Array[1]]
				*/
				/*
				series 형태 (list 안의 map 형태) 로 값을 넣어줘야함
				*/
/*				var mapObject = new Map();
				const listObject = [];
				for(var a=0; a<resultArray.length;a++){
				mapObject.put('name', resultArray[a]);
				mapObject.put('data', newArray[a]);
				var strMap = JSON.stringify(mapObject);
				console.log('MAP OBJECT->'+mapObject);
				console.log('MAP OBJECT STRINGIFY->'+strMap);
				var strMap1 =strMap.substr(7,strMap.length);
				var strMap2 = strMap1.substr(0, strMap1.length-1);
				console.log('STRINGIFY SUBSTR ->'+strMap2);
				
				listObject.push(strMap2);
				}
				console.log('LIST OBJECT ->'+listObject);
				console.log('LIST OBJECT TYPE->'+typeof(listObject)); //Object
				

				
				var listval =JSON.stringify(listObject).replace('','').trim();
				var listval1 = listval.substr(2,listval.length);
				var listval2 = listval1.substr(0,listval1.length-2);
				
				console.log('LIST OBJECT SUBSTR ->'+listval2);
*/
				var data = JSON.stringify(list);
				var data1 = data.replaceAll('["', '[').replaceAll('"]',']');
				
				
				console.log(data1);
				var tag = new StringBuffer();
				tag.append(data1);
				var text = tag.toString();
				console.log(text);
				
				chart.setData({
				  categories: dateArray,
				  series: 
				  //text
				  [{"data":[42950, 43179, 43391, 43575, 43681],"name":"인천"},{"data":[44256, 44631, 44958, 45238, 45422],"name":"광주"},{"data":[45707, 45928, 46138, 46314, 46392],"name":"서울"}]
				  
				  //[{"data":[46314, 46392],"name":"서울"},{"data":[44215, 44326],"name":"경기"},{"data":[39891, 40035],"name":"전남"}]
					
					/*[
					{ data :[ 46314, 46392 ], name : '서울' },
					{ data :[ 44215, 44326 ], name : '경기'},
					{ data :[ 39891, 40035 ], name : '전남'}
					]*/
	
				    /*{
				      name: resultArray,
				      data: qurRate, //strMap2
				    }
				  */
				  
				});
				
				}//chart suc
					
			}); //ajax 		
		}
		