<!DOCTYPE html>
<html><head><title>showCountry</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center>
<br /><h3>showCountry</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>
<br />
<br /><h5>Object: [ ${ country } ]</h5>

<br />
<table border = "1" style = "width: 50%">
<tr>
	<th>id</th>
	<th>continent</th>
	<th>abbr</th>
	<th>country</th>
	<th>code2</th>
	<th>code3</th>
	<th>number</th>
	</tr>
<tr>
	<td>${ country.id		!1 }	</td>
	<td>${ country.continent!2 }	</td>
	<td>${ country.abbr	!3 }	</td>
	<td>${ country.country	!4 }	</td>
	<td>${ country.code2	!5 }	</td>
	<td>${ country.code3	!6 }	</td>
	<td>${ country.number	!7 }	</td>
	</tr>
</table>

<br/><a href = "/">return</a>

</center></body>
</html>
