package edu.study.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.study.service.BasketService;
import edu.study.service.HomeService;
import edu.study.service.MemberService;
import edu.study.service.MypageService;
import edu.study.vo.BasketVO;
import edu.study.vo.Community_BoardVO;
import edu.study.vo.HomeSearchVO;
import edu.study.vo.MemberVO;
import edu.study.vo.OrderListVO;
import edu.study.vo.SearchVO;

/**
 * Handles requests for the application home page.
 */  
@RequestMapping(value="/mypage")
@Controller
public class MypageController {
	
	@Autowired 
	private MypageService mypageService;
	@Autowired
	private HomeService homeService;
	@Autowired
	private BasketService basketService;
	@Autowired
	private MemberService memberService;
	 
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mypage.do", method = RequestMethod.GET)
	public String mypage(Locale locale, Model model,OrderListVO vo,HttpServletRequest req,Community_BoardVO list) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	      
	      if(loginUser == null) {
	         return "redirect:/login/login.do";
	      }else {
	    	  
	    	vo.setMidx(loginUser.getMidx());
		    vo.setProgress("결제완료");
			int count = mypageService.count(vo);
			model.addAttribute("count1", count);  	    	    
	    	  
	    	vo.setMidx(loginUser.getMidx());
	    	vo.setProgress("배송중");
	    	count = mypageService.count(vo);
			model.addAttribute("count3", count);  
			
			vo.setMidx(loginUser.getMidx());
	    	vo.setProgress("배송완료");
			count = mypageService.count(vo);
			model.addAttribute("count4", count);  
	    	  
	    	MemberVO result = mypageService.detail(loginUser.getMidx());
	  		model.addAttribute("vo", result);
	  		
	  		int Midx = loginUser.getMidx();
			list.setMidx(Midx);
	  		
	  		List<Community_BoardVO> Storylist = mypageService.viewStory(list);
	  		model.addAttribute("Storylist", Storylist);
	  		
	        return "mypage/mypage";
	      }  
	}
	
	@RequestMapping(value = "/member_modify.do", method = RequestMethod.GET)
	public String member_modify(Locale locale, Model model, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		MemberVO result = mypageService.detail(loginUser.getMidx());
		model.addAttribute("vo", result);
		
		return "mypage/member_modify"; 
	}
	
	@RequestMapping(value = "/member_modify.do", method = RequestMethod.POST)
	public String member_modifyOk(Locale locale, Model model,MemberVO vo, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	       
	    int result = mypageService.update(vo);
	    loginUser = memberService.refreshMember(vo);
	      
	    session.setAttribute("loginUser", loginUser);
	
		return "redirect: /controller/mypage/mypage.do";
	}
	
	@RequestMapping(value = "/member_delete.do", method = RequestMethod.GET)
	public String member_delete(Locale locale, Model model, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		List<HomeSearchVO> searchList = homeService.listSearchList();
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
			
	    int midx = loginUser.getMidx();
		MemberVO result = mypageService.detail(midx);
		
		
		return "mypage/member_delete"; 
	}
	
	@RequestMapping(value = "/member_delete.do", method = RequestMethod.POST)
	public String member_deleteOk(Locale locale, Model model, MemberVO vo, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		List<HomeSearchVO> searchList = homeService.listSearchList();
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	    int Midx = loginUser.getMidx();
	    
		vo.setMidx(Midx);
		int result = mypageService.delete(vo);	
		
		session.setAttribute("loginUser", null);
		return "home";
	}
	
	
	@RequestMapping(value = "/order_list.do", method = RequestMethod.GET)
	public String order_list(Locale locale, Model model,OrderListVO vo, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		
	   if(loginUser == null) {
		        return "redirect:/login/login.do";
		   }else {
		
			    List<OrderListVO> orderList2 = mypageService.orderList2(loginUser);
				model.addAttribute("orderList2", orderList2);
				
				vo.setMidx(loginUser.getMidx());
			    vo.setProgress("결제완료");
				int count = mypageService.count(vo);
				model.addAttribute("count1", count);
				
				vo.setMidx(loginUser.getMidx());
				vo.setProgress("배송준비중");
				count = mypageService.count(vo);
				model.addAttribute("count2", count);
				
				vo.setMidx(loginUser.getMidx());
				vo.setProgress("배송중");
				count = mypageService.count(vo);
				model.addAttribute("count3", count);
				
				vo.setMidx(loginUser.getMidx());
				vo.setProgress("배송완료"); 
				count = mypageService.count(vo);
				model.addAttribute("count4", count);
				
		  	
		         return "mypage/order_list";
		   }  
		
	}
	
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	
	public String state(Locale locale, Model model,OrderListVO vo, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		
	   if(loginUser == null) {
		        return "redirect:/login/login.do";
		   }else {
			    String progress = req.getParameter("progress");
				
				vo.setProgress(progress);
				vo.setMidx(loginUser.getMidx());
			   
			   	List<OrderListVO> delState = mypageService.delState(vo);
				model.addAttribute("orderList2", delState);
				
				
			    vo.setProgress("결제완료");
				int count = mypageService.count(vo);
				model.addAttribute("count1", count);
				
				vo.setMidx(loginUser.getMidx());
				vo.setProgress("배송준비중");
				count = mypageService.count(vo);
				model.addAttribute("count2", count);
				
				vo.setMidx(loginUser.getMidx());
				vo.setProgress("배송중");
				count = mypageService.count(vo);
				model.addAttribute("count3", count);
				
				vo.setMidx(loginUser.getMidx());
				vo.setProgress("배송완료"); 
				count = mypageService.count(vo);
				model.addAttribute("count4", count);
				
		  	
		        return "mypage/order_list";
		       
		   }  
		
		
	}
	
	
	@RequestMapping(value = "/payment.do", method = RequestMethod.GET)
	public String payment(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/payment";
	}
	
	@RequestMapping(value = "/order_success.do", method = RequestMethod.GET)
	public String order_success(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/order_success";
	}
	
	@RequestMapping(value = "/order_fail.do", method = RequestMethod.GET)
	public String order_fail(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/order_fail";
	}
	
	@RequestMapping(value = "/basket.do", method = RequestMethod.GET)
	public String basket(Locale locale, Model model, SearchVO vo, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login/login.do";
		}else {
			List<BasketVO> basketListAll = basketService.listBasket(loginUser);
			
			model.addAttribute("basketListAll", basketListAll);
			
			return "mypage/basket";
		}
			
		
	}
	
	
	@RequestMapping(value = "/password_check.do", method = RequestMethod.GET)
	public String password_check(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/password_check";
	}
	

	@RequestMapping(value = "/password_check.do", method = RequestMethod.POST)
	public String password_checkOK(Locale locale, Model model,String pass, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	    model.addAttribute("loginUser", loginUser);
	    
		int Midx = loginUser.getMidx();
		
		MemberVO vo = new MemberVO();
		vo.setMidx(Midx);
		vo.setPass(pass);
		
		MemberVO checkPass = mypageService.checkPwd(vo);
	    
		    if(checkPass == null) {
		    	 return "mypage/password_check_fail";
		    }else {
		    	return "mypage/password_modify";
		    }
	}
	
	
	@RequestMapping(value = "/password_modify.do", method = RequestMethod.GET)
	public String password_modify(Locale locale, Model model, SearchVO vo,  HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	    
	    model.addAttribute("loginUser", loginUser);
			
		return "mypage/password_modify";
	}
	
	@RequestMapping(value = "/password_modify.do", method = RequestMethod.POST)
	public String password_modifyOK(Locale locale, Model model, MemberVO vo, HttpServletRequest req) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		List<HomeSearchVO> searchList = homeService.listSearchList();
		model.addAttribute("searchList", searchList);
		
		HttpSession session = req.getSession(); 
	    MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
	    
	    vo.getMidx();
	  	int result = mypageService.updatePwd(vo);
			
		session.setAttribute("loginUser", null);
		return "redirect: /controller/login/login.do";
	}
	
	
	@RequestMapping(value = "/review_list.do", method = RequestMethod.GET)
	public String review_list(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/review_list";
	}
		
	
	
	@RequestMapping(value = "/review_insert.do", method = RequestMethod.GET)
	public String review_insert(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/review_insert";
	}
	
	@RequestMapping(value = "/delivery.do", method = RequestMethod.GET)
	public String delivery(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/delivery";
	}
	
	@RequestMapping(value = "/mileage.do", method = RequestMethod.GET)
	public String mileage(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/mileage";
	}
	
	@RequestMapping(value = "/address_list.do", method = RequestMethod.GET)
	public String address_list(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/address_list";
	}
	
	@RequestMapping(value = "/address_insert.do", method = RequestMethod.GET)
	public String address_insert(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/address_insert";
	}
	
	@RequestMapping(value = "/address_modify.do", method = RequestMethod.GET)
	public String address_modify(Locale locale, Model model, SearchVO vo) throws Exception {
		
		int deleteResult = homeService.deleteSearchList();
		
		List<HomeSearchVO> searchList = homeService.listSearchList();
		
		model.addAttribute("searchList", searchList);
			
		return "mypage/address_modify";
	}
	
	@RequestMapping(value = "/payment.do", method = RequestMethod.POST)
	public String payment(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String sbidxStr = vo.getSbidxStr();
		String[] sbidxArray = sbidxStr.split(",");
		vo.setSbidxArray(sbidxArray);
		
		List<BasketVO> basketList = basketService.listPayFromBasket(vo);
		
		model.addAttribute("basketList", basketList);
		
		HttpSession session = req.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "redirect:/login/login.do";
		}else {
			return "mypage/payment";
		}
	}
	
	
	@RequestMapping(value = "/deleteOneBasket", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOneBasket(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String sbidxStr = req.getParameter("sbidx");
		int sbidx = Integer.parseInt(sbidxStr);
		
		vo.setSbidx(sbidx);
		
		int result = basketService.deleteOneBasket(vo);
		
		if(result > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/minusCntBasket", method = RequestMethod.POST)
	@ResponseBody
	public String minusCntBasket(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String sbidxStr = req.getParameter("sbidx");
		int sbidx = Integer.parseInt(sbidxStr);
		
		vo.setSbidx(sbidx);
		
		int result = basketService.minusCntBasket(vo);
		
		if(result > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/plusCntBasket", method = RequestMethod.POST)
	@ResponseBody
	public String plusCntBasket(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String sbidxStr = req.getParameter("sbidx");
		int sbidx = Integer.parseInt(sbidxStr);
		
		vo.setSbidx(sbidx);
		
		int result = basketService.plusCntBasket(vo);
		
		if(result > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/CheckedListBasket", method = RequestMethod.POST)
	@ResponseBody
	public String CheckedListBasket(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String checkedSbidx = req.getParameter("checkedSbidx");
		String[] sbidxArray = checkedSbidx.split(",");
		vo.setSbidxArray(sbidxArray);
		
		List<BasketVO> CheckedList = basketService.CheckedListBasket(vo);
		
		model.addAttribute("CheckedList", CheckedList);
		
		if(CheckedList.size() > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/deleteListBasket", method = RequestMethod.POST)
	@ResponseBody
	public String deleteListBasket(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String sbidxStr = req.getParameter("sbidxStr");
		String[] sbidxArray = sbidxStr.split(",");
		vo.setSbidxArray(sbidxArray);
		
		int result = basketService.deleteListBasket(vo);
		
		if(result > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/insertOrderList", method = RequestMethod.POST)
	@ResponseBody
	public String insertOrderList(Locale locale, Model model, BasketVO vo, HttpServletRequest req) throws Exception {
		
		String ordernumber = req.getParameter("ordernumber");
		vo.setOrdernumber(ordernumber);
		vo.setPaynumber(ordernumber);
		
		String sbidxStr = req.getParameter("sbidxStr");
		String[] sbidxArray = sbidxStr.split(",");
		vo.setSbidxArray(sbidxArray);
		
		int result = basketService.insertOrderList(vo);
		
		if(result > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	
}
