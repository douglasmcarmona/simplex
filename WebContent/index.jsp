<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Simplex</title>
</head>
<body>
	<div class="container-fluid">
		<div class="col-sm-6">
			<form action="response.jsp" method="get">
				<h1>Bem-vindo ao servidor simplex</h1>
				<br/><br/>
				<div class="form-inline">
					<div class="radio"><input type="radio" id="radio_min" name="minimax" value="min"/>Minimizar</div>&nbsp;&nbsp;
					<div class="radio"><input type="radio" id="radio_max" name="minimax" value="max"/>Maximizar</div>
				</div>
				<br/><br/>
				<div class="form-group">
					<label>Digite a Função Objetiva:</label>
					<input type="text" id="fo" name="fo" value=""/>
				</div>
				<div class="form-group">
					<label>Digite o número de restrições:</label>	
					<input type="text" id="num_restr" name="num_restr" value="" size="3"/>
				</div>
				<div class="form-group">
					<div class="table-responsive">
						<table id="restricoes" class="table">
						</table>
					</div>
				</div>
			</form>
		</div>
		<div class="col-sm-6">
			<textarea readonly placeholder="o resultado vai estar aqui" name="relat_geren" id="relat_geren" >
			</textarea>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#num_restr").focusout(function() {
				var restricoes = parseInt($("#num_restr").val(), 10);
				$("#restricoes").empty();
				if($.isNumeric(restricoes)) {
					$("#restricoes").append("<tbody>");
					for(var i=1; i<restricoes; i+=2) {
						$("#restricoes").append(
						"<tr>" +
							"<td>" +
								"<div id='restricao" + i + "' class='col-cm-3 col-xs-6'>" +
									"<label>Restrição " + i + ": </label>" +
									"<input type='text' name='r"+i+"' id='r"+i+"' value=''/>" +
								"</div>" +
							"</td>" +
							"<td>" +
								"<div id='restricao" + (i+1) + "' class='col-cm-3 col-xs-6'>" +
									"<label>Restrição " + (i+1) + ": </label>" +
									"<input type='text' name='r"+(i+1)+"' id='r"+(i+1)+"' value=''/>" +
								"</div>" +
							"</td>" +
						"</tr>"
						);
					}
					if(restricoes % 2 == 1) {
						$("#restricoes").append(
						"<tr>" +
							"<td>" +
								"<div id='restricao" + restricoes + "' class='col-cm-3 col-xs-6'>" +
									"<label>Restrição " + restricoes + ": </label>" +
									"<input type='text' name='r"+restricoes+"' id='r"+restricoes+"' value=''/>" +
								"</div>" +
							"</td>" +
						"</tr>"
						);
					}
					$("restricoes").append("</tbody>");
				}
			})
		});
	</script>
</body>
</html>