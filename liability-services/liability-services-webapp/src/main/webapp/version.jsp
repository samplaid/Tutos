<!doctype html>
<html>
	<head>
		<title>Example</title>
	</head>
	<body>
	<h1>Example</h1>
		<h2>Maven Information</h2>
		<table>
			<tr>
				<th>GroupId</th>
				<td>${project.groupId}</td>
			</tr>
			<tr>
				<th>Parent ArtifactId</th>
				<td>${project.parent.artifactId}</td>
			</tr>
			<tr>
				<th>Version</th>
				<td>${project.version}</td>
			</tr>
		</table>
	
		<h2>Extras</h2>
		<p>Change logs level : <a href="logs.jsp">Here</a></p>
		
	</body>
</html>