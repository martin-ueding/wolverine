/*
 * Copyright (c) 2010 Martin Ueding <dev@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Anzeige extends JPanel {

	private Image imJack, imBoden, imWand, imTuer, imAutoAn, imAutoAus, imAutoKaputt, imAutoVerbrannt;
	private Image imPark, imGegner, imStrasse, imLogo, imMedi, imAmmo, imRakete, imExplosion, imCC;
	private Image imGras, imZebra;

	private ImageObserver io;
	private ClassLoader cl;

	protected int iter = 1;

	private AffineTransform trans = new AffineTransform();

	private static final long serialVersionUID = 1L;

	private char[][] level = Wolverine.level;

	private int dpu = 40;

	private int i, j;

	private Color floor = new Color (  0,   0,   0);
	private Color border = new Color(104,195,238);
	private Color bg = new Color(76,143,174);
	private Color fg = new Color(90,168,206);

	private int ox, oy;
	private int oxu, oyu;

	public Anzeige () {
		io = this;
		cl = this.getClass().getClassLoader();

		try {
			imJack = ImageIO.read(cl.getResource("img/jack.png"));
			imBoden = ImageIO.read(cl.getResource("img/boden.png"));
			imWand = ImageIO.read(cl.getResource("img/wand.png"));
			imAutoAn = ImageIO.read(cl.getResource("img/auto.png"));
			imAutoAus = ImageIO.read(cl.getResource("img/auto2.png"));
			imAutoKaputt = ImageIO.read(cl.getResource("img/auto3.png"));
			imAutoVerbrannt = ImageIO.read(cl.getResource("img/auto4.png"));
			imPark = ImageIO.read(cl.getResource("img/park.png"));
			imGegner = ImageIO.read(cl.getResource("img/gegner.png"));
			imStrasse = ImageIO.read(cl.getResource("img/strasse.png"));
			imLogo = ImageIO.read(cl.getResource("img/logo.png"));
			imTuer = ImageIO.read(cl.getResource("img/tuer.png"));
			imMedi = ImageIO.read(cl.getResource("img/medipack.png"));
			imAmmo = ImageIO.read(cl.getResource("img/ammo.png"));
			imRakete = ImageIO.read(cl.getResource("img/rakete.png"));
			imExplosion = ImageIO.read(cl.getResource("img/explosion.png"));
			imCC = ImageIO.read(cl.getResource("img/cc.png"));
			imGras = ImageIO.read(cl.getResource("img/gras.png"));
			imZebra = ImageIO.read(cl.getResource("img/zebra.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected void paintComponent (Graphics p) {
		Graphics2D g = (Graphics2D)p;
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

		g.setColor(floor);
		g.fillRect(0, 0, getWidth(), getHeight());


		ox = (int)(Wolverine.jack.x*dpu-getWidth()/2);
		oy = (int)(Wolverine.jack.y*dpu-getHeight()/2);


		oxu = (int) (Wolverine.jack.x - getWidth()/2/dpu - 1);
		oyu = (int) (Wolverine.jack.y - getHeight()/2/dpu - 1);

		zeichneKarte(g);
		zeichneAutos(g);
		zeichneGegner(g);
		zeichneRaketen(g);
		zeichneJack(g);
		zeichneHUD(g);
		
		if (Wolverine.jack.meinAuto != null && Wolverine.jack.meinAuto.abgeriegelt)
			g.drawImage(imCC, getWidth()-160, getHeight()-80, io);

		if (iter != 0) {
			g.drawImage(imLogo, (getWidth()-imLogo.getWidth(io))/2, (getHeight()-imLogo.getHeight(io))/2, io);
		}
	}

	private void zeichneHUD(Graphics2D g) {

		g.setColor(bg);
		g.fillRect(10, 10, 20, getHeight()-20);

		g.setColor(fg);
		if (Wolverine.jack.life <= 30)
			g.setColor(Color.RED);
		g.fillRect(10, (int)(getHeight()-10-((getHeight()-20)*Wolverine.jack.life/100)), 20, (int)((getHeight()-20)*Wolverine.jack.life/100));

		g.setColor(border);
		g.drawRect(10, 10, 20, getHeight()-20);


		g.setColor(bg);
		g.fillRect(40, 10, 100, 50);

		g.setColor(border);
		g.drawRect(40, 10, 100, 50);

		g.drawString(Spr.get("Waffenname"), 43, 26);
		g.drawString(String.valueOf(Wolverine.jack.ammo), 43, 42);


		if (Wolverine.jack.meinAuto != null) {
			g.setColor(new Color(192,81,87));
			g.fillArc(getWidth()-80, getHeight()-80, 60, 60, -30, 120);

			g.setColor(Color.GRAY);

			g.fillArc(getWidth()-80, getHeight()-80, 60, 60, 90, 120);
			g.fillArc(getWidth()-75, getHeight()-75, 50, 50, -30, 140);
			g.setColor(Color.BLUE);
			g.drawLine(getWidth()-50, getHeight()-50, (int) (getWidth()-50+30*Wolverine.cos(2.61799387799+(4.18879020478*Wolverine.jack.speed/Wolverine.jack.finCar))), (int)(getHeight()-50+30*Wolverine.sin(2.61799387799+(4.18879020478*Wolverine.jack.speed/Wolverine.jack.finCar))));
			g.setColor(Color.DARK_GRAY);
			g.drawArc(getWidth()-80, getHeight()-80, 60, 60, -30, 240);
		}
	}

	private void zeichneJack(Graphics2D g) {
		if (Wolverine.jack.meinAuto == null) {
			trans.setToIdentity();
			trans.translate(getWidth()/2-dpu/2, getHeight()/2-dpu/2);
			trans.rotate(Wolverine.jack.alpha, dpu/2, dpu/2);

			g.drawImage(imJack, trans, io);
		}
	}

	private void zeichneGegner(Graphics2D g) {
		for (Iterator<Gegner> it = Wolverine.gegner.iterator(); it.hasNext();) {
			Gegner ge = (Gegner) it.next();

			trans.setToIdentity();
			trans.translate((ge.x)*dpu-ox-dpu/2, (ge.y)*dpu-oy-dpu/2);
			trans.rotate(ge.alpha, dpu/2, dpu/2);


			g.drawImage(imGegner, trans, io);
		}
	}

	private void zeichneAutos(Graphics2D g) {
		for (Iterator<Auto> it = Wolverine.autos.iterator(); it.hasNext();) {
			Auto au = (Auto) it.next();

			trans.setToIdentity();
			trans.translate(au.x*dpu-ox-50, au.y*dpu-oy-30);
			trans.rotate(au.alpha, 50, 30);

			if (au == Wolverine.jack.meinAuto && au.kaputt == 0)
				g.drawImage(imAutoAn, trans, io);
			else if (au.kaputt == 0)
				g.drawImage(imAutoAus, trans, io);
			else if (au.kaputt == 1)
				g.drawImage(imAutoKaputt, trans, io);
			else
				g.drawImage(imAutoVerbrannt, trans, io);
		}
	}

	private void zeichneKarte(Graphics2D g) {
		Image aktuell;
		for (i = oxu; i <= oxu+getWidth()/dpu+2; i++) {
			for (j = oyu; j <= oyu+getHeight()/dpu+2; j++) {
				if (i >= 0 && i < level.length && j >= 0 && j < level[0].length) {
					switch (level[i][j]) {
					case '#': aktuell = imWand; break;
					case 'A': aktuell = imPark; break;
					case '+': aktuell = imStrasse; break;
					case 'D': aktuell = imTuer; break;
					case 'L': aktuell = imMedi; break;
					case 'M': aktuell = imAmmo; break;
					case 'Z': aktuell = imZebra; break;
					case 'g': aktuell = imGras; break;
					default: aktuell = imBoden; break;
					}				
					g.drawImage(aktuell, i*dpu-ox, j*dpu-oy, io);
				}
			}
		}
	}

	private void zeichneRaketen(Graphics2D g) {
		try {
			for (Iterator<Rakete> it = Wolverine.raketen.iterator(); it.hasNext();) {
				Rakete r = (Rakete) it.next();

				trans.setToIdentity();
				trans.translate(r.x*dpu-ox-20, r.y*dpu-oy-6);
				trans.rotate(r.alpha, 20, 6);


				g.drawImage(r.explodiert ? imExplosion : imRakete, trans, io);
			}
		}
		catch (ConcurrentModificationException e) {
		}


	}
}
