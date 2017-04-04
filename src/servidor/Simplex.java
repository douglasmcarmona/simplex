import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Simplex {
	static int MINorMAX = 0;
	public static int quantEquacoes = 0;
	static int contA = 0,contB = 0; 
	public static int quantTipoDesenho = 0; 

		public void simplex(){}
		
		public static void PegarEquacao(){
		int cont = 1;
		String []vetorEquacao;
		String EquacaoMM;
	
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));  
			try{
				System.out.println("Se deseja maximizar uma equacao digite 1.");
				System.out.println("Se deseja minimizar uma equacao digite 2.");
				
				MINorMAX = Integer.parseInt(leitor.readLine());
	
				System.out.println("Quantas equacoes?");
				quantEquacoes = Integer.parseInt(leitor.readLine());
				vetorEquacao = new String [(quantEquacoes+1)];

				System.out.println("Quantos tipos de desenho?");
				quantTipoDesenho = Integer.parseInt(leitor.readLine());
				
				System.out.println("Escreva a equacao que sera minimizada ou maximizada:");
				EquacaoMM = leitor.readLine();			
				EquacaoMM = EquacaoMM.toLowerCase().trim();
				EquacaoMM = EquacaoMM.replace(" ","");

				vetorEquacao[0] =  EquacaoMM;
								
				while(cont <= quantEquacoes){
					System.out.println("Digite as equacoes:");
					EquacaoMM = leitor.readLine();
					EquacaoMM = EquacaoMM.toLowerCase().trim();
					vetorEquacao[cont] = EquacaoMM;		
					cont++;
				}	
	
				TratarEquacoes(vetorEquacao);												
				
			} catch (Exception excecao) {
		         System.out.println("Excecao: ");
							excecao.printStackTrace();
				}
	}

		public static void TratarEquacoes(String TratarString[]){
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
				ManipulacaoString(TratarString);		 
		}//fim TratarEquacoes

		public static void ManipulacaoString (String ManipularString[]){
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
				StringFinal(ManipularString);
		}//fim ManipulacaoString 
		
			//Metodo criado para transformar a minha equacao 
			public static void StringFinal(String [] stringFinal){
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

		MostrarVetor(stringFinal);		
		teste simplex = new teste(stringFinal);
 		simplex.CriarMatriz( ); 
	}//fim StringFinal

	public static String InserirValorEquacaoA(){
			String NovoValor = " ";			
			NovoValor = "a".concat(""+contA);
			contA++;		
			return NovoValor;
	}

	public static String InserirValorEquacaoB(){
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
		
	public static void main (String args[]){
		PegarEquacao();
		
		
	}
}
	
	
	
	
	
	
	
	
