package com.ez.togongda.board.controller;

import java.util.List;
import java.util.Map;

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
		public List<BoardModel> getBoardList(){
		List<BoardModel> result = 	boardService.boardList();
			
			return result;
		}
	
}
