/*
 * Copyright 2010 Martin Ueding <mu@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;

public class Wolverine {
	protected static char[][] level;
	protected static Jack jack;

	protected static LinkedList<Gegner> gegner;
	protected static LinkedList<Auto> autos;
	protected static LinkedList<Rakete> raketen;

	protected static JFrame f;
	protected static Anzeige anzeige;

	protected static final int actionRadius = 2;
	
	protected static double[] cos, sin;

	public static void main (String[] args) {

		gegner = new LinkedList<Gegner>();
		autos = new LinkedList<Auto>();
		raketen = new LinkedList<Rakete>();
		
		initTrig();

		FileLoaderClass flc = new FileLoaderClass(args.length == 0 ? "city.wol" : ""+args[0]+".sgl");
		level = flc.getLevel();

		initFenster();	
	}

	private static void initFenster() {
		f = new JFrame("Wolverine");
		f.setSize(600, 420);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(d.width/2-300, d.height/2-210);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		anzeige = new Anzeige();
		f.add(anzeige);

		f.addKeyListener(new MyKeyListener());

		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), 0, 50);
		
		f.setVisible(true);
	}

	private static void initTrig() {
		cos = new double[360];
		sin = new double[360];
		
		for (int i = 0; i < 360; i++) {
			cos[i] = Math.cos(Math.toRadians(i));
			sin[i] = Math.sin(Math.toRadians(i));
		}
	}
	
	static double cos (double a) {
		while (a < 0)
			a += 6.28318530718;
		
		return cos[(int)(a*57.29577951308)%360];
	}
	
	static double sin (double a) {
		while (a < 0)
			a += 6.28318530718;
		
		return sin[(int)(a*57.29577951308)%360];
	}

	protected static void openDoor (int x, int y) {
		if (level[x][y] == 'D') {
			level[x][y] = 'd';
			openDoor(x+1, y);
			openDoor(x-1, y);
			openDoor(x, y+1);
			openDoor(x, y-1);
		}
	}

	protected static void closeDoor (int x, int y) {
		if (level[x][y] == 'd') {
			level[x][y] = 'D';
			closeDoor(x+1, y);
			closeDoor(x-1, y);
			closeDoor(x, y+1);
			closeDoor(x, y-1);
		}
	}
}
