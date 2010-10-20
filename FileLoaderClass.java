/*
 * Copyright 2010 Martin Ueding <mu@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
					switch (level[j][i] {
						case 'S': Wolverine.jack = new Jack(j, i); level[j][i] = '.'; break;
						case 'A': Wolverine.autos.add(new Auto(j, i)); break;
						case 'E': Wolverine.gegner.add(new Gegner(j, i)); level[j][i] = '.'; break;
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
