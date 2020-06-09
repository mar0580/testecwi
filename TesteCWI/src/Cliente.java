import java.util.Scanner;

public class Cliente {

	public String processaDadosCliente(String dados) {

		String cnpj = null, name, businessArea;

		Scanner linhaScanner = new Scanner(dados);

		linhaScanner.useDelimiter("ç");

		while (linhaScanner.hasNext()) {
			cnpj = linhaScanner.next();
			name = linhaScanner.next();
			businessArea = linhaScanner.next();
		}
		
		linhaScanner.close();		
		return cnpj;
	}
}