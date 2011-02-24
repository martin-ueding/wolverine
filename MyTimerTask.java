/*
 * Copyright (c) 2010 Martin Ueding <dev@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.JOptionPane;


public class MyTimerTask extends TimerTask {
	int iter = 0;

	public void run () {
		Wolverine.jack.move();

		if (Wolverine.anzeige.iter != 0) {
			if (Wolverine.anzeige.iter < 40)
				Wolverine.anzeige.iter++;
			else if (Wolverine.anzeige.iter == 40)
				Wolverine.anzeige.iter = 0;
		}

		if (Wolverine.jack.life <= 0.0) {
			JOptionPane.showMessageDialog(Wolverine.f, Spr.get("Ende"));
			System.exit(0);
		}

		Rakete del = null;

		try {
			for (Iterator<Rakete> it = Wolverine.raketen.iterator(); it.hasNext();) {
				Rakete r = (Rakete) it.next();
				r.move();

				if (r.explodiert)
					del = r;
			}

				for (Iterator<Gegner> it = Wolverine.gegner.iterator(); it.hasNext();) {
					Gegner g = (Gegner) it.next();
					g.interact();
				}
		}

		catch (ConcurrentModificationException e) {
		}

		Wolverine.f.repaint();

		if (del != null)
			Wolverine.raketen.remove(del);
		
		iter++;
	}
}

