/*
 * Copyright 2010 Martin Ueding <mu@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

public class Jack {
	protected double x, y;
	protected double alpha;

	protected byte turn = 0;
	protected boolean go = false;

	protected double speed = 0.0;

	protected final double acc = 0.05;
	protected final double accCar = 0.01;
	protected final double accCarK = 0.006;
	protected final double fin = 0.2;
	protected final double finCar = .7;
	protected final double finCarK = .3;

	protected double life = 100.0;
	protected int ammo = 20;

	protected Auto meinAuto;

	protected Jack (int a, int b) {
		x = a + 0.5;
		y = b + 0.5;
	}

	protected void move () {

		if (meinAuto == null)
			alpha += turn * 0.3;
		else
			alpha += turn * 0.08;


		if (go) {
			if (meinAuto == null) {
				if (speed < fin)
					speed += acc;
			}
			else {
				if (meinAuto.kaputt == 0 && speed < (meinAuto.abgeriegelt ? finCar/2 : finCar))
					speed += accCar;
				else if (meinAuto.kaputt == 1 && speed < finCarK)
					speed += accCarK;
			}
		}
		else {
			if (meinAuto == null) {
				if (speed > 0)
					speed -= acc;
			}
			else {
				if (speed > 0)
					speed -= meinAuto.kaputt == 0 ? accCar : accCarK;
			}

			if (speed < 0)
				speed = 0;
		}

		if (speed != 0) {
			int newX, newY;
			if (meinAuto == null) {
				newX = (int)(x+Wolverine.cos(alpha) * speed);
				newY = (int)(y+Wolverine.sin(alpha) * speed);
			}
			else {			
				newX = (int)(x+Wolverine.cos(alpha) * speed);
				newY = (int)(y+Wolverine.sin(alpha) * speed);
			}
			if (newX >= 0 && newY >= 0 && newX < Wolverine.level.length && newY < Wolverine.level[0].length) {
				switch (Wolverine.level[(int)(x+Wolverine.cos(alpha) * speed)][(int)(y+Wolverine.sin(alpha) * speed)]) {
				case '#': {
					if (meinAuto != null && speed > finCar/2) {

						meinAuto.kaputt++;
						meinAuto.abgeriegelt = false;
						if (meinAuto.kaputt == 2)
							meinAuto = null;
						life -= 30*speed/finCar;

					}
					speed = 0.0;
					break;
				}
				
				default: {
					if (meinAuto == null) {
						x += Wolverine.cos(alpha) * speed; y += Wolverine.sin(alpha) * speed;
					}
					else {
						x += Wolverine.cos(alpha) * speed; y += Wolverine.sin(alpha) * speed;
					}

					break;
				}
				}
			}

			if (meinAuto != null) {
				meinAuto.x = x;
				meinAuto.y = y;
			}
		}

		if (meinAuto != null)
			meinAuto.alpha = alpha;
	}


	protected void fire () {
		if (Wolverine.raketen.size() > 20 || ammo <= 0 || meinAuto != null)
			return;

		ammo--;


		Wolverine.raketen.add(new Rakete(x-Wolverine.sin(alpha)/4, y+Wolverine.cos(alpha)/4, alpha));
	}
}
