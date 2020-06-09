import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WriteFile {

	public void processOutputFile(String fileOut, List<String> informacoesArquivoSaida) {

		Charset utf8 = StandardCharsets.UTF_8;

		try {
			Files.write(Paths.get(System.getProperty("user.dir") + "/data/out/" + fileOut), informacoesArquivoSaida,
					utf8);
		
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
}