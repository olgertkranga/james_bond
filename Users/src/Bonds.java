import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.swing.JRadioButton;

public class Bonds {

	private JFrame frmCurdOperationSwing;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtBook;
	private JTextField txtPurchasePrice;
	private JTextField txtSellPrice;
	private JTextField txtPurchaseDate;
	private JTextField txtSellDate;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bonds window = new Bonds();
					window.frmCurdOperationSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Bonds() {
		initialize();
		Connect();
		loadData();
	}

	/***
	 * 
	 * @param doc
	 * @param name
	 * @param address
	 * @param contactNo
	 * @param email
	 * @throws Exception
	 */
	public static void createXmlFile(Document doc, String name, String book, String purchasePrice, String sellPrice,
			String purchaseDate, String sellDate) throws Exception {

		Element root = doc.createElement("Bond");
		doc.appendChild(root);
		Element element1 = doc.createElement("Name");
		root.appendChild(element1);
		Text text1 = doc.createTextNode(name);
		element1.appendChild(text1);

		Element element2 = doc.createElement("Book");
		root.appendChild(element2);
		Text text2 = doc.createTextNode(book);
		element2.appendChild(text2);

		Element element3 = doc.createElement("PurchasePrice");
		root.appendChild(element3);
		Text text3 = doc.createTextNode(purchasePrice);
		element3.appendChild(text3);

		Element element4 = doc.createElement("SellPrice");
		root.appendChild(element4);
		Text text4 = doc.createTextNode(sellPrice);
		element4.appendChild(text4);

		Element element5 = doc.createElement("PurchaseDate");
		root.appendChild(element5);
		Text text5 = doc.createTextNode(purchaseDate);
		element5.appendChild(text5);

		Element element6 = doc.createElement("SellDate");
		root.appendChild(element6);
		Text text6 = doc.createTextNode(sellDate);
		element6.appendChild(text6);

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		String xmlString = sw.toString();

		File file = new File("bond.xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
		bw.write(xmlString);
		bw.flush();
		bw.close();
	}

	// Database Connection
	Connection con = null;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/dbjoes?characterEncoding=utf8";
			String username = "root";
			String password = "root";
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Clear All
	public void clear() {
		txtID.setText("");
		txtName.setText("");
		txtBook.setText("");
		txtPurchasePrice.setText("");
		txtSellPrice.requestFocus();
		txtPurchaseDate.requestFocus();
		txtSellDate.requestFocus();
	}

	// Load Table
	public void loadData() {
		try {
			pst = con.prepareStatement("Select * from bonds order by id");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCurdOperationSwing = new JFrame();
		frmCurdOperationSwing.setTitle("Bond operations from Olegs");
		frmCurdOperationSwing.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmCurdOperationSwing.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Bond Management System");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 259, 60);
		frmCurdOperationSwing.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(20, 71, 387, 584);
		frmCurdOperationSwing.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(21, 46, 46, 24);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);

		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(21, 81, 46, 24);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Book");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(21, 116, 46, 24);
		panel.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("PPrice");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(21, 154, 46, 24);
		panel.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("SPrice");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_4.setBounds(21, 185, 46, 24);
		panel.add(lblNewLabel_1_4);

		JLabel lblNewLabel_1_5 = new JLabel("PDate");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_5.setBounds(21, 215, 46, 24);
		panel.add(lblNewLabel_1_5);

		JLabel lblNewLabel_1_6 = new JLabel("SDate");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_6.setBounds(21, 255, 46, 24);
		panel.add(lblNewLabel_1_6);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtID.setBounds(78, 46, 287, 24);
		panel.add(txtID);
		txtID.setColumns(10);
		txtID.setVisible(false);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.setBounds(78, 81, 287, 24);
		panel.add(txtName);

		txtBook = new JTextField();
		txtBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBook.setColumns(10);
		txtBook.setBounds(78, 116, 287, 24);
		panel.add(txtBook);

		txtPurchasePrice = new JTextField();
		txtPurchasePrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPurchasePrice.setColumns(10);
		txtPurchasePrice.setBounds(78, 154, 287, 24);
		panel.add(txtPurchasePrice);

		txtSellPrice = new JTextField();
		txtSellPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSellPrice.setColumns(10);
		txtSellPrice.setBounds(78, 185, 287, 24);
		panel.add(txtSellPrice);

		txtPurchaseDate = new JTextField();
		txtPurchaseDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPurchaseDate.setColumns(10);
		txtPurchaseDate.setBounds(78, 215, 287, 24);
		panel.add(txtPurchaseDate);

		txtSellDate = new JTextField();
		txtSellDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSellDate.setColumns(10);
		txtSellDate.setBounds(78, 255, 287, 24);
		panel.add(txtSellDate);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtID.getText();
				String name = txtName.getText();
				String book = txtBook.getText();
				String purchasePrice = txtPurchasePrice.getText();
				String sellPrice = txtSellPrice.getText();
				String purchaseDate = txtPurchaseDate.getText();
				String sellDate = txtSellDate.getText();

				if (name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					txtName.requestFocus();
					return;
				}
				if (book == null || book.isEmpty() || book.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Book");
					txtBook.requestFocus();
					return;
				}
				if (purchasePrice == null || purchasePrice.isEmpty() || purchasePrice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Purchase Price");
					txtPurchasePrice.requestFocus();
					return;
				}
				if (sellPrice == null || sellPrice.isEmpty() || sellPrice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Sell Price");
					txtSellPrice.requestFocus();
					return;
				}
				if (purchaseDate == null || purchaseDate.isEmpty() || purchaseDate.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Purchase Date");
					txtPurchaseDate.requestFocus();
					return;
				}
				if (sellDate == null || sellDate.isEmpty() || sellDate.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Sell Date");
					txtSellDate.requestFocus();
					return;
				}

				if (txtID.getText().isEmpty()) {
					try {
						String sql = "insert into bonds (NAME,BOOK,PURCHASE_PRICE,SELL_PRICE,PURCHASE_DATE,SELL_DATE) values (?,?,?,?,?,?)";
						pst = con.prepareStatement(sql);
						pst.setString(1, name);
						pst.setString(2, book);
						pst.setString(3, purchasePrice);
						pst.setString(4, sellPrice);
						pst.setString(5, purchaseDate);
						pst.setString(6, sellDate);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Data insert Success");
						clear();
						loadData();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(78, 285, 89, 23);
		panel.add(btnSave);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Update Details
				String id = txtID.getText();
				String name = txtName.getText();
				String book = txtBook.getText();
				String purchasePrice = txtPurchasePrice.getText();
				String sellPrice = txtSellPrice.getText();
				String purchaseDate = txtPurchaseDate.getText();
				String sellDate = txtSellDate.getText();

				if (name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					txtName.requestFocus();
					return;
				}
				if (book == null || book.isEmpty() || book.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Book");
					txtBook.requestFocus();
					return;
				}
				if (purchasePrice == null || purchasePrice.isEmpty() || purchasePrice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Purchase Price");
					txtPurchasePrice.requestFocus();
					return;
				}
				if (sellPrice == null || sellPrice.isEmpty() || sellPrice.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Sell Price");
					txtSellPrice.requestFocus();
					return;
				}
				if (purchaseDate == null || purchaseDate.isEmpty() || purchaseDate.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Purchase Date");
					txtPurchaseDate.requestFocus();
					return;
				}
				if (sellDate == null || sellDate.isEmpty() || sellDate.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Sell Date");
					txtSellDate.requestFocus();
					return;
				}

				if (!txtID.getText().isEmpty()) {
					try {
						String sql = "update bonds set NAME=?,BOOK=?,PURCHASE_PRICE=?,SELL_PRICE=?,PURCHASE_DATE=?,SELL_DATE=? where ID=?";
						pst = con.prepareStatement(sql);
						pst.setString(1, name);
						pst.setString(2, book);
						pst.setString(3, purchasePrice);
						pst.setString(4, sellPrice);
						pst.setString(5, purchaseDate);
						pst.setString(6, sellDate);
						pst.setString(7, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Data Update Success");
						clear();
						loadData();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(177, 285, 89, 23);
		panel.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Delete Details
				String id = txtID.getText();
				if (!txtID.getText().isEmpty()) {

					int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {
						try {
							String sql = "delete from bonds where ID=?";
							pst = con.prepareStatement(sql);
							pst.setString(1, id);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Data Deleted Success");
							clear();
							loadData();

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}

			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(276, 285, 89, 23);
		panel.add(btnDelete);

		JButton btnCreateXML = new JButton("Create the file");
		btnCreateXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Delete Details
				String id = txtID.getText();
				if (!txtID.getText().isEmpty()) {

					int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Generate new XML?", "No",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {

						String name = txtName.getText();
						String book = txtBook.getText();
						String purchasePrice = txtPurchasePrice.getText();
						String sellPrice = txtSellPrice.getText();
						String purchaseDate = txtPurchaseDate.getText();
						String sellDate = txtSellDate.getText();

						try {
							DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
							DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
							Document doc = docBuilder.newDocument();
							createXmlFile(doc, name, book, purchasePrice, sellPrice, purchaseDate, sellDate);
							System.out.println("Xml File Created Successfully");
						} catch (Exception E) {
						}

					}
				}

			}
		});
		btnCreateXML.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateXML.setBounds(78, 325, 189, 23);
		btnCreateXML.setVisible(true);
		panel.add(btnCreateXML);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(417, 71, 900, 584);
		frmCurdOperationSwing.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = table.getSelectedRow();
				TableModel model = table.getModel();
				// ID NAME AGE CITY
				txtID.setText(model.getValueAt(index, 0).toString());
				txtName.setText(model.getValueAt(index, 1).toString());
				txtBook.setText(model.getValueAt(index, 2).toString());
				txtPurchasePrice.setText(model.getValueAt(index, 3).toString());
				txtSellPrice.setText(model.getValueAt(index, 4).toString());
				txtPurchaseDate.setText(model.getValueAt(index, 5).toString());
				txtSellDate.setText(model.getValueAt(index, 6).toString());
			}
		});
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		frmCurdOperationSwing.setBounds(100, 100, 910, 600);
		frmCurdOperationSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}