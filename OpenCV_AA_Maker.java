
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class OpenCV_AA_Maker {
	public static void main(String[] args) throws IOException {
		String[] sets= {"--","／","/ ","｜","\u2216 ","＼","--"};		// \u2216 is backslash
		System.load("hoge");		//please set your opencv_java(version).dll location
		File file = new File("res.txt");
        FileWriter filewriter = new FileWriter(file);
		BufferedImage original = null;
		try {
			original = ImageIO.read(new File("s.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int iWidth = original.getWidth();	//image width
		int iHeight = original.getHeight(); //image height
		int cluster_size=32;	//cluster size. you set x. (x \times x)

		BufferedImage[][] separated=new BufferedImage[iHeight/cluster_size][iWidth/cluster_size];	//separated image array
		for(int i=0; i<iHeight/cluster_size; i++) {
			for(int j=0; j<iWidth/cluster_size; j++) {
				separated[i][j]=original.getSubimage(j*cluster_size, i*cluster_size, cluster_size, cluster_size);
			}
		}
		Mat[][] grayscale=new Mat[iHeight/cluster_size][iWidth/cluster_size];		//to save grascale data
		for(int i=0; i<iHeight/cluster_size; i++) {
			StringBuilder sb=new StringBuilder();
			for(int j=0; j<iWidth/cluster_size; j++) {

				Mat mat=img2Mat(separated[i][j]);
				Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
				grayscale[i][j]=mat;		//convert to grascale
				//Imgproc.equalizeHist(grayscale[i][j], grayscale[i][j]);		// histogram equalization
				Imgproc.Canny(grayscale[i][j], grayscale[i][j], 20,200);		//edge(rinkaku) detection
				Mat lines = new Mat();
				Imgproc.HoughLines(grayscale[i][j], lines, 1, Math.PI/180, 1);
				for (int k = 0; k < lines.cols(); k++){
					double[] data = lines.get(0, k);
					double theta;
					try {
						theta = data[1];
					}
					catch(NullPointerException e) {
						theta=-10;
					}
					if(theta<0) {
						sb.append("　");
					}
					else {
						double ang=(180.0d-(theta*180.0d/Math.PI-90));
						if(ang>179.99) {		// angle = [0,180]
							ang-=180d;
						}
						int s=(int)((ang+15.0)/30+0.04);
						sb.append(sets[Math.min(s,6)]);
				}
			}
			sb.append("\n\r");
			filewriter.write(sb.toString());
		}
		filewriter.close();
		}
	}
	public static Mat img2Mat(BufferedImage in) {
		Mat out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
		byte[] data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
		int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
		for (int i = 0; i < dataBuff.length; i++) {
			data[i * 3] = (byte) ((dataBuff[i]));
			data[i * 3 + 1] = (byte) ((dataBuff[i]));
			data[i * 3 + 2] = (byte) ((dataBuff[i]));
		}
		out.put(0, 0, data);
		return out;
	}
}