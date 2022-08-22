package com.ez.togongda.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.XML;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ez.togongda.board.model.BoardModel;
import com.ez.togongda.board.service.BoardService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

@RestController
public class RestBoardController {

	@Autowired
	BoardService boardService;

	@PostMapping("/getBoardList")
	public List<BoardModel> getBoardList() throws IOException, ParseException {
		List<BoardModel> result = boardService.boardList();

		return result;
	}
	
	@PostMapping("/getCovidList")
	public List<Map<String, Object>> getCovidList(HttpServletRequest req) throws IOException{
		
		//어제 날짜 구하기 
		Calendar c1 = Calendar.getInstance(); 
	    c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1 
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			
	    String yesterday = transFormat.format(c1.getTime()); // String으로 저장
	      		
		
		String xml = getCovidData(yesterday,"경기");		
		Object json = XML.toJSONObject(xml); //xml -> json
        String jsonStr = json.toString(); // json -> string
    
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(jsonStr); 
        
        
        JsonObject covidInfoResponse = (JsonObject)jsonObject.get("response");
        JsonObject covidInfoBody = (JsonObject)covidInfoResponse.get("body"); // pageNo, totalCount
        JsonObject covidInfoItems = (JsonObject)covidInfoBody.get("items");
        
        JsonArray covidInfoItem = (JsonArray)covidInfoItems.get("item");   //item은 배열 형태 이기 때문에 JsonArray 형태로 변환
               
        List<Map<String, Object>> result = null; //마지막에 return할 List 선언
		result = new ArrayList<>();  //list 초기화

        
        if (covidInfoItem != null) { 
           int len = covidInfoItem.size(); //JsonArray의 개수를 len에 대입
           for (int i=0;i<len/2;i++){ //배열 크기만큼 반복
        	  JsonObject jso = (JsonObject) covidInfoItem.get(i); //JsonArray안에 있는 값들의 순서대로 JsonObject로 변환
        	  Map<String, Object> map = getMapFromJsonObject(jso); //변환한 JsonObject는 JSONObject를 Map<String, String> 형식으로 변환처리
        	  result.add(map); //변환한 jso가 담긴 map이 list에 순서대로 담음
        } 
        }
        

        return result; // 담은 result를 ajax에게 넘겨줌 
	}
	
	/**
	 * @param JSONObject
	 * @apiNote JSONObject를 Map<String, String> 형식으로 변환처리.
	 * @return Map<String,String>
	 * **/
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonObject(JsonObject covidInfoItem){
	    Map<String, Object> map = null;
	    
	    try {
	       map = new ObjectMapper().readValue(covidInfoItem.toString(), Map.class);
	    } catch (JsonParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return map;
	}

	
	
	// 공공 데이터를 받아오는 메서드 (나중에 stdDay를 변수로 변경해서 요청 전에 값을 변경할 때 사용해야 함)
	public String getCovidData(String date, String gubun) throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1352000/ODMS_COVID_04/callCovid04Api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Joxg8eWSKuzXLDh4EahL8TaX4%2BFovgUQi2ZjrooZPMH4r9Ez8CZRajVjua3I9aBABKatg5E3lGVpkcBm5%2FoVUQ%3D%3D"); /*Service Key(현재 인코딩)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("apiType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과형식(xml/json)*/
        urlBuilder.append("&" + URLEncoder.encode("std_day","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*기준일자*/
        urlBuilder.append("&" + URLEncoder.encode("gubun","UTF-8") + "=" + URLEncoder.encode(gubun, "UTF-8")); /*시도명*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

       String result =  sb.toString();
       
       
       return result;
	}
	
	@PostMapping(value = "/searchCovidList")
	public List<Map<String, Object>>  searchCovidList(@RequestBody Map<String, Object> params) throws IOException {

		
		String resultStr = (String) params.get("resultArray");
		String resultStr1 =resultStr.substring(0, resultStr.length()-1);		
        String resultStr2 = resultStr1.substring(1);
        
        String[] resultArray = null; 
        resultArray = resultStr2.split(",");  
        
		String date = (String) params.get("dateArray");
		String date1 =date.substring(0, date.length()-1);		
        String date2 = date1.substring(1);
        
        String[] dateArray = null; 
        dateArray = date2.split(",");  		
        
        List<Map<String, Object>> result = null; //마지막에 return할 List 선언
        result = new ArrayList<>();  //list 초기화
	
        for(int i=0; i<dateArray.length;i++) {
        	for(int j=0; j<resultArray.length;j++) {
        		
		String xml = getCovidData((dateArray[i].substring(0, dateArray[i].length()-1)).substring(1),(resultArray[j].substring(0, resultArray[j].length()-1)).substring(1));
		Object json = XML.toJSONObject(xml); //xml -> json
        String jsonStr = json.toString(); // json -> string
        
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(jsonStr); 
        JsonObject covidInfoResponse = (JsonObject)jsonObject.get("response");
        JsonObject covidInfoBody = (JsonObject)covidInfoResponse.get("body"); // pageNo, totalCount
        JsonObject covidInfoItems = (JsonObject)covidInfoBody.get("items");
        
        JsonArray covidInfoItem = (JsonArray)covidInfoItems.get("item");   //item은 배열 형태 이기 때문에 JsonArray 형태로 변환
        
        int len = covidInfoItem.size(); //JsonArray의 개수를 len에 대입
        for (int k=0;k<len/2;k++){ //배열 크기만큼 반복
     	  JsonObject jso = (JsonObject) covidInfoItem.get(k); //JsonArray안에 있는 값들의 순서대로 JsonObject로 변환
     	  Map<String, Object> map = getMapFromJsonObject(jso); //변환한 JsonObject는 JSONObject를 Map<String, String> 형식으로 변환처리
     	  result.add(map); //변환한 jso가 담긴 map이 list에 순서대로 담음
        }
        }
        }
		

		return result;
	}
	
	
	@PostMapping("/getChartCovid")
	public List<Map<String, Object>> getChartCovid(HttpServletRequest req) throws IOException{
		
		//어제 날짜 구하기 
		Calendar c1 = Calendar.getInstance(); 
	    c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1 
	    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			
	    String yesterday = transFormat.format(c1.getTime()); // String으로 저장
	      		
		
		String xml = getCovidData(yesterday,"경기");		
		Object json = XML.toJSONObject(xml); //xml -> json
        String jsonStr = json.toString(); // json -> string
    
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(jsonStr); 
        
        
        JsonObject covidInfoResponse = (JsonObject)jsonObject.get("response");
        JsonObject covidInfoBody = (JsonObject)covidInfoResponse.get("body"); // pageNo, totalCount
        JsonObject covidInfoItems = (JsonObject)covidInfoBody.get("items");
        
        JsonArray covidInfoItem = (JsonArray)covidInfoItems.get("item");   //item은 배열 형태 이기 때문에 JsonArray 형태로 변환
        
        List<Map<String, Object>> result = null; //마지막에 return할 List 선언
		result = new ArrayList<>();  //list 초기화

        
        if (covidInfoItem != null) { 
            Map<String, Object> chartMap = new HashMap<String, Object>();
           int len = covidInfoItem.size(); //JsonArray의 개수를 len에 대입
           for (int i=0;i<len/2;i++){ //배열 크기만큼 반복
        	  JsonObject jso = (JsonObject) covidInfoItem.get(i); //JsonArray안에 있는 값들의 순서대로 JsonObject로 변환
        	  Map<String, Object> map = getMapFromJsonObject(jso); //변환한 JsonObject는 JSONObject를 Map<String, String> 형식으로 변환처리

              chartMap.put("gubun",map.get("gubun"));
              chartMap.put("stdDay",map.get("stdDay"));
              chartMap.put("qurRate",map.get("qurRate")); //만명당 발생확률


        	  result.add(chartMap); //변환한 jso가 담긴 map이 list에 순서대로 담음
        } 
        }
        

        return result; // 담은 result를 ajax에게 넘겨줌 
	}
	
	@PostMapping(value = "/searchCovidChart")
	public List<Map<String, Object>>  searchCovidChart(@RequestBody Map<String, Object> params) throws IOException {
		
		String resultStr = (String) params.get("resultArray");
		String resultStr1 =resultStr.substring(0, resultStr.length()-1);		
        String resultStr2 = resultStr1.substring(1);
        
        String[] resultArray = null; 
        resultArray = resultStr2.split(",");  

		String date = (String) params.get("dateArray");
		String date1 =date.substring(0, date.length()-1);		
        String date2 = date1.substring(1);
        
        String[] dateArray = null; 
        dateArray = date2.split(",");
        
        
        List<Map<String, Object>> result = null; //마지막에 return할 List 선언
        result = new ArrayList<>();  //list 초기화

        for(int i=0; i<dateArray.length;i++) {
        	for(int j=0; j<resultArray.length;j++) {
		String xml = getCovidData((dateArray[i].substring(0, dateArray[i].length()-1)).substring(1),(resultArray[j].substring(0, resultArray[j].length()-1)).substring(1));
		Object json = XML.toJSONObject(xml); //xml -> json
        String jsonStr = json.toString(); // json -> string
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(jsonStr); 
        JsonObject covidInfoResponse = (JsonObject)jsonObject.get("response");
        JsonObject covidInfoBody = (JsonObject)covidInfoResponse.get("body"); // pageNo, totalCount
        JsonObject covidInfoItems = (JsonObject)covidInfoBody.get("items");
        
        JsonArray covidInfoItem = (JsonArray)covidInfoItems.get("item");   //item은 배열 형태 이기 때문에 JsonArray 형태로 변환
        int len = covidInfoItem.size(); //JsonArray의 개수를 len에 대입
        for (int k=0;k<len/2;k++){ //배열 크기만큼 반복
     	  JsonObject jso = (JsonObject) covidInfoItem.get(k); //JsonArray안에 있는 값들의 순서대로 JsonObject로 변환
     	  Map<String, Object> map = getMapFromJsonObject(jso); //변환한 JsonObject는 JSONObject를 Map<String, String> 형식으로 변환처리
        
     	  Map<String, Object> chartMap = new HashMap<String, Object>();
     	    chartMap.put("gubun",map.get("gubun"));
            chartMap.put("stdDay",map.get("stdDay"));
            chartMap.put("qurRate",map.get("qurRate")); //만명당 발생확률

     	  result.add(chartMap); //변환한 jso가 담긴 map이 list에 순서대로 담음
        }
        }
        }
        System.out.println(result); 

        

//        for(int i=0; i<resultArray.length;i++) { // 구분갯수만큼 반복
//        	Map<String, Object> chartMap1 = new HashMap<String, Object>(); //구분 갯수만큼 map이 생성되어 list에 담겨야 하기 때문
//        	chartMap1.put("name", result.get(i).get("gubun").toString());
//
//        	
//    		String resultStr3 =resultArray[i].substring(0, resultArray[i].length()-1).substring(1);		
//            String resultStr4 = resultStr3.substring(1);
//        	
//            for(int j=0; j<dateArray.length;j++) {  //날짜의 배열 개수
//            if(result.get(j).get("gubun").toString().equals(resultStr4)) { //만약 구분값이 동일하다면 (파라미터와 result 리스트값)
//
//            	qurRateList.add(result.get(j).get("qurRate").toString()); //qurRate 값들을 리스트 하나에 담는다.
//            	chartMap1.put("data", qurRateList);
//				/*
//				 *날짜 배열의 개수만큼 qurRate가 담기는 코드
//				*/
//            	}// if end
//        	chartList.add(chartMap1);
//
//            } //날짜 만큼 반복 종료	 	
//			/*
//			 * 리스트에 맵을 담는 코드 
//			 */		
//            
//        }// 구분횟수만큼 반복 종료
        

        List<Map<String, Object>> chartList =  new ArrayList<>();   //마지막에 return할 List 선언
        
        for(int i=0; i<resultArray.length;i++) {
	        Map<String, Object> chartMap1 = new HashMap<String, Object>(); 
			chartMap1.put("name", result.get(i).get("gubun").toString());
	        List<String> qurRateList = new ArrayList<String>(); 
	        List<String> qurList = new ArrayList<String>(); 


        	for(int j=0; j<dateArray.length*resultArray.length;j++) {
        		//result는 8개인데 날짜만큼 반복하면 총 4번 반복함 그러므로 구분횟수만큼 곱해줌
        		if(result.get(j).get("gubun").equals(resultArray[i].substring(0, resultArray[i].length()-1).substring(1))) { 
        	        
            	    qurRateList.add(result.get(j).get("qurRate").toString());		 //qurRateList.size() = 8인 상황
        		
        		}
        		//8번 반복해서 값이 잔뜩 들어간 녀석에게 구분갯수에 맞게 나눈 후 담아준다.
        			
        	
        		
        	} //날짜 횟수 반복문 end
        	String data = partition(qurRateList,dateArray.length).toString();
        	String data2 = data.replace("[", "").replace("]", "");
        	qurList.add(data2);
        	chartMap1.put("data", qurList);
		      
        	chartList.add(chartMap1);

        } // 구분 횟수 반복 end
        
        System.out.println(chartList);
        
        
		/*
			결국 맵은 구분 갯수만큼 나와야 함
			리스트에 맵을 담는 행위는 총 구분횟수 반복문 내부에 들어가야 하고			
			날짜만큼 qurRate가 담겨야 하기 때문에 날짜 반복문 내부에 들어가야 함
		 */
        
        
        
		return chartList;
	}

	//
	 private static  <T> Collection<List<T>> partition(List<T> list, int size) { 
		 final AtomicInteger counter = new AtomicInteger (0);
		 return list.stream ().collect (Collectors.groupingBy (it -> counter.getAndIncrement () / size)).values ();
		 }
	
	

}
