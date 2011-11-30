import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.math.*;


public class ColorSpaceStudy extends JFrame implements ActionListener
{
	Container cp,cp1;
	JPanel MainPanel,panel_image,panel_comp1,panel_comp2,panel_comp3,panel_result;
	JScrollPane scrollPane; 
	JMenuBar jmb;
	JMenu m_File,m_Color;
	JMenuItem m_Open,m_Save,m_Exit;
	JMenuItem RGBtoRGB,RGBtoCMY,RGBtoHSI,RGBtoXYZ,RGBtoCIELAB;;
	
	 	
	JFileChooser jfc;
	JLabel lbl_img,lbl_comp1,lbl_comp2,lbl_comp3,lbl_res;
	Image img_temp,img_gray;
	File file,file1,fileR;
	int len,w,h,len1,w1,h1;
	int pixel_result[],pixel_result1[],pixel_result2[],pixel_result3[],pix_temp[],pixel_comp1[],pixel_comp2[],pixel_comp3[];
	int pixel1[][],pixelR[][];
	byte byteArray[],byteArray1[];
	int j;
	
	FileImageInputStream inImage,inImage1;
	FileImageOutputStream outImage;
	JScrollBar vsb,hsb;
	JPanel Main_pnl;
	JScrollPane jsp;
	Image image,image1;
	BufferedImage buf_img;
	
	
	
	ColorSpaceStudy()
	{
			
			cp = new Container();
			cp.setLayout(new BorderLayout());
			Main_pnl = new JPanel();
			jmb = new JMenuBar();
			
			m_File = new JMenu("File");
			m_Color = new JMenu("Color Space");  
			m_Open = new JMenuItem("Open");
			m_Save = new JMenuItem("Save");
			m_Exit = new JMenuItem("Exit");
			RGBtoRGB = new JMenuItem("RGB to R G & B");
			RGBtoCMY = new JMenuItem("RGB to CMY");
			RGBtoHSI = new JMenuItem("RGB to HSI");
			RGBtoXYZ = new JMenuItem("RGB to XYZ");
			RGBtoCIELAB = new JMenuItem("RGB to CIE LAB");
			
			lbl_img = new JLabel("");
			lbl_comp1 = new JLabel("");
			lbl_comp2 = new JLabel("");
			lbl_comp3 = new JLabel("");
			
			
			panel_image = new JPanel();
			panel_comp1 = new JPanel();
			panel_comp2 = new JPanel();
			panel_comp3 = new JPanel();
		//	panel_result = new JPanel();
			scrollPane = new JScrollPane();
			
			vsb = new JScrollBar(JScrollBar.VERTICAL);
			hsb = new JScrollBar(JScrollBar.HORIZONTAL);
		//	jsp = new JScrollPane();
			
			setJMenuBar(jmb);
			add(cp);
			
			setBackground(new Color(255,255,255));
		
			jmb.add(m_File);
			jmb.add(m_Color);
			
			
			cp.add(Main_pnl);
			
			
			Main_pnl.setLayout(new GridLayout(1,4,5,5));
			
			Main_pnl.add(panel_image);
			Main_pnl.add(panel_comp1);
			Main_pnl.add(panel_comp2);
			Main_pnl.add(panel_comp3);
		//	Main_pnl.add(panel_result);						
			
			m_File.add(m_Open);
			m_File.add(m_Save);
			m_File.add(m_Exit);
			
			m_Color.add(RGBtoRGB);
			m_Color.add(RGBtoCMY);
			m_Color.add(RGBtoHSI);
			m_Color.add(RGBtoXYZ);
			m_Color.add(RGBtoCIELAB);
			
							
			m_Open.addActionListener(this);
			m_Save.addActionListener(this);
			m_Exit.addActionListener(this);
			
			RGBtoRGB.addActionListener(this);
			RGBtoCMY.addActionListener(this);
			RGBtoHSI.addActionListener(this);
			RGBtoXYZ.addActionListener(this);
			RGBtoCIELAB.addActionListener(this);
			
			
			panel_image.add(lbl_img);
			panel_comp1.add(lbl_comp1);
			panel_comp2.add(lbl_comp2);
			panel_comp3.add(lbl_comp3);
			//panel_result.add(lbl_res);
			
			cp.add(vsb,BorderLayout.EAST);
			cp.add(hsb,BorderLayout.SOUTH);
		
		
			jfc = new JFileChooser();
			
			cp = getContentPane();		
			setSize(900,700);
			setVisible(true);
		
			
			
	}
	
//	void display_comp1(int width,int height,int pixels[])
	
	public void actionPerformed(ActionEvent e)
	{
			
		String arg = (String)e.getActionCommand();
		
		/************************ Select,Open and display an image *****************/
		
		if(arg.equals("Open"))
		{
				int returnVal = jfc.showOpenDialog(ColorSpaceStudy.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) 
       			{
        			file = jfc.getSelectedFile();
        			String path = file.getAbsolutePath();
        		//	image = Toolkit.getDefaultToolkit().getImage(path);
        			
        				try{
							try{
								 inImage = new FileImageInputStream(file);
								 len = (int)inImage.length();
								 byteArray = new byte[len];
								 inImage.read(byteArray,0,len);
								// String path = file.getPath();
								 image = Toolkit.getDefaultToolkit().createImage(byteArray);
								 
								 MediaTracker t = new MediaTracker(this);
								 t.addImage(image,0);
								 try{
								 		t.waitForID(0);
								 
								 	}catch(InterruptedException xe)
								 	{
								 	}
								 	
								 	 w = image.getWidth(null);
        							 h = image.getHeight(null);
								    
								  
								  
							}catch(Exception fnfe)
							{
								JOptionPane.showMessageDialog(this,"File: Not Found");
							}
							}catch(Exception ice)
							{
								JOptionPane.showMessageDialog(this,"File I/O Error");
							}
												
					}
					if(image != null)
					{	
								
							//	pixel1 = new int[h][w];
								ImageIcon icon = new ImageIcon(image);	
								lbl_img.setIcon(icon);
								setVisible(true);
								
					} 
					
					
				
		
		
	}
	/*********************** RGB to 'R' , 'G' ,& 'B ' ***********************************/
	if(arg.equals("RGB to R G & B"))
	{
		
		System.out.println("In RGB");
		pixel_result = new int[w*h];
		pixel_result1 = new int[w*h];
		pixel_result2 = new int[w*h];
		pixel_result3 = new int[w*h];
		pixel_comp1 = new int[h*w];
		pixel_comp2 = new int[h*w];
		pixel_comp3 = new int[h*w];
		try 
		{
			PixelGrabber pg = new PixelGrabber(image,0,0,w,h,pixel_result,0,w);
			pg.grabPixels();
		} 
		catch (Exception e1){System.out.println("Exception at Pixel Grabbing");} 
				
		for(int i=0; i<w*h; i++)  //  Convertion of pixle form to normal single Value...
		{
			int a = pixel_result[i];
			int r = (0xff & (a>>16));
			int g = (0xff & (a>>8));
			int b = (0xff & a);
			
	
			pixel_result1[i]=r;
			pixel_result2[i]=g;
			pixel_result3[i]=b;
			
			
			
		}
		
		for(int i=0;i<w*h;i++)
    	{
    		pixel_comp1[i] = (0xff000000|pixel_result1[i]<<16|pixel_result1[i]<<8|pixel_result1[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp1,0,w));
    	ImageIcon imgI = new ImageIcon();
    	imgI.setImage(img_temp);
    	lbl_comp1.setIcon(imgI);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp2[i] = (0xff000000|pixel_result2[i]<<16|pixel_result2[i]<<8|pixel_result2[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp2,0,w));
    	ImageIcon imgII = new ImageIcon();
    	imgII.setImage(img_temp);
    	lbl_comp2.setIcon(imgII);
    	
    
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp3[i] = (0xff000000|pixel_result3[i]<<16|pixel_result3[i]<<8|pixel_result3[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp3,0,w));
    	ImageIcon imgIII = new ImageIcon();
    	imgIII.setImage(img_temp);
    	lbl_comp3.setIcon(imgIII);
    	
 
		
		
	}
	
	/********************************** RGB to CMY *****************************/
	
	
	if(arg.equals("RGB to CMY"))
	{
		
		System.out.println("In CMY");
		pixel_result = new int[w*h];
		pixel_result1 = new int[w*h];
		pixel_result2 = new int[w*h];
		pixel_result3 = new int[w*h];
		pixel_comp1 = new int[h*w];
		pixel_comp2 = new int[h*w];
		pixel_comp3 = new int[h*w];
		
		try 
		{
			PixelGrabber pg = new PixelGrabber(image,0,0,w,h,pixel_result,0,w);
			pg.grabPixels();
			
		} 
		catch (Exception e1)
		{
			System.out.println("Exception at Pixel Grabbing");
		} 
				
		for(int i=0; i<w*h; i++)  //  Convertion of pixle form to normal single Value...
		{
			int a = pixel_result[i];
			int r = (0xff & (a>>16));
			int g = (0xff & (a>>8));
			int b = (0xff & a);

           
           float c = 1-((float)r/255);
          
            c = c*255;
        
           float m = 1-((float)g/255);
           m = (m*255);
           
           float y = 1-((float)b/255);
           y = (y*255);

		
		pixel_result1[i]=(int) c;
		pixel_result2[i]= (int)m;
		pixel_result3[i]= (int)y; // avg;
	
		}
		
		for(int i=0;i<w*h;i++)
    	{
    		pixel_comp1[i] = (0xff000000|pixel_result1[i]<<16|pixel_result1[i]<<8|pixel_result1[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp1,0,w));
    	ImageIcon imgI = new ImageIcon();
    	imgI.setImage(img_temp);
    	lbl_comp1.setIcon(imgI);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp2[i] = (0xff000000|pixel_result2[i]<<16|pixel_result2[i]<<8|pixel_result2[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp2,0,w));
    	ImageIcon imgII = new ImageIcon();
    	imgII.setImage(img_temp);
    	lbl_comp2.setIcon(imgII);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp3[i] = (0xff000000|pixel_result3[i]<<16|pixel_result3[i]<<8|pixel_result3[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp3,0,w));
    	ImageIcon imgIII = new ImageIcon();
    	imgIII.setImage(img_temp);
    	lbl_comp3.setIcon(imgIII);
    	
		
	}
	
	/******************************** RGB to HSI ********************************/ 
	
	if(arg.equals("RGB to HSI"))
	{
		
		System.out.println("In HSI");
		pixel_result = new int[w*h];
		pixel_result1 = new int[w*h];
		pixel_result2 = new int[w*h];
		pixel_result3 = new int[w*h];
		pixel_comp1 = new int[h*w];
		pixel_comp2 = new int[h*w];
		pixel_comp3 = new int[h*w];
		
		try 
		{
			PixelGrabber pg = new PixelGrabber(image,0,0,w,h,pixel_result,0,w);
			pg.grabPixels();
			
		} 
		catch (Exception e1)
		{
			System.out.println("Exception at Pixel Grabbing");
		} 
				
		for(int i=0; i<w*h; i++)  //  Convertion of pixle form to normal single Value...
		{
			int a = pixel_result[i];
			int R = (0xff & (a>>16));
			int G = (0xff & (a>>8));
			int B = (0xff & a);

		   // Normalized values of r g & b	
           float r = ((float)R/(R+G+B));
           float g =((float)G/(R+G+B));
           float b =((float)B/(R+G+B));
          
           // Calculate HSI components from RGB
           double dd,dd1,dd2,dd3,h;
           dd = (0.5*((r-g)+(r-b)));
           dd1=(Math.pow((double)(r-g),(double)(r-g))) ;
           dd2 = dd1 + ((r-g)*(g-b));
           dd3 = Math.pow(dd2,0.5);
           h = (1/ (Math.cos(dd/dd3)));
           
          if(b<=g)
          {
           
           h = h*255;
           
          }
          
          if( b > g)
          {
          	
          	h = 2*Math.PI - h;
          	h = h *255;
          	
          	
          } 
		
		double s = 1 - ( 3 * (Math.min(r,Math.min(g,b))));
		s = s*255;
		
		double in = (R + G + B)/ (3.255);
		double in1 = in * 255;		
		
		pixel_result1[i]=(int) h;
		pixel_result2[i]= (int)s;
		pixel_result3[i]= (int)in;
	// avg;
	//	int avg =(int) (.33*h+.49*s+.11*in);
	//	System.out.println("Intensity = "+in+"\t"+"Avg = "+avg);
	//	pixel_result[i] = avg;

		}
		
		for(int i=0;i<w*h;i++)
    	{
    		pixel_comp1[i] = (0xff000000|pixel_result1[i]<<16|pixel_result1[i]<<8|pixel_result1[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp1,0,w));
    	ImageIcon imgI = new ImageIcon();
    	imgI.setImage(img_temp);
    	lbl_comp1.setIcon(imgI);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp2[i] = (0xff000000|pixel_result2[i]<<16|pixel_result2[i]<<8|pixel_result2[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp2,0,w));
    	ImageIcon imgII = new ImageIcon();
    	imgII.setImage(img_temp);
    	lbl_comp2.setIcon(imgII);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp3[i] = (0xff000000|pixel_result3[i]<<16|pixel_result3[i]<<8|pixel_result3[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp3,0,w));
    	ImageIcon imgIII = new ImageIcon();
    	imgIII.setImage(img_temp);
    	lbl_comp3.setIcon(imgIII);
    	
		
	}
	
	
	/********************************* RGB to XYZ ******************************/
	
	
	if(arg.equals("RGB to XYZ"))
	{
		
		System.out.println("In XYZ");
		pixel_result = new int[w*h];
		pixel_result1 = new int[w*h];
		pixel_result2 = new int[w*h];
		pixel_result3 = new int[w*h];
		pixel_comp1 = new int[h*w];
		pixel_comp2 = new int[h*w];
		pixel_comp3 = new int[h*w];
		
		try 
		{
			PixelGrabber pg = new PixelGrabber(image,0,0,w,h,pixel_result,0,w);
			pg.grabPixels();
			
		} 
		catch (Exception e1)
		{
			System.out.println("Exception at Pixel Grabbing");
		} 
				
		for(int i=0; i<w*h; i++)  //  Convertion of pixle form to normal single Value...
		{
			int a = pixel_result[i];
			int r = (0xff & (a>>16));
			int g = (0xff & (a>>8));
			int b = (0xff & a);

           
          	double var_R = ((double) r / 255 );        //R from 0 to 255
			double var_G = ((double) g / 255 );       //G from 0 to 255
			double var_B = ((double) b / 255 );      //B from 0 to 255

			if ( var_R > 0.04045 ) 
				var_R = (Math.pow((( var_R + 0.055 ) / 1.055 ),2.4));
			else                 
			 	 var_R =(var_R / 12.92);
			 	 
			if ( var_G > 0.04045 ) 
				var_G = Math.pow((( var_G + 0.055 ) / 1.055 ),2.4);
			else  
			    var_G = var_G / 12.92;
			    
			if ( var_B > 0.04045 ) 
				var_B = Math.pow((( var_B + 0.055 ) / 1.055 ),2.4);
			else       
				var_B = var_B / 12.92;

			var_R = var_R * 100;
			var_G = var_G * 100;
			var_B = var_B * 100;

			double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
			double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
			double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;
			
			
			pixel_result1[i]=(int) X;
			pixel_result2[i]= (int)Y;
			pixel_result3[i]= (int)Z; 
	
		}
		
		for(int i=0;i<w*h;i++)
    	{
    		pixel_comp1[i] = (0xff000000|pixel_result1[i]<<16|pixel_result1[i]<<8|pixel_result1[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp1,0,w));
    	ImageIcon imgI = new ImageIcon();
    	imgI.setImage(img_temp);
    	lbl_comp1.setIcon(imgI);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp2[i] = (0xff000000|pixel_result2[i]<<16|pixel_result2[i]<<8|pixel_result2[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp2,0,w));
    	ImageIcon imgII = new ImageIcon();
    	imgII.setImage(img_temp);
    	lbl_comp2.setIcon(imgII);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp3[i] = (0xff000000|pixel_result3[i]<<16|pixel_result3[i]<<8|pixel_result3[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp3,0,w));
    	ImageIcon imgIII = new ImageIcon();
    	imgIII.setImage(img_temp);
    	lbl_comp3.setIcon(imgIII);
    	
		
	}
	
	/************************* RGB to CIE L*a*b ****************************/
	
	if(arg.equals("RGB to CIE LAB"))
	{
		
		System.out.println("In CIE L*a*b");
		pixel_result = new int[w*h];
		pixel_result1 = new int[w*h];
		pixel_result2 = new int[w*h];
		pixel_result3 = new int[w*h];
		pixel_comp1 = new int[h*w];
		pixel_comp2 = new int[h*w];
		pixel_comp3 = new int[h*w];
		
		try 
		{
			PixelGrabber pg = new PixelGrabber(image,0,0,w,h,pixel_result,0,w);
			pg.grabPixels();
			
		} 
		catch (Exception e1)
		{
			System.out.println("Exception at Pixel Grabbing");
		} 
				
		for(int i=0; i<w*h; i++)  //  Convertion of pixle form to normal single Value...
		{
			int a = pixel_result[i];
			int r = (0xff & (a>>16));
			int g = (0xff & (a>>8));
			int b = (0xff & a);

           
          	double var_R = ((double) r / 255 );        //R from 0 to 255
			double var_G = ((double) g / 255 );       //G from 0 to 255
			double var_B = ((double) b / 255 );      //B from 0 to 255

			if ( var_R > 0.04045 ) 
				var_R = (Math.pow((( var_R + 0.055 ) / 1.055 ),2.4));
			else                 
			 	 var_R =(var_R / 12.92);
			 	 
			if ( var_G > 0.04045 ) 
				var_G = Math.pow((( var_G + 0.055 ) / 1.055 ),2.4);
			else  
			    var_G = var_G / 12.92;
			    
			if ( var_B > 0.04045 ) 
				var_B = Math.pow((( var_B + 0.055 ) / 1.055 ),2.4);
			else       
				var_B = var_B / 12.92;

			var_R = var_R * 100;
			var_G = var_G * 100;
			var_B = var_B * 100;

			double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
			double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
			double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;
	
			
			double ref_X = 95.047;
			double ref_Y = 100.000;
			double ref_Z  = 108.883;	
				
			double var_X = X / ref_X;          //ref_X =  95.047   Observer= 2°, Illuminant= D65
			double var_Y = Y / ref_Y;          //ref_Y = 100.000
			double var_Z = Z / ref_Z;          //ref_Z = 108.883

			if ( var_X > 0.008856 ) 
					var_X = Math.pow(var_X,( 1/3 ));
			else                    
			
				var_X = ( 7.787 * var_X ) + ( 16 / 116 );
				
			if ( var_Y > 0.008856 ) 
			
					var_Y = Math.pow(var_Y,( 1/3 ));
			else 
			
			        var_Y = ( 7.787 * var_Y ) + ( 16 / 116 );
			            
			if ( var_Z > 0.008856 )
 					var_Z = Math.pow(var_Z,( 1/3 ));
			else
			
		            var_Z = ( 7.787 * var_Z ) + ( 16 / 116 );

			double CIE_L = ( 116 * var_Y ) - 16;
			double CIE_a = 500 * ( var_X - var_Y );
			double CIE_b = 200 * ( var_Y - var_Z );
			
			pixel_result1[i]=(int)CIE_L;
			pixel_result2[i]= (int)CIE_a;
			pixel_result3[i]= (int)CIE_b; 
			
			
		}
		
	
		
		for(int i=0;i<w*h;i++)
    	{
    		pixel_comp1[i] = (0xff000000|pixel_result1[i]<<16|pixel_result1[i]<<8|pixel_result1[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp1,0,w));
    	ImageIcon imgI = new ImageIcon();
    	imgI.setImage(img_temp);
    	lbl_comp1.setIcon(imgI);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp2[i] = (0xff000000|pixel_result2[i]<<16|pixel_result2[i]<<8|pixel_result2[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp2,0,w));
    	ImageIcon imgII = new ImageIcon();
    	imgII.setImage(img_temp);
    	lbl_comp2.setIcon(imgII);
    	
    	
    	for(int i=0;i<w*h;i++)
    	{
    		pixel_comp3[i] = (0xff000000|pixel_result3[i]<<16|pixel_result3[i]<<8|pixel_result3[i]);
    	} 
    	
    	img_temp = createImage(new MemoryImageSource(w,h,pixel_comp3,0,w));
    	ImageIcon imgIII = new ImageIcon();
    	imgIII.setImage(img_temp);
    	lbl_comp3.setIcon(imgIII);
    	
		
	}
	
	
	
	if(arg.equals("Exit"))
	{
		System.exit(0);
	}
	
	
	
	
	
	
	
	
	
	
}

	public static void main(String args[])
	{
		new ColorSpaceStudy();
	}

}	