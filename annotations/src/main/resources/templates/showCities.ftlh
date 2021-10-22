<!DOCTYPE html>
<html><head><title>showCities</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center>
<br /><h3>showCities</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>
<br />
<br /><h5>TOTAL CITIES: [ ${ cities?size } out of ${ cityCount } ]</h5>

<br /><div class = "divbox" style = "width: 50%">
<table>
<tr>
	<th>id</th>
	<th>name</th>
	<th>population</th>
	</tr>

<#assign datetimer = .now>
<#list cities as city>
<tr>
	<td>${ city?index+1		 } / ${ city.id! 0 }</td>
	<td>${ city.name		!1 }</td>
	<td>${ city.population	!2 }</td>
	</tr>
	</#list>
</table>
</div>

<br/><a href = "/">return</a>

</center></body>
</html>
