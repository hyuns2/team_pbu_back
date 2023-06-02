<%@page import="javax.xml.bind.DatatypeConverter"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String mid = "INIiasTest";                           // 테스트 MID 입니다. 계약한 상점 MID 로 변경 필요
	String apiKey = "TGdxb2l3enJDWFRTbTgvREU3MGYwUT09";  // 테스트 MID 에 대한 apiKey
	String reqSvcCd = "01";
	String mTxId = "mTxId_" + Calendar.getInstance().getTimeInMillis();
	String reservedMsg = "isUseToken=Y";                 // 결과조회 응답시 개인정보 SEED 암호화 처리 요청

	// 등록가맹점 확인
	String plainText1 = mid + mTxId + apiKey;
	MessageDigest authmd = MessageDigest.getInstance("SHA-256");
	authmd.update(plainText1.getBytes("UTF-8"));
	String authHash = DatatypeConverter.printHexBinary(authmd.digest()).toLowerCase();

	String userName = "홍길동";          // 사용자 이름
	String userPhone = "01011112222";  // 사용자 전화번호
	String userBirth = "19800101";     // 사용자 생년월일
	String userHash = "";

	String flgFixedUser = "N";         // 특정사용자 고정시 Y

	if("Y".equals(flgFixedUser))
	{
		String plainText2 = userName + mid + userPhone + mTxId + userBirth + reqSvcCd;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(plainText2.getBytes("UTF-8"));
		userHash = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
	}
%>

<html>
<head>
<title>통합본인인증 요청</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<script language="javascript">
	function callSa()
	{
		let window = popupCenter();
		if(window != undefined && window != null)
		{
			document.saForm.setAttribute("target", "sa_popup");
			document.saForm.setAttribute("post", "post");
			document.saForm.setAttribute("action", "https://sa.inicis.com/auth");
			document.saForm.submit();
		}
	}

	function popupCenter() {
		let _width = 400;
		let _height = 620;
		var xPos = (document.body.offsetWidth/2) - (_width/2); // 가운데 정렬
		xPos += window.screenLeft; // 듀얼 모니터일 때

		return window.open("", "sa_popup", "width="+_width+", height="+_height+", left="+xPos+", menubar=yes, status=yes, titlebar=yes, resizable=yes");
	}
</script>
</head>
<body>

<form name="saForm">

<input type="text" name="mid" value="<%=mid %>">                             mid<br/>
<input type="text" name="reqSvcCd" value="<%=reqSvcCd %>">                   reqSvcCd<br/>
<input type="text" name="mTxId" value="<%=mTxId %>">                         mTxId<br/>
<input type="text" name="authHash" value="<%=authHash %>">                   authHash<br/>
<input type="text" name="flgFixedUser" value="<%=flgFixedUser %>">	         flgFixedUser<br/>
<input type="text" name="userName" value="<%=userName %>">                   userName<br/>
<input type="text" name="userPhone" value="<%=userPhone %>">	             userPhone<br/>
<input type="text" name="userBirth" value="<%=userBirth %>">	             userBirth<br/>
<input type="text" name="userHash" value="<%=userHash %>">		             userHash<br/>
<input type="hidden" name="reservedMsg" value="<%=reservedMsg %>">           reservedMsg<br/>
<input type="text" name="directAgency" value="">                             directAgency<br/>

<input type="text" name="successUrl" value="http://localhost:8080/test/jsp/success">                  successUrl<br/>
<input type="text" name="failUrl" value="http://localhost:8080/test/jsp/fail">                     failUrl<br/>
<!-- successUrl/failUrl 은 분리하여도 됩니다. !-->
</form>

<button onclick="callSa()">확인</button>

</body>
</html>