package view;

import controller.Features;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JFrameView extends JFrame implements GUIView {

  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  public JFrameView() {
    super();
    setTitle("Collager Project");
    setSize(400, 400); //TODO

    this.mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());


    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout());
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    String[] images = {"./Jellyfish.jpg"};
    JLabel[] imageLabel = new JLabel[images.length];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length];

    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);

      imageLabel[i].setIcon(new ImageIcon(images[i]));

//      if(i < images.length) {
//        imageLabel[i].setIcon(new ImageIcon(images[i]));
//      } else {
//        imageLabel[i].setIcon(new ImageIcon(createImageFromScratch()));
//      }

      imageScrollPane[i].setPreferredSize(new Dimension(500, 500));
      imagePanel.add(imageScrollPane[i]);
    }

//    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {

  }
}
