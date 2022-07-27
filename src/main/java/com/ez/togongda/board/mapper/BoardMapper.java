package com.ez.togongda.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ez.togongda.board.model.BoardModel;

@Mapper
public interface BoardMapper {

	public void test();

	public List<BoardModel> boardList();

}
