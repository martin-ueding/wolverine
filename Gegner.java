/*
 * Copyright (c) 2010 Martin Ueding <dev@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

public class Gegner {
	double x, y;
	double alpha;
	int iter = 0;
	

	public Gegner (int a, int b) {
		x = a + .5;
		y = b + .5;
	}

	protected void interact () {
		double dx = Wolverine.jack.x - x;
		double dy = Wolverine.jack.y - y;
	
		if (Wolverine.raketen.size() > 20)
			return;

		alpha = Math.atan(dy/dx);
		if (dx < 0)
			alpha += Math.PI;

		if (iter++ % 30 == 0)
			Wolverine.raketen.add(new Rakete(x-Wolverine.sin(alpha)/4, y+Wolverine.cos(alpha)/4, alpha));
	
	}
}
