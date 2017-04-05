package servidor;

import java.util.Map;

/**
 * Classe que trata os dados vindos do formulario para serem enviados para a classe do
 * método simplex
 * @author Douglas
 *
 */

public class Formulario {
	
	private int qtdeDesenhos;
	private int qtdeEtapas;
	private int[] minPorEtapa;
	private int[][] minPorDesPorEtapa;
	
	/**
	 * Construtor - instancia um objeto já com todos os valores alocados
	 * @param map: inputs do formulário no cliente
	 */
	public Formulario(Map<String, String[]> map) {
		qtdeDesenhos = Integer.parseInt(map.get("var_dec")[0]); // contem o numero digitado no input de tipos de desenhos	
		qtdeEtapas = Integer.parseInt(map.get("num_restr")[0]); // contem o numero digitado no input de quantidade de etapas
		minPorEtapa = new int[qtdeEtapas+1];
		minPorDesPorEtapa = new int[qtdeDesenhos+1][qtdeEtapas+1];
		/* percorre os inputs referentes aos minutos de cada etapa e aos minutos para
		cada etapa para cada desenho 
		OBS: os valores sao retornados como vetores de strings. Neste caso, basta acessar
		a primeira (e unica) posicao
		*/
		for(Map.Entry<String, String[]> par : map.entrySet()) {
			String chave = par.getKey();
			
			// se for input de minuto por etapa
			if(chave.startsWith("mins")) {
				if(chave.charAt(4) == '_') {
					String[] ss = chave.split("_");
					//ex: mins_etp_i_des_j = minutos para a etapa i do desenho j
					int i = Integer.parseInt(ss[2]),
						j = Integer.parseInt(ss[4]);
					
					minPorDesPorEtapa[j][i] = Integer.parseInt(par.getValue()[0]);
				}
				else {
					int pos = Integer.parseInt(chave.substring(4));
					minPorEtapa[pos] = Integer.parseInt(par.getValue()[0]);
				}
			}
			else continue;
		}
	}

	public int getQtdeDesenhos() {
		return qtdeDesenhos;
	}

	public void setQtdeDesenhos(int qtdeDesenhos) {
		this.qtdeDesenhos = qtdeDesenhos;
	}

	public int getQtdeEtapas() {
		return qtdeEtapas;
	}

	public void setQtdeEtapas(int qtdeEtapas) {
		this.qtdeEtapas = qtdeEtapas;
	}

	public int[] getMinPorEtapa() {
		return minPorEtapa;
	}

	public void setMinPorEtapa(int[] minPorEtapa) {
		this.minPorEtapa = minPorEtapa;
	}

	public int[][] getMinPorDesPorEtapa() {
		return minPorDesPorEtapa;
	}

	public void setMinPorDesPorEtapa(int[][] minPorDesPorEtapa) {
		this.minPorDesPorEtapa = minPorDesPorEtapa;
	}
	
	public int getMinPorEtapaX(int i) {
		return minPorEtapa[i];
	}
	
	public int getMinPorDesIPorEtapaJ(int i, int j) {
		return minPorDesPorEtapa[i][j];
	}
}
