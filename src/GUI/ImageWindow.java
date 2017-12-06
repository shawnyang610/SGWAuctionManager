package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ImageWindow extends JFrame {
	GUIAssistant guiAssistant;
	JLabel label4Images;
	public ImageWindow(GUIAssistant in_guiAssistant) throws IOException {
		guiAssistant=in_guiAssistant;
		setTitle("Images");
		setMinimumSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JPanel panelImage = new JPanel();
		
		JPanel panelButtons = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panelImage, Alignment.LEADING, 0, 345, Short.MAX_VALUE)
						.addComponent(panelButtons, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
					.addGap(161))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelImage, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JButton buttonPrevious = new JButton("Previous");
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					showImage("Previous");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton buttonNext = new JButton("Next");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					showImage("Next");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showImage("Refresh");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panelButtons = new GroupLayout(panelButtons);
		gl_panelButtons.setHorizontalGroup(
			gl_panelButtons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelButtons.createSequentialGroup()
					.addGap(26)
					.addComponent(buttonPrevious)
					.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
					.addComponent(btnRefresh)
					.addGap(43)
					.addComponent(buttonNext, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addGap(39))
		);
		gl_panelButtons.setVerticalGroup(
			gl_panelButtons.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelButtons.createSequentialGroup()
					.addContainerGap(21, Short.MAX_VALUE)
					.addGroup(gl_panelButtons.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonPrevious)
						.addComponent(buttonNext)
						.addComponent(btnRefresh))
					.addGap(23))
		);
		panelButtons.setLayout(gl_panelButtons);
		
		label4Images = new JLabel("New label");
		GroupLayout gl_panelImage = new GroupLayout(panelImage);
		gl_panelImage.setHorizontalGroup(
			gl_panelImage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImage.createSequentialGroup()
					.addContainerGap(22, Short.MAX_VALUE)
					.addComponent(label4Images, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
		);
		gl_panelImage.setVerticalGroup(
			gl_panelImage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImage.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(label4Images, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		panelImage.setLayout(gl_panelImage);
		getContentPane().setLayout(groupLayout);
		showImage("Previous");

	}
	private void showImage (String in_selector) throws IOException {
		String workingDir=System.getProperty("user.dir");
		if (guiAssistant.imageNamesList!=null) {
		int imageNum = guiAssistant.imageNamesList.length;
		if (in_selector.equals("Next") && guiAssistant.imageIndex<imageNum-1)
			guiAssistant.imageIndex++;
		else if (in_selector.equals("Previous") && guiAssistant.imageIndex>0) {
			guiAssistant.imageIndex--;
		}
		else if (in_selector.equals("Refresh")) {
			guiAssistant.imageIndex=0;
		}
		String imageFileName = guiAssistant.imageNamesList[guiAssistant.imageIndex];
		System.out.println(workingDir+"/Images/"+imageFileName);
		BufferedImage bufferedImage = null;
		bufferedImage = ImageIO.read(new File(workingDir+"/Images/"+imageFileName));
		ImageIcon imageIcon = new ImageIcon(bufferedImage);
		Image image = imageIcon.getImage().getScaledInstance(label4Images.getWidth(), label4Images.getHeight(), Image.SCALE_DEFAULT);
		label4Images.setIcon(new ImageIcon(image));
		}
	}
}
