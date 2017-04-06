package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Simplex {
	static int MINorMAX = 1;
	public static int quantEquacoes = 0;
	static int contA = 0,contB = 0; 
	public static int quantTipoDesenho = 0; 
	public static Formulario form;
	
		public void simplex(){
			
		}
		
		public static float[][] PegarEquacao(){
			System.out.println("0");
			int cont = 1;
		String []vetorEquacao;
		String EquacaoMM = "";
	
		//BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));  
			try{
				//System.out.println("Se deseja maximizar uma equacao digite 1.");
				//System.out.println("Se deseja minimizar uma equacao digite 2.");
				
				//MINorMAX = Integer.parseInt(leitor.readLine());
	
				//System.out.println("Quantas equacoes?");
				//quantEquacoes = Integer.parseInt(leitor.readLine());
				quantEquacoes = form.getQtdeEtapas();
				vetorEquacao = new String [(quantEquacoes+1)];

				//System.out.println("Quantos tipos de desenho?");
				//quantTipoDesenho = Integer.parseInt(leitor.readLine());
				quantTipoDesenho = form.getQtdeDesenhos();
				
				//System.out.println("Escreva a equacao que sera minimizada ou maximizada:");
				//EquacaoMM = leitor.readLine();			
				//EquacaoMM = EquacaoMM.toLowerCase().trim();
				//EquacaoMM = EquacaoMM.replace(" ","");
				int contX = 1;
				for(; contX<quantTipoDesenho; contX++) {
					EquacaoMM += "1x" + contX + "+";
				}
				
				EquacaoMM += "1x" + contX;
				vetorEquacao[0] =  EquacaoMM;
				/*vetorEquacao[1] = "4x1+6x2>=24";
				vetorEquacao[2] = "4x1+2x2<=16";
				vetorEquacao[3] = "x2<=3";*/
				
								
				while(cont <= quantEquacoes){
					//System.out.println("Digite as equacoes:");
					//EquacaoMM = leitor.readLine();
					//EquacaoMM = EquacaoMM.toLowerCase().trim();
					EquacaoMM = "";					
					for(contX=1; contX<quantTipoDesenho; contX++) {
						EquacaoMM += form.getMinPorDesIPorEtapaJ(contX, cont) + "x" + contX + "+";
					}
					EquacaoMM += form.getMinPorDesIPorEtapaJ(contX, cont) + "x" + quantTipoDesenho;
					EquacaoMM += "<=" + form.getMinPorEtapaX(cont);
					vetorEquacao[cont] = EquacaoMM;
					cont++;
				}	
				
				return TratarEquacoes(vetorEquacao);
				
			} catch (Exception excecao) {
		         System.out.println("Excecao: ");
							excecao.printStackTrace();
				}
			return null;
	}

		public static float[][] TratarEquacoes(String TratarString[]){
			System.out.println("1");
			String MIN = " ";	
			String aux = " ";
			String NovoValor = " ";
			String aux1 = " ", aux2 = " ";

			//Insercao do + na frente do primeiro numero
				if(MINorMAX == 1 || MINorMAX == 2){
					MIN = TratarString[0];
					if(MIN.charAt(0) >= 'a' && MIN.charAt(0) <= 'z'){
						MIN = "+".concat( MIN);						
					} else if(MIN.charAt(0) >= '0' && MIN.charAt(0) <= '9'){
						MIN = "+".concat(MIN);
						System.out.println("min: " + MIN);
					}			
				} else {
					System.out.println("Valor de Min e MAX errado!");			
				   }
			
			//Transformacao de MAX para MIN
			if(MIN.charAt(0) == '-')MIN = "+".concat(MIN.substring(1, MIN.length())); 		
				else if(MIN.charAt(0) == '+') MIN = "-".concat(MIN.substring(1, MIN.length()));
			
			for(int i = 1; i < MIN.length();i++){
				if(MIN.charAt(i) == '-' ){					
					MIN = MIN.substring(0,(i-1)) + "+".concat(MIN.substring(i+2, MIN.length()));
				}else if(MIN.charAt(i) == '+'){					
					aux = MIN.substring(0,(i)).concat("-");					
					MIN = aux.concat(MIN.substring(i+1, MIN.length()));						
				  }	
			}
			TratarString[0]	 = MIN;	
	
			//Transformacao das equacoes para insercao da variavel livre
			for(int i = 1; i<TratarString.length; i++){
				MIN = TratarString[i];			
				for(int j = 0; j< MIN.length(); j++){
					if(MIN.charAt(j) == '<'){
						if(MIN.charAt(j+1) == '='){ 
							NovoValor = InserirValorEquacaoA();	
							aux1 = "+".concat(NovoValor);	
							aux2 = aux1.concat("=");			
							aux = MIN.substring(0,(j)).concat(aux2); 
							MIN = aux.concat(MIN.substring(j+2, MIN.length()));	
						} else{
							NovoValor = InserirValorEquacaoA();	
							aux1 = "+".concat(NovoValor);	
							aux2 = aux1.concat("=");
							aux = MIN.substring(0,j).concat(aux2);
						  MIN = aux.concat(MIN.substring(j+1, MIN.length()));
 						}
					}else if(MIN.charAt(j) == '>'){
						if(MIN.charAt(j+1) == '='){
							NovoValor = InserirValorEquacaoB();	
							aux1 = "-".concat(NovoValor);	
							aux2 = aux1.concat("=");
							aux = MIN.substring(0,(j)).concat(aux2); 
							MIN = aux.concat(MIN.substring(j+2, MIN.length()));
						}else{ 
							NovoValor = InserirValorEquacaoB();	
							aux1 = "-".concat(NovoValor);	
							aux2 = aux1.concat("=");
					   	aux = MIN.substring(0,j).concat(aux2);
					  	MIN = aux.concat(MIN.substring(j+1, MIN.length()));
						}
					}					
				}
				TratarString[i] = MIN;				
			}	
				return ManipulacaoString(TratarString);	 
		}//fim TratarEquacoes

		public static float[][] ManipulacaoString (String ManipularString[]){
			System.out.println("2");
			String MIN = "";	
			String aux = "";
			String aux1 = ""; 			
			boolean teste = true;
			for(int i = 1; i<ManipularString.length ;i++){
				MIN = ManipularString[i];
				for(int j = MIN.length()-1; j> 0; j--){
					if(MIN.charAt(j) == 'a' || MIN.charAt(j) == 'b'){
							if(MIN.charAt(j-1) == '-'){
								//+b=
								aux="+".concat(MIN.substring(j,j+3));
									if(MIN.charAt(j+3) >= '0' && MIN.charAt(j+3) <= '9'){
											//-24
											aux1="-".concat(MIN.substring(j+3,MIN.length()));
									}	else {System.out.println("Valor nao esperado!");}		
										//+b=-24																			
										aux = aux.concat(aux1);
										if(MIN.charAt(0)>= '0' && MIN.charAt(0) <= '9')aux1 = "+".concat(MIN.substring(0,j-1));
										if(MIN.charAt(0)>= 'a' && MIN.charAt(0) <= 'z')aux1 = "+".concat(MIN.substring(0,j-1));		
										//+b=-24+4x+6y													
										MIN = aux.concat(aux1);
										teste = false;
														
									}else if(teste && MIN.charAt(j-1) == '+') {																	
											aux = MIN.substring(j-1,j+3);
											aux1 = aux.concat("+");
											aux = aux1.concat(MIN.substring(j+3,MIN.length()));					
											if(MIN.charAt(0)>= '0' && MIN.charAt(0) <= '9'){
													aux1 = "-".concat(MIN.substring(0,j-1));
													aux = aux.concat(aux1);
												}
											if(MIN.charAt(0)>= 'a' && MIN.charAt(0) <= 'z'){
												aux1 = "-".concat(MIN.substring(0,j-1));
												aux = aux.concat(aux1);
												MIN = aux;								
											}	
											for(int k = 5; k< aux.length();k++){											
												if(aux.charAt(k) == '+'){	
													aux1 = "-".concat(aux.substring(k+1,aux.length()));
													MIN = aux.substring(0,k).concat(aux1);											
												}	
											}	
												j = 0;			
									}
							}																
					}
					teste = true;
					ManipularString[i] = MIN;
				}
				return StringFinal(ManipularString);
		}//fim ManipulacaoString 
		
			//Metodo criado para transformar a minha equacao 
			public static float[][] StringFinal(String [] stringFinal){
				System.out.println("3");
				String MIN = "", aux = "", aux1 = "", ParteString = "";
				boolean teste = true; 
				int num = 4;

				MIN = stringFinal[0]; 
								
				//Tranformacao da esquacao maximizada e minimizada
					aux = "+0-(";
					for(int i = 0; i < MIN.length(); i++){
							if(MIN.charAt(i) == '+'){		
								aux1 = aux.concat("-");
								if(i == 0){
										aux = aux1.concat(ParteString);
								}else{	
										aux = aux1.concat(""+MIN.charAt(i));
										aux1 = aux;
									}			
							}else{
								if(MIN.charAt(i) == '-'){
										aux1 = aux.concat("+");
										if(i == 0){ aux = aux1.concat(ParteString);}
									}else{
 											aux = aux1.concat(""+MIN.charAt(i));
											aux1 = aux;
										}//fim else																						
							}//fim else
						}	
							MIN = aux.concat(")");
							stringFinal[0] = MIN;
							aux = "";
							aux1 = "";

				//Transformacao das inequacoes para a forma a = +ou-num -(expressao)			
				for(int i = 1; i < stringFinal.length ;i++){
						MIN = stringFinal[i];
						int cont = 0;

					//Percorre a minha string contando o tamanho do numero
					for(int j = 5; j < MIN.length(); j++ ){
							if(MIN.charAt(j) >= '0' && MIN.charAt(j) <= '9')cont++;
							else j = MIN.length();
							}

						aux = MIN.substring(0,(5+cont));
						aux = aux.concat("-(");							
							for(int j = 5+cont; j < MIN.length(); j++){						
									if(MIN.charAt(j) == '+'){		
										aux1 = aux.concat("-");
										teste = false;
									}else{
										if(teste && MIN.charAt(j) == '-'){
												aux1 = aux.concat("+");				
										}else{
 												aux = aux1.concat(""+MIN.charAt(j));
												aux1 = aux;
										}//fim else																						
									}//fim else
							teste = true;	
							}//fim for

					MIN = aux.concat(")");
					stringFinal[i] = MIN;
					aux = "";
					aux1 = "";
				}//fim for

		//MostrarVetor(stringFinal);		
		teste simplex = new teste(stringFinal);
 		return simplex.CriarMatriz( ); 
	}//fim StringFinal

	public static String InserirValorEquacaoA(){
		System.out.println("a");
			String NovoValor = " ";			
			NovoValor = "a".concat(""+contA);
			contA++;		
			return NovoValor;
	}

	public static String InserirValorEquacaoB(){
		System.out.println("b");
			String NovoValor = " ";			
			NovoValor = "b".concat(""+contB);
			contB++;		
			return NovoValor;
		}	

	public static void MostrarVetor(String EquacaoVetor[]){
		for(int i = 0; i < EquacaoVetor.length; i++){
			System.out.println("Equacao:" +EquacaoVetor[i]);					
		}		
	}
}
	
	
	
	
	
	
	
	
