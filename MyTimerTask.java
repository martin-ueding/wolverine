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

