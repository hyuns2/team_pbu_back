
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.util.Enumeration"%>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.io.BufferedWriter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // -------------------- 인증 수신 -------------------------------------------//
	request.setCharacterEncoding("UTF-8");
	String resultCode = request.getParameter("resultCode");
	String resultMsg = request.getParameter("resultMsg");
	/*
	 * 하기 인증결과 데이터 조회 API 통신 코드 샘플은 기본 라이브러리를 사용하여 통신이 되도록 샘플 코드를 작성한 것으로
	 * 가맹점의 상황에 맞는 통신 모듈을 사용하여 API 통신할 수 있도록 처리 해주시면 됩니다.
	 */
	JSONObject resJson = null;
	if("0000".equals(resultCode)){

		String authRequestUrl = request.getParameter("authRequestUrl");
		String txId = request.getParameter("txId");

		// 통합인증요청 시, reservedMsg = isUseToken=Y 요청된 경우 token 값 전달됩니다.
		// token = seend 암복호화 key 값
		String token = request.getParameter("token");

		JSONParser parser = new JSONParser();
		URL url = new URL(authRequestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn != null) {
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestMethod("POST");
			conn.setDefaultUseCaches(false);
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(9999);
			// post
			conn.setDoOutput(true);

			JSONObject reqJson = new JSONObject();
			reqJson.put("mid", "INIiasTest");  // 테스트 MID 입니다. 계약한 상점 MID 로 변경 필요
			reqJson.put("txId", txId);

			// parameter 전송
			if (conn.getDoOutput()) {
				conn.getOutputStream().write(reqJson.toString().getBytes());
				conn.getOutputStream().flush();
				conn.getOutputStream().close();
			}

			conn.connect();

			if (conn.getResponseCode() == HttpServletResponse.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				resJson = (JSONObject) parser.parse(br);
				br.close();
			}
		}
		for(Object key : resJson.keySet()){
			out.print("<p>"+key + " : " + resJson.get(key)+"</p>");
		}
		out.print("<p>"+"authRequestUrl" + " : " + authRequestUrl+"</p>");

		// -------------------- 결과 수신 -------------------------------------------//
	}else{
		out.print("<p>"+resultCode+"</p>");
		out.print("<p>"+resultMsg+"</p>");
	}
%>