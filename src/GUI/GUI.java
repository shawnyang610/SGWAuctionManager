package GUI;

import javax.swing.JFrame;
import Database.*;
import IOManager.IOManager;
import URL_Processor.Item2Data;
import URL_Processor.OnlineSearch2ItemsList;
import URL_Processor.URLBuilder;
import javafx.concurrent.Task;

import javax.swing.JMenuBar;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.Scrollbar;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Button;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GUI extends JFrame {
	private JTable table;
	private JTextField textField;
	private CustomizedHashMap cusMap = null;
	private String[] header = null;
	private PrintWriter outFile = null;
	private LogRecorder logger = null;
	private String selector;
	private String[][] dataJTable;
	private DefaultTableModel myTableModel = null;
	private String dumpFileName = null;
	private SimpleDateFormat dateFormat;
	private Date current;
	private String CurrentDate;
	private JTextField txtEnterKeyWord;
	private JTextField txtMin;
	private JTextField txtMax;
	private GUIAssistant guiAssistant;
	private PrintWriter outDebug;
	private boolean enableImage;
	JComboBox comboBox_categories;
	JCheckBox chckbxSearchDescriptions;
	JCheckBox chckbxBuyItNow;
	JCheckBox chckbxShowLiveItems;
	ImageWindow  imageWindow;
	boolean isTaskDone;
	Task task=null;
	JProgressBar progressBar;
	int progressBarValue;
	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 */
	public GUI(CustomizedHashMap inCusMap, String inDumpfileName, LogRecorder inLog, String inSelector,
			PrintWriter in_outDebug) throws IOException {
		isTaskDone=false;
		progressBar=null;
		progressBarValue=0;
		enableImage = false;
		outDebug = in_outDebug;
		guiAssistant = new GUIAssistant();
		getContentPane().setName("");
		setTitle("Shopgoodwill Offline Manager");
		cusMap = inCusMap;
		header = inCusMap.header;
		dumpFileName = inDumpfileName;
		logger = inLog;
		selector = inSelector;
		dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		current = new Date();
		CurrentDate = dateFormat.format(current);
		// set closing action
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				logger.writeInfo("Program exits.");
				System.exit(0);
			}
		});
		
		try {
			imageWindow = new ImageWindow(guiAssistant);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Choose database to use
		if (inSelector.equals("mysql")) {

		} else {
			// dataJTable = listToString(cusMap.getAllList());
			dataJTable = CHMAssistant.listTo2DStringAry(header, CHMAssistant.filterLists(header, cusMap.getAllList()));
		}

		setMinimumSize(new Dimension(1024, 768));
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		JCheckBoxMenuItem chckbxmntmObtainImages = new JCheckBoxMenuItem("Obtain images");
		chckbxmntmObtainImages.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (chckbxmntmObtainImages.isSelected()) {
					enableImage = true;
				}
			}
		});
		mnOptions.add(chckbxmntmObtainImages);
		GridBagLayout gridBagLayout = new GridBagLayout();

		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };

		getContentPane().setLayout(gridBagLayout);

		myTableModel = new DefaultTableModel(dataJTable, header);
		table = new JTable(myTableModel);
		table.setPreferredScrollableViewportSize(new Dimension(700, 300));

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		gbc_table.gridheight = 6;
		gbc_table.gridwidth = 10;

		JScrollPane scrlPane = new JScrollPane(table);

		getContentPane().add(scrlPane, gbc_table);

		ButtonGroup groupQueryButtons = new ButtonGroup();

		JButton btnNewEntry = new JButton("New entry");

		btnNewEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newEntry();
			}
		});

		JRadioButton rdbtnShowEntriesWith = new JRadioButton("Show entries with specified value");
		rdbtnShowEntriesWith.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) { // 1 is selected, 0 is deselected
					textField.setEditable(true);
				} else {
					textField.setEditable(false);
				}
			}
		});

		JCheckBox chckbxFuzzySearch = new JCheckBox("Fuzzy Search           ");
		chckbxFuzzySearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxFuzzySearch.isSelected()) {
					guiAssistant.offline_fuzzySearch = true;
				}
				else {
					guiAssistant.offline_fuzzySearch = false;
				}
			}
		});
		
				Button button_OpenInv = new Button("Open Inv.");
				button_OpenInv.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// step0
						// step0.1 setup Jfilechooser to choose an input file
						JFileChooser fc = new JFileChooser();
						int response = fc.showOpenDialog(null);
						if (response == JFileChooser.APPROVE_OPTION) {
							guiAssistant.inventoryFileName = fc.getSelectedFile().toString();
						}
						// step0.2:if filename not empty, open the file
						if (!guiAssistant.inventoryFileName.equals("")) {
							Scanner infile = null;
							String url;
							ArrayList<String> returnedItemsList;
							String[][] allReturnedDataEntries4Table = null;
							ArrayList<String> itemDataList, itemImageNamesList;
							ArrayList<ArrayList<String>> allReturnedList = new ArrayList<>();
							try {
								infile = new Scanner(new FileReader(guiAssistant.inventoryFileName));
							} catch (IOException e) {
								System.out.println(
										"Problem opening selected file: " + guiAssistant.inventoryFileName + "error: " + e);
							}
							// step1: keywordsList <- read all keywords from input file (2nd data on every
							// row, starting from 2nd row)
							ArrayList<String> keywordsList;
							Pattern pattern = Pattern
									.compile("(?<=\\d{1,10}\\s\\|\\s)(.{0,50})(?=\\s\\|\\s\\d*\\s\\|.*\\|.*\\|.*)");// this
																													// pattern
																													// matches
																													// 2nd col.
																													// in
																													// input.txt
							keywordsList = IOManager.getKeywordsList(pattern, infile);
							// step1 ends
							while (!keywordsList.isEmpty()) {
								// step2, generate URL with each keyword read from step1
								url = URLBuilder.buildURL(keywordsList.get(0));
								keywordsList.remove(0);
								System.out.println(url);// debug use

								// step3 search shopgoodwill using the URL and analyze the returned page
								// returnedItemList <-- get item#s for each item
								OnlineSearch2ItemsList os2il = new OnlineSearch2ItemsList(url, outDebug);
								try {
									os2il.start();
								} catch (IOException e1) {
									System.out.println(
											"Problem with OnlineSearch2ItemList.start() from inventory file select button");
									e1.printStackTrace();
								}
								returnedItemsList = os2il.getItemsList();
								// for debugging
								for (String item : returnedItemsList) {
									System.out.println("returnedItemsList: " + item);
									outDebug.println("returnedItemsList: " + item);
								} // for debugging ends
									// dynamically allocate a string[][] for displaying data in table later
									// allReturnedDataEntries4Table = new
									// String[returnedItemsList.size()][header.length];
								int counter = 0;
								for (String s : returnedItemsList) {
									System.out.println("Getting data for item# " + s);
									// step4 Data entry <-- data mine each item in the itemslist
									Item2Data item2Data = new Item2Data(s, outDebug);
									try {
										item2Data.start(enableImage);
									} catch (IOException e1) {
										System.out.println("Problem with item2data.start() from online search button");
										e1.printStackTrace();
									}
									itemDataList = item2Data.getItemDataList();
									itemImageNamesList = item2Data.getItemImageNamesList();
									// step5 database <-- enter Data entry
									allReturnedList.add(itemDataList);// step5.1 gather all data entries to an list, so it can
																		// be used to display in table later
									CustomizedHashMap.appendList2Header(header, itemDataList);// give each element a header
									cusMap.putList(itemDataList);
									CustomizedHashMap.appendList2Header("ImagesForItemNum", itemImageNamesList);
									cusMap.putList(itemImageNamesList);
									// step6 log <-- write to log
									logger.writeInfo("Write to database", itemDataList);
									logger.writeInfo("Save images to database", itemImageNamesList);
									// step7 skip

								} // step8 repeat step4,5,6 7until no more items in returndItemsList.
							} // while keywordlist is not empty
								// step9.0 convert list to String[][]
							allReturnedDataEntries4Table = new String[allReturnedList.size()][header.length];
							int counter = 0;
							for (ArrayList<String> list : allReturnedList) {
								allReturnedDataEntries4Table[counter++] = CHMAssistant.removeHeadersFromListThenToArray(header,
										list);
							}
							// step9.1 table displays the result returned
							int rowCount = myTableModel.getRowCount();
							for (int i = 0; i < rowCount; i++) {
								myTableModel.removeRow(0);
							}
							for (int i = 0; i < allReturnedDataEntries4Table.length; i++) {
								myTableModel.addRow(allReturnedDataEntries4Table[i]);
							}
						}
					}

				});
				GridBagConstraints gbc_button_OpenInv = new GridBagConstraints();
				gbc_button_OpenInv.fill = GridBagConstraints.HORIZONTAL;
				gbc_button_OpenInv.insets = new Insets(0, 0, 5, 5);
				gbc_button_OpenInv.gridx = 8;
				gbc_button_OpenInv.gridy = 7;
				getContentPane().add(button_OpenInv, gbc_button_OpenInv);
		
				Button button_1 = new Button("Report");
				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// step1 get selected file name
						JFileChooser fc = new JFileChooser();
						int response = fc.showSaveDialog(null);
						if (response == JFileChooser.APPROVE_OPTION) {
							guiAssistant.reportFileName = fc.getSelectedFile().toString();
						}

						// step2.0 execute if the filename is not empty
						if (!guiAssistant.reportFileName.equals("")) {
							// step2.1 allTableData[][] <-- if current table is not empty then get all data
							// from table
							int rowCount = myTableModel.getRowCount();
							int colCount = myTableModel.getColumnCount();
							String[][] tableData = new String[rowCount][colCount];
							if (rowCount > 0) {
								for (int r = 0; r < rowCount; r++) {
									for (int c = 0; c < colCount; c++) {
										tableData[r][c] = (String) myTableModel.getValueAt(r, c);
									}
								}
							}
							PrintWriter outfileReport = null;
							// step3 write the header and String ary to outfile
							try {
								outfileReport = new PrintWriter(guiAssistant.reportFileName);
							} catch (IOException e) {
								System.out.println("unable to open report file: " + e);
							}
							IOManager.reportHeaderWriter(outfileReport, header);
							IOManager.reportDataWriter(outfileReport, tableData);
						}

					}
				});
				GridBagConstraints gbc_button_1 = new GridBagConstraints();
				gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_button_1.insets = new Insets(0, 0, 5, 5);
				gbc_button_1.gridx = 8;
				gbc_button_1.gridy = 8;
				getContentPane().add(button_1, gbc_button_1);
		
		chckbxShowLiveItems = new JCheckBox("Live items only      ");
		chckbxShowLiveItems.setEnabled(false);
		GridBagConstraints gbc_chckbxShowLiveItems = new GridBagConstraints();
		gbc_chckbxShowLiveItems.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowLiveItems.gridx = 5;
		gbc_chckbxShowLiveItems.gridy = 9;
		getContentPane().add(chckbxShowLiveItems, gbc_chckbxShowLiveItems);
		chckbxFuzzySearch.setEnabled(false);
		GridBagConstraints gbc_chckbxFuzzySearch = new GridBagConstraints();
		gbc_chckbxFuzzySearch.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxFuzzySearch.gridx = 6;
		gbc_chckbxFuzzySearch.gridy = 9;
		getContentPane().add(chckbxFuzzySearch, gbc_chckbxFuzzySearch);

		JRadioButton rdbtnOnlineSearch = new JRadioButton("Online Search");
		rdbtnOnlineSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxFuzzySearch.setEnabled(false);
				guiAssistant.offline_fuzzySearch = false;
				comboBox_categories.setEnabled(true);
				chckbxSearchDescriptions.setEnabled(true);
				chckbxBuyItNow.setEnabled(true);
				chckbxShowLiveItems.setEnabled(false);
			}
		});
		rdbtnOnlineSearch.setSelected(true);
		GridBagConstraints gbc_rdbtnOnlineSearch = new GridBagConstraints();
		gbc_rdbtnOnlineSearch.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOnlineSearch.gridx = 1;
		gbc_rdbtnOnlineSearch.gridy = 6;
		getContentPane().add(rdbtnOnlineSearch, gbc_rdbtnOnlineSearch);
		
		JRadioButton rdbtnOfflineSearch = new JRadioButton("Offline Search");
		rdbtnOfflineSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxFuzzySearch.setEnabled(true);
				comboBox_categories.setEnabled(false);
				chckbxSearchDescriptions.setEnabled(false);
				chckbxBuyItNow.setEnabled(false);
				chckbxShowLiveItems.setEnabled(true);
			}
		});
		GridBagConstraints gbc_rdbtnOfflineSearch = new GridBagConstraints();
		gbc_rdbtnOfflineSearch.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOfflineSearch.gridx = 2;
		gbc_rdbtnOfflineSearch.gridy = 6;
		getContentPane().add(rdbtnOfflineSearch, gbc_rdbtnOfflineSearch);

		txtEnterKeyWord = new JTextField();
		txtEnterKeyWord.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				guiAssistant.st_keyWord = txtEnterKeyWord.getText();
				System.out.println("");
			}
		});
		// Setup button groups for online, offline radio buttons.
		ButtonGroup buttonGroup_ModeSelect = new ButtonGroup();
		buttonGroup_ModeSelect.add(rdbtnOnlineSearch);
		buttonGroup_ModeSelect.add(rdbtnOfflineSearch);

		txtEnterKeyWord.setText("enter key word here");
		GridBagConstraints gbc_txtEnterKeyWord = new GridBagConstraints();
		gbc_txtEnterKeyWord.gridwidth = 3;
		gbc_txtEnterKeyWord.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEnterKeyWord.insets = new Insets(0, 0, 5, 5);
		gbc_txtEnterKeyWord.gridx = 3;
		gbc_txtEnterKeyWord.gridy = 6;
		getContentPane().add(txtEnterKeyWord, gbc_txtEnterKeyWord);
		txtEnterKeyWord.setColumns(10);

		chckbxSearchDescriptions = new JCheckBox("search descriptions");
		chckbxSearchDescriptions.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (chckbxSearchDescriptions.isSelected())
					guiAssistant.sd_searchDescription = "true";
				else
					guiAssistant.sd_searchDescription = "false";
				System.out.println("guiAssistant.sd_searchDescription=" + guiAssistant.sd_searchDescription);
			}

		});
		GridBagConstraints gbc_chckbxSearchDescriptions = new GridBagConstraints();
		gbc_chckbxSearchDescriptions.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSearchDescriptions.gridx = 6;
		gbc_chckbxSearchDescriptions.gridy = 6;
		getContentPane().add(chckbxSearchDescriptions, gbc_chckbxSearchDescriptions);

		Button button = new Button("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String URL;
				ArrayList<String> returnedItemsList = null;
				ArrayList<String> itemDataList = null;
				ArrayList<String> itemImageNamesList = null;
				String[][] allReturnedDataEntries4Table = null;
				//progress bar task
				progressBar.setValue(1);
				progressBar.setVisible(true);
				
				//end of progress bar task
				
				if (rdbtnOnlineSearch.isSelected()) {
					System.out.println("online search");
					// for on-line search
					// step1 generate URL with current URL configuration
					URL = guiAssistant.buildURL();
					System.out.println("Generated URL: \r\n " + URL);
					// step2 search shopgoodwill using the URL and analyze the returned page
					// returnedItemList <-- get item#s for each item
					OnlineSearch2ItemsList os2il = new OnlineSearch2ItemsList(URL, outDebug);
					try {
						os2il.start();
					} catch (IOException e1) {
						System.out.println("Problem with OnlineSearch2ItemList.start() from online search button");
						e1.printStackTrace();
					}
					returnedItemsList = os2il.getItemsList();
					// for debugging
					for (String item : returnedItemsList) {
						System.out.println("returnedItemsList: " + item);
						outDebug.println("returnedItemsList: " + item);
					} // for debugging ends
						// dynamically allocate a string[][] for displaying data in table later
					allReturnedDataEntries4Table = new String[returnedItemsList.size()][header.length];
					int counter = 0;
					for (String s : returnedItemsList) {
						System.out.println("Getting data for item# " + s);
						// step3 Data entry <-- data mine each item in the itemslist
						Item2Data item2Data = new Item2Data(s, outDebug);
						try {
							item2Data.start(enableImage);
						} catch (IOException e1) {
							System.out.println("Problem with item2data.start() from online search button");
							e1.printStackTrace();
						}
						itemDataList = item2Data.getItemDataList();
						itemImageNamesList = item2Data.getItemImageNamesList();
						// step4 database <-- enter Data entry
						CustomizedHashMap.appendList2Header(header, itemDataList);// give each element a header
						cusMap.putList(itemDataList);
						CustomizedHashMap.appendList2Header("ImagesForItemNum", itemImageNamesList);
						cusMap.putList(itemImageNamesList);
						// step5 log <-- write to log
						logger.writeInfo("Write to database", itemDataList);
						logger.writeInfo("Save images to database", itemImageNamesList);
						// step6 String[][] tableList <-- add each data entry to
						allReturnedDataEntries4Table[counter++] = CHMAssistant.removeHeadersFromListThenToArray(header,
								itemDataList);
						if (progressBarValue<95)
							progressBarValue++;
						progressBar.setValue(progressBarValue);
						progressBar.setVisible(true);
					} // step7 repeat step3,4,5,6 until no more items in returndItemsList.

					// step7 table displays the result returned
					int rowCount = myTableModel.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						myTableModel.removeRow(0);
					}
					for (int i = 0; i < allReturnedDataEntries4Table.length; i++) {
						myTableModel.addRow(allReturnedDataEntries4Table[i]);
					}
					// for off-line search
				} else {
					System.out.println("offline search");
					if (chckbxShowLiveItems.isSelected()) {
						guiAssistant.offline_showLiveItems = true;
					} else
						guiAssistant.offline_showLiveItems = false;

					// for off-line search
					// step1 gather keywords for the search; item#, title, price, bids, seller(need
					// to match number to correct seller company name), shipping, end date, last
					// update.

					// step2 search in the database, return a list that fufills all requirements of
					// the search(title, price, seller... etc)
					// step2.1 for title, get all list and match the keyword using regex, return all
					// the list that has matching title.
					// step2.2 return all list has matching price
					// step2.3 return all list has matching bids
					// step2.4 return all list has matching seller
					// step2.5 return all list with specified ending date (by comparing end date and
					// current date)
					// step2.6 do an intersection of all steps from 2.1-2.5. return the list that
					// survived
					ArrayList<ArrayList<String>> tempAryList = null;
					try {
						tempAryList=CHMAssistant.offlineSearch(cusMap, guiAssistant.offline_fuzzySearch,guiAssistant.offline_showLiveItems , guiAssistant.st_keyWord,
								guiAssistant.s_seller, guiAssistant.lp_lowPrice, guiAssistant.hp_highPrice,
								guiAssistant.offline_shipping, guiAssistant.offline_endDate);
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}
					allReturnedDataEntries4Table = new String[tempAryList.size()][header.length];
					allReturnedDataEntries4Table = CHMAssistant.listTo2DStringAry(header, CHMAssistant.filterLists(header, tempAryList));
					// display in the table
					int rowCount = myTableModel.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						myTableModel.removeRow(0);
					}
					for (int i = 0; i < allReturnedDataEntries4Table.length; i++) {
						myTableModel.addRow(allReturnedDataEntries4Table[i]);
					}
					
				}
				progressBar.setValue(100);
				progressBar.setValue(0);
				progressBar.setVisible(true);
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 8;
		gbc_button.gridy = 6;
		getContentPane().add(button, gbc_button);

		comboBox_categories = new JComboBox(GUIAssistant.getCategoriesParametersFromFile());
		comboBox_categories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = (String) comboBox_categories.getSelectedItem();
				System.out.println(temp);
				if (!temp.equals("All Categories")) {
					guiAssistant.c_category = temp.substring(0, temp.indexOf(" "));
				} else {
					guiAssistant.c_category = "";
				}
				System.out.println(guiAssistant.c_category);
			}
		});
		comboBox_categories.setName("");
		GridBagConstraints gbc_comboBox_categories = new GridBagConstraints();
		gbc_comboBox_categories.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_categories.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_categories.gridx = 1;
		gbc_comboBox_categories.gridy = 7;
		getContentPane().add(comboBox_categories, gbc_comboBox_categories);

		JComboBox comboBox_sellers = new JComboBox(GUIAssistant.getSellersParametersFromFile());
		comboBox_sellers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = (String) comboBox_sellers.getSelectedItem();
				System.out.println(temp);
				if (!temp.equals("All Sellers")) {
					guiAssistant.s_seller = temp.substring(0, temp.indexOf(" "));
				} else {
					guiAssistant.s_seller = "";
				}
				System.out.println(guiAssistant.s_seller);
			}
		});
		comboBox_sellers.setName("fa");
		GridBagConstraints gbc_comboBox_sellers = new GridBagConstraints();
		gbc_comboBox_sellers.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_sellers.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_sellers.gridx = 2;
		gbc_comboBox_sellers.gridy = 7;
		getContentPane().add(comboBox_sellers, gbc_comboBox_sellers);

		JRadioButton rdbtnPriceRange = new JRadioButton("Price Range");
		rdbtnPriceRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnPriceRange.isSelected()) {
					txtMax.setEditable(true);
					txtMin.setEditable(true);
				} else if (!rdbtnPriceRange.isSelected()) {
					txtMax.setEditable(false);
					txtMin.setEditable(false);
				}
			}
		});
		GridBagConstraints gbc_rdbtnPriceRange = new GridBagConstraints();
		gbc_rdbtnPriceRange.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnPriceRange.gridx = 3;
		gbc_rdbtnPriceRange.gridy = 7;
		getContentPane().add(rdbtnPriceRange, gbc_rdbtnPriceRange);

		txtMin = new JTextField();
		txtMin.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtMin.isEditable()) {
					String temp = txtMin.getText();
					System.out.println(temp);

					try {
						if (Integer.parseInt(temp) > 100000 || Integer.parseInt(temp) < 0) {
							System.out.println("Please enter valid number");
						} else {
							guiAssistant.lp_lowPrice = temp;
						}
					} catch (NumberFormatException e) {
						System.out.println("please enter valid number");
					}
				}
			}

		});
		txtMin.setEditable(false);
		txtMin.setText("min");
		GridBagConstraints gbc_txtMin = new GridBagConstraints();
		gbc_txtMin.insets = new Insets(0, 0, 5, 5);
		gbc_txtMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMin.gridx = 4;
		gbc_txtMin.gridy = 7;
		getContentPane().add(txtMin, gbc_txtMin);
		txtMin.setColumns(10);

		txtMax = new JTextField();
		txtMax.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtMax.isEditable()) {
					String temp = txtMax.getText();
					System.out.println(temp);
					try {
						if (Integer.parseInt(temp) > 999999
								|| Integer.parseInt(temp) < Integer.parseInt(guiAssistant.lp_lowPrice)) {
							System.out.println("Please enter valid number");
						} else {
							guiAssistant.hp_highPrice = temp;
							System.out.println(guiAssistant.hp_highPrice);
						}
					} catch (NumberFormatException e) {
						System.out.println("please enter valid number");
					}
				}
			}
		});
		txtMax.setEditable(false);
		txtMax.setText("max");
		GridBagConstraints gbc_txtMax = new GridBagConstraints();
		gbc_txtMax.insets = new Insets(0, 0, 5, 5);
		gbc_txtMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMax.gridx = 5;
		gbc_txtMax.gridy = 7;
		getContentPane().add(txtMax, gbc_txtMax);
		txtMax.setColumns(10);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 6;
		gbc_progressBar.gridy = 7;
		getContentPane().add(progressBar, gbc_progressBar);

		chckbxBuyItNow = new JCheckBox("Buy it now only");
		chckbxBuyItNow.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxBuyItNow.isSelected()) {
					guiAssistant.sbn_showBuyItNowOnly = "true";
				} else
					guiAssistant.sbn_showBuyItNowOnly = "false";
			}
		});
		GridBagConstraints gbc_chckbxBuyItNow = new GridBagConstraints();
		gbc_chckbxBuyItNow.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxBuyItNow.gridx = 1;
		gbc_chckbxBuyItNow.gridy = 8;
		getContentPane().add(chckbxBuyItNow, gbc_chckbxBuyItNow);

		JCheckBox chckbxPickUpOnly = new JCheckBox("Pick up only");
		chckbxPickUpOnly.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxPickUpOnly.isSelected()) {
					guiAssistant.spo_showPickupOnly = "true";
				} else
					guiAssistant.spo_showPickupOnly = "false";
			}
		});
		GridBagConstraints gbc_chckbxPickUpOnly = new GridBagConstraints();
		gbc_chckbxPickUpOnly.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPickUpOnly.gridx = 2;
		gbc_chckbxPickUpOnly.gridy = 8;
		getContentPane().add(chckbxPickUpOnly, gbc_chckbxPickUpOnly);

		JCheckBox chckbxExcludePickup = new JCheckBox("Exclude pick-up");
		chckbxExcludePickup.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxExcludePickup.isSelected()) {
					guiAssistant.snpo_hidePickupOnlyItems = "true";
				} else
					guiAssistant.snpo_hidePickupOnlyItems = "false";
			}
		});
		GridBagConstraints gbc_chckbxExcludePickup = new GridBagConstraints();
		gbc_chckbxExcludePickup.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxExcludePickup.gridx = 3;
		gbc_chckbxExcludePickup.gridy = 8;
		getContentPane().add(chckbxExcludePickup, gbc_chckbxExcludePickup);

		JCheckBox chckbxcShippingOnly = new JCheckBox("1c shipping only");
		chckbxcShippingOnly.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxcShippingOnly.isSelected()) {
					guiAssistant.socs_show1CentShippingOnly = "true";
				} else
					guiAssistant.socs_show1CentShippingOnly = "false";
			}
		});
		GridBagConstraints gbc_chckbxcShippingOnly = new GridBagConstraints();
		gbc_chckbxcShippingOnly.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxcShippingOnly.gridx = 4;
		gbc_chckbxcShippingOnly.gridy = 8;
		getContentPane().add(chckbxcShippingOnly, gbc_chckbxcShippingOnly);

		JCheckBox chckbxShippingToCanana = new JCheckBox("Shipping to Canana");
		chckbxShippingToCanana.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxShippingToCanana.isSelected()) {
					guiAssistant.scs_internationalShippingCanada = "true";
				} else
					guiAssistant.scs_internationalShippingCanada = "false";
			}
		});
		GridBagConstraints gbc_chckbxShippingToCanana = new GridBagConstraints();
		gbc_chckbxShippingToCanana.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShippingToCanana.gridx = 5;
		gbc_chckbxShippingToCanana.gridy = 8;
		getContentPane().add(chckbxShippingToCanana, gbc_chckbxShippingToCanana);

		JCheckBox chckbxOutsideOfUs = new JCheckBox("Outside of US & Can.");
		chckbxOutsideOfUs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxOutsideOfUs.isSelected()) {
					guiAssistant.sis_internationalShippingOutsideUSCanada = "true";
				} else
					guiAssistant.sis_internationalShippingOutsideUSCanada = "false";
			}
		});
		GridBagConstraints gbc_chckbxOutsideOfUs = new GridBagConstraints();
		gbc_chckbxOutsideOfUs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOutsideOfUs.gridx = 6;
		gbc_chckbxOutsideOfUs.gridy = 8;
		getContentPane().add(chckbxOutsideOfUs, gbc_chckbxOutsideOfUs);
		
		JButton btnShowImage = new JButton("Show Image");
		btnShowImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!table.getSelectionModel().isSelectionEmpty()) {
					

					
					imageWindow.setVisible(true);
					int selectedRow = table.getSelectedRow();
					String selectedItemNum = (String) myTableModel.getValueAt(selectedRow, 0);
					ArrayList<ArrayList<String>> temp = cusMap.getList("PrimaryKey=ImagesForItemNum="+selectedItemNum);
					if (!temp.isEmpty() && !temp.get(0).get(0).equals("EMPTY")&&!temp.get(0).get(0).equals("")) {
						//ArrayList<String> imageNames <-- search for image names and save
						if (temp.get(0).size()>1) {
							//guiAssistant.imageIndex=0;
							String[] str_imageNames = new String[temp.get(0).size()-1];
							for (int i=1; i<temp.get(0).size(); i++) {
								str_imageNames[i-1] = temp.get(0).get(i);
								System.out.println(temp.get(0).get(i)+" ");
							}
							guiAssistant.imageNamesList = str_imageNames;
						}
					}
					else {
						System.out.println("no images");
					};
				}
			}
		});
		GridBagConstraints gbc_btnShowImage = new GridBagConstraints();
		gbc_btnShowImage.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowImage.gridx = 8;
		gbc_btnShowImage.gridy = 9;
		getContentPane().add(btnShowImage, gbc_btnShowImage);

		JLabel lblNewLabel = new JLabel("Editing Database:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 10;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		JRadioButton rdbtnShowAll = new JRadioButton("Show all");
		GridBagConstraints gbc_rdbtnShowAll = new GridBagConstraints();
		gbc_rdbtnShowAll.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnShowAll.gridx = 1;
		gbc_rdbtnShowAll.gridy = 11;
		getContentPane().add(rdbtnShowAll, gbc_rdbtnShowAll);
		groupQueryButtons.add(rdbtnShowAll);

		GridBagConstraints gbc_rdbtnShowEntriesWith = new GridBagConstraints();
		gbc_rdbtnShowEntriesWith.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnShowEntriesWith.gridx = 2;
		gbc_rdbtnShowEntriesWith.gridy = 11;
		gbc_rdbtnShowEntriesWith.gridwidth = 3;
		getContentPane().add(rdbtnShowEntriesWith, gbc_rdbtnShowEntriesWith);
		groupQueryButtons.add(rdbtnShowEntriesWith);

		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 11;
		gbc_textField.gridwidth = 2;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnLookup = new JButton("Look-up");
		btnLookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnShowEntriesWith.isSelected() && textField.getText().length() != 0) {
					showEntriesContaining(textField.getText(), selector);
				} else {
					showAll();
				}
			}
		});
		GridBagConstraints gbc_btnLookup = new GridBagConstraints();
		gbc_btnLookup.insets = new Insets(0, 0, 5, 5);
		gbc_btnLookup.gridx = 8;
		gbc_btnLookup.gridy = 11;
		getContentPane().add(btnLookup, gbc_btnLookup);
		GridBagConstraints gbc_btnNewEntry = new GridBagConstraints();
		gbc_btnNewEntry.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewEntry.gridx = 1;
		gbc_btnNewEntry.gridy = 12;
		getContentPane().add(btnNewEntry, gbc_btnNewEntry);

		JButton btnUpdate = new JButton("Save Change");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateDatabase();
			}
		});
		btnUpdate.setMinimumSize(new Dimension(91, 25));
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 3;
		gbc_btnUpdate.gridy = 12;
		getContentPane().add(btnUpdate, gbc_btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectionModel().isSelectionEmpty()) {
					System.out.println("Please select a row to delete.");
				} else {
					deleteEntry(myTableModel.getValueAt(table.getSelectedRow(), 0).toString());
					showAll();
				}
			}
		});
		btnDelete.setMinimumSize(new Dimension(91, 25));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 4;
		gbc_btnDelete.gridy = 12;
		getContentPane().add(btnDelete, gbc_btnDelete);

		JButton btnDumpData = new JButton("Dump Data");
		btnDumpData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dumpToFile();
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
			}
		});
		btnDumpData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnDumpData = new GridBagConstraints();
		gbc_btnDumpData.insets = new Insets(0, 0, 0, 5);
		gbc_btnDumpData.gridx = 5;
		gbc_btnDumpData.gridy = 12;
		getContentPane().add(btnDumpData, gbc_btnDumpData);

	}

	// convert ArrayList<ArrayList<String>> to String[][] so that JTable can uses
	// it.
	private String[][] listToString(ArrayList<ArrayList<String>> inList) {
		String[][] newString = new String[inList.size()][];
		for (int i = 0; i < inList.size(); i++) {
			newString[i] = new String[inList.get(i).size()];
			inList.get(i).toArray(newString[i]);
		}
		return newString;
	}

	private void showEntriesContaining(String inKey, String inSelector) {
		if (inSelector.equals("mysql")) {

		} else {
			// dataJTable=listToString(cusMap.getList(inKey)); //deprecated the line below
			// stripes off headers
			dataJTable = CHMAssistant.listTo2DStringAry(header, cusMap.getList(inKey));
			int rowCount = myTableModel.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				myTableModel.removeRow(0);
			}
			for (int i = 0; i < dataJTable.length; i++) {
				myTableModel.addRow(dataJTable[i]);
			}

		}
	}

	private void showAll() {
		if (selector.equals("mysql")) {

		} else {
			// dataJTable=listToString(cusMap.getAllList());
			dataJTable = CHMAssistant.listTo2DStringAry(header, CHMAssistant.filterLists(header, cusMap.getAllList()));
			int rowCount = myTableModel.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				myTableModel.removeRow(0);
			}
			for (int i = 0; i < dataJTable.length; i++) {
				myTableModel.addRow(dataJTable[i]);
			}
		}
	}

	private void deleteEntry(String inPrimaryKey) {
		cusMap.deleteList("PrimaryKey=" + header[0]+"=" + inPrimaryKey);
		logger.writeInfo("Deleted Entry with key: " + inPrimaryKey);

	}

	private void dumpToFile() throws FileNotFoundException {
		outFile = new PrintWriter(dumpFileName);
		dataJTable = listToString(cusMap.getAllList());
		for (int i = 0; i < header.length - 1; i++)
			outFile.print(header[i] + " | ");
		outFile.print(header[header.length - 1]);
		outFile.println("");
		for (int i = 0; i < dataJTable.length; i++) {
			for (int j = 0; j < dataJTable[i].length - 1; j++) {
				outFile.print(dataJTable[i][j] + " | ");
			}
			outFile.print(dataJTable[i][dataJTable[i].length - 1]);
			outFile.println("");
		}
		outFile.close();
		logger.writeInfo("Data dumped to: " + dumpFileName);
	}

	private void newEntry() {
		myTableModel.setRowCount(0);
		String[] rowData = new String[header.length];
		rowData[0] = cusMap.getAvailablePKey();
		for (int i = 1; i < header.length - 1; i++) {
			rowData[i] = "Click to edit";
		}
		rowData[header.length - 1] = CurrentDate;
		myTableModel.addRow(rowData);
	}

	private void updateDatabase() {
		if (selector.equals("mysql")) {
		} else {
			if (table.getSelectionModel().isSelectionEmpty()) {
				System.out.println("Select the row to save");
			} else {
				// check if a older version entry exists, delete if yes.
				if (cusMap.containsKey("PrimaryKey=" + myTableModel.getValueAt(table.getSelectedRow(), 0))) {
					deleteEntry((String) myTableModel.getValueAt(table.getSelectedRow(), 0));
				}
				// create new entry and put in hashmap
				ArrayList<String> newEntry = new ArrayList<String>();
				for (int i = 0; i < header.length; i++) {
					newEntry.add((String) myTableModel.getValueAt(table.getSelectedRow(), i));
				}
				cusMap.putList(newEntry);
				logger.writeInfo("Updated Entry with key: " + myTableModel.getValueAt(table.getSelectedRow(), 0));
				showAll();

			}
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
