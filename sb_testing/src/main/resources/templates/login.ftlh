<!DOCTYPE html>
<html><head><title>login</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center><br /><h3>Login!</h3>

<#if RequestParameters.error??>
	<br /><div align="center"><h5>Invalid Login! Invalid username or password!</h5></div>
	<#elseif RequestParameters.logout??>
	<br /><div align="center"><h5>Logged out! You have Logged out of application!</h5></div>
	</#if>

<br />
<form action="/login" method = "post">
	<table style = "width: 400px;">
	<tr><td>UserName: </td><td><input type="text"		name="username" value="user"		/></td></tr>
	<tr><td>Password: </td><td><input type="password"	name="password" value="secret123"	/></td></tr>
	<tr><td colspan = "2" ><input type="submit" /></td></tr>
	<table>
	</form>

<br/><a href = "/">return</a>
</center></body>
</html>
