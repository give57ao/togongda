package com.ez.togongda.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.XML;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ez.togongda.board.model.BoardModel;
import com.ez.togongda.board.service.BoardService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	@ResponseBody
	public List<Map<String, Object>> getCovidList(HttpServletRequest req) throws IOException{
		
		
		String xml = getCovidData();		
		Object json = XML.toJSONObject(xml); //xml -> json
        String jsonStr = json.toString(); // json -> string
    
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(jsonStr); 
        
        
        JsonObject covidInfoResponse = (JsonObject)jsonObject.get("response");
        JsonObject covidInfoBody = (JsonObject)covidInfoResponse.get("body"); // pageNo, totalCount
        JsonObject covidInfoItems = (JsonObject)covidInfoBody.get("items");
        JsonObject covidInfoItem = (JsonObject)covidInfoItems.get("item"); //여기있는 아이템을 covid model에 넣어야 함

        System.out.println("================================================================");
        System.out.println("pageNo :" + covidInfoBody.get("pageNo"));
        System.out.println("totalCount :" + covidInfoBody.get("totalCount"));
        System.out.println("================================================================");
        System.out.println("stdDay (기준일자) :" + covidInfoItem.get("stdDay") );
        System.out.println("deathCnt (누적 사망자 수):" + covidInfoItem.get("deathCnt") );
        System.out.println("defCnt (누적 확진자 수) :" + covidInfoItem.get("defCnt") );
        System.out.println("isolClearCnt (누적 격리해제 수) :" + covidInfoItem.get("isolClearCnt") );
        System.out.println("localOccCnt (지역발생수) :" + covidInfoItem.get("localOccCnt") );
        System.out.println("qurRate (만명당 발생 확률) :" + covidInfoItem.get("qurRate") );
        System.out.println("overFlowCnt (해외 유입 수) :" + covidInfoItem.get("overFlowCnt") );
        System.out.println("incDec (전일 대비 확진자 증감 수) :" + covidInfoItem.get("incDec") );
        System.out.println("isolIngCnt (누적 격리 해제 수) :" + covidInfoItem.get("isolIngCnt") );
        System.out.println("gubun (시도 명)  :" + covidInfoItem.get("gubun") );
        System.out.println("================================================================");
        
        Map<String, Object> map = getMapFromJsonObject(covidInfoItem); //json데이터 -> map으로 바꾸는 메서드 실행 후 map 변수에 저장
        List<Map<String, Object>> result = null; //리스트 선언

		result = new ArrayList<>(); 
		result.add(map); //저장된 map에 리스트를 넣고

        return result; //ajax에게 넘겨줌 
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
	public String getCovidData() throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1352000/ODMS_COVID_04/callCovid04Api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Joxg8eWSKuzXLDh4EahL8TaX4%2BFovgUQi2ZjrooZPMH4r9Ez8CZRajVjua3I9aBABKatg5E3lGVpkcBm5%2FoVUQ%3D%3D"); /*Service Key(현재 인코딩)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("500", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("apiType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과형식(xml/json)*/
        urlBuilder.append("&" + URLEncoder.encode("std_day","UTF-8") + "=" + URLEncoder.encode("2022-05-15", "UTF-8")); /*기준일자*/
        urlBuilder.append("&" + URLEncoder.encode("gubun","UTF-8") + "=" + URLEncoder.encode("경기", "UTF-8")); /*시도명*/
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
	
	
	

}
