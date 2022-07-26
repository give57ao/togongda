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
import org.springframework.http.ResponseEntity;
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
	
        for(int i=0; i<dateArray.length;i++) { //사용자가 선택한 날짜 개수 만큼 반복
        	for(int j=0; j<resultArray.length;j++) { // 사용자가 선택한 지역명 개수 만큼 반복
        		
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
	public  ResponseEntity<List<Map<String, Object>>>  searchCovidChart(@RequestBody Map<String, Object> params) throws IOException {
		
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

        

        List<Map<String, Object>> chartList =  new ArrayList<>();   //마지막에 return할 List 선언
        
        for(int i=0; i<resultArray.length;i++) {
	        Map<String, Object> chartMap1 = new HashMap<String, Object>(); 
			chartMap1.put("name", result.get(i).get("gubun").toString());

	        int suc = 0;
	        List<Integer> qurRateList = new ArrayList<Integer>(dateArray.length);  //날짜가 3일이면 3일만큼만 들어가게끔 배열 생성 구분갯수만큼 생성(초기화)하면 됌
        	for(int j=0; j<dateArray.length*resultArray.length;j++) {

        		if(result.get(j).get("gubun").equals(resultArray[i].substring(0, resultArray[i].length()-1).substring(1))) { 
        	        suc = suc+1;
        	        
        			String qurRateStr = result.get(j).get("qurRate").toString();
        			int qurRateInt = Integer.parseInt(qurRateStr);
            	    qurRateList.add(qurRateInt);		 //qurRateList 내부에는 인트값만 있음
        			
        			if(suc == dateArray.length) {        	
                	chartMap1.put("data", qurRateList);
        			}
            	    }

        	} //날짜 횟수 반복문 end

        	chartList.add(chartMap1);

        } // 구분 횟수 반복 end
        
        System.out.println(chartList);
        
		return ResponseEntity.ok().body(chartList);
	}

	

}
