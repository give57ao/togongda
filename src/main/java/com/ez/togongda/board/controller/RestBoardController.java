package com.ez.togongda.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.json.XML;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ez.togongda.board.model.BoardModel;
import com.ez.togongda.board.service.BoardService;

@RestController
public class RestBoardController {

	@Autowired
	BoardService boardService;

	@PostMapping("/getBoardList")
	public List<BoardModel> getBoardList() throws IOException, ParseException {
		List<BoardModel> result = boardService.boardList();
		
		String xml = getCovidData();

		System.out.println("xml :"+ xml);
		/*
		 * JSONParser parser = new JSONParser(); 
		 * JSONObject jsonObject = (JSONObject)  parser.parse(strJson); // JSON 객체의 값 읽어서 출력하기
		 * System.out.println(jsonObject.get("isolClearCnt")); // apple
		 * System.out.println(jsonObject.get("gubun")); // 1
		 * System.out.println(jsonObject.get("deathCnt")); // 1000
		 */
		
		Object json = XML.toJSONObject(xml); //xml -> json
        String jsonStr = json.toString();
        System.out.println("xml -> json :"+ jsonStr);
        
		
		return result;
	}
	
	
	
	// 공공 데이터를 받아오는 메서드
	public String getCovidData() throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1352000/ODMS_COVID_04/callCovid04Api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Joxg8eWSKuzXLDh4EahL8TaX4%2BFovgUQi2ZjrooZPMH4r9Ez8CZRajVjua3I9aBABKatg5E3lGVpkcBm5%2FoVUQ%3D%3D"); /*Service Key(현재 인코딩)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("500", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("apiType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*결과형식(xml/json)*/
        urlBuilder.append("&" + URLEncoder.encode("std_day","UTF-8") + "=" + URLEncoder.encode("2021-12-15", "UTF-8")); /*기준일자*/
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

        return sb.toString();
	}
	
	
	

}
