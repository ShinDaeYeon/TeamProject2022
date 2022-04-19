<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- 부트스트랩 -->
	<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css" />
	
	<title>스크랩 - 홈프렌즈</title>
	
	<script src="/controller/js/jquery-3.6.0.min.js"></script>
	<link href="/controller/css/header.css" rel="stylesheet">
	<link href="/controller/css/nav.css" rel="stylesheet">
	<link href="/controller/css/home.css" rel="stylesheet">
	<link href="/controller/css/footer.css" rel="stylesheet">
	<link href="/controller/css/community/home_list.css" rel="stylesheet">
	<link href="/controller/css/community/scrap.css" rel="stylesheet">
	<script src="/controller/js/community/home_list.js"></script>
	<script src="/controller/js/header.js"></script>
	<script src="/controller/js/nav.js"></script>
	<script src="/controller/js/footer.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<%@ include file="../nav.jsp" %>
	
	<!-- 본문 영역 -->
	<section id="Community_area">
		<c:if test="${loginUser == null}">
			<div class="recommend">추천 게시글</div>
			<div class="row storyList">
				<c:forEach items="${boardlist }" var="blist" end="8">
					<div class="col-12 col-sm-6 col-md-4 col-lg-4 col-xl-4 story">
						<img src="/controller/upload/${blist.img_system}" class="storyImg" onclick="location.href='home_view.do?cbidx=${blist.cbidx}&fmidx=${blist.midx }&nowPage=1'">
						<div class="storyText" onclick="location.href='home_view.do?cbidx=${blist.cbidx}&fmidx=${blist.midx }&nowPage=1'">
							<div class="storyTitle">${blist.title }</div>
							<div class="storyWriter">
								<img class="writerImg" src="/controller/image/${blist.profile_system }">${vo.writer }
							</div>
							<div class="likeyView">
								좋아요${blist.scrap_cnt } &middot; 조회${blist.hit_cnt }
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${loginUser != null && slist == null}">
			<div class="recommend">추천 게시글</div>
			<div class="row storyList">
				<c:forEach items="${boardlist }" var="blist" end="8">
					<div class="col-12 col-sm-6 col-md-4 col-lg-4 col-xl-4 story">
						<img src="/controller/upload/${blist.img_system}" class="storyImg" onclick="location.href='home_view.do?cbidx=${blist.cbidx}&fmidx=${blist.midx }&nowPage=1'">
						<div class="storyText" onclick="location.href='home_view.do?cbidx=${blist.cbidx}&fmidx=${blist.midx }&nowPage=1'">
							<div class="storyTitle">${blist.title }</div>
							<div class="storyWriter">
								<img class="writerImg" src="/controller/image/${blist.profile_system }">${vo.writer }
							</div>
							<div class="likeyView">
								좋아요${blist.scrap_cnt } &middot; 조회${blist.hit_cnt }
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${loginUser != null && slist != null}">
		<div class="row storyList">
			<c:forEach items="${slist}" var="vo" varStatus="cnt">
				<div class="col-12 col-sm-6 col-md-4 col-lg-4 col-xl-4 story">
					<img src="/controller/upload/${vo.img_system}" class="storyImg" onclick="location.href='home_view.do?cbidx=${vo.cbidx}&fmidx=${vo.midx }&nowPage=1'">
					<div class="storyText" onclick="location.href='home_view.do?cbidx=${vo.cbidx}&fmidx=${vo.midx }&nowPage=1'">
						<div class="storyTitle">${vo.title }</div>
						<div class="storyWriter">
							<img class="writerImg" src="/controller/image/${vo.profile_system }">${vo.writer }
						</div>
						<div class="likeyView">
							좋아요${vo.scrap_cnt } &middot; 조회${vo.hit_cnt }
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
	</section>
	
	<%@ include file="../footer.jsp" %>
	<!-- 부트스트랩 -->	

	<!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    -->
    
</body>
</html>