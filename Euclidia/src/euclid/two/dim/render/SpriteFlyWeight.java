package euclid.two.dim.render;

import java.awt.Image;

import javax.swing.ImageIcon;

public class SpriteFlyWeight
{
	Image img, sub;
	
	public SpriteFlyWeight()
	{
		ImageIcon ii = new ImageIcon("C:\\Users\\Wylie\\Pictures\\Game\\ZergFixed.png");
		img = ii.getImage();
		System.out.println(" width " + img.getWidth(null));
		//img = ImageIO.read(new File("images/Zergling.png"));
		/*
		sub = copy.getSubimage(	// takes each part of the character sprite and breaks it up into 9 different sprites
		   		i * width,
		    	j * height,
		    	width,
		    	height
		*/
		
	}
	
	public Image getImage()
	{
		
		System.out.println(img.getWidth(null));
		return img;
	}
	
	public Sprite getSprite()
	{
		return new Sprite(img);
	}
	
}
