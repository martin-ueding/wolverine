import java.io.IOException;
import java.io.InputStream;

public class FileLoaderClass {

	private char[][] level;

	public FileLoaderClass (String file) {
		ClassLoader cl = getClass().getClassLoader();

		try {

			InputStream fis = cl.getResourceAsStream(file);

			byte[] text = new byte[fis.available()];
			
			fis.read(text);

			int spalten = 0, zeilen = 0;
			boolean erste = true;

			for (int i = 0; i < text.length; i++) {
				if (erste)
					if (text[i] != '\n' && text[i] != '\r')
						spalten++;

				if (text[i] == '\n' || text[i] == '\r') {
					zeilen++;
					erste = false;
				}
			}

			level = new char[spalten][zeilen];

			for (int i = 0; i < zeilen; i++) {
				for (int j = 0; j < spalten; j++) {
					level[j][i] = (char)text[i*(spalten+1)+j];
					if (level[j][i] == 'S') {
						Wolverine.jack = new Jack(j, i);
						level[j][i] = '.';
					}
					if (level[j][i] == 'A') {
						Wolverine.autos.add(new Auto(j, i));
					}
					if (level[j][i] == 'E') {
						Wolverine.gegner.add(new Gegner(j, i));
						level[j][i] = '.';
					}
				}
			}

			fis.close();
		} catch (IOException e1) {
			System.out.println("Error while reading file");
			System.exit(1);
		}
	}

	char[][] getLevel() {
		return level;
	}
}
