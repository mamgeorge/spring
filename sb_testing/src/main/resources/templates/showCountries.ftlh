<!DOCTYPE html>
<html><head><title>countries</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
</head>

<body><center>
<br /><h3>showCountries</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>
<br />
<br /><h5>TOTAL COUNTRIES: [ ${ countryList?size } out of ${ countryTotal } ]</h5>

<br /><div class = "divbox" style = "width: 70%;">
<table>
<tr>
	<th>idx</th>
	<th>id</th>
	<th>continent</th>
	<th>abbr</th>
	<th>country</th>
	<th>code2</th>
	<th>code3</th>
	<th>number</th>
	</tr>
<#list countryList as country>	
<tr>
    <td>${ country?index+1 }			</td>
    <td>${ country.id			!1 }	</td>
    <td>${ country.continent	!2 }	</td>
    <td>${ country.abbr		!3 }	</td>
	<td><a href = "/countryIds?id=${ country.id !1 }" >${ country.country !4 }</a></td>
    <td>${ country.code2		!5 }	</td>
    <td>${ country.code3		!6 }	</td>
    <td>${ country.number		!7 }	</td>
	</tr>
	</#list>
</table>
</div>

<br/><a href = "/">return</a>

</center></body>
</html>
