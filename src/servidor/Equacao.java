package servidor;

public class Equacao {
	static int MINorMAX = 0;
	public static int quantEquacoes = 0;
	static int contA = 0, contB = 0;
	public static int quantTipoDesenho = 0;
	public static String[] vetorEquacao;
	public Formulario form;

	/*
	 * O metodo salva as equacoes digitadas em um vetor de equacao
	 */
	public Equacao(Formulario f) {
		this.form = f;
		int cont = 1;
		String EquacaoMM;
		String[] vetor;

		try {
			MINorMAX = 1;
			quantEquacoes = form.getQtdeDesenhos();			
			vetor = new String[(quantEquacoes + 1)];
			quantTipoDesenho = form.getQtdeDesenhos();
			EquacaoMM = "";
			int contX = 1;
			//Faz a montagem da equacao objetiva
			for(; contX<quantTipoDesenho; contX++) {//funcao objetiva
				EquacaoMM += "1x" + contX + "+";
			}
			
			EquacaoMM += "1x" + contX;
			vetor[0] = EquacaoMM;
			EquacaoMM = "";
			//faz a montagem das equacoes das restricoes
			while (cont <= quantEquacoes) {
				for(contX=1; contX<quantTipoDesenho; contX++) {
					 EquacaoMM += form.getTempoPorDesIPorEtapaJ(contX, cont) + "x" + contX + "+";
					 System.out.println("1 " + EquacaoMM);
				}
				EquacaoMM += form.getTempoPorDesIPorEtapaJ(contX, cont) + "x" + quantTipoDesenho;
				EquacaoMM += "<=" + form.getTempoPorEtapaX(cont);
				vetor[cont] = EquacaoMM;
				cont++;
				EquacaoMM = "";
			}
			//MostrarVetor(vetor);
			vetorEquacao = ClonarVetor(vetor);
		} 
		catch (Exception excecao) {
			System.out.println("Excecao: ");
			excecao.printStackTrace();
		}
	}
	
	/**
	 * Metodo criado para fazer a primeira manipulacao realizada na equacao antes de passar para o simplex
	 */
	public static void TratarEquacoes() {
		String MIN = " ";
		String aux = " ";
		String NovoValor = " ";
		String aux1 = " ", aux2 = " ";

		// Insercao do + na frente do primeiro numero
		if (MINorMAX == 1 || MINorMAX == 2) {
			MIN = vetorEquacao[0];
			if (MIN.charAt(0) >= 'a' && MIN.charAt(0) <= 'z') {
				MIN = "+".concat(MIN);
			} else if (MIN.charAt(0) >= '0' && MIN.charAt(0) <= '9') {
				MIN = "+".concat(MIN);
			}
		} else {
			System.out.println("Valor de Min e MAX errado!");
		}

		// Transformacao de MAX para MIN
		if (MINorMAX == 1) {
			if (MIN.charAt(0) == '-')
				MIN = "+".concat(MIN.substring(1, MIN.length()));
			else if (MIN.charAt(0) == '+')
				MIN = "-".concat(MIN.substring(1, MIN.length()));

			//Transforma o resto dos sinais que estao presentes na equacao
			 
			for (int i = 1; i < MIN.length(); i++) {
				if (MIN.charAt(i) == '-') {
					MIN = MIN.substring(0, (i - 1)) + "+".concat(MIN.substring(i + 2, MIN.length()));
				} else if (MIN.charAt(i) == '+') {
					aux = MIN.substring(0, (i)).concat("-");
					MIN = aux.concat(MIN.substring(i + 1, MIN.length()));
				}
			}
		}

		vetorEquacao[0] = MIN;

		// Transformacao das equacoes para insercao da variavel livre
		for (int i = 1; i < vetorEquacao.length; i++) {
			MIN = vetorEquacao[i];
			for (int j = 0; j < MIN.length(); j++) {
				//Faz as modificacoes nas restricoes de acordo com o sinal da equacao
				if (MIN.charAt(j) == '<') {
					if (MIN.charAt(j + 1) == '=') {
						NovoValor = InserirValorEquacaoA(); //Insere as variaveis livres
						aux1 = "+".concat(NovoValor);
						aux2 = aux1.concat("=");
						aux = MIN.substring(0, (j)).concat(aux2);
						MIN = aux.concat(MIN.substring(j + 2, MIN.length()));
					} else {
						NovoValor = InserirValorEquacaoA(); //Insere as variaveis livres
						aux1 = "+".concat(NovoValor);
						aux2 = aux1.concat("=");
						aux = MIN.substring(0, j).concat(aux2);
						MIN = aux.concat(MIN.substring(j + 1, MIN.length()));
					}
				} else if (MIN.charAt(j) == '>') {
					if (MIN.charAt(j + 1) == '=') {
						NovoValor = InserirValorEquacaoB(); //Insere as variaveis livres
						aux1 = "-".concat(NovoValor);
						aux2 = aux1.concat("=");
						aux = MIN.substring(0, (j)).concat(aux2);
						MIN = aux.concat(MIN.substring(j + 2, MIN.length()));
					} else {
						NovoValor = InserirValorEquacaoB(); //Insere as variaveis livres
						aux1 = "-".concat(NovoValor);
						aux2 = aux1.concat("=");
						aux = MIN.substring(0, j).concat(aux2);
						MIN = aux.concat(MIN.substring(j + 1, MIN.length()));
					}
				}
			}
			vetorEquacao[i] = MIN;
		}
	}// fim TratarEquacoes
	
	/**
	 * Realiza todas as outras modificaçoes na equacao 
	 * EX: -b0 = +24-80x1-60xs2 e +b0 = -24 -(-80x1-60x2)
	 */
	public static void ManipulacaoString() {
		String MIN = "";
		String aux = "";
		String aux1 = "";
		boolean teste = true;

		for (int i = 1; i < vetorEquacao.length; i++) {
			MIN = vetorEquacao[i];
			for (int j = MIN.length() - 1; j > 0; j--) {
				if (MIN.charAt(j) == 'a' || MIN.charAt(j) == 'b') {
					if (MIN.charAt(j - 1) == '-') {
						// +b=
						aux = "+".concat(MIN.substring(j, j + 3));
						if (MIN.charAt(j + 3) >= '0' && MIN.charAt(j + 3) <= '9') {
							// -24
							aux1 = "-".concat(MIN.substring(j + 3, MIN.length()));
						} else {
							System.out.println("Valor nao esperado!");
						}
						// +b=-24
						aux = aux.concat(aux1);
						if (MIN.charAt(0) >= '0' && MIN.charAt(0) <= '9')
							aux1 = "+".concat(MIN.substring(0, j - 1));
						if (MIN.charAt(0) >= 'a' && MIN.charAt(0) <= 'z')
							aux1 = "+".concat(MIN.substring(0, j - 1));
						// +b=-24+4x+6y
						MIN = aux.concat(aux1);
						teste = false;

					} else if (teste && MIN.charAt(j - 1) == '+') { //verifica se qual e o sinal que esta antes da variavel livre inserida
						aux = MIN.substring(j - 1, j + 3);
						aux1 = aux.concat("+");
						aux = aux1.concat(MIN.substring(j + 3, MIN.length()));
						if (MIN.charAt(0) >= '0' && MIN.charAt(0) <= '9') {
							aux1 = "-".concat(MIN.substring(0, j - 1));
							aux = aux.concat(aux1);
						}
						if (MIN.charAt(0) >= 'a' && MIN.charAt(0) <= 'z') {
							aux1 = "-".concat(MIN.substring(0, j - 1));
							aux = aux.concat(aux1);
							MIN = aux;
						}
						for (int k = 5; k < aux.length(); k++) {
							if (aux.charAt(k) == '+') {
								aux1 = "-".concat(aux.substring(k + 1, aux.length()));
								MIN = aux.substring(0, k).concat(aux1);
							}
						}
						j = 0;
					}
				}
			}
			teste = true;
			vetorEquacao[i] = MIN;
			// MostrarVetor(ManipularString);
		}
		// StringFinal(ManipularString);
	}// fim ManipulacaoString

	// Metodo criado para transformar a minha equacao
	public static void StringFinal() {
		String MIN = "", aux = "", aux1 = "", ParteString = "";
		boolean teste = true;
		//int num = 4;

		MIN = vetorEquacao[0];
		// Tranformacao da esquacao maximizada e minimizada
		aux = "+0-("; // insere na frente da funcao objetiva passada
		for (int i = 0; i < MIN.length(); i++) {
			if (MIN.charAt(i) == '+') {
				aux1 = aux.concat("-");
				if (i == 0) {
					aux = aux1.concat(ParteString);
				}
			} else {
				if (MIN.charAt(i) == '-') {
					aux1 = aux.concat("+");
					if (i == 0) {
						aux = aux1.concat(ParteString);
					}
				} else {// Concatenar numeros
					aux = aux1.concat("" + MIN.charAt(i));
					aux1 = aux;
				} // fim else
			} // fim else
		}
		MIN = aux.concat(")");
		vetorEquacao[0] = MIN;
		aux = "";
		aux1 = "";

		// Transformacao das inequacoes para a forma a = +ou-num -(expressao)
		for (int i = 1; i < vetorEquacao.length; i++) {
			MIN = vetorEquacao[i];
			int cont = 0;

			// Percorre a minha string contando o tamanho do numero
			for (int j = 5; j < MIN.length(); j++) {
				if (MIN.charAt(j) >= '0' && MIN.charAt(j) <= '9')
					cont++;
				else
					j = MIN.length();
			}
			aux = MIN.substring(0, (5 + cont));
			// System.out.println("auz4: " +aux1);
			aux = aux.concat("-(");
			// System.out.println("auz5: " +aux1);

			for (int j = 5 + cont; j < MIN.length(); j++) {
				if (MIN.charAt(j) == '+') {
					aux1 = aux.concat("-");
					teste = false;
				} else {
					if (teste && MIN.charAt(j) == '-') {
						aux1 = aux.concat("+");
					} else {
						aux = aux1.concat("" + MIN.charAt(j));
						aux1 = aux;
					} // fim else
				} // fim else
				teste = true;
			} // fim for

			MIN = aux.concat(")");
			vetorEquacao[i] = MIN;
			aux = "";
			aux1 = "";
		} // fim for

		MostrarVetor(vetorEquacao);
	}// fim StringFinal

	//Metodo criado para inserir variavel livre quando a equacao tiver <=
	public static String InserirValorEquacaoA() {
		String NovoValor = " ";
		NovoValor = "a".concat("" + contA);
		contA++;
		return NovoValor;
	}

	//Metodo criado para inserir variavel livre quando a equacao tiver >=
	public static String InserirValorEquacaoB() {
		String NovoValor = " ";
		NovoValor = "b".concat("" + contB);
		contB++;
		return NovoValor;
	}

	public static void MostrarVetor(String EquacaoVetor[]) {
		for (int i = 0; i < EquacaoVetor.length; i++) {
			System.out.println("Equacao:" + EquacaoVetor[i]);
		}
	}

	// Clona vetor
	public static String[] ClonarVetor(String vet[]) {
		String v[] = new String[quantEquacoes + 1];
		for (int linha1 = 0; linha1 < vet.length; linha1++) {
			v[linha1] = vet[linha1];
		}
		return v;
	}
	
	public static void inserirRestricaoBnBEsq(int x, int num) {
		String[] aux = new String[vetorEquacao.length + 1];
		for(int i=0; i<aux.length-1; i++) {
			aux[i] = vetorEquacao[i];
		}
		aux[aux.length-1] = "+" + InserirValorEquacaoA() + "=" + num + "-(+x" + x + ")";
		vetorEquacao = new String[aux.length];
		for(int i=0; i<vetorEquacao.length; i++)
			vetorEquacao[i] = aux[i];
		System.out.println("");
		quantEquacoes += 1;
		MostrarVetor(vetorEquacao);
	}
	
	public static void inserirRestricaoBnBDir(int x, float num) {
		String[] aux = new String[vetorEquacao.length + 1];
		for(int i=0; i<aux.length-1; i++) {
			aux[i] = vetorEquacao[i];
		}
		aux[aux.length-1] = "+" + InserirValorEquacaoA() + "=-" + num + "-(-x" + x + ")";
		vetorEquacao = new String[aux.length];
		for(int i=0; i<vetorEquacao.length; i++)
			vetorEquacao[i] = aux[i];
		System.out.println("");
		quantEquacoes += 1;
		MostrarVetor(vetorEquacao);
	}

	public static void executar() {
		TratarEquacoes();
		ManipulacaoString();
		StringFinal();
	}
}