<!DOCTYPE html>
<html><head><title>showCities</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center>
<br /><h3>showCities</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>
<br />
<br /><h5>TOTAL CITIES: [ ${ cityList?size } out of ${ cityTotal } ]</h5>

<br /><div class = "divbox" style = "width: 50%">
<table>
<tr>
	<th>idx</th>
	<th>id</th>
	<th>name</th>
	<th>population</th>
	</tr>

<#list cityList as city>
<tr>
	<td>${ city?index+1 }</td>
	<td>${ city.id			!1 }</td>
	<td><a href = "/cityIds?id=${ city.id !1 }" >${ city.name !2 }</a></td>
	<td>${ city.population	!3 }</td>
	</tr>
	</#list>
</table>
</div>

<br/><a href = "/">return</a>

</center></body>
</html>
