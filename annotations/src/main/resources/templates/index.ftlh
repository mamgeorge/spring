<!DOCTYPE html>
<html><head><title>index</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center>
<br /><h3>index</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>

<br /><br />
<table style = "width: 50%;" border = "1" >
<tr><td><a href = "/"				>/(root)		</a></td></tr>
<tr><td><a href = "/cities"		>/cities		</a></td></tr>
<tr><td><a href = "/cityIds?id=5"	>/cityIds?id=5	</a></td></tr>
<tr><td><a href = "/cityPth/5"		>/cityPth/5	</a></td></tr>
<tr><td><a href = "/timer"		>/timer		</a></td></tr>
<tr><td><a href = "/utils"		>/utils		</a></td></tr>
<tr><td><a href = "/exits"		>/exits		</a></td></tr>
</table>

<br /><hr />
<form action = "/cityIds" method = "GET">
id for: 
<input type = "text" name = "id" value = "7" />
<input type = "submit" name = "submit" />
</form>

<hr />
<br /><a class = "red" href = "/errors" >errors</a>
<br /><a class = "red" href = "/error" >error</a>

</center></body>
</html>
