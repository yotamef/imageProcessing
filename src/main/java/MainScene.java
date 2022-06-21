import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class MainScene extends JPanel {

    private String facebookURL;
    private JLabel originalLabel;
    private JLabel outputLabel;
    private JButton start;
    private JButton blackWhite;
    private JButton mirror;
    private JButton colorShift1;
    private JButton colorShift2;
    private JButton sepia;
    private JButton lighter;

    private static final boolean FIRST_TIME = true;

    public MainScene(int width,int height) {

        this.setBounds(0,0,width,height);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setVisible(true);

        JTextField usersInput = new JTextField("insert url");
        usersInput.setBounds(width/2-TEXT_FIELD_WIDTH/2,0,TEXT_FIELD_WIDTH,TEXT_FIELD_HEIGHT);
        this.add(usersInput);

        placeButtons();

        start.addActionListener((event) -> {
            facebookURL = usersInput.getText();
            getImage(facebookURL);

            placePics(FIRST_TIME);

            blackWhite.addActionListener((event1) -> {
                try {
                    filter(GRAYSCALE_NUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placePics(!FIRST_TIME);
            });
            mirror.addActionListener((event1) -> {
                try {
                    filter(MIRROR_NUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placePics(!FIRST_TIME);
            });
            colorShift1.addActionListener((event1) -> {
                try {
                    filter(COLOR_SHIFT1_NUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placePics(!FIRST_TIME);
            });
            colorShift2.addActionListener((event1) -> {
                try {
                    filter(COLOR_SHIFT2_NUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placePics(!FIRST_TIME);
            });
            sepia.addActionListener((event1) -> {
                try {
                    filter(SEPIA_NUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placePics(!FIRST_TIME);
            });
            lighter.addActionListener((event1) -> {
                try {
                    filter(LIGHTER_NUM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placePics(!FIRST_TIME);
            });

        });
    }

    public void placeButtons() {
        start = new JButton("start");
        start.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        start.setVisible(true);
        this.add(start);

        blackWhite = new JButton("Grayscale");
        blackWhite.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,2*TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        blackWhite.setVisible(true);
        this.add(blackWhite);

        mirror = new JButton("mirror");
        mirror.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,3*TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        mirror.setVisible(true);
        this.add(mirror);

        colorShift1 = new JButton("color shift1");
        colorShift1.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,4*TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        colorShift1.setVisible(true);
        this.add(colorShift1);

        colorShift2 = new JButton("color shift2");
        colorShift2.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,5*TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        colorShift2.setVisible(true);
        this.add(colorShift2);

        sepia = new JButton("sepia");
        sepia.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,6*TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        sepia.setVisible(true);
        this.add(sepia);

        lighter = new JButton("lighter");
        lighter.setBounds(Window.WINDOW_WIDTH/2-BUTTON_WIDTH/2,7*TEXT_FIELD_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
        lighter.setVisible(true);
        this.add(lighter);

    }

    public static final int GRAYSCALE_NUM = 1;
    public static final int MIRROR_NUM = 2;
    public static final int COLOR_SHIFT1_NUM = 3;
    public static final int COLOR_SHIFT2_NUM = 4;
    public static final int SEPIA_NUM = 5;
    public static final int LIGHTER_NUM = 6;


    public void filter(int filter) throws Exception{
        File file = new File(IMAGE_PATH);
        BufferedImage bufferedImage = ImageIO.read(file);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        switch (filter) {
            case GRAYSCALE_NUM:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color currentColor = new Color(bufferedImage.getRGB(x, y));
                        int currentRed = currentColor.getRed();
                        int currentGreen = currentColor.getGreen();
                        int currentBlue = currentColor.getBlue();

                        Color newColor = new Color((currentRed + currentGreen + currentBlue) / 3, (currentRed + currentGreen + currentBlue) / 3, (currentRed + currentGreen + currentBlue) / 3);
                        bufferedImage.setRGB(x, y, newColor.getRGB());

                    }
                }
                break;
            case MIRROR_NUM:
                for (int x = 0; x < width/2; x++) {
                    for (int y = 0; y < height; y++) {
                        Color currentColor = new Color(bufferedImage.getRGB(x, y));
                        Color mirrorColor = new Color(bufferedImage.getRGB(width-x-1, y));

                        bufferedImage.setRGB(x, y, mirrorColor.getRGB());
                        bufferedImage.setRGB(width-x-1,y,currentColor.getRGB());
                    }
                }
                break;
            case COLOR_SHIFT1_NUM:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color currentColor = new Color(bufferedImage.getRGB(x, y));
                        int currentRed = currentColor.getRed();
                        int currentGreen = currentColor.getGreen();
                        int currentBlue = currentColor.getBlue();

                        Color newColor = new Color(currentGreen,currentBlue,currentRed);
                        bufferedImage.setRGB(x, y, newColor.getRGB());

                    }
                }
                break;
            case COLOR_SHIFT2_NUM:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color currentColor = new Color(bufferedImage.getRGB(x, y));
                        int currentRed = currentColor.getRed();
                        int currentGreen = currentColor.getGreen();
                        int currentBlue = currentColor.getBlue();

                        Color newColor = new Color(currentBlue,currentRed,currentGreen);
                        bufferedImage.setRGB(x, y, newColor.getRGB());

                    }
                }
                break;
            case SEPIA_NUM:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color currentColor = new Color(bufferedImage.getRGB(x, y));
                        int currentRed = currentColor.getRed();
                        int currentGreen = currentColor.getGreen();
                        int currentBlue = currentColor.getBlue();

                        int sepiaRed =(int) (0.393*currentRed + 0.769*currentGreen + 0.189*currentBlue);
                        int sepiaGreen =(int) (0.349*currentRed + 0.686*currentGreen + 0.168*currentBlue);
                        int sepiaBlue =(int) (0.272*currentRed + 0.534*currentGreen + 0.131*currentBlue);

                        Color newColor = new Color(Math.min(sepiaRed, 255),Math.min(sepiaGreen, 255),Math.min(sepiaBlue, 255));
                        bufferedImage.setRGB(x, y, newColor.getRGB());

                    }
                }
                break;
            case LIGHTER_NUM:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color currentColor = new Color(bufferedImage.getRGB(x, y));
                        int currentRed = currentColor.getRed();
                        int currentGreen = currentColor.getGreen();
                        int currentBlue = currentColor.getBlue();

                        currentRed =(int) (1.2*currentRed);
                        currentGreen =(int) (1.2*currentGreen);
                        currentBlue =(int) (1.2*currentBlue);

                        Color newColor = new Color(Math.min(currentRed, 255),Math.min(currentGreen, 255),Math.min(currentBlue, 255));
                        bufferedImage.setRGB(x, y, newColor.getRGB());

                    }
                }
                break;

        }
        File output = new File(IMAGE_OUTPUT_PATH);
        ImageIO.write(bufferedImage,"jpg",output);

    }

    public void placePics(boolean firstTime) {
        try {
            File file = new File(IMAGE_PATH);
            BufferedImage bufferedImage = ImageIO.read(file);
            File output = new File(IMAGE_OUTPUT_PATH);

            originalLabel = new JLabel(new ImageIcon(bufferedImage));
            originalLabel.setBounds(0, 0, 500, 500);
            this.add(originalLabel);

            if (firstTime) {
                outputLabel = new JLabel(new ImageIcon(bufferedImage));
                ImageIO.write(bufferedImage, "jpg", output);
                outputLabel.setBounds(500 + TEXT_FIELD_WIDTH, 0, 500, 500);
                this.add(outputLabel);
            }

            else {
                BufferedImage outputImage = ImageIO.read(output);
                outputLabel.setIcon(new ImageIcon(outputImage));
            }


            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getImage(String facebookUrl) {

        try {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yotam\\Downloads\\chromedriver_win32\\chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-data-dir=C:\\Users\\Yotam\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1");
            ChromeDriver driver = new ChromeDriver(options);

            driver.get(facebookUrl);
            driver.manage().window().maximize();

            ArrayList<WebElement> pictureElements = (ArrayList<WebElement>) driver.findElements(By.tagName("image"));
            WebElement pictureElement = pictureElements.get(0);
            String picturePath = pictureElement.getAttribute("xlink:href");

            saveImage(picturePath,IMAGE_PATH);

            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }


    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }




    public static final int TEXT_FIELD_WIDTH = 250;
    public static final int TEXT_FIELD_HEIGHT = 40;
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 40;
    public static final String IMAGE_PATH = "C:\\Users\\Yotam\\Documents\\Images\\profilePic.jpg";
    public static final String IMAGE_OUTPUT_PATH = "C:\\Users\\Yotam\\Documents\\Images\\profilePicOutput.jpg";

}
