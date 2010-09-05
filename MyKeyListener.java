import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class MyKeyListener implements KeyListener {
	public void keyTyped (KeyEvent e) {
		
		if (e.getKeyChar() == '\n') {
			if (Wolverine.jack.meinAuto != null)
				Wolverine.jack.meinAuto = null;
			else {
				for (Iterator<Auto> it = Wolverine.autos.iterator(); it.hasNext();) {
					Auto auto = (Auto) it.next();
					if (auto.kaputt <= 1  && Math.sqrt(Math.pow(auto.x-Wolverine.jack.x, 2) + Math.pow(auto.y-Wolverine.jack.y, 2)) < 1) {
						Wolverine.jack.meinAuto = auto;
						Wolverine.jack.x = Wolverine.jack.meinAuto.x;
						Wolverine.jack.y = Wolverine.jack.meinAuto.y;
						Wolverine.jack.alpha = Wolverine.jack.meinAuto.alpha;
					}
				}
			}
		}
		
		if (e.getKeyChar() == 'a' && Wolverine.jack.meinAuto == null) {
			boolean found = true;
			
			for (int i = (int)(Wolverine.jack.x-Wolverine.actionRadius); i < (int)(Wolverine.jack.x+Wolverine.actionRadius) && found; i++) {
				for (int j = (int)(Wolverine.jack.y-Wolverine.actionRadius); j < (int)(Wolverine.jack.y+Wolverine.actionRadius) && found; j++) {
					if (Wolverine.level[i][j] == 'd') {
						Wolverine.closeDoor(i, j);
						found = false;
					}
					if (found && Wolverine.level[i][j] == 'D') {
						Wolverine.openDoor(i, j);
						found = false;
					}
					if (found && Wolverine.level[i][j] == 'L') {
						Wolverine.jack.life = 100.0;
						found = false;
					}
					if (found && Wolverine.level[i][j] == 'M') {
						Wolverine.jack.ammo = 50;
						found = false;
					}
				}
				
			}
		}		
		else if (e.getKeyChar() == 'a' && Wolverine.jack.meinAuto.kaputt == 0) {
			Wolverine.jack.meinAuto.abgeriegelt = !Wolverine.jack.meinAuto.abgeriegelt;
		}
	}
	
	public void keyPressed (KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			Wolverine.jack.turn = -1;
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			Wolverine.jack.turn = 1;
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			Wolverine.jack.go = true;
		else if (e.getKeyChar() == 'f')
			Wolverine.jack.fire();
		
	}
	
	public void keyReleased (KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
			Wolverine.jack.turn = 0;
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			Wolverine.jack.go = false;
	}
}
