package edu.study.service;

import edu.study.vo.MemberVO;

public interface MemberService {
	
	int insert(MemberVO vo) throws Exception;
	MemberVO login(MemberVO vo) throws Exception;
}
