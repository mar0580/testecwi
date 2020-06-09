import java.util.Scanner;

public class Vendedor {

	public String processaDadosVendedor(String dados) {

		String cpf = null, name, salary;

		Scanner linhaScanner = new Scanner(dados);

		linhaScanner.useDelimiter("ç");

		while (linhaScanner.hasNext()) {
			cpf = linhaScanner.next();
			name = linhaScanner.next();
			salary = linhaScanner.next();							
		}
		
		linhaScanner.close();		
		return cpf;
	}
}