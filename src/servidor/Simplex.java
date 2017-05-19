public class Simplex{
	private static float [][] matrizPrincipal;
	private static String [] vetorEquacao; 
	public static String resposta;
	private static String [] vetorLinha;
	private static String [] vetorColuna;
	/*
	*Preenche a minha matrizEquacao com os valores que estao nas minhas equacoes
	*/
	public static void CriarMatriz( ){
		String s = "";
		String aux = "";
		String aux1 = "";
		int linha = 0, coluna = 0, contNum = 0,variavelX = 0;
		float convert = 0;
		boolean teste = false;
		float [][] matrizAux;

		matrizAux = new float[Equacao.quantEquacoes+1][Equacao.quantTipoDesenho+1];
	
		//Inicializa a matriz com 0
		for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
			for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
				matrizAux[linha1][coluna1] = Float.parseFloat(""+0);
			}
		}

		//insere valores da equacao de otimizacao na matriz
		s = vetorEquacao[0];
		for(int j = 1; j < s.length();j++){
					if(s.charAt(j-1) != 'x'){	
							if(s.charAt(j) >= '0' && s.charAt(j) <= '9'){ 
									contNum++;
									aux = aux.concat(""+s.charAt(j));//concatena cada parte do meu numero ate encontrar algo diferente de um numero			
							}else{
								if(contNum == 1){
										if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9'){
											if(s.charAt(j-(contNum+1)) == '+'){//verifico qual é o sinal que representa o numero
												convert = Float.parseFloat(aux);
												matrizAux[linha][coluna] = convert;
												aux = "";
												coluna++;													
												contNum = 0;
												}else{
													if(s.charAt(j-(contNum+1))== '-'){//verifico qual é o sinal que representa o numero
														convert = (Float.parseFloat(aux)*-1);
														matrizAux[linha][coluna] = convert;
														aux = "";
														coluna++;																
														contNum = 0;
													}
													
												}
											}
									}else{	//se o numero tiver mais de 1 algarismo								
											if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9' && s.charAt(j-2) != 'x'){//Encontra o numero
												if(s.charAt(j-(contNum+1)) == '+'){//Verficia o sinal do numero
													convert = Float.parseFloat(aux);
													matrizAux[linha][coluna] = convert;//insere o numero na matriz
													aux = "";
													coluna++;													
													contNum = 0;
													}else{
														if(s.charAt(j-contNum)	== '-')	{
															convert = (Float.parseFloat(aux)*-1);
															matrizAux[linha][coluna] = convert;
															aux = "";
															coluna++;																
															contNum = 0;															
															}
														}												
												}						
											}	
									}
						}
				}

	//Insere os valores que estao nas inequacoes na matriz
		for(int i = 1; i < vetorEquacao.length; i++){
				s = vetorEquacao[i];	
				coluna = 0;
				linha++;
				for(int j = 4 ; j < s.length();j++){
						if(s.charAt(j-1) != 'x'){//Verifica se a posicao anterior e diferente de x
							if(s.charAt(j) >= '0' && s.charAt(j) <= '9'){ 
									contNum++;
									aux = aux.concat(""+s.charAt(j));//concatena cada parte do meu numero ate encontrar algo diferente de um numero	
								}else{	
									//Verifica o tamanho do numero
									if(contNum == 1){
										if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9'){
												//Verifica se o o sinal que esta na frente do numero e + ou -
												if(s.charAt(j-(contNum+1)) == '+'){
															convert = Float.parseFloat(aux);
															matrizAux[linha][coluna] = convert;
															aux = "";
															coluna++;													
															contNum = 0;
														}else{
															if(s.charAt(j-(contNum+1)) == '-')	{
																convert = Float.parseFloat(aux);
																convert = (convert *-1);
																matrizAux[linha][coluna] = convert;
																aux = "";	
																coluna++;														
																contNum = 0;
																}
													}
											}
									//Depois que o numero e salvo o valor e armazenado
									}else{									
												if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9'){
												//Verifica se o o sina lque esta na frente do numero e + ou -
													if(s.charAt(j-(contNum+1)) == '+'){
															convert = Float.parseFloat(aux);
															matrizAux[linha][coluna] = convert;
															aux = "";		
															coluna++;										
															contNum = 0;
														}else{
															if(s.charAt(j-(contNum+1)) == '-')	{
																convert = Float.parseFloat(aux);
																convert = (convert *-1);
																matrizAux[linha][coluna] = convert;
																aux = "";	
																coluna++;														
																contNum = 0;
																}
														}												
												}						
											}	
									}
			
						}else{
									//Salva na matriz os valores 1 quando nao existe um numero na frente do X de acordo com o sinal que o precede
									//0 quando falta alguma variavel de decisao
										if(s.charAt(j-2) == '+'){
											convert = Float.parseFloat(""+1);
											int salvarPosicao = Integer.parseInt(""+s.charAt(j));
											matrizAux[linha][salvarPosicao] = convert;
											aux = "";												
											contNum = 0;
										}else{
												if(s.charAt(j-2) == '-')	{
													convert = Float.parseFloat(""+1);
													convert = (convert *-1);
													int salvarPosicao = Integer.parseInt(""+s.charAt(j));
													matrizAux[linha][salvarPosicao] = convert;
													aux = "";																
													contNum = 0;
												}
											}	
								}				
							}
					}

					matrizPrincipal = ClonarMatriz(matrizAux);
					PreencheVetorTrocaVariavel();
					//MostrarMatriz(matrizPrincipal);									
					//MetodoFase1(matrizEquacao);
			}

			private static void MetodoFase1(){
				boolean	Testelinha,Testecoluna;	
				float	matrizAux [][] = new float[Equacao.quantEquacoes+1][Equacao.quantTipoDesenho+1]; 									
				float  MinQuociente;
				int cont, posicaoQuociente, MinLinha, MinColuna;
				boolean linha, coluna;
				float []VetorQuociente;

			do{
			
				MinQuociente = 0;
				cont = 1;posicaoQuociente = 0;MinLinha = 0;MinColuna = 0;
				linha = false; coluna = false;
				Testelinha = false;
				Testecoluna = false;	

				for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
						matrizAux[linha1][coluna1] = Float.MIN_VALUE;
				}
			}
		
				//Verfica se existe valores negativos
				//Pesquisa a linha e coluna permissivel
				for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){								
						if(matrizPrincipal[linha1][0] < 0){
								MinLinha = linha1;//linha permissivel
								linha = true;
									for(int coluna1 = 1; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
										if(matrizPrincipal[linha1][coluna1] < 0){
												MinColuna = coluna1;//coluna permissivel
												coluna = true;	
												coluna1 = Equacao.quantTipoDesenho+1;		
											}	
									}
						}		
					}
				TrocaVariaveis(MinLinha,MinColuna);
				if(linha != true)MetodoFase2();
				else{
				//Preenche o vetor com a menor variavel para verificacao de menor quociente
				VetorQuociente = new float[Equacao.quantEquacoes+1];
				for(int i = 0; i < VetorQuociente.length; i++){
					VetorQuociente[i] = Float.MIN_VALUE;	
				}
		
				//Preenche o vetor com os possiveis quocientes
				for(int m = 1; m < Equacao.quantEquacoes+1; m++){
					if((matrizPrincipal[m][MinColuna] < 0 && matrizPrincipal[m][0] < 0) || (matrizPrincipal[m][MinColuna] > 0 && matrizPrincipal[m][0] > 0)){
							VetorQuociente[cont] = matrizPrincipal[m][0]/matrizPrincipal[m][MinColuna];
					}		
				cont++;	
				}		
					
				//Verifica qual e o menor quociente
				//Pega posicao em que o menor quociente esta
				MinQuociente = VetorQuociente[0];
				for(int QQ = 1; QQ < VetorQuociente.length; QQ++){
					if(MinQuociente == Float.MIN_VALUE) {
							MinQuociente = VetorQuociente[QQ];
							posicaoQuociente = QQ;
					}
					else {
						if(VetorQuociente[QQ] != Float.MIN_VALUE && MinQuociente >= VetorQuociente[QQ]){//pega o menor quociente
							MinQuociente = VetorQuociente[QQ];	
							posicaoQuociente = QQ;
						}								
					}
				}
			//Pega o valor do menor quociente e o inverte 
			matrizAux[posicaoQuociente][MinColuna] = 1/matrizPrincipal[posicaoQuociente][MinColuna];			
			
			//Multiplicacao da linha pelo valor inverso
			for(int j = 0; j < Equacao.quantTipoDesenho+1; j++ ){
				if(j != MinColuna){
					matrizAux[posicaoQuociente][j] = matrizAux[posicaoQuociente][MinColuna]*matrizPrincipal[posicaoQuociente][j];
				}
			}
			//Multiplicacao da coluna pelo valor inverso negativo
			matrizAux[posicaoQuociente][MinColuna] = (matrizAux[posicaoQuociente][MinColuna])*(-1);
			for(int i = 0; i < Equacao.quantEquacoes+1; i++){
				if(i != posicaoQuociente){
					matrizAux[i][MinColuna] = matrizAux[posicaoQuociente][MinColuna]*matrizPrincipal[i][MinColuna];
				}
			}	
			//Preenchimento do resto da matriz
				int PercorreLinha = 0, i = 0, j = 0;
				float aux = 0;
				while(i < Equacao.quantTipoDesenho+1 ){
					if(PercorreLinha != MinColuna){
						while(j < Equacao.quantEquacoes+1){
							if(matrizAux[j][i] == Float.MIN_VALUE){	
								if(j != posicaoQuociente && i != MinColuna){
									aux =  matrizPrincipal[posicaoQuociente][PercorreLinha]* matrizAux[j][MinColuna];
									aux = matrizPrincipal[j][i]+aux;
									matrizAux[j][i] = aux;
									j++;
									aux = 0;
								}
							}else {j++;}								
						}
					}
					i++;
					j=0;
					PercorreLinha++;
				}
				
				//Onde tiver -0 a variavel é trocada para 0
				for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
						if(matrizAux[linha1][coluna1] == -0) matrizAux[linha1][coluna1] = 0;
				}
			}
		
				matrizAux[posicaoQuociente][MinColuna] = (matrizAux[posicaoQuociente][MinColuna])*(-1);
				//Verifica posicao negativa
				for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){								
						if(matrizAux[linha1][0] < 0){
								Testelinha = true;
						}	
					}	
					matrizPrincipal = ClonarMatriz(matrizAux);
					//MostrarMatriz(matrizPrincipal);
			}
		}while(Testelinha == true);

		//MetodoFase2(matrizTransformada);
	}//FimMetodoFase1		

			public static void MetodoFase2(){
			//	float [][] m = new float [Equacao.quantEquacoes+1][Equacao.quantTipoDesenho+1];
			//	m = ClonarMatriz(matriz);		
				boolean resSO, resSI , resSM , resSIli ;
			do{					
					resSO = false; resSI = false; resSM = false; resSIli = false;
				
					resSO =  SolucaoOtima(matrizPrincipal);
				
					resSI =  SolucaoImpossivel(matrizPrincipal);
					
					resSM = SolucaoMultipla (matrizPrincipal);
	 
					resSIli = SolucaoIlimitada(matrizPrincipal);

					if(resSO) System.out.println("Solucao Otima");
						else 
								if (resSI) System.out.println("Solucao Impossivel");
							else 
									if (resSM) System.out.println("Solucao Multipla");
								else 
										if (resSIli) System.out.println("Solucao Ilimitada");
									else matrizPrincipal = Teste(matrizPrincipal);
				}while(resSO == false && resSI == false && resSM == false && resSIli == false);	
			//MostrarMatriz(matrizPrincipal);
			}//fim metodo2

			//Verifica se a matriz passada e solucao ilimitada
			public static boolean SolucaoIlimitada(float matriz [][]) {
				boolean SIli = false;
				boolean linha2 = false;			
				int contPositivo = 0, contNegativo = 0; 
				
				for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					if(matriz[linha1][0] > 0) contPositivo++;
				}
							
				for(int coluna1 = 1; coluna1 < Equacao.quantTipoDesenho+1; coluna1++ ){	
					if(matriz[0][coluna1] > 0){ 
						linha2 = true;
						for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){	
							if(matriz[linha1][coluna1] < 0) contNegativo++;
						}
					}																		
				}						
				if(linha2 && contNegativo ==  Equacao.quantEquacoes)  SIli = true;									
				return SIli;
			}

			//Verifica se a matriz passada e solucao Multipla
			public static boolean SolucaoMultipla(float matriz[][]){
				int contNegativo = 0, contPositivo = 0;	
				boolean MS = false;
				boolean encontrou0 = false;
				boolean encontrouNeg = false;	
					
					for(int coluna1 = 1 ; coluna1 < Equacao.quantTipoDesenho+1; coluna1 ++){
							if(matriz [0][coluna1] ==  0) encontrou0 = true;  						
							else if (matriz [0][coluna1] < 0 ) encontrouNeg = true;														
					}									
					if(contPositivo == Equacao.quantEquacoes && encontrou0 && encontrouNeg) MS = true;										
				return MS;
			}

			//Verifica se a matriz passada e solucao impossivel
			public static boolean SolucaoImpossivel(float matriz[][]){
				boolean SI = false;		
				int contPositivo = 0; 
				//Verifica se e solucao impossivel		
				for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){	
							if(matriz[linha1][0] < 0){
									for(int coluna1 = 1 ; coluna1 < Equacao.quantTipoDesenho+1; coluna1 ++){	
											if(matriz[linha1][coluna1] > 0) contPositivo++;
										}
							}
				}
						if(contPositivo == Equacao.quantTipoDesenho){
								SI = true;
							}
				return SI;
			}

		//Verifica se a matriz passada e solucao otima
		public static boolean SolucaoOtima(float matriz[][]){
			boolean SoOt = false;
			int contNegativo = 0, contPositivo = 0;
					//Verficica se a matriz e solucao otima						
								for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){
									if(matriz[linha1][0] > 0) contPositivo++;
								}
										for(int coluna1 = 1 ; coluna1 < Equacao.quantTipoDesenho+1; coluna1 ++){
											if(matriz [0][coluna1] < 0) contNegativo++;																							
										}												
								if(contNegativo == Equacao.quantTipoDesenho && contPositivo == Equacao.quantEquacoes) SoOt = true;			
								
			return SoOt;
		}

		public static float [][] Teste(float matriz [][]){
				float [][] matrizTransformada1 = new float[Equacao.quantEquacoes+1][Equacao.quantTipoDesenho+1]; 					
				float  MinQuociente = 0;
				int cont = 0, posicaoQuociente = 0, MinLinha = 0, MinColuna = 0;
				boolean linha = false;
				boolean coluna = false;
				float []VetorQuociente1;
					
				for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
						matrizTransformada1[linha1][coluna1] = Float.MIN_VALUE;
					}
				}

				//Verfica se existe valores positivos na linha f(x)
				//Verifica se existe valores positivos na coluna
					for(int coluna1 = 1; coluna1 < Equacao.quantTipoDesenho+1; coluna1++ ){								
						if(matriz[0][coluna1] > 0){
							MinColuna = coluna1;	
							for(int linha1 = 1; linha1 < Equacao.quantEquacoes+1; linha1++ ){	
								if(matriz[linha1][coluna1] > 0){
									MinLinha = linha1;															
									linha1 = Equacao.quantEquacoes+1;	
									coluna1 = Equacao.quantTipoDesenho+1;	
								}	
							}		
						}
					}		
				TrocaVariaveis(MinLinha,MinColuna);
			//Preenche o vetor com a menor variavel para verificacao de menor quociente
				VetorQuociente1 = new float[Equacao.quantEquacoes+1];
				for(int i = 0; i < VetorQuociente1.length; i++){
					VetorQuociente1[i] = Float.MIN_VALUE;	
				}
		
				//Preenche o vetor com os possiveis quocientes
				for(int m = 1; m < Equacao.quantEquacoes+1; m++){
					if((matriz[m][MinColuna] < 0 && matriz[m][0] < 0) || (matriz[m][MinColuna] > 0 && matriz[m][0] > 0)){
							VetorQuociente1[cont] = matriz[m][0]/matriz[m][MinColuna];
					}		
				cont++;	
				}		
					
				//Verifica qual e o menor quociente
				//Pega posicao em que o menor quociente esta
				MinQuociente = VetorQuociente1[0];
				for(int QQ = 1; QQ < VetorQuociente1.length; QQ++){
					if(VetorQuociente1[QQ] != Float.MIN_VALUE){
						if(MinQuociente == Float.MIN_VALUE ){
							MinQuociente = VetorQuociente1[QQ];
						}
						if(MinQuociente > VetorQuociente1[QQ]){
							MinQuociente = VetorQuociente1[QQ];	
							posicaoQuociente = QQ;
						}								
					}
				}
			//Pega o valor do menor quociente e o inverte 
			if(posicaoQuociente+1 != Equacao.quantEquacoes+1){
			matrizTransformada1[posicaoQuociente+1][MinColuna] = 1/matriz[posicaoQuociente+1][MinColuna];
			}	else{ 
			matrizTransformada1[posicaoQuociente][MinColuna] = 1/matriz[posicaoQuociente][MinColuna];
				}
			
			//Multiplicacao da linha pelo valor inverso
			for(int j = 0; j < Equacao.quantTipoDesenho+1; j++ ){
				if(j != MinColuna){
					matrizTransformada1[posicaoQuociente+1][j] = matrizTransformada1[posicaoQuociente+1][MinColuna]*matriz[posicaoQuociente+1][j];
				}
			}
			//Multiplicacao da coluna pelo valor inverso negativo
			matrizTransformada1[posicaoQuociente+1][MinColuna] = (matrizTransformada1[posicaoQuociente+1][MinColuna])*(-1);
			for(int i = 0; i < Equacao.quantEquacoes+1; i++){
				if(i != posicaoQuociente+1){
					matrizTransformada1[i][MinColuna] = matrizTransformada1[posicaoQuociente+1][MinColuna]*matriz[i][MinColuna];
				}
			}	
			//Preenchimento do resto da matriz
				int PercorreLinha = 0, i = 0, j = 0;
				float aux = 0;
				while(i < Equacao.quantTipoDesenho+1 ){
					if(PercorreLinha != MinColuna){
						while(j < Equacao.quantEquacoes+1){
							if(matrizTransformada1[j][i] == Float.MIN_VALUE){	
								if(j != posicaoQuociente+1 && i != MinColuna){
									aux =  matriz[posicaoQuociente+1][PercorreLinha]* matrizTransformada1[j][MinColuna];
									aux = matriz[j][i]+aux;
									matrizTransformada1[j][i] = aux;
									j++;
									aux = 0;
								}
							}else {j++;}								
						}
					}
					i++;
					j=0;
					PercorreLinha++;
				}
				
				//Onde tiver -0 a variavel é trocada para 0
				for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
						if(matrizTransformada1[linha1][coluna1] == -0) matrizTransformada1[linha1][coluna1] = 0;
				}
			}
		
				matrizTransformada1[posicaoQuociente+1][MinColuna] = (matrizTransformada1[posicaoQuociente+1][MinColuna])*(-1);
				return matrizTransformada1;
		}

			public static void TrocaVariaveis(int trocaLinha, int trocaColuna){
				//System.out.println("Linha: " +trocaLinha+" Coluna: "+trocaColuna);	
				String aux = "", aux1 = "";		
				//Troca as minhas variaveis
						aux = vetorColuna[trocaColuna];
						aux1 = vetorLinha[trocaLinha];
		
						vetorColuna[trocaColuna] = aux1;	
						vetorLinha[trocaLinha] = aux;
			}	

		//Troca as variaveis de acordo com a regiao permissivel
		public static void PreencheVetorTrocaVariavel(){
				vetorLinha = new String [Equacao.quantEquacoes+1];
				vetorColuna = new String [Equacao.quantTipoDesenho+1];
				String MIN = "", aux = "", aux1 = "";
				int contLinha = 1, contColuna = 1;				
			
				vetorLinha[0] = "0";
				vetorColuna[0] = "0";  		
	
					//Preenche os meus vetores com as respectivas variaveis
					for(int i = 0;i < Equacao.quantEquacoes+1; i++){
						MIN = vetorEquacao[i];			
						for(int j = 0; j< MIN.length(); j++){
								if(MIN.charAt(j) == 'x')
								{
									aux1 = ""+MIN.charAt(j); 
									aux = aux1.concat(""+MIN.charAt(j+1));
									vetorColuna[contColuna] = aux;
									contColuna++;
								} else{
										if(MIN.charAt(j) == 'a' || MIN.charAt(j) == 'b'){
												aux1 = ""+MIN.charAt(j); 
												aux = aux1.concat(""+MIN.charAt(j+1));
												vetorLinha[contLinha] = aux;
												contLinha++;
												j = MIN.length();
											}
				
									}			
						}			
			}		
		}
			//Clona a matriz 
			public static float[][] ClonarMatriz(float Matriz[][]){
					float m [][] = new float[Equacao.quantEquacoes+1][Equacao.quantTipoDesenho+1]; 
				for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
						m[linha1][coluna1] = Matriz[linha1][coluna1];
					}
				}
				return m;
			}
		
			private static void MontarResposta(){
				String guardaString = "", aux = "";	
				float valorFinal = matrizPrincipal[0][0];
				
				if(valorFinal < 0) valorFinal = valorFinal*(-1);
				aux = aux.concat(""+valorFinal);
				resposta = "O valor total de desenhos é:" +aux+"."; 
				
					for (int j = 1; j < Equacao.quantEquacoes+1; j++){
							guardaString = vetorLinha[j]; 
								for(int i = 0; i < guardaString.length(); i++){			
									if(guardaString.charAt(i) == 'x'){
										
										resposta = resposta.concat("Quantidade de desenho do tipo"+guardaString.charAt(++i)+" é: " +matrizPrincipal[j][0]+". ");										 	
								}					
							}															
					}
			}

			//Mostrar minha matriz
			private static void MostrarMatriz(float [][] m){
				for(int linha1 = 0; linha1 < Equacao.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Equacao.quantTipoDesenho+1; coluna1++){
						System.out.print(m[linha1][coluna1] +"\t");						
					}
						System.out.println();
				}
					System.out.println();
			}

		private static void executar(){
			Equacao a = new Equacao();
			a.executar();	
			vetorEquacao = a.vetorEquacao;
			CriarMatriz();
			MetodoFase1();
			MetodoFase2();		
			MontarResposta();	
			System.out.println();
			System.out.println(resposta);
			System.out.println();
		}

		public static void main (String []args){
			executar();
		}
			
}//fim class

