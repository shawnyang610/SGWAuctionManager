package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JLabel;
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
import java.awt.event.ActionEvent;

public class ImageWindow extends JFrame {
	GUIAssistant guiAssistant;
	JLabel label4Images;
	public ImageWindow(GUIAssistant in_guiAssistant) {
		guiAssistant=in_guiAssistant;
		setTitle("Images");
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JPanel panelImage = new JPanel();
		
		JPanel panelButtons = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelImage, GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
						.addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, 598, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelImage, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		JButton buttonPrevious = new JButton("Previous");
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showImage("Previous");
			}
		});
		
		JButton buttonNext = new JButton("Next");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showImage("Next");
			}
		});
		GroupLayout gl_panelButtons = new GroupLayout(panelButtons);
		gl_panelButtons.setHorizontalGroup(
			gl_panelButtons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelButtons.createSequentialGroup()
					.addGap(194)
					.addComponent(buttonPrevious)
					.addGap(54)
					.addComponent(buttonNext, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(179, Short.MAX_VALUE))
		);
		gl_panelButtons.setVerticalGroup(
			gl_panelButtons.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelButtons.createSequentialGroup()
					.addContainerGap(25, Short.MAX_VALUE)
					.addGroup(gl_panelButtons.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonPrevious)
						.addComponent(buttonNext))
					.addGap(23))
		);
		panelButtons.setLayout(gl_panelButtons);
		
		label4Images = new JLabel("New label");
		GroupLayout gl_panelImage = new GroupLayout(panelImage);
		gl_panelImage.setHorizontalGroup(
			gl_panelImage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImage.createSequentialGroup()
					.addGap(22)
					.addComponent(label4Images, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
					.addGap(22))
		);
		gl_panelImage.setVerticalGroup(
			gl_panelImage.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelImage.createSequentialGroup()
					.addContainerGap()
					.addComponent(label4Images, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelImage.setLayout(gl_panelImage);
		getContentPane().setLayout(groupLayout);


	}
	private void showImage (String in_selector) {
		String workingDir=System.getProperty("user.dir");
		int imageNum = guiAssistant.imageNamesList.length;
		if (in_selector.equals("Next") && guiAssistant.imageIndex<imageNum-1)
			guiAssistant.imageIndex++;
		else if (in_selector.equals("Previous") && guiAssistant.imageIndex>0) {
			guiAssistant.imageIndex--;
		}
		String imageFileName = guiAssistant.imageNamesList[guiAssistant.imageIndex];
		System.out.println(workingDir+"/Images/"+imageFileName);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(workingDir+"/Images/"+imageFileName));
		Image image = imageIcon.getImage().getScaledInstance(label4Images.getWidth(), label4Images.getHeight(), Image.SCALE_SMOOTH);
		label4Images.setIcon(new ImageIcon(image));
	}
}
