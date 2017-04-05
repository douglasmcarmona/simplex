<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8;"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		<link rel="stylesheet" href="style.css"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Simplex</title>
	</head>
	<body>
		<center>
			<div class="col-xs-12"><img src="titulo.png" class="img-responsive" /></div>
		</center>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12">
					<br/>
					<div>
						<p><b>Este é um Otimizador de Desenhos. Com ele, você poderá descobrir a quantidade máxima de desenhos que você pode fazer, de acordo com os tipos de desenho (ou categorias, se preferir) e a quantidade de horas que você irá dedicar a esta prática (e para cada etapa do desenho, quando for o caso). Basta entrar com os dados abaixo e o sistema dirá quantos desenhos você poderá fazer de acordo com as suas restrições.</b></p>
					</div>
					<form action="<%request.getContextPath();%>Response" method="post">
					<%System.out.print(request.getContextPath()); %>
						<!-- <br/>
						<div class="row" style="padding-left: 30px">
							<label class="radio=online">
								<input type="radio" id="radio_min" name="minimax" value="min"/>Minimizar
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label class="radio=online">
								<input type="radio" id="radio_max" name="minimax" value="min"/>Maximizar
							</label>
						</div>
						<br/> -->
						<div class="row">
							<div class="col-md-4" style="display: inline-block;">
								<label>Quantos tipos diferentes de desenhos você irá fazer?</label>
								<input type="text" id="var_dec" name="var_dec" value=""/>
							</div>
							
							<div class="col-md-6" style="display: inline-block;">
								<label>Um desenho é concluído em quantas etapas (ex: desenhar, colorir, detalhar...)?</label>
								<input type="text" id="num_restr" name="num_restr" value="" size="3"/>
							</div>
							<div class="col-xs-3 col-md-1" style="display: inline-block;">
								<input type="button" name="btn_restr" value="Restrições" id="btn_restr" />
							</div>
							<div class="col-xs-3 col-md-1" style="display: inline-block;">
								<input type="submit" name="btn_otimo" value="Otimizar!" id="btn_otimo" />
							</div>
						</div>
						<br/>
						<div class="row">
							<div class="col-md-6" id="minutos" style="border">
								<label>Em um dia (ou seja, 24h), quantos minutos você pretende dedicar, no total...</label>
								<div id="max" class="row">
								</div>
							</div>
							<div class="col-md-6" id="etp_por_des">
								<label>Quantos minutos, em média, você acredita que são necessários...</label>
								<div id="min_por_etp" class="row">
								</div>
							</div>
						</div>
						<br/>
					</form>
				</div>
			</div>
		</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript">
			$(function() {
				// $("#relat_geren").hide();
				$("#minutos").hide();
				$("#etp_por_des").hide();

				$("#btn_restr").click(function() {
					var restricoes = parseInt($("#num_restr").val(), 10);
					$("#max").empty();
					if($.isNumeric(restricoes)) {
						var html = "";
						for (var i = 1; i <= restricoes; i++) {
							html += 
								"<div id='restricao" + i + "' class='col-md-4'>" +
									"<label style='width:70%; display:'inline-block'>Para a etapa " + i + ":</label>" +
									"<input type='text' name='mins" + i + "' id='mins" + i + "' value='' style='width:30%'/>" +
								"</div>";

						}
						$("#minutos").fadeIn();
						$(html).hide().appendTo("#max").fadeIn("slow");
	
						var desenhos = parseInt($("#var_dec").val(), 10);
						if($.isNumeric(desenhos)) {
							$("#min_por_etp").empty();
							var html = "";
							for(var i=1; i<=desenhos; i++) {
								html += 
									"<div id='desenho" + i + "' class='col-xs-12' >" +
										"<p>para o desenho " + i + "</p>" +
										"<div class='row'>";
								for(var j=1; j<=restricoes; j++) {
									html +=
										"<div id='etp" + j + "_des" + i + "' class='col-md-4'>" +
											"<label style='width:70%; display:inline-block;'>na etapa " + j + ": </label>" +
											"<input type='text' name='mins_etp_" + j + "_des_" + i + "' id='mins_etp_" + j + "_des_" + i + "' style='width:30%'>" +
										"</div>";
								}
								html += "</div></div>";
							}
						}
						$("#etp_por_des").fadeIn();
						$(html).hide().appendTo("#min_por_etp").fadeIn("slow");
					}
				});
			});
		</script>
	</body>
</html>