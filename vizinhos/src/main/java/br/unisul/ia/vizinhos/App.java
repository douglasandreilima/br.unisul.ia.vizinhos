package br.unisul.ia.vizinhos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App {

	private static Logger LOG = Logger.getLogger(App.class);

	private static final Double[] RETORNO_ANUAL = { 0.42292, 0.25685, 0.04128, 0.05812, 0.01731 };

	public static void main(String[] args) {

		LOG.info("Iniciando aplicação");

		Integer[] initialSolution1 = { 30, 25, 20, 15, 10 };

		Integer[] initialSolution2 = { 25, 30, 20, 15, 10 };

		Integer[] initialSolution3 = { 20, 25, 30, 15, 10 };

		Integer[] initialSolution4 = { 15, 25, 20, 30, 10 };

		Integer[] initialSolution5 = { 10, 25, 20, 15, 30 };

		List<Integer[]> solutions = Arrays.asList(initialSolution1, initialSolution2, initialSolution3,
				initialSolution4, initialSolution5);

		int cont = 1;
		for (Integer[] solution : solutions) {
			LOG.info("Iniciando simulação=" + cont);
			cont++;
			new App().start(solution);
		}
	}

	private void start(Integer[] solution) {
		Integer[] bestSolution = solution;
		Integer[] bestNeighbor = null;

		LOG.info("Solução inicial = " + Arrays.toString(solution) + " e retorno = " + calcReturn(solution));
		do {
			LOG.info("Gerando vizinhos");
			List<Integer[]> neighbors = getNeighbors(bestSolution);

			bestNeighbor = getBestNeighbor(neighbors, bestSolution);

			if (bestNeighbor != null) {

				bestSolution = bestNeighbor;
			}
		} while (bestNeighbor != null);

		LOG.info("Melhor solução é = " + Arrays.toString(bestSolution) + " e retorno = " + calcReturn(bestSolution));

	}

	private List<Integer[]> getNeighbors(Integer[] solution) {
		List<Integer[]> neighbors = new ArrayList<Integer[]>();

		for (int i = 0; i < 5; i++) {
			Integer[] aux = solution.clone();
			if (aux[i] == 100) {
				continue;
			}
			aux[i] = aux[i] + 5;
			for (int j = 0; j < 5; j++) {
				if (i == j || aux[j] == 0) {
					continue;
				}
				Integer[] neighbor = aux.clone();
				neighbor[j] = aux[j] - 5;
				neighbors.add(neighbor);
			}
		}

		return neighbors;

	}

	private Integer[] getBestNeighbor(List<Integer[]> neighbors, Integer[] bestSolution) {

		Integer[] bestNeighbor = bestSolution.clone();
		boolean hasBestSolution = false;

		for (Integer[] neighbor : neighbors) {
			Double returnBestSolution = calcReturn(bestNeighbor);
			Double returnNeighbor = calcReturn(neighbor);

			if (returnNeighbor > returnBestSolution) {
				bestNeighbor = neighbor;
				hasBestSolution = true;
			}
		}
		if (hasBestSolution) {
			return bestNeighbor;
		} else {
			return null;
		}
	}

	private Double calcReturn(Integer[] solution) {

		Double result = 0.0;
		for (int i = 0; i < 5; i++) {
			Double percentInvest = new Double(solution[i]) / 100;
			result += percentInvest * RETORNO_ANUAL[i];
		}
		return result;
	}
}
