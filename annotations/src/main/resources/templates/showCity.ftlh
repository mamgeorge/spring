<!DOCTYPE html>
<html><head><title>showCity</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center>
<br /><h3>showCity</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>
<br />
<br /><h5>Object: [ ${ city } ]</h5>

<br />
<table border = "1" style = "width: 50%">
<tr>
	<th>id</th>
	<th>name</th>
	<th>population</th>
	</tr>

<tr>
	<td>${ city.id }</td>
	<td>${ city.name }</td>
	<td>${ city.population }</td>
	</tr>
</table>

<br/><a href = "/">return</a>

</center></body>
</html>
