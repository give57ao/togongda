package com.ez.togongda.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ez.togongda.board.mapper.BoardMapper;
import com.ez.togongda.board.model.BoardModel;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;

	@Override
	public void test() {
		boardMapper.test();
	}

	@Override
	public List<BoardModel> boardList() {
		return boardMapper.boardList();
	}


}