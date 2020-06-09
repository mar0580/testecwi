import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class TesteCWI {

	public static void main(String[] args) {
		Path path = Paths.get(System.getProperty("user.dir") + "/data/in");

		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			startListening(watchService);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("done");
	}

	private static void startListening(WatchService watchService) throws InterruptedException {

		ReadFile rf = new ReadFile();
		while (true) {
			WatchKey queuedKey = watchService.take();
			for (WatchEvent<?> event : queuedKey.pollEvents()) {
				@SuppressWarnings("rawtypes")
				WatchEvent.Kind kind = event.kind();

				if (kind == ENTRY_CREATE) {
					rf.processInputFile(System.getProperty("user.dir") + "/data/in/" + event.context());
				}
				if (!queuedKey.reset()) {
					break;
				}
			}
		}
	}
}