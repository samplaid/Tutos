#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN">
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="ch.qos.logback.classic.Level"%>
<%@page import="ch.qos.logback.classic.Logger"%>
<%@page import="ch.qos.logback.classic.LoggerContext"%>
<%@page import="org.slf4j.LoggerFactory"%>

<html>
<head>
<title>Example : Logs</title>
</head>
<body>
	<h1>Example Logs</h1>

	<h2>Change log level</h2>
	<form action="logs.jsp" method="POST">
			Log Level : <select name="logLevel">
				<option value=""></option>
				<option value="off">OFF</option>
				<option value="fatal">FATAL</option>
				<option value="error">ERROR</option>
				<option value="warn">WARN</option>
				<option value="info">INFO</option>
				<option value="debug">DEBUG</option>
				<option value="trace">TRACE</option>
			</select><input type="submit" value="Submit">
	</form>

	<%
		String logLevel = request.getParameter("logLevel");
		if (logLevel != null) {
			setLogger(logLevel);
		}
	%>

	<%!
		public void setLogger(String level) {
			Level newLevel = Level.toLevel(level);

			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			List<Logger> loggers = lc.getLoggerList();

			for (Logger logger : loggers) {
				logger.setLevel(newLevel);
			}
		}
	%>

	<h2>Loggers</h2>
	<table>
		<tr>
			<th>Logger</th>
			<th><strong>Effective</strong> Level</th>
		</tr>
	<%
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
   		List<Logger> loggers = lc.getLoggerList();
   		for (Logger logger : loggers) {
			out.println("<tr>");
			out.println("<td>");
			out.println(logger.getName());
			out.println("</td>");
			out.println("<td>");
			out.println(logger.getEffectiveLevel());
			out.println("</td>");
			out.println("</tr>");
   		}

	%>
	</table>


</body>
</html>