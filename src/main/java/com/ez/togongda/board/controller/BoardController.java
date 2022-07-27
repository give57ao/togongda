package com.ez.togongda.board.controller;



import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.togongda.board.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;

	@GetMapping("/")
	public String main() {
		return "board";
	}
	
	@ResponseBody
	@PostMapping("/getBoardList")
	public ResponseEntity<HashMap<String, Object>> getBoardList(){
		
		HashMap<String, Object> result = new HashMap<>();
		result.put("list", boardService.boardList());
		
		return ResponseEntity.ok(result);
	}

}
