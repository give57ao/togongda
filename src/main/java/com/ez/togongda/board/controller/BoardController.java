package com.ez.togongda.board.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.ez.togongda.board.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;

	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	@GetMapping("/")
	public String main() {
		log.info("메인페이지 실행");
		boardService.test();

		return "board";
	}

}
