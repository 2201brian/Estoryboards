package interlentisimo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

import Classes.TextPrompt;
import Classes.TextPrompt.Show;
import Classes.verification;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import Classes.TextPrompt;

public class sedesList extends JFrame
{

	private JTextField id;
	private final String SQL_SELECT_SEDE = "select * from sede";
	private final String SQL_SELECT_USERS = "select * from usuarios";

	private JButton volverBtn, buscarIdBtn,listarBtn;
	private JComboBox<String> categoriaJCB;
	private JPanel consolePanel,consoleListPanel,tablePanel, btnPanel;
	private JTable tabla;
	private JScrollPane scrollPane;
	private JPanel mainPanel;
	
	private String from;

	/**
	 * Create the application.
	 */
	public sedesList(String idUser, String remitente) {
		setSize(800,600);
		setTitle("Listados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setVisible(true);
		initialize(idUser);
		from = remitente;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String idUser) 
	{
			
		mainPanel = new JPanel();
		mainPanel.setSize(700, 500);
		mainPanel.setLocation(((this.getWidth()/2)-(mainPanel.getWidth()/2)),
				              ((int)Math.round((this.getHeight()/2)-(mainPanel.getHeight()/2))));
		mainPanel.setLayout(null);
		//mainPanel.setBackground(Color.green);
		mainPanel.setVisible(true);
		this.add(mainPanel);
		
		JLabel tituloLbl = new JLabel("Consultas",JLabel.CENTER);
		tituloLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
		tituloLbl.setForeground(new Color(255, 69, 0));
		tituloLbl.setSize(205, 15);
		tituloLbl.setLocation((mainPanel.getWidth()/2-tituloLbl.getWidth()/2), 10);
		mainPanel.add(tituloLbl);
		
		JLabel infoLbl = new JLabel("Busque el id o liste por categor�a ",JLabel.CENTER);
		infoLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		infoLbl.setForeground(new Color(255, 69, 0));
		infoLbl.setSize(205, 15);
		infoLbl.setLocation((mainPanel.getWidth()/2-tituloLbl.getWidth()/2), 32);
		mainPanel.add(infoLbl);
		
		/**
		 * Panel de consola para especificaci[on de la tabla
		 */
		consolePanel = new JPanel();
		consolePanel.setBounds(0,70,400,35);
		//consolePanel.setBackground(Color.BLUE);
		consolePanel.setLayout(new FlowLayout());
		mainPanel.add(consolePanel);
		
		/**
		 * Campo identificador de sede
		 */
		
		JLabel idLbl = new JLabel("Categoria:",JLabel.CENTER);
		idLbl.setSize(50, 14);
		idLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		consolePanel.add(idLbl);
		
		/*JComboBox para la seleccion de la categoria a ser listada */
		categoriaJCB = new JComboBox<String>();
		categoriaJCB.setBounds(111, 210, 153, 22);
		categoriaJCB.addItem("Usuarios");
		categoriaJCB.addItem("Sedes");
		categoriaJCB.addItem("�rdenes");
		consolePanel.add(categoriaJCB);
		
		id = new JTextField();
		id.setColumns(10);
		id.setSize(175, 20);
		TextPrompt idPh = new TextPrompt("Identificador",id,Show.ALWAYS);
		idPh.changeAlpha(0.75f);
		consolePanel.add(id);
		
		// Boton de Consulta de un identificador particular
		buscarIdBtn = new JButton("Consultar");
		buscarIdBtn.setSize(80,22);
		buscarIdBtn.setBackground(new Color(255, 69, 0));
		buscarIdBtn.setForeground(new Color(255, 255, 255));
		buscarIdBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		buscarIdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String categoriaSeleccionada = (String) categoriaJCB.getSelectedItem();
				try {	
					listarCategorias(categoriaSeleccionada,id.getText());						
				}catch(Exception e1){
					e1.printStackTrace();
				}
				//listarCategorias(categoriaSeleccionada);
			}
			
		});
		consolePanel.add(buscarIdBtn);
		
		
		/*
		 * JPanel para el jtable
		 */
		tablePanel = new JPanel();
		tablePanel.setBounds(0,130,700,350);
		tablePanel.setBackground(Color.GRAY);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setVisible(true);
		mainPanel.add(tablePanel);
		
		tabla = new JTable();
		tablePanel.add(tabla);
		scrollPane = new JScrollPane(tabla);
		tablePanel.add(scrollPane,BorderLayout.CENTER);
		
		
		/*
		 * Boton volver
		 */
		
		btnPanel = new JPanel();
		btnPanel.setBounds(0, 550, 200, 50);
		//btnPanel.setBackground(Color.blue);
		btnPanel.setLayout(new FlowLayout());
		btnPanel.setVisible(true);
		tablePanel.add(btnPanel,BorderLayout.SOUTH);
		
		JButton volverBtn = new JButton("VOLVER");
		volverBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		volverBtn.setSize(95, 36);
		volverBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
				
				if(from.equals("users")) {
					usersMenu menu_u = new usersMenu(idUser);
				}
				else if(from.equals("sedes")) {
					sedesMenu sedemenu = new sedesMenu(idUser);
				}
				
				
			}
		});
		btnPanel.add(volverBtn);
		/*
		JButton descargarBtn = new JButton("Descargar");
		descargarBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descargarBtn.setSize(95, 36);
		descargarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
				sedesMenu sedemenu = new sedesMenu(idUser);
			}
		});
		btnPanel.add(descargarBtn);*/
	}
	
	private void listarCategorias(String categoria, String identificador) 
	{
		ControlBase control = new ControlBase();
		try 
		{
			DefaultTableModel modeloBaseDatos;
			modeloBaseDatos= control.getDatos(categoria,identificador);
			//DefaultTableModel modeloBaseDatos = control.getDatos(categoria);
			tabla.setModel(modeloBaseDatos);
			int cantidadColumnas = modeloBaseDatos.getColumnCount();
			int i=0;
			while(i<cantidadColumnas) 
			{
				tabla.getColumnModel().getColumn(i).setPreferredWidth(100);
				i++;
			}
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	
}