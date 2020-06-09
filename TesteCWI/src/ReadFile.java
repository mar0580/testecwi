import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ReadFile {

	Vendedor vendedor = new Vendedor();
	Cliente cliente = new Cliente();
	WriteFile wf = new WriteFile();	

	public void processInputFile(String file) {
		
		HashSet<String> setVendedor = new HashSet<String>();		
		HashSet<String> setCliente = new HashSet<String>();
		Map<String, Double> setVenda = new HashMap<String, Double>();
		Map<String, Double> qtdVendaPorVendedor = new HashMap<String, Double>();
		
		try {
			File f = new File(file);
			Scanner sc = new Scanner(f);
			
			// enquanto houver proxima linha, avanca
			while (sc.hasNextLine()) {

				String identificador = sc.nextLine();
				
				// interpreta somente os 3 primeiros digitos
				switch (identificador.substring(0, 3).trim()) {
				case "001":
					setVendedor.add(vendedor.processaDadosVendedor(identificador.substring(3)));					
					break;
				case "002":
					setCliente.add(cliente.processaDadosCliente(identificador.substring(3)));					
					break;
				case "003":
					String saleId, itemId;
					double totalItens = 0, itemQuantity, itemPrice;

					Scanner lineScanner = new Scanner(identificador.substring(3));
					lineScanner.useDelimiter("ç");

					while (lineScanner.hasNext()) {
						saleId = lineScanner.next();// Sale ID
						itemId = lineScanner.next(); // Item ID
						Scanner itemIdScanner = new Scanner(itemId);
						itemIdScanner.useDelimiter(",");

						// laço responsavel por tratar os itens de venda de cada vendedor
						while (itemIdScanner.hasNext()) {
							// remove colchetes
							String[] item = itemIdScanner.next().replaceAll(".*\\[|\\].*", "").split("-");
							itemQuantity = Double.valueOf(item[1]);
							itemPrice = Double.valueOf(item[2]);
							totalItens += itemQuantity * itemPrice; // calcula o valor total por Item ID
						}
						
						setVenda.put(saleId, totalItens);

						String salesMan = lineScanner.next();
						qtdVendaPorVendedor.put(salesMan, totalItens);

						itemIdScanner.close();
					}

					lineScanner.close();
					break;
				default:
					System.out.println("Identificador nao relacionado");
				}
			}

			// pega o valor maximo de venda
			String idVenda = null;
			double vendaMaisCara = Collections.max(setVenda.values());
			for (Entry<String, Double> entry : setVenda.entrySet()) {
				if (entry.getValue() == vendaMaisCara) {
					idVenda = entry.getKey();
				}
			}

			// pega o pior vendedor
			String salesManName = null;
			double piorVendedor = Collections.min(qtdVendaPorVendedor.values());
			for (Entry<String, Double> entry : qtdVendaPorVendedor.entrySet()) {
				if (entry.getValue() == piorVendedor) {
					salesManName = entry.getKey();
				}
			}
			
			List<String> listOutputFileInfo = new ArrayList<String>();			

			// adiciona as informacoes na lista
			listOutputFileInfo.add(setVendedor.size() + " vendedor(es) no arquivo");
			listOutputFileInfo.add(setCliente.size() + " cliente(s) no arquivo");
			listOutputFileInfo.add("ID de venda mais cara: " + idVenda + " - R$ " + vendaMaisCara);
			listOutputFileInfo.add("Pior vendedor: " + salesManName + " - R$ " + piorVendedor);
					
			// envia as informacoes para escrita no arquivo
			wf.processOutputFile(f.getName(), listOutputFileInfo);

			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}