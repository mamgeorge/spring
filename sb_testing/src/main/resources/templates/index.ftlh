<!DOCTYPE html>
<html><head><title>index</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
<style>
body, h3,h5 form { font-family: verdana, monospace; margin: auto; text-align: center; display: inline; }
</style>
</head>

<body><center>
<br /><h3>index</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>

<br /><br />
<table style = "width: 50%;" border = "1" >
<tr class = "gra" ><td><a href = "/"					>/(root)		</a></td></tr>
<tr class = "gra" ><td><a href = "/logins"				>/logins		</a></td></tr>
<tr class = "grn" ><td><a href = "/cities"				>/cities		</a></td></tr>
<tr class = "grn" ><td><a href = "/cityIds?id=5"		>/cityIds?id=5	</a></td></tr>
<tr class = "grn" ><td><a href = "/cityPth/5"			>/cityPth/5	    </a></td></tr>
<tr class = "grn" ><td><a href = "/cityBean"			>/cityBean  	</a></td></tr>
<tr class = "blu" ><td><a href = "/countries"			>/countries		</a></td></tr>
<tr class = "blu" ><td><a href = "/countryIds?id=5"	>/countryIds?id=5	</a></td></tr>
<tr class = "blu" ><td><a href = "/countryPth/5"		>/countryPth/5	</a></td></tr>
<tr class = "prp" ><td><a href = "/timer"				>/timer			</a></td></tr>
<tr class = "prp" ><td><a href = "/utils"				>/utils			</a></td></tr>
<tr class = "prp" ><td colspan = "2"></td></tr>
<tr class = "prp" ><td><a href = "http://localhost:8080/swagger-ui/"> http://localhost:8080/swagger-ui/</a></td></tr>
</table>

<br /><hr /><form class = "grn" style = "width: 50%;" action = "/cityIds" method = "GET"> id for:
<input type = "text" name = "id" value = "7" />
<input type = "submit" /></form>

<br /><hr /><form class = "blu" style = "width: 50%;" action = "/countryIds" method = "GET"> id for:
<input type = "text" name = "id" value = "7" />
<input type = "submit" /></form>

<hr />
<br /><a class = "red" href = "/exits"		>/exits		</a>
<br /><a class = "red" href = "/errors"	>/errors	</a>
<br /><a class = "red" href = "/error"		>/error		</a>

</center></body>
</html>
</html>
