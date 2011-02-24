/*
 * Copyright (c) 2010 Martin Ueding <dev@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Iterator;


public class Rakete {

	protected double x, y;
	protected double vx, vy;
	protected double alpha;

	private final double rspeed = .5;

	protected boolean explodiert;

	public Rakete (double c, double d, double a) {
		x = c;
		y = d;
		alpha = a;

		vx = Wolverine.cos(a)*rspeed;
		vy = Wolverine.sin(a)*rspeed;

		explodiert = false;
	}

	protected void move () {
		if (!explodiert) {
			x += vx;
			y += vy;
			
			if (x < 0 || y < 0 || x >= Wolverine.level.length || y >= Wolverine.level[0].length) {
				explodiert = true;
				return;
			}

			
			if (Wolverine.level[(int)x][(int)y] == '#') {
				explodiert = true;
			}
			
			for (Iterator<Auto> it = Wolverine.autos.iterator(); it.hasNext();) {
				Auto au = (Auto) it.next();
				
				if (Math.abs(au.x-x) < 1 && Math.abs(au.y-y) < 1) {
					explodiert = true;
					au.kaputt = 2;
				}
			}
			
			if (explodiert) {
				for (Iterator<Auto> it = Wolverine.autos.iterator(); it.hasNext();) {
					Auto au = (Auto) it.next();
					
					if (Math.abs(au.x-x) < 3 && Math.abs(au.y-y) < 3) {
						if (au.kaputt < 2)
							au.kaputt++;
					}
				}
				
				if (Math.abs(Wolverine.jack.x-x) < 1.5 && Math.abs(Wolverine.jack.y-y) < 1.5)
					Wolverine.jack.life -= 30;
			}

		}
	}

}
