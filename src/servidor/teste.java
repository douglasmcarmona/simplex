public class teste{
	private static float [][] matrizEquacao;
	private static String [] vetor; 
	private static float [][] matrizTransformada;
	/*Construtor para pegar o vetor passado como parametro
	*Salva o vetor passado para ser inserido na matriz
	*/
	public teste(String[] v) {
		this.vetor = v;
	}
	
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

		matrizEquacao = new float[Simplex.quantEquacoes+1][Simplex.quantTipoDesenho+1];

		for(int linha1 = 0; linha1 < Simplex.quantEquacoes+1; linha1++ ){
			for(int coluna1 = 0; coluna1 < Simplex.quantTipoDesenho+1; coluna1++){
						matrizEquacao[linha1][coluna1] = Float.parseFloat(""+0);
				}
			}

		//insere valores da equacao de otimizacao
		s = vetor[0];
		for(int j = 1; j < s.length();j++){
					if(s.charAt(j-1) != 'x'){	
							if(s.charAt(j) >= '0' && s.charAt(j) <= '9'){ 
									contNum++;
									aux = aux.concat(""+s.charAt(j));//concatena cada parte do meu numero ate encontrar algo diferente de um numero			
							}else{
								if(contNum == 1){
										if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9'){
												matrizEquacao[linha][coluna] = Float.parseFloat(aux);	
												aux = "";
												coluna++;
												contNum = 0;
												
											}
									}else {									
												if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9'){
													if(s.charAt(j-(contNum+1)) == '+'){
														convert = Float.parseFloat(aux);
														matrizEquacao [linha][coluna] = convert;
														aux = "";
														coluna++;													
														contNum = 0;
														}else{
															if(s.charAt(j-contNum)	== '-')	{
																convert = (Float.parseFloat(aux)*-1);
																matrizEquacao [linha][coluna] = convert;
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

	//Insere os valores que estao nas inequacoes
		for(int i = 1; i < vetor.length; i++){
				s = vetor[i];	
				coluna = 0;
				linha++;
				for(int j = 4 ; j < s.length();j++){
						if(s.charAt(j-1) != 'x'){	//Verifica se a posicao anterior e diferente de x
							if(s.charAt(j) >= '0' && s.charAt(j) <= '9'){ 
									contNum++;
									aux = aux.concat(""+s.charAt(j));//concatena cada parte do meu numero ate encontrar algo diferente de um numero	
								}else{	
									//Verifica se o tamano do numero 
									if(contNum == 1){
										if(s.charAt(j-1) >= '0' && s.charAt(j-1) <= '9'){
												//Verifica se o o sina lque esta na frente do numero e + ou -
												if(s.charAt(j-(contNum+1)) == '+'){
															convert = Float.parseFloat(aux);
															matrizEquacao [linha][coluna] = convert;
															aux = "";
															coluna++;													
															contNum = 0;
														}else{
															if(s.charAt(j-(contNum+1)) == '-')	{
																convert = Float.parseFloat(aux);
																convert = (convert *-1);
																matrizEquacao [linha][coluna] = convert;
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
															matrizEquacao [linha][coluna] = convert;
															aux = "";		
															coluna++;										
															contNum = 0;
														}else{
															if(s.charAt(j-(contNum+1)) == '-')	{
																convert = Float.parseFloat(aux);
																convert = (convert *-1);
																matrizEquacao [linha][coluna] = convert;
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
											matrizEquacao [linha][salvarPosicao] = convert;
											aux = "";												
											contNum = 0;
										}else{
												if(s.charAt(j-2) == '-')	{
													convert = Float.parseFloat(""+1);
													convert = (convert *-1);
													int salvarPosicao = Integer.parseInt(""+s.charAt(j));
													matrizEquacao [linha][salvarPosicao] = convert;
													aux = "";																
													contNum = 0;
												}
											}	
								}				
							}
					}
					MostrarMatriz(matrizEquacao);
					MetodoFase1(matrizEquacao);
			}

			private static void MetodoFase1(float [][] matriz){
				boolean	Testelinha,Testecoluna;					
				matrizTransformada = new float[Simplex.quantEquacoes+1][Simplex.quantTipoDesenho+1]; 
					
				float  MinQuociente;
				int cont, posicaoQuociente, MinLinha, MinColuna;
				boolean linha, coluna;
				float []VetorQuociente;

			do{
			
				MinQuociente = 0;
				cont = 0;posicaoQuociente = 0;MinLinha = 0;MinColuna = 0;
				linha = false; coluna = false;
				Testelinha = false;
				Testecoluna = false;	

				for(int linha1 = 0; linha1 < Simplex.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Simplex.quantTipoDesenho+1; coluna1++){
						matrizTransformada[linha1][coluna1] = Float.MIN_VALUE;
				}
			}
		
				//Verfica se existe valores negativos
				//Pesquisa a linha e coluna permissivel
				for(int linha1 = 1; linha1 < Simplex.quantEquacoes+1; linha1++ ){								
						if(matriz[linha1][0] < 0){
								MinLinha = linha1;
								linha = true;
									for(int coluna1 = 1; coluna1 < Simplex.quantTipoDesenho+1; coluna1++){
										if(matriz[linha1][coluna1] < 0){
												MinColuna = coluna1;
												coluna = true;	
												coluna1 = Simplex.quantTipoDesenho+1;		
											}	
									}
						}		
					}
				
				if(linha != true) MetodoFase2(matriz);

				//Preenche o vetor com a menor variavel para verificacao de menor quociente
				VetorQuociente = new float[Simplex.quantEquacoes+1];
				for(int i = 0; i < VetorQuociente.length; i++){
					VetorQuociente[i] = Float.MIN_VALUE;	
				}
		
				//Preenche o vetor com os possiveis quocientes
				for(int m = 1; m < Simplex.quantEquacoes+1; m++){
					if((matriz[m][MinColuna] < 0 && matriz[m][0] < 0) || (matriz[m][MinColuna] > 0 && matriz[m][0] > 0)){
							VetorQuociente[cont] = matriz[m][0]/matriz[m][MinColuna];
					}		
				cont++;	
				}		
					
				//Verifica qual e o menor quociente
				//Pega posicao em que o menor quociente esta
				MinQuociente = VetorQuociente[0];
				for(int QQ = 1; QQ < VetorQuociente.length; QQ++){
					if(VetorQuociente[QQ] != Float.MIN_VALUE && MinQuociente >= VetorQuociente[QQ]){
							MinQuociente = VetorQuociente[QQ];	
							posicaoQuociente = QQ;
						}								
				}
			//Pega o valor do menor quociente e o inverte 
			if(posicaoQuociente+1 != Simplex.quantEquacoes+1){
			matrizTransformada[posicaoQuociente+1][MinColuna] = 1/matriz[posicaoQuociente+1][MinColuna];
			}	else{ 
			matrizTransformada[posicaoQuociente][MinColuna] = 1/matriz[posicaoQuociente][MinColuna];
				}
			
			//Multiplicacao da linha pelo valor inverso
			for(int j = 0; j < Simplex.quantTipoDesenho+1; j++ ){
				if(j != MinColuna){
					matrizTransformada[posicaoQuociente+1][j] = matrizTransformada[posicaoQuociente+1][MinColuna]*matriz[posicaoQuociente+1][j];
				}
			}
			//Multiplicacao da coluna pelo valor inverso negativo
			matrizTransformada[posicaoQuociente+1][MinColuna] = (matrizTransformada[posicaoQuociente+1][MinColuna])*(-1);
			for(int i = 0; i < Simplex.quantEquacoes+1; i++){
				if(i != posicaoQuociente+1){
					matrizTransformada[i][MinColuna] = matrizTransformada[posicaoQuociente+1][MinColuna]*matriz[i][MinColuna];
				}
			}	
			//Preenchimento do resto da matriz
				int PercorreLinha = 0, i = 0, j = 0;
				float aux = 0;
				while(i < Simplex.quantTipoDesenho+1 ){
					if(PercorreLinha != MinColuna){
						while(j < Simplex.quantEquacoes+1){
							if(matrizTransformada[j][i] == Float.MIN_VALUE){	
								if(j != posicaoQuociente+1 && i != MinColuna){
									aux =  matriz[posicaoQuociente+1][PercorreLinha]* matrizTransformada[j][MinColuna];
									aux = matriz[j][i]+aux;
									matrizTransformada[j][i] = aux;
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
				for(int linha1 = 0; linha1 < Simplex.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Simplex.quantTipoDesenho+1; coluna1++){
						if(matrizTransformada[linha1][coluna1] == -0) matrizTransformada[linha1][coluna1] = 0;
				}
			}
		
				matrizTransformada[posicaoQuociente+1][MinColuna] = (matrizTransformada[posicaoQuociente+1][MinColuna])*(-1);
				//Verifica posicao negativa
				for(int linha1 = 1; linha1 < Simplex.quantEquacoes+1; linha1++ ){								
						if(matrizTransformada[linha1][0] < 0){
								Testelinha = true;
						}		
					}

					matriz = ClonarMatriz(matrizTransformada);
					MostrarMatriz(matrizTransformada);
		}while(Testelinha == true);


			MetodoFase2(matrizTransformada);									
	}//FimMetodoFase1		

			public static void MetodoFase2(float matriz [][]){
				System.out.println("Entrei aqui!");			
			}


			public static float[][] ClonarMatriz(float Matriz[][]){
					float m [][] = new float[Simplex.quantEquacoes+1][Simplex.quantTipoDesenho+1]; 
				for(int linha1 = 0; linha1 < Simplex.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Simplex.quantTipoDesenho+1; coluna1++){
						m[linha1][coluna1] = Matriz[linha1][coluna1];
					}
				}
				return m;
			}
			

		

			//Mostrar minha matriz
			private static void MostrarMatriz(float [][] m){
				for(int linha1 = 0; linha1 < Simplex.quantEquacoes+1; linha1++ ){
					for(int coluna1 = 0; coluna1 < Simplex.quantTipoDesenho+1; coluna1++){
						System.out.print(m[linha1][coluna1] +"\t");						
					}
						System.out.println();
				}
					System.out.println();
			}
			
}//fim class
